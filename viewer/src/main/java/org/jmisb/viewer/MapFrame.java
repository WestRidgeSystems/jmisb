package org.jmisb.viewer;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JFrame;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.st0601.CornerOffset;
import org.jmisb.api.klv.st0601.UasDatalinkLatitude;
import org.jmisb.api.klv.st0601.UasDatalinkLongitude;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0601.UasDatalinkTag;
import org.jmisb.api.video.IMetadataListener;
import org.jmisb.api.video.MetadataFrame;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

/**
 * Geospatial (map) view for the Viewer application.
 *
 * <p>This is intended to provided context for the video view.
 */
public class MapFrame implements IMetadataListener {

    private final JFrame frame;
    private final JXMapViewer mapViewer;
    private final List<GeoPosition> corners = new ArrayList<>();
    private final List<GeoPosition> sensorLocations = new ArrayList<>();
    private final Collection<UasDatalinkTag> CORNER_OFFSET_TAGS = new ArrayList<>();

    /**
     * Constructor.
     *
     * <p>This creates a new map frame, enables default map layers, but does not show the map.
     */
    public MapFrame() {
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLatitudePoint1);
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLatitudePoint2);
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLatitudePoint3);
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLatitudePoint4);
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLongitudePoint1);
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLongitudePoint2);
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLongitudePoint3);
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLongitudePoint4);
        mapViewer = new JXMapViewer();

        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Use 8 threads in parallel to load the tiles
        tileFactory.setThreadPoolSize(8);

        // Set the focus - maybe read from a resource?
        GeoPosition initialPosition = new GeoPosition(-34.0, 143.0);
        mapViewer.setZoom(7);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        mapViewer.setAddressLocation(initialPosition);

        SensorFootprintPainter sensorFootprintPainter = new SensorFootprintPainter(corners);
        SensorLocationPainter sensorLocationPainter =
                new SensorLocationPainter(sensorLocations, corners);
        List<Painter<JXMapViewer>> painters = new ArrayList<>();
        painters.add(sensorFootprintPainter);
        painters.add(sensorLocationPainter);
        CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
        mapViewer.setOverlayPainter(painter);

        // Display the viewer in a JFrame
        frame = new JFrame("jMISB Viewer Map");
        frame.getContentPane().add(mapViewer);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(false);
    }

    /**
     * Set whether the map frame is currently visible.
     *
     * @param state true to make map frame visible, false to hide it.
     */
    void setVisible(boolean state) {
        frame.setVisible(state);
    }
    /**
     * Clear any left-over values.
     *
     * <p>This is intended to reset the state on file change.
     */
    void clear() {
        corners.clear();
    }

    @Override
    public void onMetadataReceived(MetadataFrame metadataFrame) {
        IMisbMessage message = metadataFrame.getMisbMessage();
        if (message instanceof UasDatalinkMessage) {
            onST0601MedataReceived((UasDatalinkMessage) message);
        }
    }

    private void onST0601MedataReceived(UasDatalinkMessage uasDatalinkMessage) {
        UasDatalinkLatitude frameCentreLatitude =
                (UasDatalinkLatitude)
                        uasDatalinkMessage.getField(UasDatalinkTag.FrameCenterLatitude);
        UasDatalinkLongitude frameCentreLongitude =
                (UasDatalinkLongitude)
                        uasDatalinkMessage.getField(UasDatalinkTag.FrameCenterLongitude);
        if (frameCentreLatitude != null && frameCentreLongitude != null) {
            GeoPosition frameCentre =
                    new GeoPosition(
                            frameCentreLatitude.getDegrees(), frameCentreLongitude.getDegrees());
            mapViewer.setAddressLocation(frameCentre);
            updateCornerPoints(uasDatalinkMessage, frameCentre);
        }
        updateSensorPosition(uasDatalinkMessage);
    }

    private void updateSensorPosition(UasDatalinkMessage uasDatalinkMessage) {
        UasDatalinkLatitude sensorLatitude =
                (UasDatalinkLatitude) uasDatalinkMessage.getField(UasDatalinkTag.SensorLatitude);
        UasDatalinkLongitude sensorLongitude =
                (UasDatalinkLongitude) uasDatalinkMessage.getField(UasDatalinkTag.SensorLongitude);
        if ((sensorLatitude != null) && (sensorLongitude != null)) {
            GeoPosition sensorPosition =
                    new GeoPosition(sensorLatitude.getDegrees(), sensorLongitude.getDegrees());
            sensorLocations.clear();
            sensorLocations.add(sensorPosition);
        }
    }

    private void updateCornerPoints(
            UasDatalinkMessage uasDatalinkMessage, GeoPosition frameCentre) {
        List<GeoPosition> tempCorners = new ArrayList<>();
        if (uasDatalinkMessage.getTags().containsAll(CORNER_OFFSET_TAGS)) {
            tempCorners.add(
                    getCornerFromOffsets(
                            uasDatalinkMessage,
                            UasDatalinkTag.OffsetCornerLatitudePoint1,
                            UasDatalinkTag.OffsetCornerLongitudePoint1,
                            frameCentre));
            tempCorners.add(
                    getCornerFromOffsets(
                            uasDatalinkMessage,
                            UasDatalinkTag.OffsetCornerLatitudePoint2,
                            UasDatalinkTag.OffsetCornerLongitudePoint2,
                            frameCentre));
            tempCorners.add(
                    getCornerFromOffsets(
                            uasDatalinkMessage,
                            UasDatalinkTag.OffsetCornerLatitudePoint3,
                            UasDatalinkTag.OffsetCornerLongitudePoint3,
                            frameCentre));
            tempCorners.add(
                    getCornerFromOffsets(
                            uasDatalinkMessage,
                            UasDatalinkTag.OffsetCornerLatitudePoint4,
                            UasDatalinkTag.OffsetCornerLongitudePoint4,
                            frameCentre));
            corners.clear();
            corners.addAll(tempCorners);
        }
    }

    private GeoPosition getCornerFromOffsets(
            UasDatalinkMessage uasDatalinkMessage,
            UasDatalinkTag latTag,
            UasDatalinkTag lonTag,
            GeoPosition frameCentre) {
        CornerOffset cornerOffsetLat = (CornerOffset) uasDatalinkMessage.getField(latTag);
        CornerOffset cornerOffsetLon = (CornerOffset) uasDatalinkMessage.getField(lonTag);
        if (cornerOffsetLat != null && cornerOffsetLon != null) {
            GeoPosition geoPosition =
                    new GeoPosition(
                            frameCentre.getLatitude() + cornerOffsetLat.getDegrees(),
                            frameCentre.getLongitude() + cornerOffsetLon.getDegrees());
            return geoPosition;
        } else {
            return null;
        }
    }

    void addComponentListener(ComponentListener listener) {
        frame.addComponentListener(listener);
    }

    boolean isMatchingComponent(ComponentEvent ce) {
        return (ce.getComponent().equals(frame));
    }
}

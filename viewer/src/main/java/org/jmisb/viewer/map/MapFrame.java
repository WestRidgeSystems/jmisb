package org.jmisb.viewer.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputListener;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.video.IMetadataListener;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.st0601.CornerOffset;
import org.jmisb.st0601.FullCornerLatitude;
import org.jmisb.st0601.FullCornerLongitude;
import org.jmisb.st0601.UasDatalinkLatitude;
import org.jmisb.st0601.UasDatalinkLongitude;
import org.jmisb.st0601.UasDatalinkMessage;
import org.jmisb.st0601.UasDatalinkTag;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Geospatial (map) view for the Viewer application.
 *
 * <p>This is intended to provided context for the video view.
 */
public class MapFrame implements IMetadataListener {

    private static final String APPNAME = "jmisb_map";
    private static final String APPAUTHOR = "jmisb";
    private static final String CONFIG_FILE_NAME = "configuration.properties";

    private static final Logger LOG = LoggerFactory.getLogger(MapFrame.class);
    Properties configurationProperties = new Properties();
    private final JFrame frame;
    private final JXMapViewer mapViewer;
    private JCheckBox autoFollowCheckBox;
    private final List<GeoPosition> corners = new ArrayList<>();
    private final List<GeoPosition> sensorLocations = new ArrayList<>();
    private static final Collection<UasDatalinkTag> CORNER_OFFSET_TAGS = new ArrayList<>();
    private static final Collection<UasDatalinkTag> FULL_CORNER_TAGS = new ArrayList<>();

    static {
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLatitudePoint1);
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLatitudePoint2);
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLatitudePoint3);
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLatitudePoint4);
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLongitudePoint1);
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLongitudePoint2);
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLongitudePoint3);
        CORNER_OFFSET_TAGS.add(UasDatalinkTag.OffsetCornerLongitudePoint4);
        FULL_CORNER_TAGS.add(UasDatalinkTag.CornerLatPt1);
        FULL_CORNER_TAGS.add(UasDatalinkTag.CornerLatPt2);
        FULL_CORNER_TAGS.add(UasDatalinkTag.CornerLatPt3);
        FULL_CORNER_TAGS.add(UasDatalinkTag.CornerLatPt4);
        FULL_CORNER_TAGS.add(UasDatalinkTag.CornerLonPt1);
        FULL_CORNER_TAGS.add(UasDatalinkTag.CornerLonPt2);
        FULL_CORNER_TAGS.add(UasDatalinkTag.CornerLonPt3);
        FULL_CORNER_TAGS.add(UasDatalinkTag.CornerLonPt4);
    }

    /**
     * Factory "constructor".
     *
     * <p>This creates a new map frame, enables default map layers, but does not show the map.
     *
     * @return Initialized map frame.
     */
    public static MapFrame create() {
        MapFrame mapFrame = new MapFrame();
        mapFrame.readConfiguration();
        mapFrame.initialise();
        return mapFrame;
    }

    /**
     * Private constructor.
     *
     * <p>Use the factory method instead.
     */
    private MapFrame() {
        frame = new JFrame("jmisb Viewer Map");
        mapViewer = new JXMapViewer();
    }

    private void readConfiguration() {
        AppDirs appDirs = AppDirsFactory.getInstance();
        File configDirectory = new File(appDirs.getUserConfigDir(APPNAME, null, APPAUTHOR, true));
        if (configDirectory.exists() || configDirectory.mkdirs()) {
            LOG.info(
                    "Trying "
                            + configDirectory.getAbsolutePath()
                            + " for a map configuration location");
            File userConfigurationFile = new File(configDirectory, CONFIG_FILE_NAME);
            if (userConfigurationFile.exists()) {
                try (InputStream userConfigurationInputStream =
                                new FileInputStream(userConfigurationFile);
                        Reader userConfigurationReader =
                                new InputStreamReader(
                                        userConfigurationInputStream, StandardCharsets.UTF_8)) {
                    configurationProperties.load(userConfigurationReader);
                } catch (IOException ex) {
                    LOG.warn(
                            "Failed to parse user configuration from "
                                    + userConfigurationFile.getAbsolutePath());
                }
            } else {
                LOG.info("No configuration file found, creating from default");
                try {
                    InputStream defaultConfigurationInputStream =
                            MapFrame.class.getResourceAsStream("/" + CONFIG_FILE_NAME);
                    Files.copy(
                            defaultConfigurationInputStream,
                            userConfigurationFile.toPath(),
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    LOG.warn("Failed to create user configuration file from defaults.");
                }
            }
        } else {
            LOG.warn(
                    "Could not set up "
                            + configDirectory.getAbsolutePath()
                            + " as a map configuration location, using defaults");
        }
        if (configurationProperties.isEmpty()) {
            try (InputStream defaultConfigurationInputStream =
                    MapFrame.class.getResourceAsStream("/" + CONFIG_FILE_NAME)) {
                configurationProperties.load(defaultConfigurationInputStream);
            } catch (IOException ex) {
                LOG.warn("Failed to parse default configuration from internal resource.");
            }
        }
    }

    private void initialise() {
        // Create a TileFactoryInfo for OpenStreetMap
        String mapUrl =
                configurationProperties.getProperty("mapUrl", "http://tile.openstreetmap.org");
        TileFactoryInfo info = new OSMTileFactoryInfo("Base Map", mapUrl);
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);
        int numTileThreads =
                Integer.parseInt(configurationProperties.getProperty("numTileThreads", "8"));
        tileFactory.setThreadPoolSize(numTileThreads);
        double intialLat =
                Double.parseDouble(configurationProperties.getProperty("defaultCentreLat", "0.0"));
        double intialLon =
                Double.parseDouble(configurationProperties.getProperty("defaultCentreLon", "0.0"));
        GeoPosition initialPosition = new GeoPosition(intialLat, intialLon);
        int initialZoom =
                Integer.parseInt(configurationProperties.getProperty("defaultZoom", "16"));
        mapViewer.setZoom(initialZoom);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        mapViewer.setAddressLocation(initialPosition);
        MouseInputListener mouseInputListener = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mouseInputListener);
        mapViewer.addMouseMotionListener(mouseInputListener);
        addOverlayPainters();

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(mapViewer, BorderLayout.CENTER);
        JPanel statusPanel = new JPanel();
        contentPane.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 24));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        autoFollowCheckBox = new JCheckBox("Follow Sensor FoV");
        autoFollowCheckBox.setToolTipText(
                "Scale and centre map on the sensor field of view (full or offset corner coordinates)");
        autoFollowCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(autoFollowCheckBox);
        frame.getContentPane().add(contentPane);
        int frameWidth = Integer.parseInt(configurationProperties.getProperty("frameWidth", "800"));
        int frameHeight =
                Integer.parseInt(configurationProperties.getProperty("frameHeight", "600"));
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(false);
    }

    private void addOverlayPainters() {
        int footprintColourCode =
                Integer.parseInt(
                        configurationProperties.getProperty("footprintStrokeColour", "00FF00"), 16);
        Color footprintColour = new Color(footprintColourCode);
        SensorFootprintPainter sensorFootprintPainter =
                new SensorFootprintPainter(corners, footprintColour);
        int sensorColourCode =
                Integer.parseInt(
                        configurationProperties.getProperty("sensorStrokeColour", "00FF00"), 16);
        Color sensorColour = new Color(sensorColourCode);
        SensorLocationPainter sensorLocationPainter =
                new SensorLocationPainter(sensorLocations, corners, sensorColour);
        List<Painter<JXMapViewer>> painters = new ArrayList<>();
        painters.add(sensorFootprintPainter);
        painters.add(sensorLocationPainter);
        CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
        mapViewer.setOverlayPainter(painter);
    }

    /**
     * Set whether the map frame is currently visible.
     *
     * @param state true to make map frame visible, false to hide it.
     */
    public void setVisible(boolean state) {
        frame.setVisible(state);
    }

    /**
     * Clear any left-over values.
     *
     * <p>This is intended to reset the state on file change.
     */
    public void clear() {
        corners.clear();
        mapViewer.repaint();
    }

    @Override
    public void onMetadataReceived(MetadataFrame metadataFrame) {
        IMisbMessage message = metadataFrame.getMisbMessage();
        if (message instanceof UasDatalinkMessage) {
            onST0601MetadataReceived((UasDatalinkMessage) message);
        }
        if (message instanceof org.jmisb.mimd.v1.st1903.MIMD) {
            onMIMDReceived((org.jmisb.mimd.v1.st1903.MIMD) message);
        }
        if (message instanceof org.jmisb.mimd.v2.st1903.MIMD) {
            onMIMD2Received((org.jmisb.mimd.v2.st1903.MIMD) message);
        }
        try {
            mapViewer.repaint();
        } catch (Exception ex) {
            LOG.error("map viewer problem: " + ex.toString());
        }
    }

    private void onST0601MetadataReceived(UasDatalinkMessage uasDatalinkMessage) {
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
            if (autoFollowCheckBox.isSelected()) {
                mapViewer.setAddressLocation(frameCentre);
            }
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
        } else if (uasDatalinkMessage.getTags().containsAll(FULL_CORNER_TAGS)) {
            tempCorners.add(
                    getCorner(
                            uasDatalinkMessage,
                            UasDatalinkTag.CornerLatPt1,
                            UasDatalinkTag.CornerLonPt1));
            tempCorners.add(
                    getCorner(
                            uasDatalinkMessage,
                            UasDatalinkTag.CornerLatPt2,
                            UasDatalinkTag.CornerLonPt2));
            tempCorners.add(
                    getCorner(
                            uasDatalinkMessage,
                            UasDatalinkTag.CornerLatPt3,
                            UasDatalinkTag.CornerLonPt3));
            tempCorners.add(
                    getCorner(
                            uasDatalinkMessage,
                            UasDatalinkTag.CornerLatPt4,
                            UasDatalinkTag.CornerLonPt4));
        }
        if (!tempCorners.isEmpty()) {
            corners.clear();
            corners.addAll(tempCorners);
            if (autoFollowCheckBox.isSelected()) {
                // TODO: another option?
                mapViewer.zoomToBestFit(new HashSet<>(corners), 0.7);
            }
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

    private GeoPosition getCorner(
            UasDatalinkMessage uasDatalinkMessage, UasDatalinkTag latTag, UasDatalinkTag lonTag) {
        FullCornerLatitude cornerLat = (FullCornerLatitude) uasDatalinkMessage.getField(latTag);
        FullCornerLongitude cornerLon = (FullCornerLongitude) uasDatalinkMessage.getField(lonTag);
        if (cornerLat != null && cornerLon != null) {
            GeoPosition geoPosition =
                    new GeoPosition(cornerLat.getDegrees(), cornerLon.getDegrees());
            return geoPosition;
        } else {
            return null;
        }
    }

    private void onMIMDReceived(org.jmisb.mimd.v1.st1903.MIMD mimd) {
        // TODO: find sensor location
        org.jmisb.mimd.v1.st1903.MIMD_Platforms platforms = mimd.getPlatforms();
        if (platforms == null) {
            return;
        }
        for (IKlvKey platformKey : platforms.getIdentifiers()) {
            org.jmisb.mimd.v1.st1905.Platform platform =
                    (org.jmisb.mimd.v1.st1905.Platform) platforms.getField(platformKey);
            processMIMDStages(platform);
            processPayloads(platform);
        }
    }

    private void onMIMD2Received(org.jmisb.mimd.v2.st1903.MIMD mimd) {
        // TODO: find sensor location
        org.jmisb.mimd.v2.st1903.MIMD_Platforms platforms = mimd.getPlatforms();
        if (platforms == null) {
            return;
        }
        for (IKlvKey platformKey : platforms.getIdentifiers()) {
            org.jmisb.mimd.v2.st1905.Platform platform =
                    (org.jmisb.mimd.v2.st1905.Platform) platforms.getField(platformKey);
            processMIMDStages(platform);
            processPayloads(platform);
        }
    }

    private void processPayloads(org.jmisb.mimd.v1.st1905.Platform platform) {
        org.jmisb.mimd.v1.st1905.Platform_Payloads payloads = platform.getPayloads();
        if (payloads == null) {
            return;
        }
        for (IKlvKey payloadKey : payloads.getIdentifiers()) {
            org.jmisb.mimd.v1.st1907.Payload payload =
                    (org.jmisb.mimd.v1.st1907.Payload) payloads.getField(payloadKey);
            org.jmisb.mimd.v1.st1907.Payload_GeoIntelligenceSensors geoIntelligenceSensors =
                    payload.getGeoIntelligenceSensors();
            if (geoIntelligenceSensors == null) {
                continue;
            }
            for (IKlvKey geoIntelligenceSensorKey : geoIntelligenceSensors.getIdentifiers()) {
                org.jmisb.mimd.v1.st1907.GeoIntelligenceSensor geoIntelligenceSensor =
                        (org.jmisb.mimd.v1.st1907.GeoIntelligenceSensor)
                                geoIntelligenceSensors.getField(geoIntelligenceSensorKey);
                org.jmisb.mimd.v1.st1907.GeoIntelligenceSensor_CorrespondenceGroups
                        correspondenceGroups = geoIntelligenceSensor.getCorrespondenceGroups();
                if (correspondenceGroups == null) {
                    continue;
                }
                for (IKlvKey correspondenceGroupKey : correspondenceGroups.getIdentifiers()) {
                    org.jmisb.mimd.v1.st1907.CorrespondenceGroup correspondenceGroup =
                            (org.jmisb.mimd.v1.st1907.CorrespondenceGroup)
                                    correspondenceGroups.getField(correspondenceGroupKey);
                    if ((correspondenceGroup.getType()
                                    == org.jmisb.mimd.v1.st1907.CorrespondenceGroupType.Footprint)
                            && (correspondenceGroup.getRectangle() != null)
                            && (correspondenceGroup.getCentroid() != null)) {
                        updateFootprint(
                                correspondenceGroup.getRectangle(),
                                correspondenceGroup.getCentroid());
                    }
                }
            }
        }
    }

    private void processPayloads(org.jmisb.mimd.v2.st1905.Platform platform) {
        org.jmisb.mimd.v2.st1905.Platform_Payloads payloads = platform.getPayloads();
        if (payloads == null) {
            return;
        }
        for (IKlvKey payloadKey : payloads.getIdentifiers()) {
            org.jmisb.mimd.v2.st1907.Payload payload =
                    (org.jmisb.mimd.v2.st1907.Payload) payloads.getField(payloadKey);
            org.jmisb.mimd.v2.st1907.Payload_GeoIntelligenceSensors geoIntelligenceSensors =
                    payload.getGeoIntelligenceSensors();
            if (geoIntelligenceSensors == null) {
                continue;
            }
            for (IKlvKey geoIntelligenceSensorKey : geoIntelligenceSensors.getIdentifiers()) {
                org.jmisb.mimd.v2.st1907.GeoIntelligenceSensor geoIntelligenceSensor =
                        (org.jmisb.mimd.v2.st1907.GeoIntelligenceSensor)
                                geoIntelligenceSensors.getField(geoIntelligenceSensorKey);
                org.jmisb.mimd.v2.st1907.GeoIntelligenceSensor_CorrespondenceGroups
                        correspondenceGroups = geoIntelligenceSensor.getCorrespondenceGroups();
                if (correspondenceGroups == null) {
                    continue;
                }
                for (IKlvKey correspondenceGroupKey : correspondenceGroups.getIdentifiers()) {
                    org.jmisb.mimd.v2.st1907.CorrespondenceGroup correspondenceGroup =
                            (org.jmisb.mimd.v2.st1907.CorrespondenceGroup)
                                    correspondenceGroups.getField(correspondenceGroupKey);
                    if ((correspondenceGroup.getType()
                                    == org.jmisb.mimd.v2.st1907.CorrespondenceGroupType.Footprint)
                            && (correspondenceGroup.getRectangle() != null)
                            && (correspondenceGroup.getCentroid() != null)) {
                        updateFootprint(
                                correspondenceGroup.getRectangle(),
                                correspondenceGroup.getCentroid());
                    }
                }
            }
        }
    }

    // We probably need to track multiple instances somewhere.
    private void updateFootprint(
            org.jmisb.mimd.v1.st1907.CorrespondenceGroup_Rectangle rectangle,
            org.jmisb.mimd.v1.st1907.Correspondence centroid) {
        List<GeoPosition> tempCorners = new ArrayList<>();
        for (IKlvKey rectangleCornerKey : rectangle.getIdentifiers()) {
            org.jmisb.mimd.v1.st1907.Correspondence cornerCorrespondence =
                    (org.jmisb.mimd.v1.st1907.Correspondence)
                            rectangle.getField(rectangleCornerKey);
            GeoPosition geoPosition = getGeoPosition(cornerCorrespondence.getPosition());
            if (geoPosition != null) {
                tempCorners.add(geoPosition);
            }
        }
        // TODO: handle centroid?
        if (tempCorners.size() > 2) {
            corners.clear();
            corners.addAll(tempCorners);
            if (autoFollowCheckBox.isSelected()) {
                mapViewer.zoomToBestFit(new HashSet<>(corners), 0.7);
            }
        }
    }

    // We probably need to track multiple instances somewhere.
    private void updateFootprint(
            org.jmisb.mimd.v2.st1907.CorrespondenceGroup_Rectangle rectangle,
            org.jmisb.mimd.v2.st1907.Correspondence centroid) {
        List<GeoPosition> tempCorners = new ArrayList<>();
        for (IKlvKey rectangleCornerKey : rectangle.getIdentifiers()) {
            org.jmisb.mimd.v2.st1907.Correspondence cornerCorrespondence =
                    (org.jmisb.mimd.v2.st1907.Correspondence)
                            rectangle.getField(rectangleCornerKey);
            GeoPosition geoPosition = getGeoPosition(cornerCorrespondence.getPosition());
            if (geoPosition != null) {
                tempCorners.add(geoPosition);
            }
        }
        // TODO: handle centroid?
        if (tempCorners.size() > 2) {
            corners.clear();
            corners.addAll(tempCorners);
            if (autoFollowCheckBox.isSelected()) {
                mapViewer.zoomToBestFit(new HashSet<>(corners), 0.7);
            }
        }
    }

    private GeoPosition getGeoPosition(org.jmisb.mimd.v1.st1906.Position position) {
        if (position.getAbsGeodetic() == null) {
            return null;
        }
        org.jmisb.mimd.v1.st1906.AbsGeodetic geodeticPos = position.getAbsGeodetic();
        double latDegrees = geodeticPos.getLat().getValue() * 180.0 / Math.PI;
        double lonDegrees = geodeticPos.getLon().getValue() * 180.0 / Math.PI;
        GeoPosition geoPosition = new GeoPosition(latDegrees, lonDegrees);
        return geoPosition;
    }

    private GeoPosition getGeoPosition(org.jmisb.mimd.v2.st1906.Position position) {
        if (position.getAbsGeodetic() == null) {
            return null;
        }
        org.jmisb.mimd.v2.st1906.AbsGeodetic geodeticPos = position.getAbsGeodetic();
        double latDegrees = geodeticPos.getLat().getValue() * 180.0 / Math.PI;
        double lonDegrees = geodeticPos.getLon().getValue() * 180.0 / Math.PI;
        GeoPosition geoPosition = new GeoPosition(latDegrees, lonDegrees);
        return geoPosition;
    }

    private void processMIMDStages(org.jmisb.mimd.v1.st1905.Platform platform) {
        org.jmisb.mimd.v1.st1905.Platform_Stages stages = platform.getStages();
        for (IKlvKey stageKey : stages.getIdentifiers()) {
            org.jmisb.mimd.v1.st1906.Stage stage =
                    (org.jmisb.mimd.v1.st1906.Stage) stages.getField(stageKey);
            if (stage != null) {
                processMIMDStage(stage);
            }
        }
    }

    private void processMIMDStages(org.jmisb.mimd.v2.st1905.Platform platform) {
        org.jmisb.mimd.v2.st1905.Platform_Stages stages = platform.getStages();
        for (IKlvKey stageKey : stages.getIdentifiers()) {
            org.jmisb.mimd.v2.st1906.Stage stage =
                    (org.jmisb.mimd.v2.st1906.Stage) stages.getField(stageKey);
            if (stage != null) {
                processMIMDStage(stage);
            }
        }
    }

    private void processMIMDStage(org.jmisb.mimd.v1.st1906.Stage stage) {
        org.jmisb.mimd.v1.st1906.Position position = stage.getPosition();
        // We probably need to walk the stages
        if (position == null) {
            return;
        }
        GeoPosition sensorGeoPosition = getGeoPosition(position);
        if (sensorGeoPosition != null) {
            sensorLocations.clear();
            sensorLocations.add(sensorGeoPosition);
        }
    }

    private void processMIMDStage(org.jmisb.mimd.v2.st1906.Stage stage) {
        org.jmisb.mimd.v2.st1906.Position position = stage.getPosition();
        // We probably need to walk the stages
        if (position == null) {
            return;
        }
        GeoPosition sensorGeoPosition = getGeoPosition(position);
        if (sensorGeoPosition != null) {
            sensorLocations.clear();
            sensorLocations.add(sensorGeoPosition);
        }
    }
}

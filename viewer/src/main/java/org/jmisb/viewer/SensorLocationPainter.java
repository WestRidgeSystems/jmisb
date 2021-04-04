package org.jmisb.viewer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

/** Painter implementation for Sensor footprints. */
public class SensorLocationPainter implements Painter<JXMapViewer> {

    private final Color color = Color.BLUE;
    private static final int RADIUS = 5;
    private final List<GeoPosition> sensorLocations;
    private final List<GeoPosition> corners;

    public SensorLocationPainter(List<GeoPosition> sensorLocations, List<GeoPosition> corners) {
        this.sensorLocations = sensorLocations;
        this.corners = corners;
    }

    @Override
    public void paint(Graphics2D g, JXMapViewer map, int width, int height) {
        g = (Graphics2D) g.create();
        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(4));
        drawSensorLocation(g, map);
        g.setColor(color);
        g.setStroke(new BasicStroke(2));
        drawSensorLocation(g, map);
        g.dispose();
    }

    private void drawSensorLocation(Graphics2D g, JXMapViewer map) {
        List<GeoPosition> localSensorLocations = new ArrayList<>(sensorLocations);
        if (localSensorLocations.size() != 1) {
            return;
        }
        Point2D sensorCentrePoint =
                map.getTileFactory().geoToPixel(localSensorLocations.get(0), map.getZoom());
        g.drawOval(
                (int) sensorCentrePoint.getX() - RADIUS,
                (int) sensorCentrePoint.getY() - RADIUS,
                RADIUS * 2,
                RADIUS * 2);
        g.fillOval(
                (int) sensorCentrePoint.getX() - RADIUS,
                (int) sensorCentrePoint.getY() - RADIUS,
                RADIUS * 2,
                RADIUS * 2);
        List<GeoPosition> localCorners = new ArrayList(corners);
        if (localCorners.size() != 4) {
            return;
        }
        Point2D corner3Point = map.getTileFactory().geoToPixel(localCorners.get(2), map.getZoom());
        Point2D corner4Point = map.getTileFactory().geoToPixel(localCorners.get(3), map.getZoom());
        g.drawLine(
                (int) sensorCentrePoint.getX(),
                (int) sensorCentrePoint.getY(),
                (int) (corner3Point.getX() + corner4Point.getX()) / 2,
                (int) (corner3Point.getY() + corner4Point.getY()) / 2);
    }
}

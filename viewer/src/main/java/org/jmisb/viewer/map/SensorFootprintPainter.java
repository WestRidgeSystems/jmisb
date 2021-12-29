package org.jmisb.viewer.map;

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
public class SensorFootprintPainter implements Painter<JXMapViewer> {

    private final Color colour;
    private final List<GeoPosition> corners;

    public SensorFootprintPainter(List<GeoPosition> corners, Color strokeColour) {
        this.corners = corners;
        this.colour = strokeColour;
    }

    @Override
    public void paint(Graphics2D g, JXMapViewer map, int width, int height) {
        g = (Graphics2D) g.create();
        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(4));
        drawCorners(g, map);
        g.setColor(colour);
        g.setStroke(new BasicStroke(2));
        drawCorners(g, map);
        g.dispose();
    }

    private void drawCorners(Graphics2D g, JXMapViewer map) {
        int lastX = 0;
        int lastY = 0;
        int firstX = 0;
        int firstY = 0;
        boolean first = true;
        List<GeoPosition> localCorners = new ArrayList<>(corners);
        if (localCorners.size() != 4) {
            return;
        }
        int count = 1;
        for (GeoPosition gp : localCorners) {
            Point2D pt = map.getTileFactory().geoToPixel(gp, map.getZoom());
            g.drawString(Integer.toString(count), (int) pt.getX(), (int) pt.getY());
            count += 1;
            if (first) {
                firstX = (int) pt.getX();
                firstY = (int) pt.getY();
                first = false;
            } else {
                g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
            }
            lastX = (int) pt.getX();
            lastY = (int) pt.getY();
        }
        // Close the curve
        g.drawLine(lastX, lastY, firstX, firstY);
    }
}

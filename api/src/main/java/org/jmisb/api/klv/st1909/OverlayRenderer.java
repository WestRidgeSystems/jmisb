package org.jmisb.api.klv.st1909;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * Renderer for ST1909 Metadata Overlays.
 *
 * <p>This class takes BufferedImages and renders metadata text according to MISB ST1909 "Metadata
 * Overlay for Visualization".
 */
public class OverlayRenderer {

    private FontMetrics fontMetrics;
    private int firstLineOffset;
    private int lineSpacing;
    private Graphics2D graphics2D;
    private final OverlayRendererConfiguration configuration;

    /**
     * Create an overlay renderer with default options.
     *
     * <p>See {@link OverlayRendererConfiguration} for the options.
     */
    public OverlayRenderer() {
        configuration = new OverlayRendererConfiguration();
    }

    /**
     * Create overlay renderer with specified options.
     *
     * @param rendererConfiguration the configuration options.
     */
    public OverlayRenderer(OverlayRendererConfiguration rendererConfiguration) {
        configuration = rendererConfiguration;
    }

    /**
     * Render the metadata over the given image.
     *
     * @param image the image to draw on
     * @param metadata the metadata key/value pairs
     * @param timeStamp the motion imagery (frame) time stamp
     */
    public void render(BufferedImage image, MetadataItems metadata, ST0603TimeStamp timeStamp) {
        graphics2D = image.createGraphics();
        graphics2D.setFont(configuration.getFont());
        fontMetrics = graphics2D.getFontMetrics();
        firstLineOffset = fontMetrics.getAscent();
        lineSpacing = fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent() + 1;
        graphics2D.setColor(configuration.getFillColor());
        graphics2D.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(
                RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        renderMainSensorGroup(image, metadata);
        renderTrueNorthArrowGroup(image, metadata);
        renderLaserSensorGroup(image, metadata);
        renderClassificationAndReleasabilityGroup(image, metadata);
        renderPlatformGroup(image, metadata);
        renderDateTimeGroup(image, metadata, timeStamp);
        renderTargetGroup(image, metadata);
        // TODO: reticle - need to manage target location if not in frame center mode, which will be
        // messy.
        graphics2D.dispose();
    }

    /**
     * Render the metadata over the given image.
     *
     * @param image the image to draw on
     * @param metadata the metadata key/value pairs
     */
    public void render(BufferedImage image, MetadataItems metadata) {
        render(image, metadata, null);
    }

    private void renderMainSensorGroup(BufferedImage image, MetadataItems metadata) {
        // ST1909-02, but the rendering is based on the text base line, and we need to be strictly
        // below that
        int rowAnchor = (int) (image.getHeight() * 0.037) + firstLineOffset;
        // ST1909-03
        int columnAnchor = (int) (image.getWidth() * 0.02);
        // ST1909-04
        renderTextLeftAligned(metadata, MetadataKey.MainSensorName, columnAnchor, rowAnchor, 0);
        renderTextLeftAligned(metadata, MetadataKey.AzAngle, columnAnchor, rowAnchor, 1);
        renderTextLeftAligned(metadata, MetadataKey.ElAngle, columnAnchor, rowAnchor, 2);
    }

    private void renderTrueNorthArrowGroup(BufferedImage image, MetadataItems metadata) {
        // ST1909-30
        int columnAnchor = (int) (image.getWidth() * 0.02);
        // ST1909-29
        int rowAnchor = (int) (image.getHeight() * 0.5);
        // ST1909-31
        int size = configuration.getNorthCircleSize();
        graphics2D.drawOval(columnAnchor, rowAnchor - size, 2 * size, 2 * size);
        drawLetterN(columnAnchor, rowAnchor);
        drawNorthArrow(metadata, columnAnchor, rowAnchor);
    }

    private void drawNorthArrow(MetadataItems metadata, int columnAnchor, int rowAnchor)
            throws NumberFormatException {
        if (metadata.getItemKeys().contains(MetadataKey.NorthAngle)) {
            // ST1909-32
            double northAngle =
                    Double.parseDouble(metadata.getValue(MetadataKey.NorthAngle)) * Math.PI / 180.0;
            Polygon triangle = calculateNorthArrowPolygon(northAngle, columnAnchor, rowAnchor);
            graphics2D.drawPolygon(triangle);
            graphics2D.fillPolygon(triangle);
        }
    }

    private void drawLetterN(int columnAnchor, int rowAnchor) {
        GlyphVector glyphVector =
                configuration.getFont().createGlyphVector(graphics2D.getFontRenderContext(), "N");
        Shape textShape = glyphVector.getOutline();
        Rectangle2D bounds = textShape.getBounds2D();
        int verticalOffset = (int) ((bounds.getMaxY() - bounds.getMinY()) / 2);
        int horizontalOffset = (int) ((bounds.getMaxX() - bounds.getMinX()) / 2);
        // ST1909-32
        graphics2D.drawString(
                "N",
                columnAnchor + configuration.getNorthCircleSize() - horizontalOffset,
                rowAnchor + verticalOffset);
    }

    private Polygon calculateNorthArrowPolygon(double northAngle, int columnAnchor, int rowAnchor) {
        int size = configuration.getNorthCircleSize();
        int columnOffset = size + calculateColumnOffset(size, northAngle);
        int rowOffset = calculateRowOffset(size, northAngle);
        Point triangleCentrePoint = new Point(columnAnchor + columnOffset, rowAnchor + rowOffset);
        Polygon triangle = new Polygon();
        int triangleSize = size / 4;
        int x0 = triangleCentrePoint.x + calculateColumnOffset(triangleSize, northAngle);
        int y0 = triangleCentrePoint.y + calculateRowOffset(triangleSize, northAngle);
        double angle120 = 120.0 * Math.PI / 180.0;
        triangle.addPoint(x0, y0);
        int x1 = triangleCentrePoint.x + calculateColumnOffset(triangleSize, northAngle + angle120);
        int y1 = triangleCentrePoint.y + calculateRowOffset(triangleSize, northAngle + angle120);
        triangle.addPoint(x1, y1);
        int x2 = triangleCentrePoint.x + calculateColumnOffset(triangleSize, northAngle - angle120);
        int y2 = triangleCentrePoint.y + calculateRowOffset(triangleSize, northAngle - angle120);
        triangle.addPoint(x2, y2);
        return triangle;
    }

    // Exposed for unit testing purposes
    protected int calculateRowOffset(int size, double northAngle) {
        int rowOffset = (int) Math.round(Math.sin((-1 * northAngle) + (Math.PI / 2)) * size);
        return -1 * rowOffset;
    }

    // Exposed for unit testing purposes
    protected int calculateColumnOffset(int size, double northAngle) {
        int columnOffset = (int) Math.round(Math.cos((-1 * northAngle) + (Math.PI / 2)) * size);
        return columnOffset;
    }

    private void renderLaserSensorGroup(BufferedImage image, MetadataItems metadata) {
        // ST1909-35
        int columnAnchor = (int) (image.getWidth() * 0.02);
        // ST1909-34
        int rowAnchor = (int) (image.getHeight() * 0.963);
        // ST1909-36
        renderTextLeftAligned(metadata, MetadataKey.LaserSensorName, columnAnchor, rowAnchor, -2);
        renderTextLeftAligned(metadata, MetadataKey.LaserSensorStatus, columnAnchor, rowAnchor, -1);
        renderTextLeftAligned(metadata, MetadataKey.LaserPrfCode, columnAnchor, rowAnchor, 0);
    }

    private void renderClassificationAndReleasabilityGroup(
            BufferedImage image, MetadataItems metadata) {
        // ST1909-09, plus offset to make sure text is below anchor point per ST1909-11
        int rowAnchor = (int) (image.getHeight() * 0.037) + firstLineOffset;
        // ST1909-10
        int columnAnchor = (int) (image.getWidth() * 0.5);
        // ST1909-11
        renderTextCentred(
                metadata,
                MetadataKey.ClassificationAndReleasabilityLine1,
                columnAnchor,
                rowAnchor,
                0);
        renderTextCentred(
                metadata,
                MetadataKey.ClassificationAndReleasabilityLine2,
                columnAnchor,
                rowAnchor,
                1);
    }

    private void renderPlatformGroup(BufferedImage image, MetadataItems metadata) {
        // ST1909-14, plus offset to make sure text is below anchor point per ST1909-16
        int rowAnchor = (int) (image.getHeight() * 0.037) + firstLineOffset;
        // ST1909-15
        int columnAnchor = (int) (image.getWidth() * 0.98);
        // ST1909-16
        renderTextRightAligned(metadata, MetadataKey.PlatformName, columnAnchor, rowAnchor, 0);
        renderTextRightAligned(metadata, MetadataKey.PlatformLatitude, columnAnchor, rowAnchor, 1);
        renderTextRightAligned(metadata, MetadataKey.PlatformLongitude, columnAnchor, rowAnchor, 2);
        renderTextRightAligned(metadata, MetadataKey.PlatformAltitude, columnAnchor, rowAnchor, 3);
    }

    private void renderTargetGroup(BufferedImage image, MetadataItems metadata) {
        // ST1909-52
        int columnAnchor = (int) (image.getWidth() * 0.98);
        // ST1909-51
        int rowAnchor = (int) (image.getHeight() * 0.963);
        // ST1909-53
        renderTextRightAligned(metadata, MetadataKey.TargetElevation, columnAnchor, rowAnchor, 0);
        renderTextRightAligned(metadata, MetadataKey.TargetLongitude, columnAnchor, rowAnchor, -1);
        renderTextRightAligned(metadata, MetadataKey.TargetLatitude, columnAnchor, rowAnchor, -2);
        renderTextRightAligned(metadata, MetadataKey.VerticalFOV, columnAnchor, rowAnchor, -3);
        renderTextRightAligned(metadata, MetadataKey.HorizontalFOV, columnAnchor, rowAnchor, -4);
        renderTextRightAligned(metadata, MetadataKey.TargetWidth, columnAnchor, rowAnchor, -5);
        renderTextRightAligned(metadata, MetadataKey.SlantRange, columnAnchor, rowAnchor, -6);
    }

    private void renderDateTimeGroup(
            BufferedImage image, MetadataItems metadata, ST0603TimeStamp motionImageryTimestamp) {
        // ST1909-44
        int columnAnchor = (int) (image.getWidth() * 0.5);
        // ST1909-43
        int rowAnchor = (int) (image.getHeight() * 0.963);
        // ST1909-45
        renderTextCentred(metadata, MetadataKey.MetadataTimestamp, columnAnchor, rowAnchor, 0);
        if (motionImageryTimestamp != null) {
            // ST 1909-47 and ST 1909-48
            String frameTime =
                    "FT " + ST1909DateTimeFormatter.format(motionImageryTimestamp.getDateTime());
            renderFormattedTextCentred(frameTime, columnAnchor, rowAnchor, -1);
        }
    }

    private void renderTextLeftAligned(
            MetadataItems metadata,
            MetadataKey key,
            int columnAnchor,
            int rowAnchor,
            int lineNumber) {
        if (metadata.getItemKeys().contains(key)) {
            graphics2D.drawString(
                    metadata.getValue(key), columnAnchor, rowAnchor + lineNumber * lineSpacing);
        }
    }

    private void renderTextCentred(
            MetadataItems metadata,
            MetadataKey metadataKey,
            int columnAnchor,
            int rowAnchor,
            int lineNumber) {
        String text = metadata.getValue(metadataKey);
        if (text != null) {
            renderFormattedTextCentred(text, columnAnchor, rowAnchor, lineNumber);
        }
    }

    private void renderFormattedTextCentred(
            String text, int columnAnchor, int rowAnchor, int lineNumber) {
        int width = fontMetrics.stringWidth(text);
        graphics2D.drawString(text, columnAnchor - width / 2, rowAnchor + lineNumber * lineSpacing);
    }

    private void renderTextRightAligned(
            MetadataItems metadata,
            MetadataKey key,
            int columnAnchor,
            int rowAnchor,
            int lineNumber) {
        if (metadata.getItemKeys().contains(key)) {
            String text = metadata.getValue(key);
            int width = fontMetrics.stringWidth(text);
            graphics2D.drawString(text, columnAnchor - width, rowAnchor + lineNumber * lineSpacing);
        }
    }
}

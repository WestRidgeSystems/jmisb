package org.jmisb.api.klv.st1909;

import static org.testng.Assert.*;

import java.awt.image.BufferedImage;
import org.testng.annotations.Test;

/** Unit tests for OverlayRenderer */
public class OverlayRendererTest {

    public OverlayRendererTest() {}

    @Test
    public void rowOffsetForNorth() {
        OverlayRenderer renderer = new OverlayRenderer();
        int rowOffset = renderer.calculateRowOffset(100, 0.0);
        // True north should have all Y offset.
        // Negative because the coordinate system is left/down.
        assertEquals(rowOffset, -100);
    }

    @Test
    public void rowOffsetForEast() {
        OverlayRenderer renderer = new OverlayRenderer();
        int rowOffset = renderer.calculateRowOffset(100, 90.0 * Math.PI / 180.0);
        // Should have no offset.
        assertEquals(rowOffset, 0);
    }

    @Test
    public void rowOffsetForWest() {
        OverlayRenderer renderer = new OverlayRenderer();
        int rowOffset = renderer.calculateRowOffset(100, 270.0 * Math.PI / 180.0);
        // Should have no offset.
        assertEquals(rowOffset, 0);
    }

    @Test
    public void rowOffsetForSouth() {
        OverlayRenderer renderer = new OverlayRenderer();
        int rowOffset = renderer.calculateRowOffset(100, Math.PI);
        // South should have all Y offset.
        assertEquals(rowOffset, 100);
    }

    @Test
    public void columnOffsetForNorth() {
        OverlayRenderer renderer = new OverlayRenderer();
        int columnOffset = renderer.calculateColumnOffset(100, 0.0);
        // True north should have no x offset
        assertEquals(columnOffset, 0);
    }

    @Test
    public void columnOffsetForWest() {
        OverlayRenderer renderer = new OverlayRenderer();
        int columnOffset = renderer.calculateColumnOffset(100, 270.0 * Math.PI / 180.0);
        // Back at the origin.
        assertEquals(columnOffset, -100);
    }

    @Test
    public void columnOffsetForSouth() {
        OverlayRenderer renderer = new OverlayRenderer();
        int columnOffset = renderer.calculateColumnOffset(100, Math.PI);
        // No x offset,
        assertEquals(columnOffset, 0);
    }

    @Test
    public void columnOffsetForEast() {
        OverlayRenderer renderer = new OverlayRenderer();
        int columnOffset = renderer.calculateColumnOffset(100, 90.0 * Math.PI / 180.0);
        assertEquals(columnOffset, 100);
    }

    @Test
    public void checkOffsetsFor45Degrees() {
        OverlayRenderer renderer = new OverlayRenderer();
        double angle = 45 * Math.PI / 180.0;
        int columnOffset = renderer.calculateColumnOffset(100, angle);
        assertEquals(columnOffset, 71);
        int rowOffset = renderer.calculateRowOffset(100, angle);
        assertEquals(rowOffset, -71);
    }

    @Test
    public void checkOffsetsFor135Degrees() {
        OverlayRenderer renderer = new OverlayRenderer();
        double angle = 135 * Math.PI / 180.0;
        int columnOffset = renderer.calculateColumnOffset(100, angle);
        assertEquals(columnOffset, 71);
        int rowOffset = renderer.calculateRowOffset(100, angle);
        assertEquals(rowOffset, 71);
    }

    @Test
    public void checkOffsetsFor225Degrees() {
        OverlayRenderer renderer = new OverlayRenderer();
        double angle = 225 * Math.PI / 180.0;
        int columnOffset = renderer.calculateColumnOffset(100, angle);
        assertEquals(columnOffset, -71);
        int rowOffset = renderer.calculateRowOffset(100, angle);
        assertEquals(rowOffset, 71);
    }

    @Test
    public void checkOffsetsFor315Degrees() {
        OverlayRenderer renderer = new OverlayRenderer();
        double angle = 315 * Math.PI / 180.0;
        int columnOffset = renderer.calculateColumnOffset(100, angle);
        assertEquals(columnOffset, -71);
        int rowOffset = renderer.calculateRowOffset(100, angle);
        assertEquals(rowOffset, -71);
    }

    @Test
    public void checkOffsetsForNegative45Degrees() {
        OverlayRenderer renderer = new OverlayRenderer();
        double angle = -45 * Math.PI / 180.0;
        int columnOffset = renderer.calculateColumnOffset(100, angle);
        assertEquals(columnOffset, -71);
        int rowOffset = renderer.calculateRowOffset(100, angle);
        assertEquals(rowOffset, -71);
    }

    @Test
    public void checkRenderEmpty() {
        OverlayRenderer renderer = new OverlayRenderer(new OverlayRendererConfiguration());
        BufferedImage image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
        MetadataItems metadataItems = new MetadataItems();
        renderer.render(image, metadataItems);
        assertNotNull(image);
    }

    @Test
    public void checkRenderMost() {
        OverlayRenderer renderer = new OverlayRenderer(new OverlayRendererConfiguration());
        BufferedImage image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
        MetadataItems metadataItems = new MetadataItems();
        metadataItems.addItem(MetadataKey.AzAngle, "1.2");
        metadataItems.addItem(MetadataKey.ElAngle, "3.2");
        metadataItems.addItem(MetadataKey.ClassificationAndReleasabilityLine1, "L1");
        metadataItems.addItem(MetadataKey.HorizontalFOV, "0.1");
        metadataItems.addItem(MetadataKey.VerticalFOV, "0.2");
        metadataItems.addItem(MetadataKey.LaserPrfCode, "Laser PRF 1234");
        metadataItems.addItem(MetadataKey.LaserSensorName, "Laser Designator");
        metadataItems.addItem(MetadataKey.LaserSensorStatus, "Laser ON");
        metadataItems.addItem(MetadataKey.MainSensorName, "Sensor A");
        metadataItems.addItem(MetadataKey.MetadataTimestamp, "MT 2020-01-02T12:23:34.3Z");
        metadataItems.addItem(MetadataKey.NorthAngle, "60.0");
        metadataItems.addItem(MetadataKey.PlatformAltitude, "1425m HAE ALT");
        metadataItems.addItem(MetadataKey.PlatformLatitude, "LAT");
        metadataItems.addItem(MetadataKey.PlatformLongitude, "LON");
        metadataItems.addItem(MetadataKey.PlatformName, "MQ-99 SURE THING");
        metadataItems.addItem(MetadataKey.SlantRange, "33.34");
        metadataItems.addItem(MetadataKey.TargetElevation, "0.1m HAE");
        metadataItems.addItem(MetadataKey.TargetLatitude, "Target LAT");
        metadataItems.addItem(MetadataKey.TargetLongitude, "Target LON");
        metadataItems.addItem(MetadataKey.TargetWidth, "340.3m");
        renderer.render(image, metadataItems);
        assertNotNull(image);
    }
}

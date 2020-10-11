package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Range Image Plane Pixel Size (ST1206 Tag 7). */
public class RangeImagePlanePixelSizeTest {
    @Test
    public void testConstructFromValue() {
        RangeImagePlanePixelSize uut = new RangeImagePlanePixelSize(3.4);
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x1b, (byte) 0x33});
        assertEquals(uut.getDisplayName(), "Range Image Plane Pixel Size");
        assertEquals(uut.getDisplayableValue(), "3.40m");
        assertEquals(uut.getPixelSize(), 3.400, 0.001);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        RangeImagePlanePixelSize uut =
                new RangeImagePlanePixelSize(
                        new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x03, (byte) 0x20});
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x03, (byte) 0x20});
        assertEquals(uut.getDisplayName(), "Range Image Plane Pixel Size");
        assertEquals(uut.getDisplayableValue(), "32.39m");
        assertEquals(uut.getPixelSize(), 32.391, 0.001);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.RangeImagePlanePixelSize,
                        new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x20});
        assertTrue(value instanceof RangeImagePlanePixelSize);
        RangeImagePlanePixelSize uut = (RangeImagePlanePixelSize) value;
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x20});
        assertEquals(uut.getDisplayName(), "Range Image Plane Pixel Size");
        assertEquals(uut.getDisplayableValue(), "0.39m");
        assertEquals(uut.getPixelSize(), 0.390, 0.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new RangeImagePlanePixelSize(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new RangeImagePlanePixelSize(1000000.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new RangeImagePlanePixelSize(new byte[] {0x01, 0x02, 0x03});
    }
}

package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ImageColumns (ST1206 Item 9). */
public class ImageColumnsTest {
    @Test
    public void testConstructFromValue() {
        ImageColumns uut = new ImageColumns(1920);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x07, (byte) 0x80});
        assertEquals(uut.getDisplayName(), "Image Columns");
        assertEquals(uut.getDisplayableValue(), "1920px");
        assertEquals(uut.getImageColumns(), 1920);
    }

    @Test
    public void testConstructFromValueMin() {
        ImageColumns uut = new ImageColumns(1);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x01});
        assertEquals(uut.getDisplayName(), "Image Columns");
        assertEquals(uut.getDisplayableValue(), "1px");
        assertEquals(uut.getImageColumns(), 1);
    }

    @Test
    public void testConstructFromValueMax() {
        ImageColumns uut = new ImageColumns(65535);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xFF, (byte) 0xFF});
        assertEquals(uut.getDisplayName(), "Image Columns");
        assertEquals(uut.getDisplayableValue(), "65535px");
        assertEquals(uut.getImageColumns(), 65535);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        ImageColumns uut = new ImageColumns(new byte[] {(byte) 0x07, (byte) 0x80});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x07, (byte) 0x80});
        assertEquals(uut.getDisplayName(), "Image Columns");
        assertEquals(uut.getDisplayableValue(), "1920px");
        assertEquals(uut.getImageColumns(), 1920);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.ImageColumns, new byte[] {(byte) 0x07, (byte) 0x80});
        assertTrue(value instanceof ImageColumns);
        ImageColumns uut = (ImageColumns) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x07, (byte) 0x80});
        assertEquals(uut.getDisplayName(), "Image Columns");
        assertEquals(uut.getDisplayableValue(), "1920px");
        assertEquals(uut.getImageColumns(), 1920);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ImageColumns(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new ImageColumns(65536);
        ;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new ImageColumns(new byte[] {0x01, 0x02, 0x03});
    }
}

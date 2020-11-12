package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ImageRows (ST1206 Item 9). */
public class ImageRowsTest {
    @Test
    public void testConstructFromValue() {
        ImageRows uut = new ImageRows(1920);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x07, (byte) 0x80});
        assertEquals(uut.getDisplayName(), "Image Rows");
        assertEquals(uut.getDisplayableValue(), "1920px");
        assertEquals(uut.getImageRows(), 1920);
    }

    @Test
    public void testConstructFromValueMin() {
        ImageRows uut = new ImageRows(1);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x01});
        assertEquals(uut.getDisplayName(), "Image Rows");
        assertEquals(uut.getDisplayableValue(), "1px");
        assertEquals(uut.getImageRows(), 1);
    }

    @Test
    public void testConstructFromValueMax() {
        ImageRows uut = new ImageRows(65535);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xFF, (byte) 0xFF});
        assertEquals(uut.getDisplayName(), "Image Rows");
        assertEquals(uut.getDisplayableValue(), "65535px");
        assertEquals(uut.getImageRows(), 65535);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        ImageRows uut = new ImageRows(new byte[] {(byte) 0x07, (byte) 0x80});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x07, (byte) 0x80});
        assertEquals(uut.getDisplayName(), "Image Rows");
        assertEquals(uut.getDisplayableValue(), "1920px");
        assertEquals(uut.getImageRows(), 1920);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.ImageRows, new byte[] {(byte) 0x07, (byte) 0x80});
        assertTrue(value instanceof ImageRows);
        ImageRows uut = (ImageRows) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x07, (byte) 0x80});
        assertEquals(uut.getDisplayName(), "Image Rows");
        assertEquals(uut.getDisplayableValue(), "1920px");
        assertEquals(uut.getImageRows(), 1920);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ImageRows(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new ImageRows(65536);
        ;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new ImageRows(new byte[] {0x01, 0x02, 0x03});
    }
}

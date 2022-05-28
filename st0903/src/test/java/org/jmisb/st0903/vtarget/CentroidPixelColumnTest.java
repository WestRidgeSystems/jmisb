package org.jmisb.st0903.vtarget;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.EncodingMode;
import org.testng.annotations.Test;

/** Tests for Centroid Pixel Column (Tag 20) */
public class CentroidPixelColumnTest {

    public CentroidPixelColumnTest() {}

    @Test
    public void testConstructFromValue() {
        CentroidPixelColumn index = new CentroidPixelColumn(1137);
        assertEquals(index.getBytes(), new byte[] {(byte) 0x04, (byte) 0x71});
        assertEquals(index.getDisplayName(), "Centroid Pixel Column");
        assertEquals(index.getDisplayableValue(), "1137");
        assertEquals(index.getValue(), 1137L);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        CentroidPixelColumn index = new CentroidPixelColumn(new byte[] {(byte) 0x04, (byte) 0x71});
        assertEquals(index.getBytes(), new byte[] {(byte) 0x04, (byte) 0x71});
        assertEquals(index.getDisplayName(), "Centroid Pixel Column");
        assertEquals(index.getDisplayableValue(), "1137");
        assertEquals(index.getValue(), 1137L);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.CentroidPixColumn,
                        new byte[] {(byte) 0x04, (byte) 0x71},
                        EncodingMode.IMAPB);
        assertTrue(value instanceof CentroidPixelColumn);
        CentroidPixelColumn index = (CentroidPixelColumn) value;
        assertEquals(index.getBytes(), new byte[] {(byte) 0x04, (byte) 0x71});
        assertEquals(index.getDisplayName(), "Centroid Pixel Column");
        assertEquals(index.getDisplayableValue(), "1137");
        assertEquals(index.getValue(), 1137L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new CentroidPixelColumn(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new CentroidPixelColumn(4294967296L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new CentroidPixelColumn(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }
}

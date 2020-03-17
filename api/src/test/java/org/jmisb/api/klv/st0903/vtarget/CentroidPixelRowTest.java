package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for Centroid Pixel Row (Tag 19)
 */
public class CentroidPixelRowTest {
    
    public CentroidPixelRowTest() {
    }

    @Test
    public void testConstructFromValue()
    {
        CentroidPixelRow index = new CentroidPixelRow(872);
        assertEquals(index.getBytes(), new byte[]{(byte)0x03, (byte)0x68});
        assertEquals(index.getDisplayName(), "Centroid Pixel Row");
        assertEquals(index.getDisplayableValue(), "872");
        assertEquals(index.getValue(), 872L);
    }
    
    @Test
    public void testConstructFromEncodedBytes()
    {
        CentroidPixelRow index = new CentroidPixelRow(new byte[]{(byte)0x03, (byte)0x68});
        assertEquals(index.getBytes(), new byte[]{(byte)0x03, (byte)0x68});
        assertEquals(index.getDisplayName(), "Centroid Pixel Row");
        assertEquals(index.getDisplayableValue(), "872");
        assertEquals(index.getValue(), 872L);
    }
    
    @Test
    public void testFactory() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.CentroidPixRow, new byte[]{(byte)0x03, (byte)0x68});
        assertTrue(value instanceof CentroidPixelRow);
        CentroidPixelRow index = (CentroidPixelRow)value;
        assertEquals(index.getBytes(), new byte[]{(byte)0x03, (byte)0x68});
        assertEquals(index.getDisplayName(), "Centroid Pixel Row");
        assertEquals(index.getDisplayableValue(), "872");
        assertEquals(index.getValue(), 872L);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new CentroidPixelRow(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new CentroidPixelRow(4294967296L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new CentroidPixelRow(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05});
    }
}

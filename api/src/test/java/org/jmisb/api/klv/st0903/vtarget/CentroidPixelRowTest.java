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

    @Test
    public void testConstructFromEncodedBytes1()
    {
        CentroidPixelRow index = new CentroidPixelRow(new byte[]{(byte)0x01});
        assertEquals(index.getBytes(), new byte[]{(byte)0x01});
        assertEquals(index.getDisplayName(), "Centroid Pixel Row");
        assertEquals(index.getDisplayableValue(), "1");
        assertEquals(index.getValue(), 1L);
    }

    @Test
    public void testConstructFromEncodedBytes255()
    {
        CentroidPixelRow index = new CentroidPixelRow(new byte[]{(byte)0xFF});
        assertEquals(index.getBytes(), new byte[]{(byte)0xFF});
        assertEquals(index.getDisplayName(), "Centroid Pixel Row");
        assertEquals(index.getDisplayableValue(), "255");
        assertEquals(index.getValue(), 255L);
    }

    @Test
    public void testConstructFromEncodedBytes256()
    {
        CentroidPixelRow index = new CentroidPixelRow(new byte[]{(byte)0x01, (byte)0x00});
        assertEquals(index.getBytes(), new byte[]{(byte)0x01, (byte)0x00});
        assertEquals(index.getDisplayName(), "Centroid Pixel Row");
        assertEquals(index.getDisplayableValue(), "256");
        assertEquals(index.getValue(), 256L);
    }

    @Test
    public void testConstructFromEncodedBytes65535()
    {
        CentroidPixelRow index = new CentroidPixelRow(new byte[]{(byte)0xFF, (byte)0xFF});
        assertEquals(index.getBytes(), new byte[]{(byte)0xFF, (byte)0xFF});
        assertEquals(index.getDisplayName(), "Centroid Pixel Row");
        assertEquals(index.getDisplayableValue(), "65535");
        assertEquals(index.getValue(), 65535L);
    }

    @Test
    public void testConstructFromEncodedBytes65536()
    {
        CentroidPixelRow index = new CentroidPixelRow(new byte[]{(byte)0x01, (byte)0x00, (byte)0x00});
        assertEquals(index.getBytes(), new byte[]{(byte)0x01, (byte)0x00, (byte)0x00});
        assertEquals(index.getDisplayName(), "Centroid Pixel Row");
        assertEquals(index.getDisplayableValue(), "65536");
        assertEquals(index.getValue(), 65536L);
    }

    @Test
    public void testConstructFromEncodedBytes16777215()
    {
        CentroidPixelRow index = new CentroidPixelRow(new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(index.getBytes(), new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(index.getDisplayName(), "Centroid Pixel Row");
        assertEquals(index.getDisplayableValue(), "16777215");
        assertEquals(index.getValue(), 16777215L);
    }

    @Test
    public void testConstructFromEncodedBytes16777216()
    {
        CentroidPixelRow index = new CentroidPixelRow(new byte[]{(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00});
        assertEquals(index.getBytes(), new byte[]{(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00});
        assertEquals(index.getDisplayName(), "Centroid Pixel Row");
        assertEquals(index.getDisplayableValue(), "16777216");
        assertEquals(index.getValue(), 16777216L);
    }

    @Test
    public void testConstructFromEncodedBytes4294967295()
    {
        CentroidPixelRow index = new CentroidPixelRow(new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(index.getBytes(), new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(index.getDisplayName(), "Centroid Pixel Row");
        assertEquals(index.getDisplayableValue(), "4294967295");
        assertEquals(index.getValue(), 4294967295L);
    }
}

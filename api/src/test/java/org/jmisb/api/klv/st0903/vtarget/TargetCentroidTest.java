package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for Target Centroid (Tag 1).
 * 
 * This does a lot of the broader checks for PixelValue superclass too.
 */
public class TargetCentroidTest {

    @Test
    public void testConstructFromValue()
    {
        TargetCentroid index = new TargetCentroid(409600);
        assertEquals(index.getBytes(), new byte[]{(byte)0x06, (byte)0x40, (byte)0x00});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "409600");
        assertEquals(index.getPixelNumber(), 409600L);
    }

    @Test
    public void testConstructFromEncodedBytes()
    {
        TargetCentroid index = new TargetCentroid(new byte[]{(byte)0x06, (byte)0x40, (byte)0x00});
        assertEquals(index.getBytes(), new byte[]{(byte)0x06, (byte)0x40, (byte)0x00});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "409600");
        assertEquals(index.getPixelNumber(), 409600L);
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.TargetCentroid, new byte[]{(byte)0x06, (byte)0x40, (byte)0x00});
        assertTrue(value instanceof TargetCentroid);
        TargetCentroid index = (TargetCentroid)value;
        assertEquals(index.getBytes(), new byte[]{(byte)0x06, (byte)0x40, (byte)0x00});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "409600");
        assertEquals(index.getPixelNumber(), 409600L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new TargetCentroid(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new TargetCentroid(281_474_976_710_656L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new TargetCentroid(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});
    }
    
    @Test
    public void testConstructFromEncodedBytes1()
    {
        TargetCentroid index = new TargetCentroid(new byte[]{(byte)0x01});
        assertEquals(index.getBytes(), new byte[]{(byte)0x01});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "1");
        assertEquals(index.getPixelNumber(), 1L);
    }
    
    @Test
    public void testConstructFromEncodedBytes255()
    {
        TargetCentroid index = new TargetCentroid(new byte[]{(byte)0xFF});
        assertEquals(index.getBytes(), new byte[]{(byte)0xFF});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "255");
        assertEquals(index.getPixelNumber(), 255L);
    }
    
    @Test
    public void testConstructFromEncodedBytes256()
    {
        TargetCentroid index = new TargetCentroid(new byte[]{(byte)0x01, (byte)0x00});
        assertEquals(index.getBytes(), new byte[]{(byte)0x01, (byte)0x00});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "256");
        assertEquals(index.getPixelNumber(), 256L);
    }

    @Test
    public void testConstructFromEncodedBytes65535()
    {
        TargetCentroid index = new TargetCentroid(new byte[]{(byte)0xFF, (byte)0xFF});
        assertEquals(index.getBytes(), new byte[]{(byte)0xFF, (byte)0xFF});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "65535");
        assertEquals(index.getPixelNumber(), 65535L);
    }

    @Test
    public void testConstructFromEncodedBytes65536()
    {
        TargetCentroid index = new TargetCentroid(new byte[]{(byte)0x01, (byte)0x00, (byte)0x00});
        assertEquals(index.getBytes(), new byte[]{(byte)0x01, (byte)0x00, (byte)0x00});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "65536");
        assertEquals(index.getPixelNumber(), 65536L);
    }

    @Test
    public void testConstructFromEncodedBytes16777215()
    {
        TargetCentroid index = new TargetCentroid(new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(index.getBytes(), new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "16777215");
        assertEquals(index.getPixelNumber(), 16777215L);
    }

    @Test
    public void testConstructFromEncodedBytes16777216()
    {
        TargetCentroid index = new TargetCentroid(new byte[]{(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00});
        assertEquals(index.getBytes(), new byte[]{(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "16777216");
        assertEquals(index.getPixelNumber(), 16777216L);
    }

    @Test
    public void testConstructFromEncodedBytes4294967295()
    {
        TargetCentroid index = new TargetCentroid(new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(index.getBytes(), new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "4294967295");
        assertEquals(index.getPixelNumber(), 4294967295L);
    }
    
    @Test
    public void testConstructFromEncodedBytes4294967296()
    {
        TargetCentroid index = new TargetCentroid(new byte[]{(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00});
        assertEquals(index.getBytes(), new byte[]{(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "4294967296");
        assertEquals(index.getPixelNumber(), 4294967296L);
    }

    @Test
    public void testConstructFromEncodedBytes1099511627775()
    {
        TargetCentroid index = new TargetCentroid(new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(index.getBytes(), new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "1099511627775");
        assertEquals(index.getPixelNumber(), 1099511627775L);
    }
    
    @Test
    public void testConstructFromEncodedBytes1099511627776()
    {
        TargetCentroid index = new TargetCentroid(new byte[]{(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00});
        assertEquals(index.getBytes(), new byte[]{(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "1099511627776");
        assertEquals(index.getPixelNumber(), 1099511627776L);
    }
    
    @Test
    public void testConstructFromEncodedBytes281474976710655()
    {
        TargetCentroid index = new TargetCentroid(new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(index.getBytes(), new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(index.getDisplayName(), "Target Centroid");
        assertEquals(index.getDisplayableValue(), "281474976710655");
        assertEquals(index.getPixelNumber(), 281474976710655L);
    }
}

package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.VTargetMetadataKey;
import org.jmisb.api.klv.st0903.VTargetPack;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for Target Location Offset Latitude (Tag 10)
 */
public class TargetLocationOffsetLatTest {
    
    public TargetLocationOffsetLatTest() {
    }

    @Test
    public void testConstructFromValue()
    {
        TargetLocationOffsetLat offset = new TargetLocationOffsetLat(10.0);
        assertEquals(offset.getBytes(), new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertEquals(offset.getDisplayName(), "Target Location Offset Latitude");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }
    
    @Test
    public void testConstructFromEncodedBytes()
    {
        TargetLocationOffsetLat offset = new TargetLocationOffsetLat(new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertEquals(offset.getBytes(), new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertEquals(offset.getDisplayName(), "Target Location Offset Latitude");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }
    
    @Test
    public void testFactory() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.TargetLocationOffsetLat, new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertTrue(value instanceof TargetLocationOffsetLat);
        TargetLocationOffsetLat offset = (TargetLocationOffsetLat)value;
        assertEquals(offset.getBytes(), new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertEquals(offset.getDisplayName(), "Target Location Offset Latitude");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new TargetLocationOffsetLat(-19.200001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new TargetLocationOffsetLat(19.200001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new TargetLocationOffsetLat(new byte[]{0x01, 0x02});
    }
}

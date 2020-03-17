package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for Boundary Top Left Longitude Offset (Tag 14)
 */
public class BoundaryTopLeftLonOffsetTest {
    
    public BoundaryTopLeftLonOffsetTest() {
    }

    @Test
    public void testConstructFromValue()
    {
        BoundaryTopLeftLonOffset offset = new BoundaryTopLeftLonOffset(10.0);
        assertEquals(offset.getBytes(), new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertEquals(offset.getDisplayName(), "Boundary Top Left Lon Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }
    
    @Test
    public void testConstructFromEncodedBytes()
    {
        BoundaryTopLeftLonOffset offset = new BoundaryTopLeftLonOffset(new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertEquals(offset.getBytes(), new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertEquals(offset.getDisplayName(), "Boundary Top Left Lon Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }
    
    @Test
    public void testFactory() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.BoundaryTopLeftLonOffset, new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertTrue(value instanceof BoundaryTopLeftLonOffset);
        BoundaryTopLeftLonOffset offset = (BoundaryTopLeftLonOffset)value;
        assertEquals(offset.getBytes(), new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertEquals(offset.getDisplayName(), "Boundary Top Left Lon Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new BoundaryTopLeftLonOffset(-19.200001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new BoundaryTopLeftLonOffset(19.200001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new BoundaryTopLeftLonOffset(new byte[]{0x01, 0x02, 0x03, 0x04});
    }
}

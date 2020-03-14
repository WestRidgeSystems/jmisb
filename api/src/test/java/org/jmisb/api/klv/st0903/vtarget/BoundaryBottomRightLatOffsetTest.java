package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.VTargetMetadataKey;
import org.jmisb.api.klv.st0903.VTargetPack;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for Boundary Bottom Right Latitude Offset (Tag 15)
 */
public class BoundaryBottomRightLatOffsetTest {

    public BoundaryBottomRightLatOffsetTest() {
    }

    @Test
    public void testConstructFromValue()
    {
        BoundaryBottomRightLatOffset offset = new BoundaryBottomRightLatOffset(10.0);
        assertEquals(offset.getBytes(), new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertEquals(offset.getDisplayName(), "Boundary Bottom Right Lat Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test
    public void testConstructFromEncodedBytes()
    {
        BoundaryBottomRightLatOffset offset = new BoundaryBottomRightLatOffset(new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertEquals(offset.getBytes(), new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertEquals(offset.getDisplayName(), "Boundary Bottom Right Lat Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.BoundaryBottomRightLatOffset, new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertTrue(value instanceof BoundaryBottomRightLatOffset);
        BoundaryBottomRightLatOffset offset = (BoundaryBottomRightLatOffset)value;
        assertEquals(offset.getBytes(), new byte[]{(byte)0x3A, (byte)0x66, (byte)0x67});
        assertEquals(offset.getDisplayName(), "Boundary Bottom Right Lat Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new BoundaryBottomRightLatOffset(-19.200001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new BoundaryBottomRightLatOffset(19.200001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new BoundaryBottomRightLatOffset(new byte[]{0x01, 0x02});
    }
}

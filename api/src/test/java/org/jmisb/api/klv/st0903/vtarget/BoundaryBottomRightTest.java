package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for Boundary Bottom Right (Tag 3).
 */
public class BoundaryBottomRightTest {

    @Test
    public void testConstructFromValue()
    {
        BoundaryBottomRight index = new BoundaryBottomRight(409600);
        assertEquals(index.getBytes(), new byte[]{(byte)0x06, (byte)0x40, (byte)0x00});
        assertEquals(index.getDisplayName(), "Boundary Bottom Right");
        assertEquals(index.getDisplayableValue(), "409600");
        assertEquals(index.getPixelNumber(), 409600L);
    }

    @Test
    public void testConstructFromEncodedBytes()
    {
        BoundaryBottomRight index = new BoundaryBottomRight(new byte[]{(byte)0x06, (byte)0x40, (byte)0x00});
        assertEquals(index.getBytes(), new byte[]{(byte)0x06, (byte)0x40, (byte)0x00});
        assertEquals(index.getDisplayName(), "Boundary Bottom Right");
        assertEquals(index.getDisplayableValue(), "409600");
        assertEquals(index.getPixelNumber(), 409600L);
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.BoundaryBottomRight, new byte[]{(byte)0x06, (byte)0x40, (byte)0x00});
        assertTrue(value instanceof BoundaryBottomRight);
        BoundaryBottomRight index = (BoundaryBottomRight)value;
        assertEquals(index.getBytes(), new byte[]{(byte)0x06, (byte)0x40, (byte)0x00});
        assertEquals(index.getDisplayName(), "Boundary Bottom Right");
        assertEquals(index.getDisplayableValue(), "409600");
        assertEquals(index.getPixelNumber(), 409600L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new BoundaryBottomRight(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new BoundaryBottomRight(281_474_976_710_656L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new BoundaryBottomRight(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});
    }
}

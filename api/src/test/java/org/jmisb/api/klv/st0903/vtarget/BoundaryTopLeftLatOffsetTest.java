package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.testng.annotations.Test;

/** Tests for Boundary Top Left Latitude Offset (Tag 13) */
public class BoundaryTopLeftLatOffsetTest {

    public BoundaryTopLeftLatOffsetTest() {}

    @Test
    public void testConstructFromValue() {
        BoundaryTopLeftLatOffset offset = new BoundaryTopLeftLatOffset(10.0);
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertEquals(offset.getDisplayName(), "Boundary Top Left Lat Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        BoundaryTopLeftLatOffset offset =
                new BoundaryTopLeftLatOffset(new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertEquals(offset.getDisplayName(), "Boundary Top Left Lat Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.BoundaryTopLeftLatOffset,
                        new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertTrue(value instanceof BoundaryTopLeftLatOffset);
        BoundaryTopLeftLatOffset offset = (BoundaryTopLeftLatOffset) value;
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertEquals(offset.getDisplayName(), "Boundary Top Left Lat Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new BoundaryTopLeftLatOffset(-19.200001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new BoundaryTopLeftLatOffset(19.200001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new BoundaryTopLeftLatOffset(new byte[] {0x01, 0x02, 0x03, 0x04});
    }
}

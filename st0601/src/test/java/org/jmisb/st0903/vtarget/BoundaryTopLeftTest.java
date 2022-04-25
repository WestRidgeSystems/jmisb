package org.jmisb.st0903.vtarget;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.EncodingMode;
import org.testng.annotations.Test;

/** Tests for Boundary Top Left (Tag 2). */
public class BoundaryTopLeftTest {

    @Test
    public void testConstructFromValue() {
        BoundaryTopLeft index = new BoundaryTopLeft(409600);
        assertEquals(index.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40, (byte) 0x00});
        assertEquals(index.getDisplayName(), "Boundary Top Left");
        assertEquals(index.getDisplayableValue(), "409600");
        assertEquals(index.getPixelNumber(), 409600L);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        BoundaryTopLeft index =
                new BoundaryTopLeft(new byte[] {(byte) 0x06, (byte) 0x40, (byte) 0x00});
        assertEquals(index.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40, (byte) 0x00});
        assertEquals(index.getDisplayName(), "Boundary Top Left");
        assertEquals(index.getDisplayableValue(), "409600");
        assertEquals(index.getPixelNumber(), 409600L);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.BoundaryTopLeft,
                        new byte[] {(byte) 0x06, (byte) 0x40, (byte) 0x00},
                        EncodingMode.IMAPB);
        assertTrue(value instanceof BoundaryTopLeft);
        BoundaryTopLeft index = (BoundaryTopLeft) value;
        assertEquals(index.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40, (byte) 0x00});
        assertEquals(index.getDisplayName(), "Boundary Top Left");
        assertEquals(index.getDisplayableValue(), "409600");
        assertEquals(index.getPixelNumber(), 409600L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new BoundaryTopLeft(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new BoundaryTopLeft(281_474_976_710_656L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new BoundaryTopLeft(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});
    }
}

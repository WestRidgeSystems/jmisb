package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.testng.annotations.Test;

/** Tests for Target Color (Tag 8) */
public class TargetColorTest {
    @Test
    public void testConstructFromValue() {
        TargetColor color = new TargetColor((short) 85, (short) 136, (short) 51);
        assertEquals(color.getBytes(), new byte[] {(byte) 0x55, (byte) 0x88, (byte) 0x33});
        assertEquals(color.getDisplayName(), "Target Color");
        assertEquals(color.getDisplayableValue(), "[85, 136, 51]");
        assertEquals(color.getRed(), 85);
        assertEquals(color.getGreen(), 136);
        assertEquals(color.getBlue(), 51);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        TargetColor color = new TargetColor(new byte[] {(byte) 0x55, (byte) 0x88, (byte) 0x33});
        assertEquals(color.getBytes(), new byte[] {(byte) 0x55, (byte) 0x88, (byte) 0x33});
        assertEquals(color.getDisplayName(), "Target Color");
        assertEquals(color.getDisplayableValue(), "[85, 136, 51]");
        assertEquals(color.getRed(), 85);
        assertEquals(color.getGreen(), 136);
        assertEquals(color.getBlue(), 51);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetColor,
                        new byte[] {(byte) 0x55, (byte) 0x88, (byte) 0x33});
        assertTrue(value instanceof TargetColor);
        TargetColor color = (TargetColor) value;
        assertEquals(color.getBytes(), new byte[] {(byte) 0x55, (byte) 0x88, (byte) 0x33});
        assertEquals(color.getDisplayName(), "Target Color");
        assertEquals(color.getDisplayableValue(), "[85, 136, 51]");
        assertEquals(color.getRed(), 85);
        assertEquals(color.getGreen(), 136);
        assertEquals(color.getBlue(), 51);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmallRed() {
        new TargetColor((short) -1, (short) 0, (short) 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmallGreen() {
        new TargetColor((short) 0, (short) -1, (short) 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmallBlue() {
        new TargetColor((short) 0, (short) 0, (short) -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBigRed() {
        new TargetColor((short) 256, (short) 255, (short) 255);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBigGreen() {
        new TargetColor((short) 255, (short) 256, (short) 255);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBigBlue() {
        new TargetColor((short) 255, (short) 255, (short) 256);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TargetColor(new byte[] {0x01, 0x02});
    }
}

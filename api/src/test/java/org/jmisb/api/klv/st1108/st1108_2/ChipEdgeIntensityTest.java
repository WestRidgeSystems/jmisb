package org.jmisb.api.klv.st1108.st1108_2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for Chip Edge Intensity (ST 1108.2 Tag 12). */
public class ChipEdgeIntensityTest {

    @Test
    public void testConstructFromValue() {
        ChipEdgeIntensity uut = new ChipEdgeIntensity(30);
        assertEquals(uut.getDisplayName(), "Chip Edge Intensity");
        assertEquals(uut.getDisplayableValue(), "30");
        assertEquals(uut.getIntensity(), 30);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        ChipEdgeIntensity uut = new ChipEdgeIntensity(new byte[] {(byte) 0x00, (byte) 0x03F});
        assertEquals(uut.getDisplayName(), "Chip Edge Intensity");
        assertEquals(uut.getDisplayableValue(), "63");
        assertEquals(uut.getIntensity(), 63);
    }

    @Test
    public void testConstructFromEncodedBytesMin() {
        ChipEdgeIntensity uut = new ChipEdgeIntensity(new byte[] {(byte) 0x00, (byte) 0x00});
        assertEquals(uut.getDisplayName(), "Chip Edge Intensity");
        assertEquals(uut.getDisplayableValue(), "0");
        assertEquals(uut.getIntensity(), 0);
    }

    @Test
    public void testConstructFromEncodedBytesMax() {
        ChipEdgeIntensity uut = new ChipEdgeIntensity(new byte[] {(byte) 0x03, (byte) 0x0E8});
        assertEquals(uut.getDisplayName(), "Chip Edge Intensity");
        assertEquals(uut.getDisplayableValue(), "1000");
        assertEquals(uut.getIntensity(), 1000);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ChipEdgeIntensity(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new ChipEdgeIntensity(1001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new ChipEdgeIntensity(new byte[] {0x01});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new ChipEdgeIntensity(new byte[] {0x01, 0x02, 0x03});
    }
}

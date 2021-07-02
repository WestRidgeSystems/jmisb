package org.jmisb.api.klv.st1108.st1108_2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for Chip Frequency Ratio (ST 1108.2 Tag 13). */
public class ChipFrequencyRatioTest {

    @Test
    public void testConstructFromValue() {
        ChipFrequencyRatio uut = new ChipFrequencyRatio(30);
        assertEquals(uut.getDisplayName(), "Chip Frequency Ratio");
        assertEquals(uut.getDisplayableValue(), "30");
        assertEquals(uut.getRatio(), 30);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        ChipFrequencyRatio uut = new ChipFrequencyRatio(new byte[] {(byte) 0x00, (byte) 0x03F});
        assertEquals(uut.getDisplayName(), "Chip Frequency Ratio");
        assertEquals(uut.getDisplayableValue(), "63");
        assertEquals(uut.getRatio(), 63);
    }

    @Test
    public void testConstructFromEncodedBytesMin() {
        ChipFrequencyRatio uut = new ChipFrequencyRatio(new byte[] {(byte) 0x00, (byte) 0x00});
        assertEquals(uut.getDisplayName(), "Chip Frequency Ratio");
        assertEquals(uut.getDisplayableValue(), "0");
        assertEquals(uut.getRatio(), 0);
    }

    @Test
    public void testConstructFromEncodedBytesMax() {
        ChipFrequencyRatio uut = new ChipFrequencyRatio(new byte[] {(byte) 0xFF, (byte) 0x0FF});
        assertEquals(uut.getDisplayName(), "Chip Frequency Ratio");
        assertEquals(uut.getDisplayableValue(), "65535");
        assertEquals(uut.getRatio(), 65535);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ChipFrequencyRatio(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new ChipFrequencyRatio(65536);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new ChipFrequencyRatio(new byte[] {0x01});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new ChipFrequencyRatio(new byte[] {0x01, 0x02, 0x03});
    }
}

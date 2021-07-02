package org.jmisb.api.klv.st1108.st1108_2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for Chip PSNR (ST 1108.2 Tag 14). */
public class ChipPSNRTest {

    @Test
    public void testConstructFromValue() {
        ChipPSNR uut = new ChipPSNR(30);
        assertEquals(uut.getDisplayName(), "Chip PSNR");
        assertEquals(uut.getDisplayableValue(), "30 dB");
        assertEquals(uut.getPSNR(), 30);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        ChipPSNR uut = new ChipPSNR(new byte[] {(byte) 0x03F});
        assertEquals(uut.getDisplayName(), "Chip PSNR");
        assertEquals(uut.getDisplayableValue(), "63 dB");
        assertEquals(uut.getPSNR(), 63);
    }

    @Test
    public void testConstructFromEncodedBytesMin() {
        ChipPSNR uut = new ChipPSNR(new byte[] {(byte) 0x00});
        assertEquals(uut.getDisplayName(), "Chip PSNR");
        assertEquals(uut.getDisplayableValue(), "0 dB");
        assertEquals(uut.getPSNR(), 0);
    }

    @Test
    public void testConstructFromEncodedBytesMax() {
        ChipPSNR uut = new ChipPSNR(new byte[] {(byte) 0x64});
        assertEquals(uut.getDisplayName(), "Chip PSNR");
        assertEquals(uut.getDisplayableValue(), "100 dB");
        assertEquals(uut.getPSNR(), 100);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ChipPSNR(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new ChipPSNR(101);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new ChipPSNR(new byte[] {});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new ChipPSNR(new byte[] {0x01, 0x02});
    }
}

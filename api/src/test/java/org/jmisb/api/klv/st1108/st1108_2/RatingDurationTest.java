package org.jmisb.api.klv.st1108.st1108_2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for Rating Duration (ST 1108.2 Tag 7). */
public class RatingDurationTest {

    @Test
    public void testConstructFromValue() {
        RatingDuration uut = new RatingDuration(30);
        assertEquals(uut.getDisplayName(), "Rating Duration");
        assertEquals(uut.getDisplayableValue(), "30 frames");
        assertEquals(uut.getDuration(), 30);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        RatingDuration uut = new RatingDuration(new byte[] {(byte) 0x00, (byte) 0x03F});
        assertEquals(uut.getDisplayName(), "Rating Duration");
        assertEquals(uut.getDisplayableValue(), "63 frames");
        assertEquals(uut.getDuration(), 63);
    }

    @Test
    public void testConstructFromEncodedBytesMin() {
        RatingDuration uut = new RatingDuration(new byte[] {(byte) 0x00, (byte) 0x00});
        assertEquals(uut.getDisplayName(), "Rating Duration");
        assertEquals(uut.getDisplayableValue(), "0 frames");
        assertEquals(uut.getDuration(), 0);
    }

    @Test
    public void testConstructFromEncodedBytesMax() {
        RatingDuration uut = new RatingDuration(new byte[] {(byte) 0xFF, (byte) 0x0FF});
        assertEquals(uut.getDisplayName(), "Rating Duration");
        assertEquals(uut.getDisplayableValue(), "65535 frames");
        assertEquals(uut.getDuration(), 65535);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new RatingDuration(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new RatingDuration(65536);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new RatingDuration(new byte[] {0x01});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new RatingDuration(new byte[] {0x01, 0x02, 0x03});
    }
}

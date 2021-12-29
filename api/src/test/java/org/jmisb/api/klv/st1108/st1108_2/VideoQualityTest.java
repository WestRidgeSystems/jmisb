package org.jmisb.api.klv.st1108.st1108_2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for Video Quality (ST 1108.2 Tag 2). */
public class VideoQualityTest {

    @Test
    public void testConstructFromValue() {
        VideoQuality uut = new VideoQuality(3);
        assertEquals(uut.getDisplayName(), "Video Quality");
        assertEquals(uut.getDisplayableValue(), "3");
        assertEquals(uut.getQuality(), 3);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        VideoQuality uut = new VideoQuality(new byte[] {(byte) 0x03});
        assertEquals(uut.getDisplayName(), "Video Quality");
        assertEquals(uut.getDisplayableValue(), "3");
        assertEquals(uut.getQuality(), 3);
    }

    @Test
    public void testConstructFromEncodedBytesMin() {
        VideoQuality uut = new VideoQuality(new byte[] {(byte) 0x00});
        assertEquals(uut.getDisplayName(), "Video Quality");
        assertEquals(uut.getDisplayableValue(), "0");
        assertEquals(uut.getQuality(), 0);
    }

    @Test
    public void testConstructFromEncodedBytesMax() {
        VideoQuality uut = new VideoQuality(new byte[] {(byte) 0x64});
        assertEquals(uut.getDisplayName(), "Video Quality");
        assertEquals(uut.getDisplayableValue(), "100");
        assertEquals(uut.getQuality(), 100);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new VideoQuality(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new VideoQuality(101);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new VideoQuality(new byte[] {});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new VideoQuality(new byte[] {0x01, 0x02});
    }
}

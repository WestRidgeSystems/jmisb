package org.jmisb.api.klv.st1108.st1108_2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for Video Interpretability (ST 1108.2 Tag 2). */
public class VideoInterpretabilityTest {

    @Test
    public void testConstructFromValue() {
        VideoInterpretability uut = new VideoInterpretability(3);
        assertEquals(uut.getDisplayName(), "Video Interpretability");
        assertEquals(uut.getDisplayableValue(), "3");
        assertEquals(uut.getInterpretability(), 3);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        VideoInterpretability uut = new VideoInterpretability(new byte[] {(byte) 0x03});
        assertEquals(uut.getDisplayName(), "Video Interpretability");
        assertEquals(uut.getDisplayableValue(), "3");
        assertEquals(uut.getInterpretability(), 3);
    }

    @Test
    public void testConstructFromEncodedBytesMin() {
        VideoInterpretability uut = new VideoInterpretability(new byte[] {(byte) 0x00});
        assertEquals(uut.getDisplayName(), "Video Interpretability");
        assertEquals(uut.getDisplayableValue(), "0");
        assertEquals(uut.getInterpretability(), 0);
    }

    @Test
    public void testConstructFromEncodedBytesMax() {
        VideoInterpretability uut = new VideoInterpretability(new byte[] {(byte) 0x0e});
        assertEquals(uut.getDisplayName(), "Video Interpretability");
        assertEquals(uut.getDisplayableValue(), "14");
        assertEquals(uut.getInterpretability(), 14);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new VideoInterpretability(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new VideoInterpretability(15);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new VideoInterpretability(new byte[] {});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new VideoInterpretability(new byte[] {0x01, 0x02});
    }
}

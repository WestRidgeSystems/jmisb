package org.jmisb.api.klv.st1108.st1108_2;

import static org.testng.Assert.*;

import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.testng.annotations.Test;

/** Tests for Most Recent Frame Time (ST 1108.2 Tag 1). */
public class MostRecentFrameTimeTest {

    @Test
    public void testConstructFromValue() {
        MostRecentFrameTime uut = new MostRecentFrameTime(new ST0603TimeStamp(0));
        assertEquals(uut.getDisplayName(), "Most Recent Frame Time");
        assertEquals(uut.getDisplayableValue(), "0");
        assertEquals(uut.getTime().getMicroseconds(), 0);
    }

    @Test
    public void testConstructFromBytes() {
        MostRecentFrameTime uut =
                new MostRecentFrameTime(
                        new byte[] {
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x08
                        });
        assertEquals(uut.getDisplayName(), "Most Recent Frame Time");
        assertEquals(uut.getDisplayableValue(), "8");
        assertEquals(uut.getTime().getMicroseconds(), 8);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new MostRecentFrameTime(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new MostRecentFrameTime(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new MostRecentFrameTime(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x8, 0x09});
    }
}

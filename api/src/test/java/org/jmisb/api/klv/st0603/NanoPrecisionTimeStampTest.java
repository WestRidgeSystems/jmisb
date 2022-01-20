package org.jmisb.api.klv.st0603;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * Unit tests for NanoPrecisionTimeStampTest.
 *
 * <p>This is a combination of made up values, and checks from ST 0603.5 Section 7.3 (which
 * generally have "7_3" in the test name).
 */
public class NanoPrecisionTimeStampTest {

    public NanoPrecisionTimeStampTest() {}

    @Test
    public void constructFromValue() {
        NanoPrecisionTimeStamp uut = new NanoPrecisionTimeStamp(1641720845123456789l);
        assertEquals(uut.getDisplayName(), "Nano Precision Time Stamp");
        assertEquals(uut.getDisplayableValue(), "1641720845123456789 ns");
        assertEquals(uut.getNanoseconds(), 1641720845123456789l);
        assertEquals(
                uut.getBytes(),
                new byte[] {0x16, (byte) 0xC8, (byte) 0x90, 0x69, 0x11, (byte) 0xF0, 0x0F, 0x15});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructFromValueBad() {
        new NanoPrecisionTimeStamp(-1);
    }

    @Test
    public void constructFromBytes() {
        NanoPrecisionTimeStamp uut =
                new NanoPrecisionTimeStamp(
                        new byte[] {
                            0x16, (byte) 0xC8, (byte) 0x90, 0x69, 0x11, (byte) 0xF0, 0x0F, 0x15
                        },
                        0);
        assertEquals(uut.getDisplayName(), "Nano Precision Time Stamp");
        assertEquals(uut.getDisplayableValue(), "1641720845123456789 ns");
        assertEquals(uut.getNanoseconds(), 1641720845123456789l);
        assertEquals(
                uut.getBytes(),
                new byte[] {0x16, (byte) 0xC8, (byte) 0x90, 0x69, 0x11, (byte) 0xF0, 0x0F, 0x15});
    }

    @Test
    public void constructFromBytesOffset() {
        NanoPrecisionTimeStamp uut =
                new NanoPrecisionTimeStamp(
                        new byte[] {
                            0x34,
                            0x56,
                            0x16,
                            (byte) 0xC8,
                            (byte) 0x90,
                            0x69,
                            0x11,
                            (byte) 0xF0,
                            0x0F,
                            0x15
                        },
                        2);
        assertEquals(uut.getDisplayName(), "Nano Precision Time Stamp");
        assertEquals(uut.getDisplayableValue(), "1641720845123456789 ns");
        assertEquals(uut.getNanoseconds(), 1641720845123456789l);
        assertEquals(
                uut.getBytes(),
                new byte[] {0x16, (byte) 0xC8, (byte) 0x90, 0x69, 0x11, (byte) 0xF0, 0x0F, 0x15});
    }

    @Test
    public void checkToPrecisionTimeStamp_73_7A2C() {
        NanoPrecisionTimeStamp uut = new NanoPrecisionTimeStamp(0x7A2C);
        assertEquals(uut.getDisplayName(), "Nano Precision Time Stamp");
        assertEquals(uut.getDisplayableValue(), "31276 ns");
        assertEquals(uut.getNanoseconds(), 31276);
        assertEquals(uut.toPrecisionTimeStamp().getMicroseconds(), 31);
        assertEquals(uut.toPrecisionTimeStamp().getMicroseconds(), 0x1F);
    }

    @Test
    public void checkToPrecisionTimeStamp_73_9211DF() {
        NanoPrecisionTimeStamp uut = new NanoPrecisionTimeStamp(0x009211DF);
        assertEquals(uut.getDisplayName(), "Nano Precision Time Stamp");
        assertEquals(uut.getDisplayableValue(), "9572831 ns");
        assertEquals(uut.getNanoseconds(), 9572831);
        assertEquals(uut.toPrecisionTimeStamp().getMicroseconds(), 9573);
        assertEquals(uut.toPrecisionTimeStamp().getMicroseconds(), 0x00002565);
    }

    @Test
    public void checkToPrecisionTimeStamp_73_921118() {
        NanoPrecisionTimeStamp uut = new NanoPrecisionTimeStamp(0x00921118);
        assertEquals(uut.getDisplayName(), "Nano Precision Time Stamp");
        assertEquals(uut.getDisplayableValue(), "9572632 ns");
        assertEquals(uut.getNanoseconds(), 9572632);
        assertEquals(uut.toPrecisionTimeStamp().getMicroseconds(), 9573);
        assertEquals(uut.toPrecisionTimeStamp().getMicroseconds(), 0x00002565);
    }
}

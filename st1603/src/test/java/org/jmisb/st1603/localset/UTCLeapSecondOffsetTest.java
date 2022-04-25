package org.jmisb.st1603.localset;

import org.jmisb.st1603.localset.UTCLeapSecondOffset;
import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for UTCLeapSecondOffset. */
public class UTCLeapSecondOffsetTest {

    @Test
    public void checkCreateFromValue() {
        UTCLeapSecondOffset uut = new UTCLeapSecondOffset(37);
        assertEquals(uut.getLeapSecondOffset(), 37l);
        assertEquals(uut.getDisplayName(), "UTC Leap Second Offset");
        assertEquals(uut.getDisplayableValue(), "37 seconds");
        assertEquals(uut.getBytes(), new byte[] {0x25});
    }

    @Test
    public void checkCreateFromValueNegative() {
        UTCLeapSecondOffset uut = new UTCLeapSecondOffset(-2);
        assertEquals(uut.getLeapSecondOffset(), -2l);
        assertEquals(uut.getDisplayName(), "UTC Leap Second Offset");
        assertEquals(uut.getDisplayableValue(), "-2 seconds");
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xFE});
    }

    @Test
    public void checkCreateFromBytes() throws KlvParseException {
        UTCLeapSecondOffset uut = new UTCLeapSecondOffset(new byte[] {0x26});
        assertEquals(uut.getLeapSecondOffset(), 38l);
        assertEquals(uut.getDisplayName(), "UTC Leap Second Offset");
        assertEquals(uut.getDisplayableValue(), "38 seconds");
        assertEquals(uut.getBytes(), new byte[] {0x26});
    }

    @Test
    public void checkCreateFromBytesNegative() throws KlvParseException {
        UTCLeapSecondOffset uut = new UTCLeapSecondOffset(new byte[] {(byte) 0xFD});
        assertEquals(uut.getLeapSecondOffset(), -3l);
        assertEquals(uut.getDisplayName(), "UTC Leap Second Offset");
        assertEquals(uut.getDisplayableValue(), "-3 seconds");
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xFD});
    }

    @Test
    public void checkCreateFromMultiBytes() throws KlvParseException {
        UTCLeapSecondOffset uut = new UTCLeapSecondOffset(new byte[] {0x10, 0x20, 0x30});
        assertEquals(uut.getLeapSecondOffset(), 1056816L);
        assertEquals(uut.getDisplayName(), "UTC Leap Second Offset");
        assertEquals(uut.getDisplayableValue(), "1056816 seconds");
        assertEquals(uut.getBytes(), new byte[] {0x10, 0x20, 0x30});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkCreateFromBytesEmpty() throws KlvParseException {
        new UTCLeapSecondOffset(new byte[0]);
    }
}

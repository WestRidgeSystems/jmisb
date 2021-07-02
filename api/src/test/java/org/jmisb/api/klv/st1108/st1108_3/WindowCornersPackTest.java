package org.jmisb.api.klv.st1108.st1108_3;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for Window Corners Pack (ST 1108 Tag 3). */
public class WindowCornersPackTest {

    @Test
    public void testConstructFromValue() {
        WindowCornersPack uut = new WindowCornersPack(1, 2, 516, 775);
        assertEquals(uut.getBytes(), new byte[] {0x01, 0x02, (byte) 0x84, 0x04, (byte) 0x86, 0x07});
        assertEquals(uut.getDisplayName(), "Window Corners");
        assertEquals(uut.getDisplayableValue(), "[1, 2], [516, 775]");
        assertEquals(uut.getStartingRow(), 1);
        assertEquals(uut.getStartingColumn(), 2);
        assertEquals(uut.getEndingRow(), 516);
        assertEquals(uut.getEndingColumn(), 775);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        WindowCornersPack uut =
                new WindowCornersPack(
                        new byte[] {0x01, 0x02, (byte) 0x84, 0x04, (byte) 0x86, 0x07});
        assertEquals(uut.getBytes(), new byte[] {0x01, 0x02, (byte) 0x84, 0x04, (byte) 0x86, 0x07});
        assertEquals(uut.getDisplayName(), "Window Corners");
        assertEquals(uut.getDisplayableValue(), "[1, 2], [516, 775]");
        assertEquals(uut.getStartingRow(), 1);
        assertEquals(uut.getStartingColumn(), 2);
        assertEquals(uut.getEndingRow(), 516);
        assertEquals(uut.getEndingColumn(), 775);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badArrayLengthShort() throws KlvParseException {
        new WindowCornersPack(new byte[] {0x01, 0x02, (byte) 0x82, 0x04, (byte) 0x83});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badArrayLengthLong() throws KlvParseException {
        new WindowCornersPack(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }
}

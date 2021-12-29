package org.jmisb.api.klv.st1108.st1108_2;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for Chip Location, Size and Bit Depth (ST 1108.2 Tag 9). */
public class ChipLocationSizeBitDepthTest {

    @Test
    public void testConstructFromValue() {
        ChipLocationSizeBitDepth uut = new ChipLocationSizeBitDepth(30, 40, 64, 16);
        assertEquals(uut.getDisplayName(), "Chip Location, Size & Bit Depth");
        assertEquals(uut.getDisplayableValue(), "[30, 40], 64x64, 16");
        assertEquals(uut.getColumnIndex(), 30);
        assertEquals(uut.getRowIndex(), 40);
        assertEquals(uut.getChipLength(), 64);
        assertEquals(uut.getBitDepth(), 16);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badArrayLengthShort() throws KlvParseException {
        new ChipLocationSizeBitDepth(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badArrayLengthLong() throws KlvParseException {
        new ChipLocationSizeBitDepth(
                new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09});
    }
}

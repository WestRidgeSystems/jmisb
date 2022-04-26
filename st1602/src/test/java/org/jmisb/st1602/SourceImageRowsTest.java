package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Source Image Rows. */
public class SourceImageRowsTest {
    @Test
    public void testConstructFromValue() {
        SourceImageRows uut = new SourceImageRows(1200);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x04, (byte) 0xB0});
        assertEquals(uut.getDisplayName(), "Source Image Rows");
        assertEquals(uut.getDisplayableValue(), "1200px");
        assertEquals(uut.getRows(), 1200);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        SourceImageRows uut = new SourceImageRows(new byte[] {(byte) 0x04, (byte) 0xB0});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x04, (byte) 0xB0});
        assertEquals(uut.getDisplayName(), "Source Image Rows");
        assertEquals(uut.getDisplayableValue(), "1200px");
        assertEquals(uut.getRows(), 1200);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SourceImageRows(-1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new SourceImageRows(
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
    }
}

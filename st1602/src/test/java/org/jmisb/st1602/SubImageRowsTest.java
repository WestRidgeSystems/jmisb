package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Sub-Image Rows. */
public class SubImageRowsTest {
    @Test
    public void testConstructFromValue() {
        SubImageRows uut = new SubImageRows(400);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01, (byte) 0x90});
        assertEquals(uut.getDisplayName(), "Sub-Image Rows");
        assertEquals(uut.getDisplayableValue(), "400px");
        assertEquals(uut.getRows(), 400);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        SubImageRows uut = new SubImageRows(new byte[] {(byte) 0x01, (byte) 0x90});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01, (byte) 0x90});
        assertEquals(uut.getDisplayName(), "Sub-Image Rows");
        assertEquals(uut.getDisplayableValue(), "400px");
        assertEquals(uut.getRows(), 400);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SubImageRows(-1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new SubImageRows(
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
    }
}

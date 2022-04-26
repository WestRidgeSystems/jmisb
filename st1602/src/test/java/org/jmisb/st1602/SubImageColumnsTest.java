package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Sub-Image Columns. */
public class SubImageColumnsTest {
    @Test
    public void testConstructFromValue() {
        SubImageColumns uut = new SubImageColumns(600);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x02, (byte) 0x58});
        assertEquals(uut.getDisplayName(), "Sub-Image Columns");
        assertEquals(uut.getDisplayableValue(), "600px");
        assertEquals(uut.getColumns(), 600);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        SubImageColumns uut = new SubImageColumns(new byte[] {(byte) 0x02, (byte) 0x58});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x02, (byte) 0x58});
        assertEquals(uut.getDisplayName(), "Sub-Image Columns");
        assertEquals(uut.getDisplayableValue(), "600px");
        assertEquals(uut.getColumns(), 600);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SubImageColumns(-1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new SubImageColumns(
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
    }
}

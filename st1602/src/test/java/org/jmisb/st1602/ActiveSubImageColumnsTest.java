package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Active Sub-Image Columns. */
public class ActiveSubImageColumnsTest {
    @Test
    public void testConstructFromValue() {
        ActiveSubImageColumns uut = new ActiveSubImageColumns(600);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x02, (byte) 0x58});
        assertEquals(uut.getDisplayName(), "Active Sub-Image Columns");
        assertEquals(uut.getDisplayableValue(), "600px");
        assertEquals(uut.getColumns(), 600);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        ActiveSubImageColumns uut =
                new ActiveSubImageColumns(new byte[] {(byte) 0x02, (byte) 0x58});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x02, (byte) 0x58});
        assertEquals(uut.getDisplayName(), "Active Sub-Image Columns");
        assertEquals(uut.getDisplayableValue(), "600px");
        assertEquals(uut.getColumns(), 600);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ActiveSubImageColumns(-1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new ActiveSubImageColumns(
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
    }
}

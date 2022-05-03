package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Source Image Columns. */
public class SourceImageColumnsTest {
    @Test
    public void testConstructFromValue() {
        SourceImageColumns uut = new SourceImageColumns(1400);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x05, (byte) 0x78});
        assertEquals(uut.getDisplayName(), "Source Image Columns");
        assertEquals(uut.getDisplayableValue(), "1400px");
        assertEquals(uut.getColumns(), 1400);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        SourceImageColumns uut = new SourceImageColumns(new byte[] {(byte) 0x05, (byte) 0x78});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x05, (byte) 0x78});
        assertEquals(uut.getDisplayName(), "Source Image Columns");
        assertEquals(uut.getDisplayableValue(), "1400px");
        assertEquals(uut.getColumns(), 1400);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SourceImageColumns(-1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new SourceImageColumns(
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
    }
}

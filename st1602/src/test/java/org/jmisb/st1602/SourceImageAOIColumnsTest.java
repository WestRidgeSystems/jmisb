package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Source Image AOI Columns. */
public class SourceImageAOIColumnsTest {
    @Test
    public void testConstructFromValue() {
        SourceImageAOIColumns uut = new SourceImageAOIColumns(500);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01, (byte) 0xf4});
        assertEquals(uut.getDisplayName(), "Source Image AOI Columns");
        assertEquals(uut.getDisplayableValue(), "500px");
        assertEquals(uut.getColumns(), 500);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        SourceImageAOIColumns uut =
                new SourceImageAOIColumns(new byte[] {(byte) 0x01, (byte) 0xf4});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01, (byte) 0xf4});
        assertEquals(uut.getDisplayName(), "Source Image AOI Columns");
        assertEquals(uut.getDisplayableValue(), "500px");
        assertEquals(uut.getColumns(), 500);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SourceImageAOIColumns(-1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new SourceImageAOIColumns(
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
    }
}

package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Source Image AOI Rows. */
public class SourceImageAOIRowsTest {
    @Test
    public void testConstructFromValue() {
        SourceImageAOIRows uut = new SourceImageAOIRows(800);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x03, (byte) 0x20});
        assertEquals(uut.getDisplayName(), "Source Image AOI Rows");
        assertEquals(uut.getDisplayableValue(), "800px");
        assertEquals(uut.getRows(), 800);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        SourceImageAOIRows uut = new SourceImageAOIRows(new byte[] {(byte) 0x03, (byte) 0x20});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x03, (byte) 0x20});
        assertEquals(uut.getDisplayName(), "Source Image AOI Rows");
        assertEquals(uut.getDisplayableValue(), "800px");
        assertEquals(uut.getRows(), 800);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SourceImageAOIRows(-1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new SourceImageAOIRows(
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
    }
}

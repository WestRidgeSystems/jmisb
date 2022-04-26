package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Source Image AOI Y Position. */
public class SourceImageAOIPositionYTest {
    @Test
    public void testConstructFromValue() {
        SourceImageAOIPositionY uut = new SourceImageAOIPositionY(150);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x96});
        assertEquals(uut.getDisplayName(), "Source Image AOI Position Y");
        assertEquals(uut.getDisplayableValue(), "150px");
        assertEquals(uut.getPosition(), 150);
    }

    @Test
    public void testConstructFromValueNegative() {
        SourceImageAOIPositionY uut = new SourceImageAOIPositionY(-150);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xFF, (byte) 0x6a});
        assertEquals(uut.getDisplayName(), "Source Image AOI Position Y");
        assertEquals(uut.getDisplayableValue(), "-150px");
        assertEquals(uut.getPosition(), -150);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        SourceImageAOIPositionY uut =
                new SourceImageAOIPositionY(new byte[] {(byte) 0x00, (byte) 0x96});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x96});
        assertEquals(uut.getDisplayName(), "Source Image AOI Position Y");
        assertEquals(uut.getDisplayableValue(), "150px");
        assertEquals(uut.getPosition(), 150);
    }

    @Test
    public void testConstructFromBytesNegative() throws KlvParseException {
        SourceImageAOIPositionY uut =
                new SourceImageAOIPositionY(new byte[] {(byte) 0xff, (byte) 0x6a});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xff, (byte) 0x6a});
        assertEquals(uut.getDisplayName(), "Source Image AOI Position Y");
        assertEquals(uut.getDisplayableValue(), "-150px");
        assertEquals(uut.getPosition(), -150);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new SourceImageAOIPositionY(
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
    }
}

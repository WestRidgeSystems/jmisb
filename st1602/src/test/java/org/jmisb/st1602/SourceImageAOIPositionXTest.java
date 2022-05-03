package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Source Image AOI X Position. */
public class SourceImageAOIPositionXTest {
    @Test
    public void testConstructFromValue() {
        SourceImageAOIPositionX uut = new SourceImageAOIPositionX(20);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x14});
        assertEquals(uut.getDisplayName(), "Source Image AOI Position X");
        assertEquals(uut.getDisplayableValue(), "20px");
        assertEquals(uut.getPosition(), 20);
    }

    @Test
    public void testConstructFromValueNegative() {
        SourceImageAOIPositionX uut = new SourceImageAOIPositionX(-20);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xec});
        assertEquals(uut.getDisplayName(), "Source Image AOI Position X");
        assertEquals(uut.getDisplayableValue(), "-20px");
        assertEquals(uut.getPosition(), -20);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        SourceImageAOIPositionX uut = new SourceImageAOIPositionX(new byte[] {(byte) 0x14});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x14});
        assertEquals(uut.getDisplayName(), "Source Image AOI Position X");
        assertEquals(uut.getDisplayableValue(), "20px");
        assertEquals(uut.getPosition(), 20);
    }

    @Test
    public void testConstructFromBytesNegative() throws KlvParseException {
        SourceImageAOIPositionX uut = new SourceImageAOIPositionX(new byte[] {(byte) 0xec});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xec});
        assertEquals(uut.getDisplayName(), "Source Image AOI Position X");
        assertEquals(uut.getDisplayableValue(), "-20px");
        assertEquals(uut.getPosition(), -20);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new SourceImageAOIPositionX(
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
    }
}

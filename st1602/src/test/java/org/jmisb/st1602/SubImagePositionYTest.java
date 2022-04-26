package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Sub-Image Position Y. */
public class SubImagePositionYTest {
    @Test
    public void testConstructFromValue() {
        SubImagePositionY uut = new SubImagePositionY(220);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0xdc});
        assertEquals(uut.getDisplayName(), "Sub-Image Position Y");
        assertEquals(uut.getDisplayableValue(), "220px");
        assertEquals(uut.getPosition(), 220);
    }

    @Test
    public void testConstructFromValueNegative() {
        SubImagePositionY uut = new SubImagePositionY(-100);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x9c});
        assertEquals(uut.getDisplayName(), "Sub-Image Position Y");
        assertEquals(uut.getDisplayableValue(), "-100px");
        assertEquals(uut.getPosition(), -100);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        SubImagePositionY uut = new SubImagePositionY(new byte[] {(byte) 0x00, (byte) 0xdc});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0xdc});
        assertEquals(uut.getDisplayName(), "Sub-Image Position Y");
        assertEquals(uut.getDisplayableValue(), "220px");
        assertEquals(uut.getPosition(), 220);
    }

    @Test
    public void testConstructFromBytesNegative() throws KlvParseException {
        SubImagePositionY uut = new SubImagePositionY(new byte[] {(byte) 0x9c});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x9c});
        assertEquals(uut.getDisplayName(), "Sub-Image Position Y");
        assertEquals(uut.getDisplayableValue(), "-100px");
        assertEquals(uut.getPosition(), -100);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new SubImagePositionY(
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
    }
}

package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Sub-Image Position X. */
public class SubImagePositionXTest {
    @Test
    public void testConstructFromValue() {
        SubImagePositionX uut = new SubImagePositionX(380);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01, (byte) 0x7C});
        assertEquals(uut.getDisplayName(), "Sub-Image Position X");
        assertEquals(uut.getDisplayableValue(), "380px");
        assertEquals(uut.getPosition(), 380);
    }

    @Test
    public void testConstructFromValueNegative() {
        SubImagePositionX uut = new SubImagePositionX(-10);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xf6});
        assertEquals(uut.getDisplayName(), "Sub-Image Position X");
        assertEquals(uut.getDisplayableValue(), "-10px");
        assertEquals(uut.getPosition(), -10);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        SubImagePositionX uut = new SubImagePositionX(new byte[] {(byte) 0x01, (byte) 0x7C});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01, (byte) 0x7C});
        assertEquals(uut.getDisplayName(), "Sub-Image Position X");
        assertEquals(uut.getDisplayableValue(), "380px");
        assertEquals(uut.getPosition(), 380);
    }

    @Test
    public void testConstructFromBytesNegative() throws KlvParseException {
        SubImagePositionX uut = new SubImagePositionX(new byte[] {(byte) 0xf6});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xf6});
        assertEquals(uut.getDisplayName(), "Sub-Image Position X");
        assertEquals(uut.getDisplayableValue(), "-10px");
        assertEquals(uut.getPosition(), -10);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new SubImagePositionX(
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
    }
}

package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Active Sub-Image Offset X. */
public class ActiveSubImageOffsetXTest {
    @Test
    public void testConstructFromValue() {
        ActiveSubImageOffsetX uut = new ActiveSubImageOffsetX(380);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01, (byte) 0x7C});
        assertEquals(uut.getDisplayName(), "Active Sub-Image Offset X");
        assertEquals(uut.getDisplayableValue(), "380px");
        assertEquals(uut.getOffset(), 380);
    }

    @Test
    public void testConstructFromValueNegative() {
        ActiveSubImageOffsetX uut = new ActiveSubImageOffsetX(-10);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xf6});
        assertEquals(uut.getDisplayName(), "Active Sub-Image Offset X");
        assertEquals(uut.getDisplayableValue(), "-10px");
        assertEquals(uut.getOffset(), -10);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        ActiveSubImageOffsetX uut =
                new ActiveSubImageOffsetX(new byte[] {(byte) 0x01, (byte) 0x7C});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01, (byte) 0x7C});
        assertEquals(uut.getDisplayName(), "Active Sub-Image Offset X");
        assertEquals(uut.getDisplayableValue(), "380px");
        assertEquals(uut.getOffset(), 380);
    }

    @Test
    public void testConstructFromBytesNegative() throws KlvParseException {
        ActiveSubImageOffsetX uut = new ActiveSubImageOffsetX(new byte[] {(byte) 0xf6});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xf6});
        assertEquals(uut.getDisplayName(), "Active Sub-Image Offset X");
        assertEquals(uut.getDisplayableValue(), "-10px");
        assertEquals(uut.getOffset(), -10);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new ActiveSubImageOffsetX(
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
    }
}

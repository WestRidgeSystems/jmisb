package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Active Sub-Image Offset Y. */
public class ActiveSubImageOffsetYTest {
    @Test
    public void testConstructFromValue() {
        ActiveSubImageOffsetY uut = new ActiveSubImageOffsetY(220);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0xdc});
        assertEquals(uut.getDisplayName(), "Active Sub-Image Offset Y");
        assertEquals(uut.getDisplayableValue(), "220px");
        assertEquals(uut.getOffset(), 220);
    }

    @Test
    public void testConstructFromValueNegative() {
        ActiveSubImageOffsetY uut = new ActiveSubImageOffsetY(-100);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x9c});
        assertEquals(uut.getDisplayName(), "Active Sub-Image Offset Y");
        assertEquals(uut.getDisplayableValue(), "-100px");
        assertEquals(uut.getOffset(), -100);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        ActiveSubImageOffsetY uut =
                new ActiveSubImageOffsetY(new byte[] {(byte) 0x00, (byte) 0xdc});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0xdc});
        assertEquals(uut.getDisplayName(), "Active Sub-Image Offset Y");
        assertEquals(uut.getDisplayableValue(), "220px");
        assertEquals(uut.getOffset(), 220);
    }

    @Test
    public void testConstructFromBytesNegative() throws KlvParseException {
        ActiveSubImageOffsetY uut = new ActiveSubImageOffsetY(new byte[] {(byte) 0x9c});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x9c});
        assertEquals(uut.getDisplayName(), "Active Sub-Image Offset Y");
        assertEquals(uut.getDisplayableValue(), "-100px");
        assertEquals(uut.getOffset(), -100);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new ActiveSubImageOffsetY(
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
    }
}

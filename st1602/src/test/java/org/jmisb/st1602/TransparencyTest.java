package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Transparency. */
public class TransparencyTest {
    @Test
    public void testConstructFromValue() {
        Transparency version = new Transparency(1);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x01});
        assertEquals(version.getDisplayName(), "Transparency");
        assertEquals(version.getDisplayableValue(), "1");
        assertEquals(version.getTransparency(), 1);
    }

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException {
        Transparency version = new Transparency(new byte[] {(byte) 0x02});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x02});
        assertEquals(version.getDisplayName(), "Transparency");
        assertEquals(version.getDisplayableValue(), "2");
        assertEquals(version.getTransparency(), 2);
    }

    @Test
    public void testConstructFromEncodedBytes0() throws KlvParseException {
        Transparency version = new Transparency(new byte[] {(byte) 0x00});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(version.getDisplayName(), "Transparency");
        assertEquals(version.getDisplayableValue(), "0");
        assertEquals(version.getTransparency(), 0);
    }

    @Test
    public void testConstructFromEncodedBytes255() throws KlvParseException {
        Transparency version = new Transparency(new byte[] {(byte) 0xFF});
        assertEquals(version.getBytes(), new byte[] {(byte) 0xFF});
        assertEquals(version.getDisplayName(), "Transparency");
        assertEquals(version.getDisplayableValue(), "255");
        assertEquals(version.getTransparency(), 255);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new Transparency(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLarge() {
        new Transparency(256);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new Transparency(new byte[] {(byte) 0x00, (byte) 0x01});
    }
}

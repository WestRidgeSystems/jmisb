package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Z-Order. */
public class ZOrderTest {
    @Test
    public void testConstructFromValue() {
        ZOrder version = new ZOrder(1);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x01});
        assertEquals(version.getDisplayName(), "Z-Order");
        assertEquals(version.getDisplayableValue(), "1");
        assertEquals(version.getValue(), 1);
    }

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException {
        ZOrder version = new ZOrder(new byte[] {(byte) 0x02});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x02});
        assertEquals(version.getDisplayName(), "Z-Order");
        assertEquals(version.getDisplayableValue(), "2");
        assertEquals(version.getValue(), 2);
    }

    @Test
    public void testConstructFromEncodedBytes0() throws KlvParseException {
        ZOrder version = new ZOrder(new byte[] {(byte) 0x00});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(version.getDisplayName(), "Z-Order");
        assertEquals(version.getDisplayableValue(), "0");
        assertEquals(version.getValue(), 0);
    }

    @Test
    public void testConstructFromEncodedBytes255() throws KlvParseException {
        ZOrder version = new ZOrder(new byte[] {(byte) 0xFF});
        assertEquals(version.getBytes(), new byte[] {(byte) 0xFF});
        assertEquals(version.getDisplayName(), "Z-Order");
        assertEquals(version.getDisplayableValue(), "255");
        assertEquals(version.getValue(), 255);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ZOrder(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLarge() {
        new ZOrder(256);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new ZOrder(new byte[] {(byte) 0x00, (byte) 0x01});
    }
}

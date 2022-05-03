package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1602 Document Version. */
public class ST1602DocumentVersionTest {
    @Test
    public void testConstructFromValue() {
        ST1602DocumentVersion version = new ST1602DocumentVersion(1);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x01});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1602.1");
        assertEquals(version.getVersion(), 1);
    }

    @Test
    public void testConstructFromValue127() {
        ST1602DocumentVersion version = new ST1602DocumentVersion(127);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x7F});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1602.127");
        assertEquals(version.getVersion(), 127);
    }

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException {
        ST1602DocumentVersion version = new ST1602DocumentVersion(new byte[] {(byte) 0x02});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x02});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1602.2");
        assertEquals(version.getVersion(), 2);
    }

    @Test
    public void testConstructFromEncodedBytes0() throws KlvParseException {
        ST1602DocumentVersion version = new ST1602DocumentVersion(new byte[] {(byte) 0x00});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1602.0");
        assertEquals(version.getVersion(), 0);
    }

    @Test
    public void testConstructFromEncodedBytes255() throws KlvParseException {
        ST1602DocumentVersion version =
                new ST1602DocumentVersion(new byte[] {(byte) 0x81, (byte) 0x7F});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x81, (byte) 0x7F});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1602.255");
        assertEquals(version.getVersion(), 255);
    }

    @Test
    public void testConstructFromEncodedBytes256() throws KlvParseException {
        ST1602DocumentVersion version =
                new ST1602DocumentVersion(new byte[] {(byte) 0x82, (byte) 0x00});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x82, (byte) 0x00});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1602.256");
        assertEquals(version.getVersion(), 256);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ST1602DocumentVersion(-1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadBytes() throws KlvParseException {
        new ST1602DocumentVersion(new byte[] {(byte) 0x80});
    }
}

package org.jmisb.st1601;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1601 Document Version. */
public class ST1601DocumentVersionTest {
    @Test
    public void testConstructFromValue() {
        ST1601DocumentVersion version = new ST1601DocumentVersion(1);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x01});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1601.1");
        assertEquals(version.getVersion(), 1);
    }

    @Test
    public void testConstructFromValue255() {
        ST1601DocumentVersion version = new ST1601DocumentVersion(255);
        assertEquals(version.getBytes(), new byte[] {(byte) 0xFF});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1601.255");
        assertEquals(version.getVersion(), 255);
    }

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException {
        ST1601DocumentVersion version = new ST1601DocumentVersion(new byte[] {(byte) 0x02});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x02});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1601.2");
        assertEquals(version.getVersion(), 2);
    }

    @Test
    public void testConstructFromEncodedBytes0() throws KlvParseException {
        ST1601DocumentVersion version = new ST1601DocumentVersion(new byte[] {(byte) 0x00});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1601.0");
        assertEquals(version.getVersion(), 0);
    }

    @Test
    public void testConstructFromEncodedBytes255() throws KlvParseException {
        ST1601DocumentVersion version = new ST1601DocumentVersion(new byte[] {(byte) 0xFF});
        assertEquals(version.getBytes(), new byte[] {(byte) 0xFF});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1601.255");
        assertEquals(version.getVersion(), 255);
    }

    @Test
    public void testConstructFromEncodedBytes256() throws KlvParseException {
        ST1601DocumentVersion version =
                new ST1601DocumentVersion(new byte[] {(byte) 0x01, (byte) 0x00});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x01, (byte) 0x00});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1601.256");
        assertEquals(version.getVersion(), 256);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ST1601DocumentVersion(-1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badArrayLength() throws KlvParseException {
        new ST1601DocumentVersion(new byte[] {0x01, 0x02, 0x03});
    }
}

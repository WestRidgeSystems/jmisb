package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1206 Document Version (ST1206 Tag 28). */
public class DocumentVersionTest {
    @Test
    public void testConstructFromValue() {
        DocumentVersion version = new DocumentVersion(1);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x01});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST1206.1");
        assertEquals(version.getVersion(), 1);
    }

    @Test
    public void testConstructFromValue255() {
        DocumentVersion version = new DocumentVersion(255);
        assertEquals(version.getBytes(), new byte[] {(byte) 0xFF});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST1206.255");
        assertEquals(version.getVersion(), 255);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        DocumentVersion version = new DocumentVersion(new byte[] {(byte) 0x04});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x04});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST1206.4");
        assertEquals(version.getVersion(), 4);
    }

    @Test
    public void testConstructFromEncodedBytes0() {
        DocumentVersion version = new DocumentVersion(new byte[] {(byte) 0x00});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST1206.0");
        assertEquals(version.getVersion(), 0);
    }

    @Test
    public void testConstructFromEncodedBytes255() {
        DocumentVersion version = new DocumentVersion(new byte[] {(byte) 0xFF});
        assertEquals(version.getBytes(), new byte[] {(byte) 0xFF});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST1206.255");
        assertEquals(version.getVersion(), 255);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.DocumentVersion, new byte[] {(byte) 0x01});
        assertTrue(value instanceof DocumentVersion);
        DocumentVersion version = (DocumentVersion) value;
        assertEquals(version.getBytes(), new byte[] {(byte) 0x01});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST1206.1");
        assertEquals(version.getVersion(), 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new DocumentVersion(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new DocumentVersion(256);
        ;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new DocumentVersion(new byte[] {0x01, 0x02});
    }
}

package org.jmisb.st1202;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1202 Document Version. */
public class ST1202DocumentVersionTest {
    @Test
    public void testConstructFromValue() {
        ST1202DocumentVersion version = new ST1202DocumentVersion(1);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x01});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1202.1");
        assertEquals(version.getVersion(), 1);
    }

    @Test
    public void testConstructFromValue127() {
        ST1202DocumentVersion version = new ST1202DocumentVersion(127);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x7f});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1202.127");
        assertEquals(version.getVersion(), 127);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        ST1202DocumentVersion version = new ST1202DocumentVersion(new byte[] {(byte) 0x02});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x02});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1202.2");
        assertEquals(version.getVersion(), 2);
    }

    @Test
    public void testConstructFromEncodedBytes0() {
        ST1202DocumentVersion version = new ST1202DocumentVersion(new byte[] {(byte) 0x00});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1202");
        assertEquals(version.getVersion(), 0);
    }

    @Test
    public void testConstructFromEncodedBytes127() {
        ST1202DocumentVersion version = new ST1202DocumentVersion(new byte[] {(byte) 0x7F});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x7F});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1202.127");
        assertEquals(version.getVersion(), 127);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IGeneralizedTransformationMetadataValue value =
                GeneralizedTransformationLocalSet.createValue(
                        GeneralizedTransformationParametersKey.DocumentVersion,
                        new byte[] {(byte) 0x02});
        assertTrue(value instanceof ST1202DocumentVersion);
        ST1202DocumentVersion version = (ST1202DocumentVersion) value;
        assertEquals(version.getBytes(), new byte[] {(byte) 0x02});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1202.2");
        assertEquals(version.getVersion(), 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ST1202DocumentVersion(-1);
    }
}

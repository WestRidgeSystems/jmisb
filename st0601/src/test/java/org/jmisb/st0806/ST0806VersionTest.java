package org.jmisb.st0806;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST0806Version (ST0806 Tag 8). */
public class ST0806VersionTest {
    @Test
    public void testConstructFromValue() {
        ST0806Version version = new ST0806Version(4);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x04});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0806.4");
        assertEquals(version.getVersion(), 4);
    }

    @Test
    public void testConstructFromValue128() {
        ST0806Version version = new ST0806Version(128);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x80});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0806.128");
        assertEquals(version.getVersion(), 128);
    }

    @Test
    public void testConstructFromValue255() {
        ST0806Version version = new ST0806Version(255);
        assertEquals(version.getBytes(), new byte[] {(byte) 0xFF});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0806.255");
        assertEquals(version.getVersion(), 255);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        ST0806Version version = new ST0806Version(new byte[] {(byte) 0x04});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x04});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0806.4");
        assertEquals(version.getVersion(), 4);
    }

    @Test
    public void testConstructFromEncodedBytes0() {
        ST0806Version version = new ST0806Version(new byte[] {(byte) 0x00});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0806.0");
        assertEquals(version.getVersion(), 0);
    }

    @Test
    public void testConstructFromEncodedBytes255() {
        ST0806Version version = new ST0806Version(new byte[] {(byte) 0xFF});
        assertEquals(version.getBytes(), new byte[] {(byte) 0xFF});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0806.255");
        assertEquals(version.getVersion(), 255);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IRvtMetadataValue value =
                RvtLocalSet.createValue(
                        RvtMetadataKey.UASLSVersionNumber, new byte[] {(byte) 0x04});
        assertTrue(value instanceof ST0806Version);
        ST0806Version version = (ST0806Version) value;
        assertEquals(version.getBytes(), new byte[] {(byte) 0x04});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0806.4");
        assertEquals(version.getVersion(), 4);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ST0806Version(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new ST0806Version(256);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new ST0806Version(new byte[] {0x01, 0x02});
    }
}

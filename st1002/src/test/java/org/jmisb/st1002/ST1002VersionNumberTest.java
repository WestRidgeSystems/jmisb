package org.jmisb.st1002;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1002 Version Number (ST 1002 Tag 11). */
public class ST1002VersionNumberTest {
    @Test
    public void testConstructFromValue() {
        ST1002VersionNumber version = new ST1002VersionNumber(1);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x01});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST 1002.1");
        assertEquals(version.getVersion(), 1);
    }

    @Test
    public void testConstructFromValue127() {
        ST1002VersionNumber version = new ST1002VersionNumber(127);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x7f});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST 1002.127");
        assertEquals(version.getVersion(), 127);
    }

    @Test
    public void testConstructFromValue129() {
        ST1002VersionNumber version = new ST1002VersionNumber(129);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x81, (byte) 0x01});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST 1002.129");
        assertEquals(version.getVersion(), 129);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        ST1002VersionNumber version = new ST1002VersionNumber(new byte[] {(byte) 0x02});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x02});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST 1002.2");
        assertEquals(version.getVersion(), 2);
    }

    @Test
    public void testConstructFromEncodedBytes0() {
        ST1002VersionNumber version = new ST1002VersionNumber(new byte[] {(byte) 0x00});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST 1002");
        assertEquals(version.getVersion(), 0);
    }

    @Test
    public void testConstructFromEncodedBytes127() {
        ST1002VersionNumber version = new ST1002VersionNumber(new byte[] {(byte) 0x7F});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x7F});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST 1002.127");
        assertEquals(version.getVersion(), 127);
    }

    @Test
    public void testConstructFromEncodedBytes255() {
        ST1002VersionNumber version =
                new ST1002VersionNumber(new byte[] {(byte) 0x81, (byte) 0x7F});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x81, (byte) 0x7F});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST 1002.255");
        assertEquals(version.getVersion(), 255);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IRangeImageMetadataValue value =
                RangeImageLocalSet.createValue(
                        RangeImageMetadataKey.DocumentVersion, new byte[] {(byte) 0x02});
        assertTrue(value instanceof ST1002VersionNumber);
        ST1002VersionNumber version = (ST1002VersionNumber) value;
        assertEquals(version.getBytes(), new byte[] {(byte) 0x02});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST 1002.2");
        assertEquals(version.getVersion(), 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ST1002VersionNumber(-1);
    }
}

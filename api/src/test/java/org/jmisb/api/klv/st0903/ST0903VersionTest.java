package org.jmisb.api.klv.st0903;

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for ST0903Version (ST0903 Tag 4).
 */
public class ST0903VersionTest
{
    @Test
    public void testConstructFromValue()
    {
        ST0903Version version = new ST0903Version(4);
        assertEquals(version.getBytes(), new byte[]{(byte)0x04});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0903.4");
        assertEquals(version.getVersion(), 4);
    }
    
    @Test
    public void testConstructFromValue128()
    {
        ST0903Version version = new ST0903Version(128);
        assertEquals(version.getBytes(), new byte[]{(byte)0x80});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0903.128");
        assertEquals(version.getVersion(), 128);
    }

    @Test
    public void testConstructFromValue255()
    {
        ST0903Version version = new ST0903Version(255);
        assertEquals(version.getBytes(), new byte[]{(byte)0xFF});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0903.255");
        assertEquals(version.getVersion(), 255);
    }
    
    @Test
    public void testConstructFromValue256()
    {
        ST0903Version version = new ST0903Version(256);
        assertEquals(version.getBytes(), new byte[]{(byte)0x01, (byte)0x00});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0903.256");
        assertEquals(version.getVersion(), 256);
    }

    @Test
    public void testConstructFromValue65535()
    {
        ST0903Version version = new ST0903Version(65535);
        assertEquals(version.getBytes(), new byte[]{(byte)0xFF, (byte)0xFF});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0903.65535");
        assertEquals(version.getVersion(), 65535);
    }
    
    @Test
    public void testConstructFromEncodedBytes()
    {
        ST0903Version version = new ST0903Version(new byte[]{(byte)0x04});
        assertEquals(version.getBytes(), new byte[]{(byte)0x04});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0903.4");
        assertEquals(version.getVersion(), 4);
    }
    
    @Test
    public void testConstructFromEncodedBytes0()
    {
        ST0903Version version = new ST0903Version(new byte[]{(byte)0x00});
        assertEquals(version.getBytes(), new byte[]{(byte)0x00});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0903.0");
        assertEquals(version.getVersion(), 0);
    }
    
    @Test
    public void testConstructFromEncodedBytes255()
    {
        ST0903Version version = new ST0903Version(new byte[]{(byte)0xFF});
        assertEquals(version.getBytes(), new byte[]{(byte)0xFF});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0903.255");
        assertEquals(version.getVersion(), 255);
    }
    
    @Test
    public void testConstructFromEncodedBytes256()
    {
        ST0903Version version = new ST0903Version(new byte[]{(byte)0x01, (byte)0x00});
        assertEquals(version.getBytes(), new byte[]{(byte)0x01, (byte)0x00});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0903.256");
        assertEquals(version.getVersion(), 256);
    }

    @Test
    public void testConstructFromEncodedBytes65535()
    {
        ST0903Version version = new ST0903Version(new byte[]{(byte)0xFF, (byte)0xFF});
        assertEquals(version.getBytes(), new byte[]{(byte)0xFF, (byte)0xFF});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0903.65535");
        assertEquals(version.getVersion(), 65535);
    }
    
    @Test
    public void testFactoryEncodedBytes() throws KlvParseException
    {
        IVmtiMetadataValue value = VmtiLocalSet.createValue(VmtiMetadataKey.VersionNumber, new byte[]{(byte)0x04});
        assertTrue(value instanceof ST0903Version);
        ST0903Version version = (ST0903Version)value;
        assertEquals(version.getBytes(), new byte[]{(byte)0x04});
        assertEquals(version.getDisplayName(), "Version Number");
        assertEquals(version.getDisplayableValue(), "ST0903.4");
        assertEquals(version.getVersion(), 4);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ST0903Version(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new ST0903Version(65536);;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new ST0903Version(new byte[]{0x01, 0x02, 0x03});
    }
}

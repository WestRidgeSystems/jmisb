package org.jmisb.api.klv.st0102;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Unit tests for ST0102Version.
 */
public class ST0102VersionTest
{
    public ST0102VersionTest() {
    }

    @Test
    public void checkBuildFromValue()
    {
        ST0102Version version = new ST0102Version(12);
        assertEquals(version.getDisplayName(), "ST0102 Version");
        assertEquals(version.getDisplayableValue(), "12");
        assertEquals(version.getVersion(), 12);
    }

    @Test
    public void checkBuildFromEncodedValue()
    {
        ST0102Version version = new ST0102Version(new byte[]{(byte)0x00, (byte)0x0C});
        assertEquals(version.getDisplayName(), "ST0102 Version");
        assertEquals(version.getDisplayableValue(), "12");
        assertEquals(version.getVersion(), 12);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalEncodedValueTooLong() {
        byte[] bytes3 = new byte[]{(byte) 0x01, (byte) 0x02, (byte)0x03};
        new ST0102Version(bytes3);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalEncodedValueTooShort() {
        byte[] bytes1 = new byte[]{(byte) 0x01};
        new ST0102Version(bytes1);
    }
}

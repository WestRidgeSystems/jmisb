package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST0809Version. */
public class ST0809VersionTest {

    @Test
    public void fromValue() {
        ST0809Version uut = new ST0809Version(2);
        assertEquals(uut.getVersion(), 2);
        assertEquals(uut.getDisplayName(), "Version");
        assertEquals(uut.getDisplayableValue(), "ST 0809.2");
        assertEquals(uut.getBytes(), new byte[] {0x00, 0x02});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        ST0809Version uut = new ST0809Version(new byte[] {0x00, 0x02});
        assertEquals(uut.getVersion(), 2);
        assertEquals(uut.getDisplayName(), "Version");
        assertEquals(uut.getDisplayableValue(), "ST 0809.2");
        assertEquals(uut.getBytes(), new byte[] {0x00, 0x02});
    }

    @Test
    public void fromBytesMin() throws KlvParseException {
        ST0809Version uut = new ST0809Version(new byte[] {0x00, 0x00});
        assertEquals(uut.getVersion(), 0);
        assertEquals(uut.getDisplayName(), "Version");
        assertEquals(uut.getDisplayableValue(), "ST 0809.0");
        assertEquals(uut.getBytes(), new byte[] {0x00, 0x00});
    }

    @Test
    public void fromBytesMax() throws KlvParseException {
        ST0809Version uut = new ST0809Version(new byte[] {(byte) 0xFF, (byte) 0xFF});
        assertEquals(uut.getVersion(), 65535);
        assertEquals(uut.getDisplayName(), "Version");
        assertEquals(uut.getDisplayableValue(), "ST 0809.65535");
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xFF, (byte) 0xFF});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.Version, new byte[] {0x00, 0x02});
        assertTrue(v instanceof ST0809Version);
        ST0809Version uut = (ST0809Version) v;
        assertEquals(uut.getVersion(), 2);
        assertEquals(uut.getDisplayName(), "Version");
        assertEquals(uut.getDisplayableValue(), "ST 0809.2");
        assertEquals(uut.getBytes(), new byte[] {0x00, 0x02});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ST0809Version(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLarge() {
        new ST0809Version(65536);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new ST0809Version(new byte[] {0x01});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new ST0809Version(new byte[] {0x01, 0x02, 0x03});
    }
}

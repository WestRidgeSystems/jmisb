package org.jmisb.api.klv.st0806.poiaoi;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class PoiAltitudeTest {
    @Test
    public void testConstructFromValue() {
        // From ST 0601 Density Altitude:
        PoiAltitude alt = new PoiAltitude(14818.68);
        assertEquals(alt.getBytes(), new byte[] {(byte) 0xca, (byte) 0x35});
        assertEquals(alt.getMeters(), 14818.68, 0.15);
        assertEquals(alt.getDisplayableValue(), "14818.7m");
        assertEquals(alt.getDisplayName(), "POI Altitude");
    }

    @Test
    public void testConstructFromEncoded() {
        // From ST 0601 Density Altitude:
        byte[] encoded = new byte[] {(byte) 0xca, (byte) 0x35};
        PoiAltitude alt = new PoiAltitude(encoded);
        assertEquals(alt.getBytes(), new byte[] {(byte) 0xca, (byte) 0x35});
        assertEquals(alt.getMeters(), 14818.68, 0.15);
        assertEquals(alt.getDisplayableValue(), "14818.7m");
        assertEquals(alt.getDisplayName(), "POI Altitude");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0xca, (byte) 0x35};
        IRvtPoiAoiMetadataValue v =
                RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiAltitude, bytes);
        assertTrue(v instanceof PoiAltitude);
        PoiAltitude alt = (PoiAltitude) v;
        assertEquals(alt.getMeters(), 14818.68, 0.15);
        assertEquals(alt.getDisplayableValue(), "14818.7m");
        assertEquals(alt.getDisplayName(), "POI Altitude");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PoiAltitude(-900.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PoiAltitude(19000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLong() {
        new PoiAltitude(new byte[] {0x01, 0x02, 0x03});
    }
}

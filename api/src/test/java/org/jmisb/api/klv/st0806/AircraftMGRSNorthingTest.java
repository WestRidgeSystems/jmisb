package org.jmisb.api.klv.st0806;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class AircraftMGRSNorthingTest {
    @Test
    public void testConstructFromValue() {
        // Min
        AircraftMGRSNorthing northing = new AircraftMGRSNorthing(0);
        assertEquals(northing.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00});

        // Max
        northing = new AircraftMGRSNorthing(99999);
        assertEquals(northing.getBytes(), new byte[] {(byte) 0x01, (byte) 0x86, (byte) 0x9F});

        northing = new AircraftMGRSNorthing(74565);
        assertEquals(northing.getBytes(), new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45});

        assertEquals(northing.getDisplayName(), "Aircraft MGRS Northing");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        AircraftMGRSNorthing northing =
                new AircraftMGRSNorthing(new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(northing.getValue(), 0);
        assertEquals(northing.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(northing.getDisplayableValue(), "0");

        // Max
        northing = new AircraftMGRSNorthing(new byte[] {(byte) 0x01, (byte) 0x86, (byte) 0x9F});
        assertEquals(northing.getValue(), 99999);
        assertEquals(northing.getBytes(), new byte[] {(byte) 0x01, (byte) 0x86, (byte) 0x9F});
        assertEquals(northing.getDisplayableValue(), "99999");

        northing = new AircraftMGRSNorthing(new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45});
        assertEquals(northing.getValue(), 74565);
        assertEquals(northing.getBytes(), new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45});
        assertEquals(northing.getDisplayableValue(), "74565");
        assertEquals(northing.getDisplayName(), "Aircraft MGRS Northing");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45};
        IRvtMetadataValue v = RvtLocalSet.createValue(RvtMetadataKey.MGRSNorthing, bytes);
        assertTrue(v instanceof AircraftMGRSNorthing);
        AircraftMGRSNorthing northing = (AircraftMGRSNorthing) v;
        assertEquals(northing.getValue(), 74565);
        assertEquals(northing.getBytes(), new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45});
        assertEquals(northing.getDisplayableValue(), "74565");
        assertEquals(northing.getDisplayName(), "Aircraft MGRS Northing");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new AircraftMGRSNorthing(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new AircraftMGRSNorthing(100000);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new AircraftMGRSNorthing(new byte[] {0x01, 0x02, 0x03, 0x04});
    }
}

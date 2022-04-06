package org.jmisb.st0806;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class AircraftMGRSLatitudeBandAndGridSquareTest {
    @Test
    public void testConstructFromValue() {
        AircraftMGRSLatitudeBandAndGridSquare locAndGridSquare =
                new AircraftMGRSLatitudeBandAndGridSquare("QFJ");
        assertEquals(
                locAndGridSquare.getBytes(), new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A});
        assertEquals(
                locAndGridSquare.getDisplayName(), "Aircraft MGRS Latitude Band and Grid Square");
        assertEquals(locAndGridSquare.getDisplayableValue(), "QFJ");
        assertEquals(locAndGridSquare.getLatitudeBand(), "Q");
        assertEquals(locAndGridSquare.getGridSquare(), "FJ");
    }

    @Test
    public void testConstructFromEncoded() {
        AircraftMGRSLatitudeBandAndGridSquare locAndGridSquare =
                new AircraftMGRSLatitudeBandAndGridSquare(
                        new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A});
        assertEquals(locAndGridSquare.getDisplayableValue(), "QFJ");
        assertEquals(
                locAndGridSquare.getBytes(), new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A});
        assertEquals(
                locAndGridSquare.getDisplayName(), "Aircraft MGRS Latitude Band and Grid Square");
        assertEquals(locAndGridSquare.getLatitudeBand(), "Q");
        assertEquals(locAndGridSquare.getGridSquare(), "FJ");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A};
        IRvtMetadataValue v =
                RvtLocalSet.createValue(RvtMetadataKey.MGRSLatitudeBandAndGridSquare, bytes);
        assertTrue(v instanceof AircraftMGRSLatitudeBandAndGridSquare);
        AircraftMGRSLatitudeBandAndGridSquare locAndGridSquare =
                (AircraftMGRSLatitudeBandAndGridSquare) v;
        assertEquals(
                locAndGridSquare.getBytes(), new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A});
        assertEquals(locAndGridSquare.getDisplayableValue(), "QFJ");
        assertEquals(
                locAndGridSquare.getDisplayName(), "Aircraft MGRS Latitude Band and Grid Square");
        assertEquals(locAndGridSquare.getLatitudeBand(), "Q");
        assertEquals(locAndGridSquare.getGridSquare(), "FJ");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new AircraftMGRSLatitudeBandAndGridSquare(new byte[] {0x51, 0x46});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new AircraftMGRSLatitudeBandAndGridSquare(new byte[] {0x51, 0x46, 0x4A, 0x30});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badStringLengthShort() {
        new AircraftMGRSLatitudeBandAndGridSquare("QF");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badStringLengthLong() {
        new AircraftMGRSLatitudeBandAndGridSquare("QFQA");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badStringLengthEmpty() {
        new AircraftMGRSLatitudeBandAndGridSquare("");
    }
}

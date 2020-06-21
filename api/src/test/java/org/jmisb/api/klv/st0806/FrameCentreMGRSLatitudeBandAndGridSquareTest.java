package org.jmisb.api.klv.st0806;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class FrameCentreMGRSLatitudeBandAndGridSquareTest {
    @Test
    public void testConstructFromValue() {
        FrameCentreMGRSLatitudeBandAndGridSquare locAndGridSquare =
                new FrameCentreMGRSLatitudeBandAndGridSquare("QFJ");
        assertEquals(
                locAndGridSquare.getBytes(), new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A});
        assertEquals(
                locAndGridSquare.getDisplayName(),
                "Frame Center MGRS Latitude Band and Grid Square");
        assertEquals(locAndGridSquare.getDisplayableValue(), "QFJ");
        assertEquals(locAndGridSquare.getLatitudeBand(), "Q");
        assertEquals(locAndGridSquare.getGridSquare(), "FJ");
    }

    @Test
    public void testConstructFromEncoded() {
        FrameCentreMGRSLatitudeBandAndGridSquare locAndGridSquare =
                new FrameCentreMGRSLatitudeBandAndGridSquare(
                        new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A});
        assertEquals(locAndGridSquare.getDisplayableValue(), "QFJ");
        assertEquals(
                locAndGridSquare.getBytes(), new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A});
        assertEquals(
                locAndGridSquare.getDisplayName(),
                "Frame Center MGRS Latitude Band and Grid Square");
        assertEquals(locAndGridSquare.getLatitudeBand(), "Q");
        assertEquals(locAndGridSquare.getGridSquare(), "FJ");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A};
        IRvtMetadataValue v =
                RvtLocalSet.createValue(
                        RvtMetadataKey.MGRSLatitudeBandAndGridSquareSecondValue, bytes);
        assertTrue(v instanceof FrameCentreMGRSLatitudeBandAndGridSquare);
        FrameCentreMGRSLatitudeBandAndGridSquare locAndGridSquare =
                (FrameCentreMGRSLatitudeBandAndGridSquare) v;
        assertEquals(
                locAndGridSquare.getBytes(), new byte[] {(byte) 0x51, (byte) 0x46, (byte) 0x4A});
        assertEquals(locAndGridSquare.getDisplayableValue(), "QFJ");
        assertEquals(
                locAndGridSquare.getDisplayName(),
                "Frame Center MGRS Latitude Band and Grid Square");
        assertEquals(locAndGridSquare.getLatitudeBand(), "Q");
        assertEquals(locAndGridSquare.getGridSquare(), "FJ");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new FrameCentreMGRSLatitudeBandAndGridSquare(new byte[] {0x51, 0x46});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new FrameCentreMGRSLatitudeBandAndGridSquare(new byte[] {0x51, 0x46, 0x4A, 0x30});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badStringLengthShort() {
        new FrameCentreMGRSLatitudeBandAndGridSquare("QF");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badStringLengthLong() {
        new FrameCentreMGRSLatitudeBandAndGridSquare("QFQA");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badStringLengthEmpty() {
        new FrameCentreMGRSLatitudeBandAndGridSquare("");
    }
}

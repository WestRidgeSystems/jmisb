package org.jmisb.api.klv.st0806;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class FrameCenterMGRSLatitudeBandAndGridSquareTest {
    @Test
    public void testConstructFromValue() {
        FrameCenterMGRSLatitudeBandAndGridSquare locAndGridSquare =
                new FrameCenterMGRSLatitudeBandAndGridSquare("QFJ");
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
        FrameCenterMGRSLatitudeBandAndGridSquare locAndGridSquare =
                new FrameCenterMGRSLatitudeBandAndGridSquare(
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
        assertTrue(v instanceof FrameCenterMGRSLatitudeBandAndGridSquare);
        FrameCenterMGRSLatitudeBandAndGridSquare locAndGridSquare =
                (FrameCenterMGRSLatitudeBandAndGridSquare) v;
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
        new FrameCenterMGRSLatitudeBandAndGridSquare(new byte[] {0x51, 0x46});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new FrameCenterMGRSLatitudeBandAndGridSquare(new byte[] {0x51, 0x46, 0x4A, 0x30});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badStringLengthShort() {
        new FrameCenterMGRSLatitudeBandAndGridSquare("QF");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badStringLengthLong() {
        new FrameCenterMGRSLatitudeBandAndGridSquare("QFQA");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badStringLengthEmpty() {
        new FrameCenterMGRSLatitudeBandAndGridSquare("");
    }
}

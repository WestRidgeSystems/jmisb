package org.jmisb.st0806;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class FrameCenterMGRSNorthingTest {
    @Test
    public void testConstructFromValue() {
        // Min
        FrameCenterMGRSNorthing northing = new FrameCenterMGRSNorthing(0);
        assertEquals(northing.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00});

        // Max
        northing = new FrameCenterMGRSNorthing(99999);
        assertEquals(northing.getBytes(), new byte[] {(byte) 0x01, (byte) 0x86, (byte) 0x9F});

        northing = new FrameCenterMGRSNorthing(74565);
        assertEquals(northing.getBytes(), new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45});

        assertEquals(northing.getDisplayName(), "Frame Center MGRS Northing");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        FrameCenterMGRSNorthing northing =
                new FrameCenterMGRSNorthing(new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(northing.getValue(), 0);
        assertEquals(northing.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(northing.getDisplayableValue(), "0");

        // Max
        northing = new FrameCenterMGRSNorthing(new byte[] {(byte) 0x01, (byte) 0x86, (byte) 0x9F});
        assertEquals(northing.getValue(), 99999);
        assertEquals(northing.getBytes(), new byte[] {(byte) 0x01, (byte) 0x86, (byte) 0x9F});
        assertEquals(northing.getDisplayableValue(), "99999");

        northing = new FrameCenterMGRSNorthing(new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45});
        assertEquals(northing.getValue(), 74565);
        assertEquals(northing.getBytes(), new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45});
        assertEquals(northing.getDisplayableValue(), "74565");
        assertEquals(northing.getDisplayName(), "Frame Center MGRS Northing");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45};
        IRvtMetadataValue v =
                RvtLocalSet.createValue(RvtMetadataKey.MGRSNorthingSecondValue, bytes);
        assertTrue(v instanceof FrameCenterMGRSNorthing);
        FrameCenterMGRSNorthing northing = (FrameCenterMGRSNorthing) v;
        assertEquals(northing.getValue(), 74565);
        assertEquals(northing.getBytes(), new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45});
        assertEquals(northing.getDisplayableValue(), "74565");
        assertEquals(northing.getDisplayName(), "Frame Center MGRS Northing");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new FrameCenterMGRSNorthing(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new FrameCenterMGRSNorthing(100000);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new FrameCenterMGRSNorthing(new byte[] {0x01, 0x02, 0x03, 0x04});
    }
}

package org.jmisb.st0806;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class FrameCenterMGRSEastingTest {
    @Test
    public void testConstructFromValue() {
        // Min
        FrameCenterMGRSEasting easting = new FrameCenterMGRSEasting(0);
        assertEquals(easting.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00});

        // Max
        easting = new FrameCenterMGRSEasting(99999);
        assertEquals(easting.getBytes(), new byte[] {(byte) 0x01, (byte) 0x86, (byte) 0x9F});

        easting = new FrameCenterMGRSEasting(74565);
        assertEquals(easting.getBytes(), new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45});

        assertEquals(easting.getDisplayName(), "Frame Center MGRS Easting");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        FrameCenterMGRSEasting easting =
                new FrameCenterMGRSEasting(new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(easting.getValue(), 0);
        assertEquals(easting.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(easting.getDisplayableValue(), "0");

        // Max
        easting = new FrameCenterMGRSEasting(new byte[] {(byte) 0x01, (byte) 0x86, (byte) 0x9F});
        assertEquals(easting.getValue(), 99999);
        assertEquals(easting.getBytes(), new byte[] {(byte) 0x01, (byte) 0x86, (byte) 0x9F});
        assertEquals(easting.getDisplayableValue(), "99999");

        easting = new FrameCenterMGRSEasting(new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45});
        assertEquals(easting.getValue(), 74565);
        assertEquals(easting.getBytes(), new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45});
        assertEquals(easting.getDisplayableValue(), "74565");
        assertEquals(easting.getDisplayName(), "Frame Center MGRS Easting");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45};
        IRvtMetadataValue v = RvtLocalSet.createValue(RvtMetadataKey.MGRSEastingSecondValue, bytes);
        assertTrue(v instanceof FrameCenterMGRSEasting);
        FrameCenterMGRSEasting easting = (FrameCenterMGRSEasting) v;
        assertEquals(easting.getValue(), 74565);
        assertEquals(easting.getBytes(), new byte[] {(byte) 0x01, (byte) 0x23, (byte) 0x45});
        assertEquals(easting.getDisplayableValue(), "74565");
        assertEquals(easting.getDisplayName(), "Frame Center MGRS Easting");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new FrameCenterMGRSEasting(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new FrameCenterMGRSEasting(100000);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new FrameCenterMGRSEasting(new byte[] {0x01, 0x02});
    }
}

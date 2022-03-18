package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for FogCoverPercentage. */
public class FogCoverPercentageTest {

    @Test
    public void fromValue() {
        FogCoverPercentage uut = new FogCoverPercentage(30.0f);
        assertEquals(uut.getValue(), 30.0f);
        assertEquals(uut.getDisplayName(), "Fog Coverage");
        assertEquals(uut.getDisplayableValue(), "30.0 %");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0xf0, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        FogCoverPercentage uut =
                new FogCoverPercentage(
                        new byte[] {(byte) 0x41, (byte) 0xf0, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getValue(), 30.0f);
        assertEquals(uut.getDisplayName(), "Fog Coverage");
        assertEquals(uut.getDisplayableValue(), "30.0 %");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0xf0, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.FogCoverPercentage,
                        new byte[] {(byte) 0x41, (byte) 0xf0, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof FogCoverPercentage);
        FogCoverPercentage uut = (FogCoverPercentage) v;
        assertEquals(uut.getValue(), 30.0f);
        assertEquals(uut.getDisplayName(), "Fog Coverage");
        assertEquals(uut.getDisplayableValue(), "30.0 %");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0xf0, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() throws KlvParseException {
        new FogCoverPercentage(-0.1f);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLarge() throws KlvParseException {
        new FogCoverPercentage(100.1f);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new FogCoverPercentage(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new FogCoverPercentage(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new FogCoverPercentage(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

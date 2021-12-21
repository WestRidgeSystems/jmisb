package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for FogExtinctionCoefficient. */
public class FogExtinctionCoefficientTest {

    @Test
    public void fromValue() {
        FogExtinctionCoefficient uut = new FogExtinctionCoefficient(0.023f);
        assertEquals(uut.getValue(), 0.023f);
        assertEquals(uut.getDisplayName(), "Fog Extinction Coefficient");
        assertEquals(uut.getDisplayableValue(), "0.023 1/m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3c, (byte) 0xbc, (byte) 0x6a, (byte) 0x7f});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        FogExtinctionCoefficient uut =
                new FogExtinctionCoefficient(
                        new byte[] {(byte) 0x3c, (byte) 0xbc, (byte) 0x6a, (byte) 0x7f});
        assertEquals(uut.getValue(), 0.023f);
        assertEquals(uut.getDisplayName(), "Fog Extinction Coefficient");
        assertEquals(uut.getDisplayableValue(), "0.023 1/m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3c, (byte) 0xbc, (byte) 0x6a, (byte) 0x7f});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.FogExtinctionCoefficient,
                        new byte[] {(byte) 0x3c, (byte) 0xbc, (byte) 0x6a, (byte) 0x7f});
        assertTrue(v instanceof FogExtinctionCoefficient);
        FogExtinctionCoefficient uut = (FogExtinctionCoefficient) v;
        assertEquals(uut.getValue(), 0.023f);
        assertEquals(uut.getDisplayName(), "Fog Extinction Coefficient");
        assertEquals(uut.getDisplayableValue(), "0.023 1/m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3c, (byte) 0xbc, (byte) 0x6a, (byte) 0x7f});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new FogExtinctionCoefficient(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new FogExtinctionCoefficient(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new FogExtinctionCoefficient(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

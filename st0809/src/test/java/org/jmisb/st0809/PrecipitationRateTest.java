package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for PrecipitationRate. */
public class PrecipitationRateTest {

    @Test
    public void fromValue() {
        PrecipitationRate uut = new PrecipitationRate(0.244f);
        assertEquals(uut.getPrecipitationRate(), 0.244f);
        assertEquals(uut.getDisplayName(), "Precipitation Rate");
        assertEquals(uut.getDisplayableValue(), "0.24 mm/hr");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3e, (byte) 0x79, (byte) 0xdb, (byte) 0x23});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        PrecipitationRate uut =
                new PrecipitationRate(
                        new byte[] {(byte) 0x3e, (byte) 0x79, (byte) 0xdb, (byte) 0x23});
        assertEquals(uut.getPrecipitationRate(), 0.244f);
        assertEquals(uut.getDisplayName(), "Precipitation Rate");
        assertEquals(uut.getDisplayableValue(), "0.24 mm/hr");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3e, (byte) 0x79, (byte) 0xdb, (byte) 0x23});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.PrecipitationRate,
                        new byte[] {(byte) 0x3e, (byte) 0x79, (byte) 0xdb, (byte) 0x23});
        assertTrue(v instanceof PrecipitationRate);
        PrecipitationRate uut = (PrecipitationRate) v;
        assertEquals(uut.getPrecipitationRate(), 0.244f);
        assertEquals(uut.getDisplayName(), "Precipitation Rate");
        assertEquals(uut.getDisplayableValue(), "0.24 mm/hr");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3e, (byte) 0x79, (byte) 0xdb, (byte) 0x23});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new PrecipitationRate(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new PrecipitationRate(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new PrecipitationRate(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

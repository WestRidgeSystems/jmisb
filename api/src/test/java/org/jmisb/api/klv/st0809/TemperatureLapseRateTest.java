package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for TemperatureLapseRate. */
public class TemperatureLapseRateTest {

    @Test
    public void fromValue() {
        TemperatureLapseRate uut = new TemperatureLapseRate(1.34f);
        assertEquals(uut.getLapseRate(), 1.34f);
        assertEquals(uut.getDisplayName(), "Temperature Lapse Rate");
        assertEquals(uut.getDisplayableValue(), "1.34 °C");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3f, (byte) 0xab, (byte) 0x85, (byte) 0x1f});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        TemperatureLapseRate uut =
                new TemperatureLapseRate(
                        new byte[] {(byte) 0x3f, (byte) 0xab, (byte) 0x85, (byte) 0x1f});
        assertEquals(uut.getLapseRate(), 1.34f);
        assertEquals(uut.getDisplayName(), "Temperature Lapse Rate");
        assertEquals(uut.getDisplayableValue(), "1.34 °C");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3f, (byte) 0xab, (byte) 0x85, (byte) 0x1f});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.TemperatureLapseRate,
                        new byte[] {(byte) 0x3f, (byte) 0xab, (byte) 0x85, (byte) 0x1f});
        assertTrue(v instanceof TemperatureLapseRate);
        TemperatureLapseRate uut = (TemperatureLapseRate) v;
        assertEquals(uut.getLapseRate(), 1.34f);
        assertEquals(uut.getDisplayName(), "Temperature Lapse Rate");
        assertEquals(uut.getDisplayableValue(), "1.34 °C");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3f, (byte) 0xab, (byte) 0x85, (byte) 0x1f});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new TemperatureLapseRate(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new TemperatureLapseRate(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new TemperatureLapseRate(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

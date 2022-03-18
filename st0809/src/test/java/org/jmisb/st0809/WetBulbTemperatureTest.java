package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for WetBulbTemperature. */
public class WetBulbTemperatureTest {

    @Test
    public void fromValue() {
        WetBulbTemperature uut = new WetBulbTemperature(38.7f);
        assertEquals(uut.getTemperature(), 38.7f);
        assertEquals(uut.getDisplayName(), "Wet Bulb Temperature");
        assertEquals(uut.getDisplayableValue(), "38.70°C");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x1A, (byte) 0xCC, (byte) 0xCD});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        WetBulbTemperature uut =
                new WetBulbTemperature(
                        new byte[] {(byte) 0x42, (byte) 0x1A, (byte) 0xCC, (byte) 0xCD});
        assertEquals(uut.getTemperature(), 38.7f);
        assertEquals(uut.getDisplayName(), "Wet Bulb Temperature");
        assertEquals(uut.getDisplayableValue(), "38.70°C");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x1A, (byte) 0xCC, (byte) 0xCD});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.WetBulbTemperature,
                        new byte[] {(byte) 0x42, (byte) 0x1A, (byte) 0xCC, (byte) 0xCD});
        assertTrue(v instanceof WetBulbTemperature);
        WetBulbTemperature uut = (WetBulbTemperature) v;
        assertEquals(uut.getTemperature(), 38.7f);
        assertEquals(uut.getDisplayName(), "Wet Bulb Temperature");
        assertEquals(uut.getDisplayableValue(), "38.70°C");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x1A, (byte) 0xCC, (byte) 0xCD});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new WetBulbTemperature(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new WetBulbTemperature(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new WetBulbTemperature(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

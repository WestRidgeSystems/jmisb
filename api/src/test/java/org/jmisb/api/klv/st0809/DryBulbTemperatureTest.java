package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for DryBulbTemperature. */
public class DryBulbTemperatureTest {

    @Test
    public void fromValue() {
        DryBulbTemperature uut = new DryBulbTemperature(-10.0f);
        assertEquals(uut.getTemperature(), -10.0f);
        assertEquals(uut.getDisplayName(), "Dry Bulb Temperature");
        assertEquals(uut.getDisplayableValue(), "-10.00°C");
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xC1, 0x20, 0x00, 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        DryBulbTemperature uut = new DryBulbTemperature(new byte[] {(byte) 0xC1, 0x20, 0x00, 0x00});
        assertEquals(uut.getTemperature(), -10.0f);
        assertEquals(uut.getDisplayName(), "Dry Bulb Temperature");
        assertEquals(uut.getDisplayableValue(), "-10.00°C");
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xC1, 0x20, 0x00, 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.DryBulbTemperature,
                        new byte[] {(byte) 0xC1, 0x20, 0x00, 0x00});
        assertTrue(v instanceof DryBulbTemperature);
        DryBulbTemperature uut = (DryBulbTemperature) v;
        assertEquals(uut.getTemperature(), -10.0f);
        assertEquals(uut.getDisplayName(), "Dry Bulb Temperature");
        assertEquals(uut.getDisplayableValue(), "-10.00°C");
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xC1, 0x20, 0x00, 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new DryBulbTemperature(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new DryBulbTemperature(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new DryBulbTemperature(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

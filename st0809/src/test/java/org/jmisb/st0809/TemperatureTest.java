package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for Temperature. */
public class TemperatureTest {

    @Test
    public void fromValue() {
        Temperature uut = new Temperature(10.0f);
        assertEquals(uut.getTemperature(), 10.0f);
        assertEquals(uut.getDisplayName(), "Temperature");
        assertEquals(uut.getDisplayableValue(), "10.00°C");
        assertEquals(uut.getBytes(), new byte[] {0x41, 0x20, 0x00, 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        Temperature uut = new Temperature(new byte[] {0x41, 0x20, 0x00, 0x00});
        assertEquals(uut.getTemperature(), 10.0f);
        assertEquals(uut.getDisplayName(), "Temperature");
        assertEquals(uut.getDisplayableValue(), "10.00°C");
        assertEquals(uut.getBytes(), new byte[] {0x41, 0x20, 0x00, 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.Temperature, new byte[] {0x41, 0x20, 0x00, 0x00});
        assertTrue(v instanceof Temperature);
        Temperature uut = (Temperature) v;
        assertEquals(uut.getTemperature(), 10.0f);
        assertEquals(uut.getDisplayName(), "Temperature");
        assertEquals(uut.getDisplayableValue(), "10.00°C");
        assertEquals(uut.getBytes(), new byte[] {0x41, 0x20, 0x00, 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new Temperature(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new Temperature(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new Temperature(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

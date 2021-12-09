package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for WetBulbTemperatureHeatIndex. */
public class WetBulbTemperatureHeatIndexTest {

    @Test
    public void fromValue() {
        WetBulbTemperatureHeatIndex uut = new WetBulbTemperatureHeatIndex(8.72f);
        assertEquals(uut.getTemperature(), 8.72f);
        assertEquals(uut.getDisplayName(), "Wet Bulb Temperature Heat Index");
        assertEquals(uut.getDisplayableValue(), "8.72°C");
        assertEquals(uut.getBytes(), new byte[] {0x41, 0x0B, (byte) 0x85, 0x1F});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        WetBulbTemperatureHeatIndex uut =
                new WetBulbTemperatureHeatIndex(new byte[] {0x41, 0x0B, (byte) 0x85, 0x1F});
        assertEquals(uut.getTemperature(), 8.72f);
        assertEquals(uut.getDisplayName(), "Wet Bulb Temperature Heat Index");
        assertEquals(uut.getDisplayableValue(), "8.72°C");
        assertEquals(uut.getBytes(), new byte[] {0x41, 0x0B, (byte) 0x85, 0x1F});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.WetBulbTemperatureHeatIndex,
                        new byte[] {0x41, 0x0B, (byte) 0x85, 0x1F});
        assertTrue(v instanceof WetBulbTemperatureHeatIndex);
        WetBulbTemperatureHeatIndex uut = (WetBulbTemperatureHeatIndex) v;
        assertEquals(uut.getTemperature(), 8.72f);
        assertEquals(uut.getDisplayName(), "Wet Bulb Temperature Heat Index");
        assertEquals(uut.getDisplayableValue(), "8.72°C");
        assertEquals(uut.getBytes(), new byte[] {0x41, 0x0B, (byte) 0x85, 0x1F});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new WetBulbTemperatureHeatIndex(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new WetBulbTemperatureHeatIndex(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new WetBulbTemperatureHeatIndex(
                new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

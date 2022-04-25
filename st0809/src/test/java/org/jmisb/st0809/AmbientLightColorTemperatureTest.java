package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for AmbientLightColorTemperature. */
public class AmbientLightColorTemperatureTest {

    @Test
    public void fromValue() {
        AmbientLightColorTemperature uut = new AmbientLightColorTemperature(4800f);
        assertEquals(uut.getColorTemperature(), 4800.0f);
        assertEquals(uut.getDisplayName(), "Ambient Light Color Temperature");
        assertEquals(uut.getDisplayableValue(), "4800 K");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x45, (byte) 0x96, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        AmbientLightColorTemperature uut =
                new AmbientLightColorTemperature(
                        new byte[] {(byte) 0x45, (byte) 0x96, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getColorTemperature(), 4800.0f);
        assertEquals(uut.getDisplayName(), "Ambient Light Color Temperature");
        assertEquals(uut.getDisplayableValue(), "4800 K");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x45, (byte) 0x96, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.AmbientLightColorTemperature,
                        new byte[] {(byte) 0x45, (byte) 0x96, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof AmbientLightColorTemperature);
        AmbientLightColorTemperature uut = (AmbientLightColorTemperature) v;
        assertEquals(uut.getColorTemperature(), 4800.0f);
        assertEquals(uut.getDisplayName(), "Ambient Light Color Temperature");
        assertEquals(uut.getDisplayableValue(), "4800 K");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x45, (byte) 0x96, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new AmbientLightColorTemperature(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new AmbientLightColorTemperature(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new AmbientLightColorTemperature(
                new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

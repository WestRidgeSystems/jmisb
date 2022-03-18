package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for RelativeHumidity. */
public class RelativeHumidityTest {

    @Test
    public void fromValue() {
        RelativeHumidity uut = new RelativeHumidity(85.0f);
        assertEquals(uut.getValue(), 85.0f);
        assertEquals(uut.getDisplayName(), "Relative Humidity");
        assertEquals(uut.getDisplayableValue(), "85.0 %");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0xaa, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        RelativeHumidity uut =
                new RelativeHumidity(
                        new byte[] {(byte) 0x42, (byte) 0xaa, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getValue(), 85.0f);
        assertEquals(uut.getDisplayName(), "Relative Humidity");
        assertEquals(uut.getDisplayableValue(), "85.0 %");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0xaa, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.RelativeHumidity,
                        new byte[] {(byte) 0x42, (byte) 0xaa, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof RelativeHumidity);
        RelativeHumidity uut = (RelativeHumidity) v;
        assertEquals(uut.getValue(), 85.0f);
        assertEquals(uut.getDisplayName(), "Relative Humidity");
        assertEquals(uut.getDisplayableValue(), "85.0 %");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0xaa, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() throws KlvParseException {
        new RelativeHumidity(-0.1f);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLarge() throws KlvParseException {
        new RelativeHumidity(100.1f);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new RelativeHumidity(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new RelativeHumidity(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new RelativeHumidity(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

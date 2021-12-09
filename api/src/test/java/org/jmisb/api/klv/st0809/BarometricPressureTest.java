package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for BarometricPressure. */
public class BarometricPressureTest {

    @Test
    public void fromValue() {
        BarometricPressure uut = new BarometricPressure(101320.0f);
        assertEquals(uut.getPressure(), 101320.0f);
        assertEquals(uut.getDisplayName(), "Barometric Pressure");
        assertEquals(uut.getDisplayableValue(), "101320.00 Pa");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x47, (byte) 0xc5, (byte) 0xe4, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        BarometricPressure uut =
                new BarometricPressure(
                        new byte[] {(byte) 0x47, (byte) 0xc5, (byte) 0xe4, (byte) 0x00});
        assertEquals(uut.getPressure(), 101320.0f);
        assertEquals(uut.getDisplayName(), "Barometric Pressure");
        assertEquals(uut.getDisplayableValue(), "101320.00 Pa");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x47, (byte) 0xc5, (byte) 0xe4, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.BarometricPressure,
                        new byte[] {(byte) 0x47, (byte) 0xc5, (byte) 0xe4, (byte) 0x00});
        assertTrue(v instanceof BarometricPressure);
        BarometricPressure uut = (BarometricPressure) v;
        assertEquals(uut.getPressure(), 101320.0f);
        assertEquals(uut.getDisplayName(), "Barometric Pressure");
        assertEquals(uut.getDisplayableValue(), "101320.00 Pa");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x47, (byte) 0xc5, (byte) 0xe4, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new BarometricPressure(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new BarometricPressure(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new BarometricPressure(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

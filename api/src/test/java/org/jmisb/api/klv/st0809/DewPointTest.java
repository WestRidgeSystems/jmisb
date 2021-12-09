package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for DewPoint. */
public class DewPointTest {

    @Test
    public void fromValue() {
        DewPoint uut = new DewPoint(0.244f);
        assertEquals(uut.getTemperature(), 0.244f);
        assertEquals(uut.getDisplayName(), "Dew Point");
        assertEquals(uut.getDisplayableValue(), "0.24°C");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3e, (byte) 0x79, (byte) 0xdb, (byte) 0x23});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        DewPoint uut =
                new DewPoint(new byte[] {(byte) 0x3e, (byte) 0x79, (byte) 0xdb, (byte) 0x23});
        assertEquals(uut.getTemperature(), 0.244f);
        assertEquals(uut.getDisplayName(), "Dew Point");
        assertEquals(uut.getDisplayableValue(), "0.24°C");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3e, (byte) 0x79, (byte) 0xdb, (byte) 0x23});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.DewPoint,
                        new byte[] {(byte) 0x3e, (byte) 0x79, (byte) 0xdb, (byte) 0x23});
        assertTrue(v instanceof DewPoint);
        DewPoint uut = (DewPoint) v;
        assertEquals(uut.getTemperature(), 0.244f);
        assertEquals(uut.getDisplayName(), "Dew Point");
        assertEquals(uut.getDisplayableValue(), "0.24°C");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3e, (byte) 0x79, (byte) 0xdb, (byte) 0x23});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new DewPoint(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new DewPoint(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new DewPoint(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

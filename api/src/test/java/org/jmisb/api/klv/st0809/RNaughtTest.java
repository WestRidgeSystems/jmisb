package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for RNaught. */
public class RNaughtTest {

    @Test
    public void fromValue() {
        RNaught uut = new RNaught(12.0f);
        assertEquals(uut.getValue(), 12.0f);
        assertEquals(uut.getDisplayName(), "R Naught");
        assertEquals(uut.getDisplayableValue(), "12.0 cm");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0x40, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        RNaught uut = new RNaught(new byte[] {(byte) 0x41, (byte) 0x40, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getValue(), 12.0f);
        assertEquals(uut.getDisplayName(), "R Naught");
        assertEquals(uut.getDisplayableValue(), "12.0 cm");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0x40, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.RNaught,
                        new byte[] {(byte) 0x41, (byte) 0x40, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof RNaught);
        RNaught uut = (RNaught) v;
        assertEquals(uut.getValue(), 12.0f);
        assertEquals(uut.getDisplayName(), "R Naught");
        assertEquals(uut.getDisplayableValue(), "12.0 cm");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0x40, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new RNaught(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new RNaught(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new RNaught(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

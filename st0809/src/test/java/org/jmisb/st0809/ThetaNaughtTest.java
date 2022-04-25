package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ThetaNaught. */
public class ThetaNaughtTest {

    @Test
    public void fromValue() {
        ThetaNaught uut = new ThetaNaught(12.6f);
        assertEquals(uut.getValue(), 12.6f);
        assertEquals(uut.getDisplayName(), "Theta Naught");
        assertEquals(uut.getDisplayableValue(), "12.60 µr");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0x49, (byte) 0x99, (byte) 0x9a});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        ThetaNaught uut =
                new ThetaNaught(new byte[] {(byte) 0x41, (byte) 0x49, (byte) 0x99, (byte) 0x9a});
        assertEquals(uut.getValue(), 12.6f);
        assertEquals(uut.getDisplayName(), "Theta Naught");
        assertEquals(uut.getDisplayableValue(), "12.60 µr");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0x49, (byte) 0x99, (byte) 0x9a});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.ThetaNaught,
                        new byte[] {(byte) 0x41, (byte) 0x49, (byte) 0x99, (byte) 0x9a});
        assertTrue(v instanceof ThetaNaught);
        ThetaNaught uut = (ThetaNaught) v;
        assertEquals(uut.getValue(), 12.6f);
        assertEquals(uut.getDisplayName(), "Theta Naught");
        assertEquals(uut.getDisplayableValue(), "12.60 µr");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0x49, (byte) 0x99, (byte) 0x9a});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new ThetaNaught(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new ThetaNaught(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new ThetaNaught(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

package org.jmisb.st1202;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for Denominator_Y. */
public class Denominator_Y_Test {

    @Test
    public void fromValue() {
        Denominator_Y uut = new Denominator_Y(0.2f);
        assertEquals(uut.getValue(), 0.2f);
        assertEquals(uut.getDisplayName(), "Denominator - y factor");
        assertEquals(uut.getDisplayableValue(), "0.200");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3E, (byte) 0x4C, (byte) 0xCC, (byte) 0xCD});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        Denominator_Y uut =
                new Denominator_Y(new byte[] {(byte) 0x3E, (byte) 0x4C, (byte) 0xCC, (byte) 0xCD});
        assertEquals(uut.getValue(), 0.2f);
        assertEquals(uut.getDisplayName(), "Denominator - y factor");
        assertEquals(uut.getDisplayableValue(), "0.200");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3E, (byte) 0x4C, (byte) 0xCC, (byte) 0xCD});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IGeneralizedTransformationMetadataValue v =
                GeneralizedTransformationLocalSet.createValue(
                        GeneralizedTransformationParametersKey.Denominator_y,
                        new byte[] {(byte) 0x3E, (byte) 0x4C, (byte) 0xCC, (byte) 0xCD});
        assertTrue(v instanceof Denominator_Y);
        Denominator_Y uut = (Denominator_Y) v;
        assertEquals(uut.getValue(), 0.2f);
        assertEquals(uut.getDisplayName(), "Denominator - y factor");
        assertEquals(uut.getDisplayableValue(), "0.200");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3E, (byte) 0x4C, (byte) 0xCC, (byte) 0xCD});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new Denominator_Y(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new Denominator_Y(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new Denominator_Y(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

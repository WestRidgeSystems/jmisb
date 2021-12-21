package org.jmisb.st1202;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for Denominator_X. */
public class Denominator_X_Test {

    @Test
    public void fromValue() {
        Denominator_X uut = new Denominator_X(0.0f);
        assertEquals(uut.getValue(), 0.0f);
        assertEquals(uut.getDisplayName(), "Denominator - x factor");
        assertEquals(uut.getDisplayableValue(), "0.000");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        Denominator_X uut =
                new Denominator_X(new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getValue(), 0.0f);
        assertEquals(uut.getDisplayName(), "Denominator - x factor");
        assertEquals(uut.getDisplayableValue(), "0.000");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IGeneralizedTransformationMetadataValue v =
                GeneralizedTransformationLocalSet.createValue(
                        GeneralizedTransformationParametersKey.Denominator_x,
                        new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof Denominator_X);
        Denominator_X uut = (Denominator_X) v;
        assertEquals(uut.getValue(), 0.0f);
        assertEquals(uut.getDisplayName(), "Denominator - x factor");
        assertEquals(uut.getDisplayableValue(), "0.000");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new Denominator_X(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new Denominator_X(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new Denominator_X(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

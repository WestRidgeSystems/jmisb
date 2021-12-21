package org.jmisb.st1202;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for X_Numerator_X. */
public class X_Numerator_X_Test {

    @Test
    public void fromValue() {
        X_Numerator_X uut = new X_Numerator_X(0.5f);
        assertEquals(uut.getValue(), 0.5f);
        assertEquals(uut.getDisplayName(), "x Equation Numerator - x factor");
        assertEquals(uut.getDisplayableValue(), "0.500");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3f, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        X_Numerator_X uut =
                new X_Numerator_X(new byte[] {(byte) 0x3f, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getValue(), 0.5f);
        assertEquals(uut.getDisplayName(), "x Equation Numerator - x factor");
        assertEquals(uut.getDisplayableValue(), "0.500");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3f, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IGeneralizedTransformationMetadataValue v =
                GeneralizedTransformationLocalSet.createValue(
                        GeneralizedTransformationParametersKey.X_Numerator_x,
                        new byte[] {(byte) 0x3f, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof X_Numerator_X);
        X_Numerator_X uut = (X_Numerator_X) v;
        assertEquals(uut.getValue(), 0.5f);
        assertEquals(uut.getDisplayName(), "x Equation Numerator - x factor");
        assertEquals(uut.getDisplayableValue(), "0.500");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3f, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new X_Numerator_X(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new X_Numerator_X(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new X_Numerator_X(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

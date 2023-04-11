package org.jmisb.st1202;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for Y_Numerator_Constant. */
public class Y_Numerator_Constant_Test {

    @Test
    public void fromValue() {
        Y_Numerator_Constant uut = new Y_Numerator_Constant(556.25f);
        assertEquals(uut.getValue(), 556.25f);
        assertEquals(uut.getDisplayName(), "y Equation Numerator - Constant factor");
        assertEquals(uut.getDisplayableValue(), "556.250");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x44, (byte) 0x0B, (byte) 0x10, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        Y_Numerator_Constant uut =
                new Y_Numerator_Constant(
                        new byte[] {(byte) 0x44, (byte) 0x0B, (byte) 0x10, (byte) 0x00});
        assertEquals(uut.getValue(), 556.25f);
        assertEquals(uut.getDisplayName(), "y Equation Numerator - Constant factor");
        assertEquals(uut.getDisplayableValue(), "556.250");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x44, (byte) 0x0B, (byte) 0x10, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IGeneralizedTransformationMetadataValue v =
                GeneralizedTransformationLocalSet.createValue(
                        GeneralizedTransformationParametersKey.Y_Numerator_Constant,
                        new byte[] {(byte) 0x44, (byte) 0x0B, (byte) 0x10, (byte) 0x00});
        assertTrue(v instanceof Y_Numerator_Constant);
        Y_Numerator_Constant uut = (Y_Numerator_Constant) v;
        assertEquals(uut.getValue(), 556.25f);
        assertEquals(uut.getDisplayName(), "y Equation Numerator - Constant factor");
        assertEquals(uut.getDisplayableValue(), "556.250");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x44, (byte) 0x0B, (byte) 0x10, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new Y_Numerator_Constant(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new Y_Numerator_Constant(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new Y_Numerator_Constant(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}

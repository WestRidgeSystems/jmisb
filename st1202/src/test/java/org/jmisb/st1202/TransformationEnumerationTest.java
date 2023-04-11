package org.jmisb.st1202;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1202 Transformation Enumeration. */
public class TransformationEnumerationTest {
    @Test
    public void testConstructFromValue() {
        TransformationEnumeration uut = TransformationEnumeration.CHIPPING;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01});
        assertEquals(uut.getDisplayName(), "Transformation Enumeration");
        assertEquals(uut.getDisplayableValue(), "Chipping Transformation (CT)");
        assertEquals(uut.getEncodedValue(), 1);
        assertEquals(uut.getDescription(), "Chipping Transformation (CT)");
        assertEquals(uut.getUnits(), "px");
    }

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException {
        TransformationEnumeration uut =
                TransformationEnumeration.fromBytes(new byte[] {(byte) 0x02});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x02});
        assertEquals(uut.getDisplayName(), "Transformation Enumeration");
        assertEquals(uut.getDisplayableValue(), "Child-Parent Transformation (CPT)");
        assertEquals(uut.getEncodedValue(), 2);
        assertEquals(uut.getDescription(), "Child-Parent Transformation (CPT)");
        assertEquals(uut.getUnits(), "mm");
    }

    @Test
    public void testConstructFromEncodedBytes4() throws KlvParseException {
        TransformationEnumeration uut =
                TransformationEnumeration.fromBytes(new byte[] {(byte) 0x04});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x04});
        assertEquals(uut.getDisplayName(), "Transformation Enumeration");
        assertEquals(uut.getDisplayableValue(), "Optical Transformation (OT)");
        assertEquals(uut.getEncodedValue(), 4);
        assertEquals(uut.getDescription(), "Optical Transformation (OT)");
        assertEquals(uut.getUnits(), "mm");
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IGeneralizedTransformationMetadataValue value =
                GeneralizedTransformationLocalSet.createValue(
                        GeneralizedTransformationParametersKey.TransformationEnumeration,
                        new byte[] {(byte) 0x02});
        assertTrue(value instanceof TransformationEnumeration);
        TransformationEnumeration uut = (TransformationEnumeration) value;
        assertEquals(uut.getDisplayName(), "Transformation Enumeration");
        assertEquals(uut.getDisplayableValue(), "Child-Parent Transformation (CPT)");
        assertEquals(uut.getEncodedValue(), 2);
        assertEquals(uut.getDescription(), "Child-Parent Transformation (CPT)");
        assertEquals(uut.getUnits(), "mm");
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp =
                    "Transformation Enumeration should be encoded as a 1 byte array")
    public void testConstructFromEncodedBytesTooLong() throws KlvParseException {
        TransformationEnumeration.fromBytes(new byte[] {(byte) 0x01, (byte) 0x02});
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp =
                    "Transformation Enumeration should be encoded as a 1 byte array")
    public void testConstructFromEncodedBytesTooShort() throws KlvParseException {
        TransformationEnumeration.fromBytes(new byte[] {});
    }

    @Test
    public void testLookup() throws KlvParseException {
        TransformationEnumeration uut = TransformationEnumeration.lookup(4);
        assertEquals(uut, TransformationEnumeration.OPTICAL);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x04});
        assertEquals(uut.getDisplayName(), "Transformation Enumeration");
        assertEquals(uut.getDisplayableValue(), "Optical Transformation (OT)");
        assertEquals(uut.getEncodedValue(), 4);
        assertEquals(uut.getDescription(), "Optical Transformation (OT)");
        assertEquals(uut.getUnits(), "mm");
    }

    @Test
    public void testLookupUnknown() throws KlvParseException {
        TransformationEnumeration uut = TransformationEnumeration.lookup(5);
        assertEquals(uut, TransformationEnumeration.UNKNOWN);
    }

    @Test()
    public void testSerialiseOther() throws KlvParseException {
        assertEquals(TransformationEnumeration.OTHER.getBytes(), new byte[] {(byte) 0x00});
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Cannot serialise UNKNOWN Transformation Enumeration")
    public void testSerialiseUnknown() throws KlvParseException {
        TransformationEnumeration.UNKNOWN.getBytes();
    }
}

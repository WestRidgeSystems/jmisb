package org.jmisb.st1202;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for Generalized Transformation Local Set implementation. */
public class GeneralizedTransformationLocalSetTest extends LoggerChecks {

    private final byte[] bytesAll =
            new byte[] {
                0x01,
                0x04,
                0x3F,
                (byte) 0x80,
                0x00,
                0x00,
                0x02,
                0x04,
                0x40,
                0x00,
                0x00,
                0x00,
                0x03,
                0x04,
                0x40,
                0x40,
                0x00,
                0x00,
                0x04,
                0x04,
                0x40,
                (byte) 0x80,
                0x00,
                0x00,
                0x05,
                0x04,
                0x40,
                (byte) 0xa0,
                0x00,
                0x00,
                0x06,
                0x04,
                0x40,
                (byte) 0xc0,
                0x00,
                0x00,
                0x07,
                0x04,
                0x40,
                (byte) 0xe0,
                0x00,
                0x00,
                0x08,
                0x04,
                0x41,
                0x00,
                0x00,
                0x00,
                0x09,
                90,
                0x08,
                0b01000010,
                (byte) 0x3d,
                (byte) 0xcc,
                (byte) 0xcc,
                (byte) 0xcd,
                (byte) 0x3e,
                (byte) 0x4c,
                (byte) 0xcc,
                (byte) 0xcd,
                (byte) 0x3e,
                (byte) 0x99,
                (byte) 0x99,
                (byte) 0x9a,
                (byte) 0x3e,
                (byte) 0xcc,
                (byte) 0xcc,
                (byte) 0xcd,
                (byte) 0x3f,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x3f,
                (byte) 0x19,
                (byte) 0x99,
                (byte) 0x9a,
                (byte) 0x3f,
                (byte) 0x33,
                (byte) 0x33,
                (byte) 0x33,
                (byte) 0x3f,
                (byte) 0x4c,
                (byte) 0xcc,
                (byte) 0xcd,
                (byte) 0x40,
                (byte) 0xa3,
                (byte) 0x41,
                (byte) 0x47,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                0x0a,
                0x01,
                0x02,
                0x0b,
                0x01,
                0x02
            };

    public GeneralizedTransformationLocalSetTest() {
        super(GeneralizedTransformationLocalSet.class);
    }

    @Test
    public void checkFromBytesSimple() throws KlvParseException {
        // This is not a reasonable local set, just serves for testing
        byte[] bytes = new byte[] {0x0a, 0x01, 0x02};
        GeneralizedTransformationLocalSet uut = new GeneralizedTransformationLocalSet(bytes);
        verifyNoLoggerMessages();
        assertEquals(
                uut.getUniversalLabel(),
                GeneralizedTransformationLocalSet.GeneralizedTransformationLocalSetUl);
        assertEquals(uut.displayHeader(), "ST 1202 Generalized Transformation Local Set");
        assertEquals(uut.getIdentifiers().size(), 1);
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.DocumentVersion));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.DocumentVersion)
                        .getDisplayableValue(),
                "ST 1202.2");
        assertEquals(uut.frameMessage(true), bytes);
        verifyNoLoggerMessages();
    }

    @Test
    public void checkFromBytesWithUnknown() throws KlvParseException {
        // This is not a reasonable local set, just serves for testing
        byte[] bytes = new byte[] {0x7f, 0x01, 0x02, 0x0a, 0x01, 0x02};
        GeneralizedTransformationLocalSet uut = new GeneralizedTransformationLocalSet(bytes);
        this.verifySingleLoggerMessage("Unknown Generalized Transformation tag: 127");
        assertEquals(
                uut.getUniversalLabel(),
                GeneralizedTransformationLocalSet.GeneralizedTransformationLocalSetUl);
        assertEquals(uut.displayHeader(), "ST 1202 Generalized Transformation Local Set");
        assertEquals(uut.getIdentifiers().size(), 1);
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.DocumentVersion));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.DocumentVersion)
                        .getDisplayableValue(),
                "ST 1202.2");
        assertEquals(uut.frameMessage(true), new byte[] {0x0a, 0x01, 0x02});
        verifyNoLoggerMessages();
    }

    @Test
    public void checkFromValues() throws KlvParseException {
        Map<GeneralizedTransformationParametersKey, IGeneralizedTransformationMetadataValue> map =
                new HashMap<>();
        map.put(
                GeneralizedTransformationParametersKey.DocumentVersion,
                new ST1202DocumentVersion(2));
        GeneralizedTransformationLocalSet uut = new GeneralizedTransformationLocalSet(map);
        verifyNoLoggerMessages();
        assertEquals(
                uut.getUniversalLabel(),
                GeneralizedTransformationLocalSet.GeneralizedTransformationLocalSetUl);
        assertEquals(uut.displayHeader(), "ST 1202 Generalized Transformation Local Set");
        assertEquals(uut.getIdentifiers().size(), 1);
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.DocumentVersion));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.DocumentVersion)
                        .getDisplayableValue(),
                "ST 1202.2");
        assertEquals(uut.frameMessage(true), new byte[] {0x0a, 0x01, 0x02});
        verifyNoLoggerMessages();
    }

    @Test
    public void checkFromBytesAll() throws KlvParseException {
        verifyNoLoggerMessages();
        GeneralizedTransformationLocalSet uut = new GeneralizedTransformationLocalSet(bytesAll);
        verifyNoLoggerMessages();
        assertEquals(
                uut.getUniversalLabel(),
                GeneralizedTransformationLocalSet.GeneralizedTransformationLocalSetUl);
        assertEquals(uut.displayHeader(), "ST 1202 Generalized Transformation Local Set");
        assertEquals(uut.getIdentifiers().size(), 11);
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.X_Numerator_x));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.X_Numerator_x)
                        .getDisplayableValue(),
                "1.000");
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.X_Numerator_y));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.X_Numerator_y)
                        .getDisplayableValue(),
                "2.000");
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.X_Numerator_Constant));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.X_Numerator_Constant)
                        .getDisplayableValue(),
                "3.000");
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.Y_Numerator_x));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.Y_Numerator_x)
                        .getDisplayableValue(),
                "4.000");
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.Y_Numerator_y));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.Y_Numerator_y)
                        .getDisplayableValue(),
                "5.000");
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.Y_Numerator_Constant));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.Y_Numerator_Constant)
                        .getDisplayableValue(),
                "6.000");
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.Denominator_x));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.Denominator_x)
                        .getDisplayableValue(),
                "7.000");
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.Denominator_y));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.Denominator_y)
                        .getDisplayableValue(),
                "8.000");
        assertTrue(uut.getIdentifiers().contains(GeneralizedTransformationParametersKey.SDCC));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.SDCC).getDisplayableValue(),
                "[SDCC]");
        assertTrue(uut.getField(GeneralizedTransformationParametersKey.SDCC) instanceof SDCC_FLP);
        SDCC_FLP sdccFlp = (SDCC_FLP) uut.getField(GeneralizedTransformationParametersKey.SDCC);
        assertEquals(sdccFlp.getSDCCMatrix()[0][0], 0.1, 0.0000001);
        assertEquals(sdccFlp.getSDCCMatrix()[0][1], 0.01, 0.001);
        assertEquals(sdccFlp.getSDCCMatrix()[1][0], 0.01, 0.001);
        assertEquals(sdccFlp.getSDCCMatrix()[7][7], 0.8, 0.0000001);
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.DocumentVersion));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.DocumentVersion)
                        .getDisplayableValue(),
                "ST 1202.2");
        assertTrue(
                uut.getIdentifiers()
                        .contains(
                                GeneralizedTransformationParametersKey.TransformationEnumeration));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.TransformationEnumeration)
                        .getDisplayableValue(),
                "Child-Parent Transformation (CPT)");
        assertEquals(uut.frameMessage(true), bytesAll);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badSerialisationNonNested() {
        Map<GeneralizedTransformationParametersKey, IGeneralizedTransformationMetadataValue> map =
                new HashMap<>();
        map.put(
                GeneralizedTransformationParametersKey.DocumentVersion,
                new ST1202DocumentVersion(2));
        GeneralizedTransformationLocalSet uut = new GeneralizedTransformationLocalSet(map);
        uut.frameMessage(false);
    }

    @Test
    public void lookupUnknown() throws KlvParseException {
        verifyNoLoggerMessages();
        IGeneralizedTransformationMetadataValue uut =
                GeneralizedTransformationLocalSet.createValue(
                        GeneralizedTransformationParametersKey.Undefined, new byte[] {0x03});
        this.verifySingleLoggerMessage(
                "Unknown Generalized Transformation Metadata tag: Undefined");
        assertNull(uut);
    }
}

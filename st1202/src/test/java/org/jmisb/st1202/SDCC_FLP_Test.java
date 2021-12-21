package org.jmisb.st1202;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.st1010.EncodingFormat;
import org.jmisb.st1010.SDCC;
import org.jmisb.st1010.SDCCValueIdentifierKey;
import org.jmisb.st1010.SDCCValueWrap;
import org.testng.annotations.Test;

/** Tests for ST 1202 SDCC Floating Length Pack. */
public class SDCC_FLP_Test {
    private final double[][] matrix =
            new double[][] {
                {0.10, 0.01, 0.02, 0.00, 0.00, 0.00, 0.00, 0.00},
                {0.01, 0.20, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00},
                {0.02, 0.00, 0.30, 0.00, 0.00, 0.00, 0.00, 0.00},
                {0.00, 0.00, 0.00, 0.40, 0.00, 0.00, 0.00, 0.00},
                {0.00, 0.00, 0.00, 0.00, 0.50, 0.00, 0.00, 0.00},
                {0.00, 0.00, 0.00, 0.00, 0.00, 0.60, 0.00, 0.00},
                {0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.70, 0.00},
                {0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.80},
            };

    private final byte[] sdccBytes =
            new byte[] {
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
            };

    @Test
    public void testConstructFromSdccValue() {
        SDCC sdcc = new SDCC();
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        sdcc.setStandardDeviationLength(Float.BYTES);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.ST1201);
        sdcc.setCorrelationCoefficientLength(2);
        sdcc.setValues(matrix);
        SDCC_FLP uut = new SDCC_FLP(sdcc);
        assertEquals(uut.getDisplayName(), "Standard Deviation and Correlation Coefficients");
        assertEquals(uut.getDisplayableValue(), "[SDCC]");
        assertEquals(uut.getBytes(), sdccBytes);
        assertEquals(uut.getSDCC().getStandardDeviationFormat(), EncodingFormat.IEEE);
        assertEquals(uut.getSDCC().getStandardDeviationLength(), 4);
        assertEquals(uut.getSDCC().getCorrelationCoefficientFormat(), EncodingFormat.ST1201);
        assertEquals(uut.getSDCC().getCorrelationCoefficientLength(), 2);
        assertEquals(uut.getSDCC().getValues(), matrix);
        assertEquals(uut.getSDCCMatrix(), matrix);
        assertEquals(uut.getIdentifiers().size(), 64);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        SDCC_FLP uut = new SDCC_FLP(sdccBytes);
        assertEquals(uut.getDisplayName(), "Standard Deviation and Correlation Coefficients");
        assertEquals(uut.getDisplayableValue(), "[SDCC]");
        assertEquals(uut.getBytes(), sdccBytes);
        assertEquals(uut.getSDCC().getStandardDeviationFormat(), EncodingFormat.IEEE);
        assertEquals(uut.getSDCC().getStandardDeviationLength(), 4);
        assertEquals(uut.getSDCC().getCorrelationCoefficientFormat(), EncodingFormat.ST1201);
        assertEquals(uut.getSDCC().getCorrelationCoefficientLength(), 2);
        assertEquals(uut.getSDCC().getValues().length, matrix.length);
        assertEquals(uut.getSDCC().getValues()[0].length, matrix[0].length);
        double[][] values = uut.getSDCC().getValues();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == j) {
                    assertEquals(values[i][j], matrix[i][j], 0.0000001);
                } else {
                    assertEquals(values[i][j], matrix[i][j], 0.0001);
                }
            }
        }
        values = uut.getSDCCMatrix();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == j) {
                    assertEquals(values[i][j], matrix[i][j], 0.0000001);
                } else {
                    assertEquals(values[i][j], matrix[i][j], 0.0001);
                }
            }
        }
        assertEquals(uut.getIdentifiers().size(), 64);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IGeneralizedTransformationMetadataValue value =
                GeneralizedTransformationLocalSet.createValue(
                        GeneralizedTransformationParametersKey.SDCC, sdccBytes);
        assertTrue(value instanceof SDCC_FLP);
        SDCC_FLP uut = (SDCC_FLP) value;
        assertEquals(uut.getDisplayName(), "Standard Deviation and Correlation Coefficients");
        assertEquals(uut.getDisplayableValue(), "[SDCC]");
        assertEquals(uut.getBytes(), sdccBytes);
        assertEquals(uut.getSDCC().getStandardDeviationFormat(), EncodingFormat.IEEE);
        assertEquals(uut.getSDCC().getStandardDeviationLength(), 4);
        assertEquals(uut.getSDCC().getCorrelationCoefficientFormat(), EncodingFormat.ST1201);
        assertEquals(uut.getSDCC().getCorrelationCoefficientLength(), 2);
        assertEquals(uut.getSDCC().getValues().length, matrix.length);
        assertEquals(uut.getSDCC().getValues()[0].length, matrix[0].length);
        assertEquals(uut.getSDCC().getValues().length, matrix.length);
        assertEquals(uut.getSDCC().getValues()[0].length, matrix[0].length);
        double[][] values = uut.getSDCC().getValues();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == j) {
                    assertEquals(values[i][j], matrix[i][j], 0.0000001);
                } else {
                    assertEquals(values[i][j], matrix[i][j], 0.0001);
                }
            }
        }
        values = uut.getSDCCMatrix();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == j) {
                    assertEquals(values[i][j], matrix[i][j], 0.0000001);
                } else {
                    assertEquals(values[i][j], matrix[i][j], 0.0001);
                }
            }
        }
        assertEquals(uut.getIdentifiers().size(), 64);
        for (IKlvKey identifier : uut.getIdentifiers()) {
            assertTrue(identifier instanceof SDCCValueIdentifierKey);
            SDCCValueIdentifierKey id = (SDCCValueIdentifierKey) identifier;
            int row = id.getRow();
            int column = id.getColumn();
            var fieldValue = uut.getField(id);
            assertTrue(fieldValue instanceof SDCCValueWrap);
            SDCCValueWrap wrappedValue = (SDCCValueWrap) fieldValue;
            if (row == column) {
                assertEquals(wrappedValue.getValue(), matrix[row][column], 0.0000001);
            } else {
                assertEquals(wrappedValue.getValue(), matrix[row][column], 0.0001);
            }
        }
    }
}

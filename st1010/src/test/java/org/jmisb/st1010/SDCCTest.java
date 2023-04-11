package org.jmisb.st1010;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for SDCC. */
public class SDCCTest {

    public SDCCTest() {}

    @Test
    public void checkDefaults() {
        SDCC uut = new SDCC();
        assertEquals(uut.getCorrelationCoefficientFormat(), EncodingFormat.ST1201);
        assertEquals(uut.getStandardDeviationFormat(), EncodingFormat.IEEE);
        assertEquals(uut.getCorrelationCoefficientLength(), 3);
        assertEquals(uut.getStandardDeviationLength(), 4);
        assertEquals(uut.getValues().length, 0);
    }

    @Test
    public void checkSetterAndCopy() {
        SDCC uut = new SDCC();
        uut.setCorrelationCoefficientFormat(EncodingFormat.IEEE);
        uut.setStandardDeviationFormat(EncodingFormat.ST1201);
        uut.setCorrelationCoefficientLength(8);
        uut.setStandardDeviationLength(3);
        uut.setValues(new double[][] {{5.3, 0.2}, {0.2, 1.6}});
        assertEquals(uut.getCorrelationCoefficientFormat(), EncodingFormat.IEEE);
        assertEquals(uut.getStandardDeviationFormat(), EncodingFormat.ST1201);
        assertEquals(uut.getCorrelationCoefficientLength(), 8);
        assertEquals(uut.getStandardDeviationLength(), 3);
        assertEquals(uut.getValues().length, 2);
        assertEquals(uut.getValues()[0].length, 2);
        assertEquals(uut.getValues()[0][0], 5.3);
        assertEquals(uut.getValues()[0][1], 0.2);
        assertEquals(uut.getValues()[1][0], 0.2);
        assertEquals(uut.getValues()[1][1], 1.6);
        assertEquals(uut.getIdentifiers().size(), 4);
        boolean found00 = false;
        boolean found01 = false;
        boolean found10 = false;
        boolean found11 = false;
        for (SDCCValueIdentifierKey identifier : uut.getIdentifiers()) {
            if ((identifier.getRow() == 0) && (identifier.getColumn() == 0)) {
                found00 = true;
                assertEquals(uut.getField(identifier).getDisplayName(), "[0][0]");
                assertEquals(uut.getField(identifier).getDisplayableValue(), "5.300");
                assertEquals(uut.getField(identifier).getRow(), 0);
                assertEquals(uut.getField(identifier).getColumn(), 0);
                assertEquals(uut.getField(identifier).getValue(), 5.3, 0.0001);
            }
            if ((identifier.getRow() == 0) && (identifier.getColumn() == 1)) {
                found01 = true;
                assertEquals(uut.getField(identifier).getDisplayName(), "[0][1]");
                assertEquals(uut.getField(identifier).getDisplayableValue(), "0.200");
                assertEquals(uut.getField(identifier).getRow(), 0);
                assertEquals(uut.getField(identifier).getColumn(), 1);
                assertEquals(uut.getField(identifier).getValue(), 0.2, 0.0001);
            }
            if ((identifier.getRow() == 1) && (identifier.getColumn() == 0)) {
                found10 = true;
                assertEquals(uut.getField(identifier).getDisplayName(), "[1][0]");
                assertEquals(uut.getField(identifier).getDisplayableValue(), "0.200");
                assertEquals(uut.getField(identifier).getRow(), 1);
                assertEquals(uut.getField(identifier).getColumn(), 0);
                assertEquals(uut.getField(identifier).getValue(), 0.2, 0.0001);
            }

            if ((identifier.getRow() == 1) && (identifier.getColumn() == 1)) {
                found11 = true;
                assertEquals(uut.getField(identifier).getDisplayName(), "[1][1]");
                assertEquals(uut.getField(identifier).getDisplayableValue(), "1.600");
                assertEquals(uut.getField(identifier).getRow(), 1);
                assertEquals(uut.getField(identifier).getColumn(), 1);
                assertEquals(uut.getField(identifier).getValue(), 1.6, 0.0001);
            }
        }
        assertTrue(found00);
        assertTrue(found01);
        assertTrue(found10);
        assertTrue(found11);
        SDCC copy = new SDCC(uut);
        assertEquals(copy.getCorrelationCoefficientFormat(), EncodingFormat.IEEE);
        assertEquals(copy.getStandardDeviationFormat(), EncodingFormat.ST1201);
        assertEquals(copy.getCorrelationCoefficientLength(), 8);
        assertEquals(copy.getStandardDeviationLength(), 3);
        assertEquals(copy.getValues().length, 2);
        assertEquals(copy.getValues()[0].length, 2);
        assertEquals(copy.getValues()[0][0], 5.3);
        assertEquals(copy.getValues()[0][1], 0.2);
        assertEquals(copy.getValues()[1][0], 0.2);
        assertEquals(copy.getValues()[1][1], 1.6);
        assertEquals(uut.getIdentifiers().size(), 4);
    }
}

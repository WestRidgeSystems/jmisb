package org.jmisb.st1010;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class SDCCParserTest {

    public SDCCParserTest() {}

    @Test
    public void checkSingleStandardDeviation() throws KlvParseException {
        SDCCParser parser = new SDCCParser();
        SDCC result =
                parser.parse(
                        new byte[] {
                            (byte) 0x01,
                            (byte) 0x80,
                            (byte) 0x04,
                            (byte) 0x40,
                            (byte) 0x4C,
                            (byte) 0xCC,
                            (byte) 0xCD
                        });
        assertEquals(result.getStandardDeviationLength(), 4);
        assertEquals(result.getCorrelationCoefficientLength(), 0);
        assertEquals(result.getStandardDeviationFormat(), EncodingFormat.IEEE);
        assertEquals(result.getCorrelationCoefficientFormat(), EncodingFormat.IEEE);
        assertValuesAreEqual(result.getValues(), new double[][] {{3.2}});
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp =
                    "ST 1010 parsing only supports 1 and 2 byte mode selection, got 0x80, 0x84")
    public void checkBadModeControl() throws KlvParseException {
        SDCCParser parser = new SDCCParser();
        parser.parse(
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x80,
                    (byte) 0x84,
                    (byte) 0x00,
                    (byte) 0x40,
                    (byte) 0x4C,
                    (byte) 0xCC,
                    (byte) 0xCD
                });
    }

    @Test
    public void checkSingleStandardDeviationDouble() throws KlvParseException {
        SDCCParser parser = new SDCCParser();
        SDCC result =
                parser.parse(
                        new byte[] {
                            (byte) 0x01,
                            (byte) 0x90,
                            (byte) 0x08,
                            (byte) 0x40,
                            (byte) 0x2A,
                            (byte) 0x66,
                            (byte) 0x66,
                            (byte) 0x66,
                            (byte) 0x66,
                            (byte) 0x66,
                            (byte) 0x66
                        });
        assertEquals(result.getStandardDeviationLength(), 8);
        assertEquals(result.getCorrelationCoefficientLength(), 0);
        assertEquals(result.getStandardDeviationFormat(), EncodingFormat.IEEE);
        assertEquals(result.getCorrelationCoefficientFormat(), EncodingFormat.ST1201);
        assertValuesAreEqual(result.getValues(), new double[][] {{13.2}});
    }

    @Test
    public void checkTwoVariable() throws KlvParseException {
        SDCCParser parser = new SDCCParser();
        SDCC result =
                parser.parse(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x84,
                            (byte) 0x04,
                            (byte) 0x40,
                            (byte) 0x4C,
                            (byte) 0xCC,
                            (byte) 0xCD,
                            (byte) 0x41,
                            (byte) 0x81,
                            (byte) 0x99,
                            (byte) 0x9a,
                            (byte) 0x3e,
                            (byte) 0xfa,
                            (byte) 0xe1,
                            (byte) 0x48
                        });
        assertEquals(result.getStandardDeviationLength(), 4);
        assertEquals(result.getCorrelationCoefficientLength(), 4);
        assertEquals(result.getStandardDeviationFormat(), EncodingFormat.IEEE);
        assertEquals(result.getCorrelationCoefficientFormat(), EncodingFormat.IEEE);
        assertValuesAreEqual(result.getValues(), new double[][] {{3.2, 0.49}, {0.49, 16.2}});
    }

    @Test
    public void checkTwoVariableMode1() throws KlvParseException {
        SDCCParser parser = new SDCCParser();
        parser.setStandardDeviationFormat(EncodingFormat.IEEE);
        SDCC result =
                parser.parse(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x42,
                            (byte) 0x40,
                            (byte) 0x4C,
                            (byte) 0xCC,
                            (byte) 0xCD,
                            (byte) 0x41,
                            (byte) 0x81,
                            (byte) 0x99,
                            (byte) 0x9a,
                            (byte) 0x5f,
                            (byte) 0x5c
                        });
        assertEquals(result.getStandardDeviationLength(), 4);
        assertEquals(result.getCorrelationCoefficientLength(), 2);
        assertEquals(result.getStandardDeviationFormat(), EncodingFormat.IEEE);
        assertEquals(result.getCorrelationCoefficientFormat(), EncodingFormat.ST1201);
        assertValuesAreEqual(result.getValues(), new double[][] {{3.2, 0.49}, {0.49, 16.2}});
    }

    private void assertValuesAreEqual(double[][] actual, double[][] expected) {
        assertEquals(actual.length, expected.length);
        if (expected.length > 0) {
            assertEquals(actual[0].length, expected[0].length);
            for (int r = 0; r < expected.length; r++) {
                for (int c = 0; c < expected[r].length; c++) {
                    assertEquals(actual[r][c], expected[r][c], 0.00001);
                }
            }
        }
    }

    @Test
    public void checkThreeVariableSparse() throws KlvParseException {
        SDCCParser parser = new SDCCParser();
        SDCC result =
                parser.parse(
                        new byte[] {
                            (byte) 0x03,
                            (byte) 0xB3,
                            (byte) 0x04,
                            (byte) 0b01000000,
                            (byte) 0x40,
                            (byte) 0x4C,
                            (byte) 0xCC,
                            (byte) 0xCD,
                            (byte) 0x41,
                            (byte) 0x81,
                            (byte) 0x99,
                            (byte) 0x9a,
                            (byte) 0x43,
                            (byte) 0x6a,
                            (byte) 0x33,
                            (byte) 0x33,
                            (byte) 0x5f,
                            (byte) 0x5c,
                            (byte) 0x28
                        });
        assertEquals(result.getStandardDeviationLength(), 4);
        assertEquals(result.getCorrelationCoefficientLength(), 3);
        assertEquals(result.getStandardDeviationFormat(), EncodingFormat.IEEE);
        assertEquals(result.getCorrelationCoefficientFormat(), EncodingFormat.ST1201);
        assertValuesAreEqual(
                result.getValues(),
                new double[][] {
                    {3.2, 0.0, 0.49},
                    {0.0, 16.2, 0.0},
                    {0.49, 0.0, 234.2}
                });
    }

    @Test
    public void checkFiveVariableSparse() throws KlvParseException {
        SDCCParser parser = new SDCCParser();
        SDCC result =
                parser.parse(
                        new byte[] {
                            (byte) 0x05,
                            (byte) 0xB3,
                            (byte) 0x04,
                            (byte) 0b01000000,
                            (byte) 0b10000000,
                            (byte) 0x40,
                            (byte) 0x4C,
                            (byte) 0xCC,
                            (byte) 0xCD,
                            (byte) 0x41,
                            (byte) 0x81,
                            (byte) 0x99,
                            (byte) 0x9a,
                            (byte) 0x43,
                            (byte) 0x6a,
                            (byte) 0x33,
                            (byte) 0x33,
                            (byte) 0x3e,
                            (byte) 0xa3,
                            (byte) 0xd7,
                            (byte) 0x0a,
                            (byte) 0x40,
                            (byte) 0xc9,
                            (byte) 0x99,
                            (byte) 0x9a,
                            (byte) 0x5f,
                            (byte) 0x5c,
                            (byte) 0x28,
                            (byte) 0x80,
                            (byte) 0x00,
                            (byte) 0x00
                        });
        assertEquals(result.getStandardDeviationLength(), 4);
        assertEquals(result.getCorrelationCoefficientLength(), 3);
        assertEquals(result.getStandardDeviationFormat(), EncodingFormat.IEEE);
        assertEquals(result.getCorrelationCoefficientFormat(), EncodingFormat.ST1201);
        assertValuesAreEqual(
                result.getValues(),
                new double[][] {
                    {3.2, 0.0, 0.49, 0.0, 0.0},
                    {0.0, 16.2, 0.0, 0.0, 0.0},
                    {0.49, 0.0, 234.2, 0.0, 1.0},
                    {0.0, 0.0, 0.0, 0.32, 0.0},
                    {0.0, 0.0, 1.0, 0.0, 6.3}
                });
    }
}

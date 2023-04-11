package org.jmisb.st1010;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for SDCCWriter */
public class SDCCWriterTest {

    public SDCCWriterTest() {}

    @Test
    public void checkSingleStandardDeviation() throws KlvParseException {
        SDCC sdcc = new SDCC();
        sdcc.setValues(new double[][] {{3.2}});
        sdcc.setStandardDeviationLength(4);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        SDCCSerialiser writer = new SDCCSerialiser();
        byte[] encoded = writer.encode(sdcc);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x80,
                    (byte) 0x04,
                    (byte) 0x40,
                    (byte) 0x4C,
                    (byte) 0xCC,
                    (byte) 0xCD
                });
    }

    @Test
    public void checkSingleStandardDeviationDouble() throws KlvParseException {
        SDCC sdcc = new SDCC();
        sdcc.setValues(new double[][] {{13.2}});
        sdcc.setStandardDeviationLength(8);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        SDCCSerialiser writer = new SDCCSerialiser();
        byte[] encoded = writer.encode(sdcc);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x80,
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
    }

    @Test
    public void checkSingleStandardDeviationDoubleMode1Fallback() throws KlvParseException {
        SDCC sdcc = new SDCC();
        sdcc.setValues(new double[][] {{13.2}});
        sdcc.setStandardDeviationLength(8);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        SDCCSerialiser writer = new SDCCSerialiser();
        writer.setPreferMode1(true);
        byte[] encoded = writer.encode(sdcc);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x80,
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
    }

    @Test
    public void checkTwoVariable() throws KlvParseException {
        SDCC sdcc = new SDCC();
        sdcc.setValues(new double[][] {{3.2, 0.49}, {0.49, 16.2}});
        sdcc.setStandardDeviationLength(4);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        sdcc.setCorrelationCoefficientLength(4);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.IEEE);
        SDCCSerialiser writer = new SDCCSerialiser();
        byte[] encoded = writer.encode(sdcc);
        assertEquals(
                encoded,
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
    }

    @Test
    public void checkTwoVariableST1201() throws KlvParseException {
        SDCC sdcc = new SDCC();
        sdcc.setValues(new double[][] {{3.2, 0.49}, {0.49, 16.2}});
        sdcc.setStandardDeviationLength(4);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        sdcc.setCorrelationCoefficientLength(3);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.ST1201);
        SDCCSerialiser writer = new SDCCSerialiser();
        byte[] encoded = writer.encode(sdcc);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x93,
                    (byte) 0x04,
                    (byte) 0x40,
                    (byte) 0x4C,
                    (byte) 0xCC,
                    (byte) 0xCD,
                    (byte) 0x41,
                    (byte) 0x81,
                    (byte) 0x99,
                    (byte) 0x9a,
                    (byte) 0x5f,
                    (byte) 0x5c,
                    (byte) 0x28,
                });
    }

    @Test
    public void checkTwoVariableST1201Mode1Fallback() throws KlvParseException {
        SDCC sdcc = new SDCC();
        sdcc.setValues(new double[][] {{3.2, 0.49}, {0.49, 16.2}});
        sdcc.setStandardDeviationLength(4);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        sdcc.setCorrelationCoefficientLength(8);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.ST1201);
        SDCCSerialiser writer = new SDCCSerialiser();
        writer.setSparseEnabled(false);
        writer.setPreferMode1(true);
        byte[] encoded = writer.encode(sdcc);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x98,
                    (byte) 0x04,
                    (byte) 0x40,
                    (byte) 0x4C,
                    (byte) 0xCC,
                    (byte) 0xCD,
                    (byte) 0x41,
                    (byte) 0x81,
                    (byte) 0x99,
                    (byte) 0x9a,
                    (byte) 0x5f,
                    (byte) 0x5c,
                    (byte) 0x28,
                    (byte) 0xf5,
                    (byte) 0xc2,
                    (byte) 0x8f,
                    (byte) 0x5c,
                    (byte) 0x00
                });
    }

    @Test
    public void checkTwoVariableIEEEDoubleCovariance() throws KlvParseException {
        SDCC sdcc = new SDCC();
        sdcc.setValues(new double[][] {{3.2, 0.49}, {0.49, 16.2}});
        sdcc.setStandardDeviationLength(4);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        sdcc.setCorrelationCoefficientLength(8);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.IEEE);
        SDCCSerialiser writer = new SDCCSerialiser();
        byte[] encoded = writer.encode(sdcc);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x88,
                    (byte) 0x04,
                    (byte) 0x40,
                    (byte) 0x4C,
                    (byte) 0xCC,
                    (byte) 0xCD,
                    (byte) 0x41,
                    (byte) 0x81,
                    (byte) 0x99,
                    (byte) 0x9A,
                    (byte) 0x3F,
                    (byte) 0xDF,
                    (byte) 0x5C,
                    (byte) 0x28,
                    (byte) 0xF5,
                    (byte) 0xC2,
                    (byte) 0x8F,
                    (byte) 0x5C
                });
    }

    @Test
    public void checkTwoVariableIEEEDoubleCovarianceMode1Fallback() throws KlvParseException {
        SDCC sdcc = new SDCC();
        sdcc.setValues(new double[][] {{3.2, 0.49}, {0.49, 16.2}});
        sdcc.setStandardDeviationLength(4);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        sdcc.setCorrelationCoefficientLength(8);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.IEEE);
        SDCCSerialiser writer = new SDCCSerialiser();
        writer.setPreferMode1(true);
        byte[] encoded = writer.encode(sdcc);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x88,
                    (byte) 0x04,
                    (byte) 0x40,
                    (byte) 0x4C,
                    (byte) 0xCC,
                    (byte) 0xCD,
                    (byte) 0x41,
                    (byte) 0x81,
                    (byte) 0x99,
                    (byte) 0x9A,
                    (byte) 0x3F,
                    (byte) 0xDF,
                    (byte) 0x5C,
                    (byte) 0x28,
                    (byte) 0xF5,
                    (byte) 0xC2,
                    (byte) 0x8F,
                    (byte) 0x5C
                });
    }

    @Test
    public void checkTwoVariableMode1() throws KlvParseException {
        SDCC sdcc = new SDCC();
        sdcc.setValues(new double[][] {{3.2, 0.49}, {0.49, 16.2}});
        sdcc.setStandardDeviationLength(4);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        sdcc.setCorrelationCoefficientLength(2);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.ST1201);
        SDCCSerialiser writer = new SDCCSerialiser();
        writer.setPreferMode1(true);
        writer.setSparseEnabled(false);
        byte[] encoded = writer.encode(sdcc);
        assertEquals(
                encoded,
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
        assertTrue(writer.isPreferMode1());
    }

    @Test
    public void checkThreeVariableNotSparse() throws KlvParseException {
        SDCC sdcc = new SDCC();
        sdcc.setValues(
                new double[][] {
                    {3.2, 0.0, 0.49},
                    {0.0, 16.2, 0.0},
                    {0.49, 0.0, 234.2}
                });
        sdcc.setStandardDeviationLength(4);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        sdcc.setCorrelationCoefficientLength(3);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.ST1201);
        SDCCSerialiser writer = new SDCCSerialiser();
        writer.setSparseEnabled(false);
        byte[] encoded = writer.encode(sdcc);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x03,
                    (byte) 0x93,
                    (byte) 0x04,
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
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x5f,
                    (byte) 0x5c,
                    (byte) 0x28,
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                });
    }

    @Test
    public void checkThreeVariableSparse() throws KlvParseException {
        SDCC sdcc = new SDCC();
        sdcc.setValues(
                new double[][] {
                    {3.2, 0.0, 0.49},
                    {0.0, 16.2, 0.0},
                    {0.49, 0.0, 234.2}
                });
        sdcc.setStandardDeviationLength(4);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        sdcc.setCorrelationCoefficientLength(3);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.ST1201);
        SDCCSerialiser writer = new SDCCSerialiser();
        writer.setSparseEnabled(true);
        byte[] encoded = writer.encode(sdcc);
        assertEquals(
                encoded,
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
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "SDCC requires a square input array")
    public void checkNonSquareValues() {
        SDCC sdcc = new SDCC();
        sdcc.setValues(
                new double[][] {
                    {3.2, 0.0, 0.49},
                    {0.0, 16.2, 0.0}
                });
        SDCCSerialiser writer = new SDCCSerialiser();
        writer.encode(sdcc);
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp =
                    "SDCC IEEE format length 4 and 8 is currently supported. More work required for 2 byte. Other lengths not sensible.")
    public void checkIEEEBadLengthStandardDeviation() {
        SDCC sdcc = new SDCC();
        sdcc.setValues(new double[][] {{3.2, 0.0, 0.49}, {0.0, 16.2, 0.0}, {0.49, 0.0, 234.2}});
        sdcc.setStandardDeviationLength(3);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        sdcc.setCorrelationCoefficientLength(4);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.IEEE);
        SDCCSerialiser writer = new SDCCSerialiser();
        writer.encode(sdcc);
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp =
                    "SDCC IEEE format length 4 and 8 is currently supported. More work required for 2 byte. Other lengths not sensible.")
    public void checkIEEEBadLengthCorrelationCoefficients() {
        SDCC sdcc = new SDCC();
        sdcc.setValues(new double[][] {{3.2, 0.0, 0.49}, {0.0, 16.2, 0.0}, {0.49, 0.0, 234.2}});
        sdcc.setStandardDeviationLength(4);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        sdcc.setCorrelationCoefficientLength(3);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.IEEE);
        SDCCSerialiser writer = new SDCCSerialiser();
        writer.encode(sdcc);
    }

    @Test
    public void checkThreeVariableSparseDefault() throws KlvParseException {
        SDCC sdcc = new SDCC();
        sdcc.setValues(
                new double[][] {
                    {3.2, 0.0, 0.49},
                    {0.0, 16.2, 0.0},
                    {0.49, 0.0, 234.2}
                });
        sdcc.setStandardDeviationLength(4);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        sdcc.setCorrelationCoefficientLength(3);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.ST1201);
        SDCCSerialiser writer = new SDCCSerialiser();
        byte[] encoded = writer.encode(sdcc);
        assertEquals(
                encoded,
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
    }

    @Test
    public void checkFiveVariableSparse() throws KlvParseException {
        SDCC sdcc = new SDCC();
        sdcc.setValues(
                new double[][] {
                    {3.2, 0.0, 0.49, 0.0, 0.0},
                    {0.0, 16.2, 0.0, 0.0, 0.0},
                    {0.49, 0.0, 234.2, 0.0, 1.0},
                    {0.0, 0.0, 0.0, 0.32, 0.0},
                    {0.0, 0.0, 1.0, 0.0, 6.3}
                });
        sdcc.setStandardDeviationLength(4);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        sdcc.setCorrelationCoefficientLength(3);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.ST1201);
        SDCCSerialiser writer = new SDCCSerialiser();
        byte[] encoded = writer.encode(sdcc);
        assertEquals(
                encoded,
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
    }

    @Test
    public void checkFiveVariableSparseMode1() throws KlvParseException {
        SDCC sdcc = new SDCC();
        sdcc.setValues(
                new double[][] {
                    {3.2, 0.0, 0.49, 0.0, 0.0},
                    {0.0, 16.2, 0.0, 0.0, 0.0},
                    {0.49, 0.0, 234.2, 0.0, 1.0},
                    {0.0, 0.0, 0.0, 0.32, 0.0},
                    {0.0, 0.0, 1.0, 0.0, 6.3}
                });
        sdcc.setStandardDeviationLength(4);
        sdcc.setStandardDeviationFormat(EncodingFormat.IEEE);
        sdcc.setCorrelationCoefficientLength(3);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.ST1201);
        SDCCSerialiser writer = new SDCCSerialiser();
        writer.setPreferMode1(true);
        byte[] encoded = writer.encode(sdcc);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x05,
                    (byte) 0x4B,
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
    }
}

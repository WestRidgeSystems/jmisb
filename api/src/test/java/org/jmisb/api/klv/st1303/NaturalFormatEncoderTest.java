package org.jmisb.api.klv.st1303;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for BooleanArrayEncoder. */
public class NaturalFormatEncoderTest {

    public NaturalFormatEncoderTest() {}

    @Test(expectedExceptions = KlvParseException.class)
    public void check1DRealBadLength() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encode(new double[0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DIntBadColumns() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encode(new long[1][0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DIntBadRows() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encode(new long[0][1]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DIntBadRowsAndColumns() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encode(new long[0][0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check1DUintBadLength() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encodeUnsigned(new long[0]);
    }

    @Test
    public void check1DimEncoding() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        long[] input = new long[] {130, 170, 155, 143, 190};
        byte[] encoded = encoder.encodeUnsigned(input);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x82,
                    (byte) 0xaa,
                    (byte) 0x9B,
                    (byte) 0x8F,
                    (byte) 0xBE
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        long[] decoded = checkDecoder.decodeUInt1D(encoded, 0);
        assertEquals(decoded, input);
    }

    @Test
    public void check1DimEncodingTwpByte() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        long[] input = new long[] {130, 170, 258, 143, 190};
        byte[] encoded = encoder.encodeUnsigned(input);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x82,
                    (byte) 0x00,
                    (byte) 0xaa,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x8F,
                    (byte) 0x00,
                    (byte) 0xBE
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        long[] decoded = checkDecoder.decodeUInt1D(encoded, 0);
        assertEquals(decoded, input);
    }
}

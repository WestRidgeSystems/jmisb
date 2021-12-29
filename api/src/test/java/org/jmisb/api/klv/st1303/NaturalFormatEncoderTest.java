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
    public void check1DIntBadLength() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encode(new long[0]);
    }

    @Test
    public void check1DimSignedEncoding() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        long[] input = new long[] {130, -170, 65535, 143, -256};
        byte[] encoded = encoder.encode(input);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x08,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x82,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0x56,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x8f,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0x00
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        long[] decoded = checkDecoder.decodeInt1D(encoded, 0);
        assertEquals(decoded, input);
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
    public void check1DimUnsignedEncoding() throws KlvParseException {
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
    public void check1DimUnsignedEncodingTwoByte() throws KlvParseException {
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

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DUnsignedBadColumns() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encodeUnsigned(new long[1][0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DUnsignedBadRows() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encodeUnsigned(new long[0][1]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DUnsignedBadRowsAndColumns() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encodeUnsigned(new long[0][0]);
    }

    @Test
    public void check2DUnsigned() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        byte[] encoded = encoder.encodeUnsigned(new long[][] {{130, 170, 258}, {143, 190, 5}});
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x03,
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
                    (byte) 0xBE,
                    (byte) 0x00,
                    (byte) 0x05
                });
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DBooleanBadColumns() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encode(new boolean[1][0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DBooleanBadRows() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encode(new boolean[0][1]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DBooleanBadRowsAndColumns() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encode(new boolean[0][0]);
    }

    @Test
    public void check2DBoolean() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        byte[] encoded =
                encoder.encode(new boolean[][] {{true, false, true}, {false, false, true}});
        assertEquals(
                encoded,
                new byte[] {0x02, 0x02, 0x03, 0x01, 0x1, 0x01, 0x00, 0x01, 0x00, 0x00, 0x01});
    }
}

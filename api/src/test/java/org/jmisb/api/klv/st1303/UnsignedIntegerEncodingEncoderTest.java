package org.jmisb.api.klv.st1303;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for UnsignedIntegerEncodingEncoder. */
public class UnsignedIntegerEncodingEncoderTest {

    public UnsignedIntegerEncodingEncoderTest() {}

    @Test
    public void check2D() throws KlvParseException {
        UnsignedIntegerEncodingEncoder encoder = new UnsignedIntegerEncodingEncoder();
        long[][] input = new long[][] {{12, 54, 350}, {2, 2048, 0}, {127, 128, 1}};
        byte[] encoded = encoder.encode(input);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x00,
                    (byte) 0x0C,
                    (byte) 0x36,
                    (byte) 0x82,
                    (byte) 0x5E,
                    (byte) 0x02,
                    (byte) 0x90,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x7F,
                    (byte) 0x81,
                    (byte) 0x00,
                    (byte) 0x01
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        long[][] decoded = checkDecoder.decodeUInt2D(encoded, 0);
        assertEquals(decoded, input);
    }

    @Test
    public void check2DimEncodingBias() throws KlvParseException {
        UnsignedIntegerEncodingEncoder encoder = new UnsignedIntegerEncodingEncoder();
        long[][] input = new long[][] {{130, 170, 155, 143, 190}};
        byte[] encoded = encoder.encode(input);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        long[][] decoded = checkDecoder.decodeUInt2D(encoded, 0);
        assertEquals(decoded, input);
    }

    @Test
    public void check1DimEncodingBias() throws KlvParseException {
        UnsignedIntegerEncodingEncoder encoder = new UnsignedIntegerEncodingEncoder();
        long[] input = new long[] {130, 170, 155, 143, 190};
        byte[] encoded = encoder.encode(input);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        long[] decoded = checkDecoder.decodeUInt1D(encoded, 0);
        assertEquals(decoded, input);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkDimension1D() throws KlvParseException {
        UnsignedIntegerEncodingEncoder encoder = new UnsignedIntegerEncodingEncoder();
        encoder.encode(new long[0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadRowDimension() throws KlvParseException {
        UnsignedIntegerEncodingEncoder encoder = new UnsignedIntegerEncodingEncoder();
        encoder.encode(new long[0][1]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadColumnDimension() throws KlvParseException {
        UnsignedIntegerEncodingEncoder encoder = new UnsignedIntegerEncodingEncoder();
        encoder.encode(new long[1][0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadBothDimensions() throws KlvParseException {
        UnsignedIntegerEncodingEncoder encoder = new UnsignedIntegerEncodingEncoder();
        encoder.encode(new long[0][0]);
    }
}

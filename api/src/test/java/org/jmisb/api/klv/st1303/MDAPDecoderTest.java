package org.jmisb.api.klv.st1303;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/**
 * Unit tests for MDAPDecoder.
 *
 * <p>These essentially check rebuilding structures from byte arrays.
 */
public class MDAPDecoderTest {

    public MDAPDecoderTest() {}

    @Test(expectedExceptions = KlvParseException.class)
    public void checkShortArray() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint2D(new byte[] {}, 0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadDimensions() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint2D(
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x02,
                    (byte) 0xc0,
                    (byte) 0x8c,
                    (byte) 0x20,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x40,
                    (byte) 0xd2,
                    (byte) 0x8e,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x8e,
                    (byte) 0x00
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadAPA_Unused() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x00, // APA
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadAPA_BooleanArray() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x03, // APA
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadAPA_UnsignedInteger() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x04, // APA
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadAPA_RunLengthEncoded() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x05, // APA
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadAPA_NoSuchValue() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x55, // APA
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                },
                0);
    }

    @Test
    public void decodeDouble2D_NaturalEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[][] decoded =
                decoder.decodeFloatingPoint2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x08,
                            (byte) 0x01,
                            (byte) 0x40,
                            (byte) 0x24,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        },
                        0);
        double[][] expected = new double[][] {{10.0}};
        assertEquals(decoded, expected);
    }

    @Test
    public void decodeDouble2D_NaturalEncodingOffset() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[][] decoded =
                decoder.decodeFloatingPoint2D(
                        new byte[] {
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x08,
                            (byte) 0x01,
                            (byte) 0x40,
                            (byte) 0x24,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        },
                        2);
        double[][] expected = new double[][] {{10.0}};
        assertEquals(decoded, expected);
    }

    @Test
    public void decodeDouble2D_NaturalEncodingFloat() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[][] decoded =
                decoder.decodeFloatingPoint2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x04,
                            (byte) 0x01,
                            (byte) 0x41,
                            (byte) 0x20,
                            (byte) 0x00,
                            (byte) 0x00
                        },
                        0);
        double[][] expected = new double[][] {{10.0}};
        assertEquals(decoded, expected);
    }

    @Test
    public void decodeDouble2D_NaturalEncodingFloat_twoValues() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[][] decoded =
                decoder.decodeFloatingPoint2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x04,
                            (byte) 0x01,
                            (byte) 0x41,
                            (byte) 0x20,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x41,
                            (byte) 0xA0,
                            (byte) 0x00,
                            (byte) 0x00
                        },
                        0);
        double[][] expected = new double[][] {{10.0, 20.0}};
        assertEquals(decoded, expected);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void decodeDouble2D_NaturalEncoding_BadLength() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x41,
                    (byte) 0x20,
                    (byte) 0x00,
                    (byte) 0x00
                },
                0);
    }

    @Test
    public void decodeDouble2D_ST1201() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[][] decoded =
                decoder.decodeFloatingPoint2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x03,
                            (byte) 0x02,
                            (byte) 0xc0,
                            (byte) 0x8c,
                            (byte) 0x20,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x40,
                            (byte) 0xd2,
                            (byte) 0x8e,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x03,
                            (byte) 0x8e,
                            (byte) 0x00
                        },
                        0);
        double[][] expected = new double[][] {{10.0}};
        assertEquals(decoded, expected);
    }

    @Test
    public void decodeFloatAsDouble2D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[][] decoded =
                decoder.decodeFloatingPoint2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x03,
                            (byte) 0x02,
                            (byte) 0xc4,
                            (byte) 0x61,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x46,
                            (byte) 0x94,
                            (byte) 0x70,
                            (byte) 0x00,
                            (byte) 0x03,
                            (byte) 0x8e,
                            (byte) 0x00
                        },
                        0);
        double[][] expected = new double[][] {{10.0}};
        assertEquals(decoded, expected);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void decodeBadAPASLength() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x02,
                    (byte) 0xc4,
                    (byte) 0x61,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x46,
                    (byte) 0x94,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x8e,
                    (byte) 0x00
                },
                0);
    }

    @Test
    public void decodeDouble2DFourElement() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[][] decoded =
                decoder.decodeFloatingPoint2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x04,
                            (byte) 0x03,
                            (byte) 0x02,
                            (byte) 0xc0,
                            (byte) 0x8c,
                            (byte) 0x20,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x40,
                            (byte) 0xd2,
                            (byte) 0x8e,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x03,
                            (byte) 0x8e,
                            (byte) 0x00,
                            (byte) 0x03,
                            (byte) 0x84,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0xe8,
                            (byte) 0x00,
                            (byte) 0x00
                        },
                        0);
        double[][] expected = new double[][] {{10.0, 0.0, -900.0, Double.NEGATIVE_INFINITY}};
        assertEquals(decoded, expected);
    }

    @Test
    public void decodeDouble2DFourElementSquare() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[][] decoded =
                decoder.decodeFloatingPoint2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x02,
                            (byte) 0x02,
                            (byte) 0x03,
                            (byte) 0x02,
                            (byte) 0xc0,
                            (byte) 0x8c,
                            (byte) 0x20,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x40,
                            (byte) 0xd2,
                            (byte) 0x8e,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x03,
                            (byte) 0x8e,
                            (byte) 0x00,
                            (byte) 0x03,
                            (byte) 0x84,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0xe8,
                            (byte) 0x00,
                            (byte) 0x00
                        },
                        0);
        double[][] expected = new double[][] {{10.0, 0.0}, {-900.0, Double.NEGATIVE_INFINITY}};
        assertEquals(decoded, expected);
    }
}

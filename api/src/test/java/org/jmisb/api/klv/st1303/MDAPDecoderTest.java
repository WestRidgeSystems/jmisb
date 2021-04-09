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
    public void checkShortArrayFloatingPoint1D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint1D(new byte[] {}, 0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkShortArrayFloatingPoint2D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint2D(new byte[] {}, 0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadDimensionsFloatingPoint1D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint1D(
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
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadDimensionsFloatingPoint2D() throws KlvParseException {
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
    public void checkBadAPA_UnusedFloatingPoint1D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint1D(
                new byte[] {
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
    public void checkBadAPA_UnusedFloatingPoint2D() throws KlvParseException {
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
    public void checkBadAPA_BooleanArrayFloatingPoint1D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint1D(
                new byte[] {
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
    public void checkBadAPA_BooleanArrayFloatingPoint2D() throws KlvParseException {
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
    public void checkBadAPA_UnsignedIntegerFloatingPoint1D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint1D(
                new byte[] {
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
    public void checkBadAPA_UnsignedIntegerFloatingPoint2D() throws KlvParseException {
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
    public void checkBadAPA_RunLengthEncodedFloatingPoint1D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint1D(
                new byte[] {
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
    public void checkBadAPA_RunLengthEncodedFloatingPoint2D() throws KlvParseException {
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
    public void decodeDouble1D_NaturalEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[] decoded =
                decoder.decodeFloatingPoint1D(
                        new byte[] {
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
        double[] expected = new double[] {10.0};
        assertEquals(decoded, expected);
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
    public void decodeDouble1D_NaturalEncodingOffset() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[] decoded =
                decoder.decodeFloatingPoint1D(
                        new byte[] {
                            (byte) 0x00,
                            (byte) 0x00,
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
        double[] expected = new double[] {10.0};
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
    public void decodeDouble1D_NaturalEncodingFloat() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[] decoded =
                decoder.decodeFloatingPoint1D(
                        new byte[] {
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
        double[] expected = new double[] {10.0};
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
    public void decodeDouble1D_NaturalEncodingFloat_twoValues() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[] decoded =
                decoder.decodeFloatingPoint1D(
                        new byte[] {
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
        double[] expected = new double[] {10.0, 20.0};
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
    public void decodeDouble1D_NaturalEncoding_BadLength() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint1D(
                new byte[] {
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
    public void decodeDouble1D_ST1201() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[] decoded =
                decoder.decodeFloatingPoint1D(
                        new byte[] {
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
        double[] expected = new double[] {10.0};
        assertEquals(decoded, expected);
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
    public void decodeFloatAsDouble1D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[] decoded =
                decoder.decodeFloatingPoint1D(
                        new byte[] {
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
        double[] expected = new double[] {10.0};
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
    public void decodeBadAPASLength_FloatingPoint1D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeFloatingPoint1D(
                new byte[] {
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

    @Test(expectedExceptions = KlvParseException.class)
    public void decodeBadAPASLength_FloatingPoint2D() throws KlvParseException {
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
    public void decodeDouble1DFourElement() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        double[] decoded =
                decoder.decodeFloatingPoint1D(
                        new byte[] {
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
        double[] expected = new double[] {10.0, 0.0, -900.0, Double.NEGATIVE_INFINITY};
        assertEquals(decoded, expected);
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

    @Test
    public void testBoolean2DimBooleanArrayEncodingSingleElement() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        boolean[][] decoded =
                decoder.decodeBoolean2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x03,
                            (byte) 0x80
                        },
                        0);
        assertEquals(decoded, new boolean[][] {{true}});
    }

    @Test
    public void testBoolean2DimNaturalFormatEncodingSingleElement() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        boolean[][] decoded =
                decoder.decodeBoolean2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x01
                        },
                        0);
        assertEquals(decoded, new boolean[][] {{true}});
    }

    @Test
    public void testBoolean2DimNaturalFormatEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        boolean[][] decoded =
                decoder.decodeBoolean2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x05,
                            (byte) 0x04,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x01
                        },
                        0);
        assertEquals(
                decoded,
                new boolean[][] {
                    {false, true, false, false},
                    {true, false, false, false},
                    {true, false, true, false},
                    {true, false, false, false},
                    {true, true, true, true}
                });
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadST1201APA_Boolean2D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeBoolean2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x02, // APA
                    (byte) 0x00
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadDimensionsBoolean2D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeBoolean2D(
                new byte[] {(byte) 0x01, (byte) 0x05, (byte) 0x01, (byte) 0x03, (byte) 0x00}, 0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadEbytesBoolean2D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeBoolean2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x05,
                    (byte) 0x04,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x48,
                    (byte) 0xA8,
                    (byte) 0xF0
                },
                0);
    }

    @Test
    public void testBoolean2DimBooleanArrayEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        boolean[][] decoded =
                decoder.decodeBoolean2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x05,
                            (byte) 0x04,
                            (byte) 0x01,
                            (byte) 0x03,
                            (byte) 0x48,
                            (byte) 0xA8,
                            (byte) 0xF0
                        },
                        0);
        assertEquals(
                decoded,
                new boolean[][] {
                    {false, true, false, false},
                    {true, false, false, false},
                    {true, false, true, false},
                    {true, false, false, false},
                    {true, true, true, true}
                });
    }

    @Test
    public void testBoolean2DimRunLengthEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        boolean[][] decoded =
                decoder.decodeBoolean2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x05,
                            (byte) 0x04,
                            (byte) 0x01,
                            (byte) 0x05,
                            (byte) 0x0,
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x04,
                            (byte) 0x04,
                            (byte) 0x00,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x03,
                            (byte) 0x03,
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x01
                        },
                        0);
        assertEquals(
                decoded,
                new boolean[][] {
                    {false, true, false, false},
                    {true, false, false, false},
                    {true, false, true, false},
                    {true, false, false, false},
                    {true, true, true, true}
                });
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadUnsignedIntegerAPA_Boolean2D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeBoolean2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x04, // APA
                    (byte) 0x00
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadAPA_Boolean2D() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeBoolean2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x55, // APA
                    (byte) 0x00
                },
                0);
    }

    @Test
    public void testInt2DimNaturalEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        long[][] decoded =
                decoder.decodeInt2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x02
                        },
                        0);
        assertEquals(decoded, new long[][] {{0x2}});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testInt2Dim_BadAPA_Unused() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeInt2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testInt2Dim_BadAPA_ST1201Encoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeInt2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testInt2Dim_BadAPA_BooleanEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeInt2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testInt2Dim_BadAPA_UnsignedIntegerEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeInt2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testInt2Dim_BadAPA_RunLengthEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeInt2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testInt2Dim_BadAPA_NoSuchValue() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeInt2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x55,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testInt2Dim_BadDimension() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeUInt2D(
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test
    public void testUint1DimNaturalEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        long[] decoded =
                decoder.decodeUInt1D(
                        new byte[] {
                            (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x02
                        },
                        0);
        assertEquals(decoded, new long[] {0x2});
    }

    @Test
    public void testUint1DimNaturalEncoding_3Bytes() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        long[] decoded =
                decoder.decodeUInt1D(
                        new byte[] {
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x03,
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x03
                        },
                        0);
        assertEquals(decoded, new long[] {0x2, 0x10203});
    }

    @Test
    public void testUint1DimUnsignedIntegerEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        long[] decoded =
                decoder.decodeUInt1D(
                        new byte[] {
                            (byte) 0x01,
                            (byte) 0x09,
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
                        },
                        0);
        assertEquals(decoded, new long[] {12, 54, 350, 2, 2048, 0, 127, 128, 1});
    }

    @Test
    public void testUint1DimUnsignedIntegerEncodingBias() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        long[] decoded =
                decoder.decodeUInt1D(
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
                        },
                        0);
        assertEquals(decoded, new long[] {130, 170, 155, 143, 190});
    }

    @Test
    public void testUint2DimNaturalEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        long[][] decoded =
                decoder.decodeUInt2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x02
                        },
                        0);
        assertEquals(decoded, new long[][] {{0x2}});
    }

    @Test
    public void testUint2DimNaturalEncoding_3Bytes() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        long[][] decoded =
                decoder.decodeUInt2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x03,
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x03
                        },
                        0);
        assertEquals(decoded, new long[][] {{0x2, 0x10203}});
    }

    @Test
    public void testUint2DimNaturalEncoding_3BytesRow() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        long[][] decoded =
                decoder.decodeUInt2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x03,
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x03
                        },
                        0);
        assertEquals(decoded, new long[][] {{0x2}, {0x10203}});
    }

    @Test
    public void testUint2DimUnsignedIntegerEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        long[][] decoded =
                decoder.decodeUInt2D(
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
                        },
                        0);
        assertEquals(decoded, new long[][] {{12, 54, 350}, {2, 2048, 0}, {127, 128, 1}});
    }

    @Test
    public void testUint2DimUnsignedIntegerEncodingBias() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        long[][] decoded =
                decoder.decodeUInt2D(
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
                        },
                        0);
        assertEquals(decoded, new long[][] {{130, 170, 155, 143, 190}});
    }

    @Test
    public void testUint2DimUnsignedIntegerEncodingBiasRows() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        long[][] decoded =
                decoder.decodeUInt2D(
                        new byte[] {
                            (byte) 0x02,
                            (byte) 0x05,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x04,
                            (byte) 0x81,
                            (byte) 0x02,
                            (byte) 0x00,
                            (byte) 0x28,
                            (byte) 0x19,
                            (byte) 0x0D,
                            (byte) 0x3C
                        },
                        0);
        assertEquals(decoded, new long[][] {{130}, {170}, {155}, {143}, {190}});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testUint1Dim_BadAPA_Unused() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeUInt1D(
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testUint1Dim_BadAPA_ST1201Encoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeUInt1D(
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testUint1Dim_BadAPA_BooleanEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeUInt1D(
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testUint1Dim_BadAPA_RunLengthEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeUInt1D(
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testUint1Dim_BadAPA_NoSuchValue() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeUInt1D(
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x55,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testUint1Dim_BadDimension() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeUInt1D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testUint2Dim_BadAPA_Unused() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeUInt2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testUint2Dim_BadAPA_ST1201Encoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeUInt2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testUint2Dim_BadAPA_BooleanEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeUInt2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testUint2Dim_BadAPA_RunLengthEncoding() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeUInt2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testUint2Dim_BadAPA_NoSuchValue() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeUInt2D(
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x55,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testUint2Dim_BadDimension() throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        decoder.decodeUInt2D(
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x28,
                    (byte) 0x19,
                    (byte) 0x0D,
                    (byte) 0x3C
                },
                0);
    }
}

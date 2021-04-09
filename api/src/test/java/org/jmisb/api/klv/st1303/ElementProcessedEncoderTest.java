package org.jmisb.api.klv.st1303;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ElementProcessedEncoder */
public class ElementProcessedEncoderTest {

    public ElementProcessedEncoderTest() {}

    @Test
    public void check1DSingleItem() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 19000.0, 3);
        byte[] encoded = encoder.encode(new double[] {10.0});
        assertEquals(
                encoded,
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
                });
    }

    @Test
    public void check1DSingleItemFloat() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 19000.0, 3);
        byte[] encoded = encoder.encode(new float[] {10.0f});
        assertEquals(
                encoded,
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
                });
    }

    @Test
    public void check1DSimpleArray() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 19000.0, 3);
        byte[] encoded = encoder.encode(new double[] {10.0, 0.0, -900.0, Double.NEGATIVE_INFINITY});
        assertEquals(
                encoded,
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
                });
    }

    @Test
    public void check1DSimpleArrayFloat() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0f, 19000.0f, 3);
        byte[] encoded =
                encoder.encode(new float[] {10.0f, 0.0f, -900.0f, Float.NEGATIVE_INFINITY});
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x04,
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
                });
    }

    @Test
    public void check2DSingleItem() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 19000.0, 3);
        byte[] encoded = encoder.encode(new double[][] {{10.0}});
        assertEquals(
                encoded,
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
                });
    }

    @Test
    public void check2DSingleItemFloat() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0f, 19000.0f, 3);
        byte[] encoded = encoder.encode(new float[][] {{10.0f}});
        assertEquals(
                encoded,
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
                });
    }

    @Test
    public void check2D() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 19000.0, 3);
        byte[] encoded =
                encoder.encode(new double[][] {{10.0, 0.0, -900.0, Double.NEGATIVE_INFINITY}});
        assertEquals(
                encoded,
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
                });
    }

    @Test
    public void check2DSquare() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 19000.0, 3);
        byte[] encoded =
                encoder.encode(new double[][] {{10.0, 0.0}, {-900.0, Double.NEGATIVE_INFINITY}});
        assertEquals(
                encoded,
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
                });
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check1DDoubleBadLength() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 19000.0, 3);
        assertNotNull(encoder);
        encoder.encode(new double[0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check1DFloatBadLength() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 19000.0, 3);
        assertNotNull(encoder);
        encoder.encode(new float[0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DFloatBadColumns() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 19000.0, 3);
        assertNotNull(encoder);
        encoder.encode(new float[1][0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DFloatBadRows() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 19000.0, 3);
        assertNotNull(encoder);
        encoder.encode(new float[0][1]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DFloatBadRowsAndColumns() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 19000.0, 3);
        assertNotNull(encoder);
        encoder.encode(new float[0][0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DDoubleBadColumns() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 19000.0, 3);
        assertNotNull(encoder);
        encoder.encode(new double[1][0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DDoubleBadRows() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 19000.0, 3);
        assertNotNull(encoder);
        encoder.encode(new double[0][1]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DDoubleBadRowsAndColumns() throws KlvParseException {
        ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 19000.0, 3);
        assertNotNull(encoder);
        encoder.encode(new double[0][0]);
    }
}

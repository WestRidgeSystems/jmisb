package org.jmisb.api.klv.st1303;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for RunLengthEncodingEncoder. */
public class RunLengthEncodingEncoderTest {

    public RunLengthEncodingEncoderTest() {}

    @Test
    public void check2Dboolean() throws KlvParseException {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        boolean[][] input =
                new boolean[][] {
                    {false, true, false, false},
                    {true, false, false, false},
                    {true, false, true, false},
                    {true, false, false, false},
                    {true, true, true, true}
                };
        byte[] encoded = encoder.encode(input);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x05,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        boolean[][] decoded = checkDecoder.decodeBoolean2D(encoded, 0);
        assertEquals(decoded, input);
    }

    @Test
    public void check2DAlternateBoolean() throws KlvParseException {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        boolean[][] input =
                new boolean[][] {
                    {true, true, false, false},
                    {true, false, false, false},
                    {true, false, true, false},
                    {true, false, false, false},
                    {true, true, true, true}
                };
        byte[] encoded = encoder.encode(input);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x05,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x00,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x02
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        boolean[][] decoded = checkDecoder.decodeBoolean2D(encoded, 0);
        assertEquals(decoded, input);
    }

    @Test
    public void check2DRectangularVerticalBoolean() throws KlvParseException {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        boolean[][] input =
                new boolean[][] {
                    {true, true, false, false},
                    {true, true, false, false},
                    {true, true, false, false},
                    {true, true, true, true},
                    {true, true, true, true}
                };
        byte[] encoded = encoder.encode(input);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x05,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x02
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        boolean[][] decoded = checkDecoder.decodeBoolean2D(encoded, 0);
        assertEquals(decoded, input);
    }

    @Test
    public void check2DRectangularHorizontalBoolean() throws KlvParseException {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        boolean[][] input =
                new boolean[][] {
                    {true, true, true, true},
                    {false, false, false, false},
                    {false, false, false, false},
                    {false, false, false, false},
                    {true, true, true, true}
                };
        byte[] encoded = encoder.encode(input);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x05,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x04
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        boolean[][] decoded = checkDecoder.decodeBoolean2D(encoded, 0);
        assertEquals(decoded, input);
    }

    @Test
    public void checkSingleElement2DTrueBoolean() throws KlvParseException {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        byte[] encoded = encoder.encode(new boolean[][] {{true}});
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x05, (byte) 0x01
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        boolean[][] decoded = checkDecoder.decodeBoolean2D(encoded, 0);
        assertEquals(decoded, new boolean[][] {{true}});
    }

    @Test
    public void checkSingleElement2DFalseBoolean() throws KlvParseException {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        byte[] encoded = encoder.encode(new boolean[][] {{false}});
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x05, (byte) 0x00
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        boolean[][] decoded = checkDecoder.decodeBoolean2D(encoded, 0);
        assertEquals(decoded, new boolean[][] {{false}});
    }

    @Test
    public void checkSingleRow2DTrueBoolean() throws KlvParseException {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        byte[] encoded = encoder.encode(new boolean[][] {{true, true, true, true}});
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02, (byte) 0x01, (byte) 0x04, (byte) 0x01, (byte) 0x05, (byte) 0x01
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        boolean[][] decoded = checkDecoder.decodeBoolean2D(encoded, 0);
        assertEquals(decoded, new boolean[][] {{true, true, true, true}});
    }

    @Test
    public void checkSingleRow2DMixedBoolean() throws KlvParseException {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        byte[] encoded = encoder.encode(new boolean[][] {{false, true, true, true}});
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        boolean[][] decoded = checkDecoder.decodeBoolean2D(encoded, 0);
        assertEquals(decoded, new boolean[][] {{false, true, true, true}});
    }

    @Test
    public void checkNonRectangularBoolean() throws KlvParseException {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        boolean[][] input =
                new boolean[][] {
                    {false, true, true, false},
                    {false, true, false, false},
                    {false, true, false, false}
                };
        byte[] encoded = encoder.encode(input);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        boolean[][] decoded = checkDecoder.decodeBoolean2D(encoded, 0);
        assertEquals(decoded, input);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadRowDimensionBoolean() throws KlvParseException {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        encoder.encode(new boolean[0][1]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadColumnDimensionBoolean() throws KlvParseException {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        encoder.encode(new boolean[1][0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadBothDimensionsBoolean() throws KlvParseException {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        encoder.encode(new boolean[0][0]);
    }
}

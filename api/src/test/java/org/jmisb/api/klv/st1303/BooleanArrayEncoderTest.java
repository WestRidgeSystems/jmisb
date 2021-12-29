package org.jmisb.api.klv.st1303;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for BooleanArrayEncoder. */
public class BooleanArrayEncoderTest {

    public BooleanArrayEncoderTest() {}

    @Test
    public void check2D() throws KlvParseException {
        BooleanArrayEncoder encoder = new BooleanArrayEncoder();
        byte[] encoded =
                encoder.encode(
                        new boolean[][] {
                            {false, true, false, false},
                            {true, false, false, false},
                            {true, false, true, false},
                            {true, false, false, false},
                            {true, true, true, true}
                        });
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x05,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x48,
                    (byte) 0xA8,
                    (byte) 0xF0
                });
    }

    @Test
    public void checkSingleElement2D() throws KlvParseException {
        BooleanArrayEncoder encoder = new BooleanArrayEncoder();
        byte[] encoded = encoder.encode(new boolean[][] {{true}});
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x03, (byte) 0x80
                });
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DIntBadColumns() throws KlvParseException {
        BooleanArrayEncoder encoder = new BooleanArrayEncoder();
        assertNotNull(encoder);
        encoder.encode(new boolean[1][0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DIntBadRows() throws KlvParseException {
        BooleanArrayEncoder encoder = new BooleanArrayEncoder();
        assertNotNull(encoder);
        encoder.encode(new boolean[0][1]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DIntBadRowsAndColumns() throws KlvParseException {
        BooleanArrayEncoder encoder = new BooleanArrayEncoder();
        assertNotNull(encoder);
        encoder.encode(new boolean[0][0]);
    }
}

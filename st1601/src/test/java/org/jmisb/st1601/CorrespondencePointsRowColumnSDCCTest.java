package org.jmisb.st1601;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1601 Correspondence Points Row / Column SDCC. */
public class CorrespondencePointsRowColumnSDCCTest {

    private static final double[][] POINTS_1_IMAGE =
            new double[][] {
                {1.5, 1.7, 0.3, 2.7},
                {1.4, 4.2, 0.6, 1.3},
                {0.2, 0.1, 0.8, -0.2}
            };

    private static final double[][] POINTS_2_IMAGE =
            new double[][] {
                {1.5, 1.7, 0.3, 2.7},
                {1.4, 4.2, 0.6, 1.3},
                {0.2, 0.1, 0.8, -0.2},
                {2.5, 1.3, 1.6, 4.3},
                {1.3, 0.6, 3.1, 0.1},
                {-0.1, 0.1, 0.0, 0.8}
            };

    private static final byte[] BYTES_1_IMAGE =
            new byte[] {
                0x02,
                0x03,
                0x04,
                0x03,
                0x01,
                (byte) 0x01,
                (byte) 0x80,
                (byte) 0x00,
                (byte) 0x01,
                (byte) 0xb3,
                (byte) 0x33,
                (byte) 0x00,
                (byte) 0x4c,
                (byte) 0xcc,
                (byte) 0x02,
                (byte) 0xb3,
                (byte) 0x33,
                (byte) 0x01,
                (byte) 0x66,
                (byte) 0x66,
                (byte) 0x04,
                (byte) 0x33,
                (byte) 0x33,
                (byte) 0x00,
                (byte) 0x99,
                (byte) 0x99,
                (byte) 0x01,
                (byte) 0x4c,
                (byte) 0xcc,
                (byte) 0x4c,
                (byte) 0xcc,
                (byte) 0xcc,
                (byte) 0x46,
                (byte) 0x66,
                (byte) 0x66,
                (byte) 0x73,
                (byte) 0x33,
                (byte) 0x33,
                (byte) 0x33,
                (byte) 0x33,
                (byte) 0x33
            };

    private static final byte[] BYTES_2_IMAGE =
            new byte[] {
                0x02,
                0x06,
                0x04,
                0x03,
                0x01,
                (byte) 0x01,
                (byte) 0x80,
                (byte) 0x00,
                (byte) 0x01,
                (byte) 0xb3,
                (byte) 0x33,
                (byte) 0x00,
                (byte) 0x4c,
                (byte) 0xcc,
                (byte) 0x02,
                (byte) 0xb3,
                (byte) 0x33,
                (byte) 0x01,
                (byte) 0x66,
                (byte) 0x66,
                (byte) 0x04,
                (byte) 0x33,
                (byte) 0x33,
                (byte) 0x00,
                (byte) 0x99,
                (byte) 0x99,
                (byte) 0x01,
                (byte) 0x4c,
                (byte) 0xcc,
                (byte) 0x4c,
                (byte) 0xcc,
                (byte) 0xcc,
                (byte) 0x46,
                (byte) 0x66,
                (byte) 0x66,
                (byte) 0x73,
                (byte) 0x33,
                (byte) 0x33,
                (byte) 0x33,
                (byte) 0x33,
                (byte) 0x33,
                (byte) 0x02,
                (byte) 0x80,
                (byte) 0x00,
                (byte) 0x01,
                (byte) 0x4c,
                (byte) 0xcc,
                (byte) 0x01,
                (byte) 0x99,
                (byte) 0x99,
                (byte) 0x04,
                (byte) 0x4c,
                (byte) 0xcc,
                (byte) 0x01,
                (byte) 0x4c,
                (byte) 0xcc,
                (byte) 0x00,
                (byte) 0x99,
                (byte) 0x99,
                (byte) 0x03,
                (byte) 0x19,
                (byte) 0x99,
                (byte) 0x00,
                (byte) 0x19,
                (byte) 0x99,
                (byte) 0x39,
                (byte) 0x99,
                (byte) 0x99,
                (byte) 0x46,
                (byte) 0x66,
                (byte) 0x66,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x73,
                (byte) 0x33,
                (byte) 0x33
            };

    @Test
    public void testConstructFromValue() {
        CorrespondencePointsRowColumnSDCC uut =
                new CorrespondencePointsRowColumnSDCC(POINTS_2_IMAGE);
        assertEquals(uut.getBytes(), BYTES_2_IMAGE);
        assertEquals(
                uut.getDisplayName(),
                "Correspondence Points – Row / Column Standard Deviation & Correlation Coefficients");
        assertEquals(uut.getDisplayableValue(), "[Coefficients]");
        assertEquals(uut.getCoefficients(), POINTS_2_IMAGE);
    }

    @Test
    public void testConstructFromValueSingleImage() {
        CorrespondencePointsRowColumnSDCC uut =
                new CorrespondencePointsRowColumnSDCC(POINTS_1_IMAGE);
        assertEquals(uut.getBytes(), BYTES_1_IMAGE);
        assertEquals(
                uut.getDisplayName(),
                "Correspondence Points – Row / Column Standard Deviation & Correlation Coefficients");
        assertEquals(uut.getDisplayableValue(), "[Coefficients]");
        assertEquals(uut.getCoefficients(), POINTS_1_IMAGE);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        CorrespondencePointsRowColumnSDCC uut =
                new CorrespondencePointsRowColumnSDCC(BYTES_2_IMAGE);
        assertEquals(uut.getBytes(), BYTES_2_IMAGE);
        assertEquals(
                uut.getDisplayName(),
                "Correspondence Points – Row / Column Standard Deviation & Correlation Coefficients");
        assertEquals(uut.getDisplayableValue(), "[Coefficients]");
        assertEquals(uut.getCoefficients().length, POINTS_2_IMAGE.length);
        for (int r = 0; r < uut.getCoefficients().length; r++) {
            assertEquals(uut.getCoefficients()[r], POINTS_2_IMAGE[r], 0.0001);
        }
    }

    @Test
    public void testConstructFromBytesSingleImage() throws KlvParseException {
        CorrespondencePointsRowColumnSDCC uut =
                new CorrespondencePointsRowColumnSDCC(BYTES_1_IMAGE);
        assertEquals(uut.getBytes(), BYTES_1_IMAGE);
        assertEquals(
                uut.getDisplayName(),
                "Correspondence Points – Row / Column Standard Deviation & Correlation Coefficients");
        assertEquals(uut.getDisplayableValue(), "[Coefficients]");
        assertEquals(uut.getCoefficients().length, POINTS_1_IMAGE.length);
        for (int r = 0; r < uut.getCoefficients().length; r++) {
            assertEquals(uut.getCoefficients()[r], POINTS_1_IMAGE[r], 0.0001);
        }
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testConstructFromBytesBadArray() throws KlvParseException {
        new CorrespondencePointsRowColumnSDCC(new byte[0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testConstructFromBytesBadDims() throws KlvParseException {
        new CorrespondencePointsRowColumnSDCC(new byte[] {0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testConstructFromBytesBadAPA() throws KlvParseException {
        new CorrespondencePointsRowColumnSDCC(
                new byte[] {0x02, 0x01, 0x01, 0x03, 0x02, 0x00, 0x00, 0x00});
    }

    @Test
    public void testToBytesBadNumRows() throws KlvParseException {
        CorrespondencePointsRowColumnSDCC uut =
                new CorrespondencePointsRowColumnSDCC(new double[][] {});
        assertEquals(uut.getBytes(), new byte[0]);
    }

    @Test
    public void testToBytesBadNumCols() throws KlvParseException {
        CorrespondencePointsRowColumnSDCC uut =
                new CorrespondencePointsRowColumnSDCC(new double[][] {{}, {}, {}});
        assertEquals(uut.getBytes(), new byte[0]);
    }
}

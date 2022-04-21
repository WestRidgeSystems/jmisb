package org.jmisb.st1601;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1601 Correspondence Points Lat / Lon / Elev SDCC. */
public class CorrespondencePointsLatLonElevSDCCTest {

    private static final double[][] LAT_LON_VALUES =
            new double[][] {
                {1.5, 1.7, 9.3, 2.7},
                {1.4, 4.2, 8.6, 1.3},
                {0.2, 0.1, 0.8, -0.2}
            };

    private static final double[][] LAT_LON_ELEV_VALUES =
            new double[][] {
                {1.5, 1.7, 9.3, 2.7},
                {1.4, 4.2, 8.6, 1.3},
                {0.2, 0.1, 0.8, -0.2},
                {2.5, 11.3, 11.6, 4.3},
                {0.3, 0.6, 0.2, 0.1},
                {-0.1, 0.1, 0.0, 0.8}
            };

    private static final byte[] LAT_LON_BYTES =
            new byte[] {
                0x02,
                0x03,
                0x04,
                0x03,
                0x01,
                0x00,
                0x30,
                0x00,
                0x00,
                0x36,
                0x66,
                0x01,
                0x29,
                (byte) 0x99,
                0x00,
                0x56,
                0x66,
                0x00,
                0x2c,
                (byte) 0xcc,
                0x00,
                (byte) 0x86,
                0x66,
                0x01,
                0x13,
                0x33,
                0x00,
                0x29,
                (byte) 0x99,
                0x4c,
                (byte) 0xcc,
                (byte) 0xcc,
                0x46,
                0x66,
                0x66,
                0x73,
                0x33,
                0x33,
                0x33,
                0x33,
                0x33
            };

    private static final byte[] LAT_LON_ELEV_BYTES =
            new byte[] {
                0x02,
                0x06,
                0x04,
                0x03,
                0x01,
                0x00,
                0x30,
                0x00,
                0x00,
                0x36,
                0x66,
                0x01,
                0x29,
                (byte) 0x99,
                0x00,
                0x56,
                0x66,
                0x00,
                0x2c,
                (byte) 0xcc,
                0x00,
                (byte) 0x86,
                0x66,
                0x01,
                0x13,
                0x33,
                0x00,
                0x29,
                (byte) 0x99,
                0x4c,
                (byte) 0xcc,
                (byte) 0xcc,
                0x46,
                0x66,
                0x66,
                0x73,
                0x33,
                0x33,
                0x33,
                0x33,
                0x33,
                0x00,
                0x50,
                0x00,
                0x01,
                0x69,
                (byte) 0x99,
                0x01,
                0x73,
                0x33,
                0x00,
                (byte) 0x89,
                (byte) 0x99,
                0x53,
                0x33,
                0x33,
                0x66,
                0x66,
                0x66,
                0x4c,
                (byte) 0xcc,
                (byte) 0xcc,
                0x46,
                0x66,
                0x66,
                0x39,
                (byte) 0x99,
                (byte) 0x99,
                0x46,
                0x66,
                0x66,
                0x40,
                0x00,
                0x00,
                0x73,
                0x33,
                0x33,
            };

    @Test
    public void testConstructFromValue() {
        CorrespondencePointsLatLonElevSDCC uut =
                new CorrespondencePointsLatLonElevSDCC(LAT_LON_ELEV_VALUES);
        assertEquals(uut.getBytes(), LAT_LON_ELEV_BYTES);
        assertEquals(
                uut.getDisplayName(),
                "Correspondence Points – Latitude / Longitude / Elevation Standard Deviation & Correlation Coefficients");
        assertEquals(uut.getDisplayableValue(), "[Coefficients]");
        assertEquals(uut.getCoefficients(), LAT_LON_ELEV_VALUES);
    }

    @Test
    public void testConstructFromValueLatLon() {
        CorrespondencePointsLatLonElevSDCC uut =
                new CorrespondencePointsLatLonElevSDCC(LAT_LON_VALUES);
        assertEquals(uut.getBytes(), LAT_LON_BYTES);
        assertEquals(
                uut.getDisplayName(),
                "Correspondence Points – Latitude / Longitude / Elevation Standard Deviation & Correlation Coefficients");
        assertEquals(uut.getDisplayableValue(), "[Coefficients]");
        assertEquals(uut.getCoefficients(), LAT_LON_VALUES);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        CorrespondencePointsLatLonElevSDCC uut =
                new CorrespondencePointsLatLonElevSDCC(LAT_LON_ELEV_BYTES);
        assertEquals(uut.getBytes(), LAT_LON_ELEV_BYTES);
        assertEquals(
                uut.getDisplayName(),
                "Correspondence Points – Latitude / Longitude / Elevation Standard Deviation & Correlation Coefficients");
        assertEquals(uut.getDisplayableValue(), "[Coefficients]");
        assertEquals(uut.getCoefficients().length, LAT_LON_ELEV_VALUES.length);
        for (int r = 0; r < uut.getCoefficients().length; r++) {
            assertEquals(uut.getCoefficients()[r], LAT_LON_ELEV_VALUES[r], 0.0001);
        }
    }

    @Test
    public void testConstructFromBytesLatLon() throws KlvParseException {
        CorrespondencePointsLatLonElevSDCC uut =
                new CorrespondencePointsLatLonElevSDCC(LAT_LON_BYTES);
        assertEquals(uut.getBytes(), LAT_LON_BYTES);
        assertEquals(
                uut.getDisplayName(),
                "Correspondence Points – Latitude / Longitude / Elevation Standard Deviation & Correlation Coefficients");
        assertEquals(uut.getDisplayableValue(), "[Coefficients]");
        assertEquals(uut.getCoefficients().length, LAT_LON_VALUES.length);
        for (int r = 0; r < uut.getCoefficients().length; r++) {
            assertEquals(uut.getCoefficients()[r], LAT_LON_VALUES[r], 0.0001);
        }
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testConstructFromBytesBadArray() throws KlvParseException {
        new CorrespondencePointsLatLonElevSDCC(new byte[0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testConstructFromBytesBadDims() throws KlvParseException {
        new CorrespondencePointsLatLonElevSDCC(new byte[] {0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testConstructFromBytesBadAPA() throws KlvParseException {
        new CorrespondencePointsLatLonElevSDCC(
                new byte[] {0x02, 0x01, 0x01, 0x03, 0x02, 0x00, 0x00, 0x00});
    }

    @Test
    public void testToBytesBadNumRows() throws KlvParseException {
        CorrespondencePointsLatLonElevSDCC uut =
                new CorrespondencePointsLatLonElevSDCC(new double[][] {});
        assertEquals(uut.getBytes(), new byte[0]);
    }

    @Test
    public void testToBytesBadNumCols() throws KlvParseException {
        CorrespondencePointsLatLonElevSDCC uut =
                new CorrespondencePointsLatLonElevSDCC(new double[][] {{}, {}, {}});
        assertEquals(uut.getBytes(), new byte[0]);
    }
}

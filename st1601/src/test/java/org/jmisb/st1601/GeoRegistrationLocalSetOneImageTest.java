package org.jmisb.st1601;

import static org.testng.Assert.*;

import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/**
 * Unit tests for one-image Geo-Registration Local Set.
 *
 * <p>The mandatory items are Document Version (Tag 1), Algorithm Name (Tag 2), and Algorithm
 * Version (Tag 3).
 *
 * <p>For one-image registration, add the two-row version of Correspondence Points - Row / Column
 * (Tag 4), the Correspondence Points - Latitude / Longitude (Tag 5), the algorithm configuration
 * identifier (Tag 7), the Correspondence Points - Elevation (Tag 8) and the error estimates for the
 * correspondence points (Tag 10).
 */
public class GeoRegistrationLocalSetOneImageTest {

    private static final byte[] BYTES =
            new byte[] {
                0x01,
                0x01,
                0x01,
                0x02,
                0x0a,
                0x67,
                0x6f,
                0x6f,
                0x64,
                0x20,
                0x74,
                0x6f,
                0x6f,
                0x6c,
                0x73,
                0x03,
                0x05,
                0x36,
                0x2e,
                0x37,
                0x36,
                0x61,
                0x04,
                0x0d,
                0x02,
                0x02,
                0x04,
                0x01,
                0x01,
                (byte) 0x85,
                (byte) 0x80,
                0x61,
                0x45,
                0x1f,
                0x5b,
                0x7a,
                (byte) 0x81,
                0x05,
                0x35,
                0x02,
                0x02,
                0x04,
                0x04,
                0x02,
                (byte) 0xc0,
                0x66,
                (byte) 0x80,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x40,
                0x66,
                (byte) 0x80,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x35,
                0x3e,
                (byte) 0xfc,
                0x7a,
                0x35,
                0x3e,
                (byte) 0xfc,
                (byte) 0xa4,
                0x35,
                0x3e,
                (byte) 0xfc,
                (byte) 0xce,
                0x35,
                0x3e,
                (byte) 0xfc,
                (byte) 0xf8,
                0x39,
                0x05,
                0x5e,
                0x4a,
                0x39,
                0x05,
                0x5e,
                0x74,
                0x39,
                0x05,
                0x5e,
                (byte) 0x9e,
                0x39,
                0x05,
                0x5e,
                (byte) 0xc8,
                0x07,
                0x10,
                (byte) 0x5d,
                (byte) 0x9b,
                (byte) 0xbe,
                (byte) 0x5d,
                (byte) 0xa2,
                (byte) 0xb9,
                (byte) 0x4b,
                (byte) 0x48,
                (byte) 0x83,
                (byte) 0xcf,
                (byte) 0x0e,
                (byte) 0xdf,
                (byte) 0x75,
                (byte) 0x9b,
                (byte) 0x15,
                (byte) 0x09,
                0x08,
                0x20,
                0x01,
                0x04,
                0x03,
                0x02,
                (byte) 0xc0,
                (byte) 0x8c,
                0x20,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x40,
                (byte) 0xe3,
                (byte) 0x88,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x04,
                (byte) 0xb0,
                0x00,
                0x04,
                (byte) 0xb0,
                (byte) 0x80,
                0x04,
                (byte) 0xb0,
                0x00,
                0x04,
                (byte) 0xaf,
                (byte) 0x80,
                0x0a,
                0x4d,
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

    private static final long[][] POINTS =
            new long[][] {
                {133, 128, 97, 69},
                {31, 91, 122, 129},
            };

    private static final double[][] GEO_POINTS =
            new double[][] {
                {32.98416, 32.98417, 32.98418, 32.98419},
                {48.08388, 48.08389, 48.08390, 48.08391}
            };

    private static final double[] ELEVATIONS = new double[] {1500.0, 1501.0, 1500.0, 1499.0};

    private static final double[][] COEFFICIENTS =
            new double[][] {
                {1.5, 1.7, 9.3, 2.7},
                {1.4, 4.2, 8.6, 1.3},
                {0.2, 0.1, 0.8, -0.2},
                {2.5, 11.3, 11.6, 4.3},
                {0.3, 0.6, 0.2, 0.1},
                {-0.1, 0.1, 0.0, 0.8}
            };

    @Test
    public void checkFromBytesNested() throws KlvParseException {
        GeoRegistrationLocalSet uut =
                GeoRegistrationLocalSet.fromNestedBytes(BYTES, 0, BYTES.length);
        checkLocalSet(uut);
    }

    static void checkLocalSet(GeoRegistrationLocalSet uut) {
        assertEquals(uut.displayHeader(), "ST 1601 Geo-Registration");
        assertEquals(uut.getDisplayName(), "Geo-Registration Local Set");
        assertEquals(uut.getDisplayableValue(), "Geo-Registration");
        assertEquals(uut.getIdentifiers().size(), 8);
        assertTrue(
                uut.getIdentifiers()
                        .containsAll(
                                Set.<GeoRegistrationKey>of(
                                        GeoRegistrationKey.DocumentVersion,
                                        GeoRegistrationKey.AlgorithmName,
                                        GeoRegistrationKey.AlgorithmVersion,
                                        GeoRegistrationKey.CorrespondencePointsRowColumn,
                                        GeoRegistrationKey.CorrespondencePointsLatLon,
                                        GeoRegistrationKey.AlgorithmConfigurationIdentifier,
                                        GeoRegistrationKey.CorrespondencePointsElevation,
                                        GeoRegistrationKey.CorrespondencePointsLatLonElevSDCC)));
        IGeoRegistrationValue docVersion = uut.getField(GeoRegistrationKey.DocumentVersion);
        assertTrue(docVersion instanceof ST1601DocumentVersion);
        assertEquals(docVersion.getDisplayName(), "Document Version");
        assertEquals(docVersion.getDisplayableValue(), "ST 1601.1");
        IGeoRegistrationValue name = uut.getField(GeoRegistrationKey.AlgorithmName);
        assertTrue(name instanceof GeoRegistrationAlgorithmName);
        assertEquals(name.getDisplayName(), "Algorithm Name");
        assertEquals(name.getDisplayableValue(), "good tools");
        IGeoRegistrationValue version = uut.getField(GeoRegistrationKey.AlgorithmVersion);
        assertTrue(version instanceof GeoRegistrationAlgorithmVersion);
        assertEquals(version.getDisplayName(), "Algorithm Version");
        assertEquals(version.getDisplayableValue(), "6.76a");
        IGeoRegistrationValue points =
                uut.getField(GeoRegistrationKey.CorrespondencePointsRowColumn);
        assertTrue(points instanceof CorrespondencePointsRowColumn);
        assertEquals(points.getDisplayName(), "Correspondence Points – Row/Column");
        assertEquals(points.getDisplayableValue(), "[Correspondence Points – Row/Column]");
        CorrespondencePointsRowColumn correspondencePointsRowColumn =
                (CorrespondencePointsRowColumn) points;
        assertEquals(correspondencePointsRowColumn.getCorrespondencePoints(), POINTS);
        IGeoRegistrationValue geoPoints =
                uut.getField(GeoRegistrationKey.CorrespondencePointsLatLon);
        assertTrue(geoPoints instanceof CorrespondencePointsLatLon);
        assertEquals(geoPoints.getDisplayName(), "Correspondence Points – Latitude/Longitude");
        assertEquals(
                geoPoints.getDisplayableValue(), "[Correspondence Points – Latitude/Longitude]");
        CorrespondencePointsLatLon latLonPoints = (CorrespondencePointsLatLon) geoPoints;
        assertEquals(latLonPoints.getCorrespondencePoints().length, GEO_POINTS.length);
        for (int r = 0; r < latLonPoints.getCorrespondencePoints().length; r++) {
            assertEquals(latLonPoints.getCorrespondencePoints()[r], GEO_POINTS[r], 0.00001);
        }
        IGeoRegistrationValue configUuid =
                uut.getField(GeoRegistrationKey.AlgorithmConfigurationIdentifier);
        assertTrue(configUuid instanceof AlgorithmConfigurationIdentifier);
        assertEquals(configUuid.getDisplayName(), "Algorithm Configuration Identifier");
        assertEquals(configUuid.getDisplayableValue(), "5d9bbe5d-a2b9-4b48-83cf-0edf759b1509");
        IGeoRegistrationValue elevations =
                uut.getField(GeoRegistrationKey.CorrespondencePointsElevation);
        assertTrue(elevations instanceof CorrespondencePointsElevation);
        assertEquals(elevations.getDisplayName(), "Correspondence Points – Elevation");
        assertEquals(elevations.getDisplayableValue(), "[Correspondence Points – Elevation]");
        CorrespondencePointsElevation correspondencePointsElevation =
                (CorrespondencePointsElevation) elevations;
        assertEquals(correspondencePointsElevation.getElevations(), ELEVATIONS);
        IGeoRegistrationValue sdcc =
                uut.getField(GeoRegistrationKey.CorrespondencePointsLatLonElevSDCC);
        assertTrue(sdcc instanceof CorrespondencePointsLatLonElevSDCC);
        assertEquals(
                sdcc.getDisplayName(),
                "Correspondence Points – Latitude / Longitude / Elevation Standard Deviation & Correlation Coefficients");
        assertEquals(sdcc.getDisplayableValue(), "[Coefficients]");
        CorrespondencePointsLatLonElevSDCC correspondencePointsLatLonElevSDCC =
                (CorrespondencePointsLatLonElevSDCC) sdcc;
        double[][] coefficients = correspondencePointsLatLonElevSDCC.getCoefficients();
        assertEquals(coefficients.length, COEFFICIENTS.length);
        for (int r = 0; r < coefficients.length; r++) {
            assertEquals(coefficients[r], COEFFICIENTS[r], 0.0001);
        }
        assertEquals(uut.frameMessage(true), BYTES);
    }
}

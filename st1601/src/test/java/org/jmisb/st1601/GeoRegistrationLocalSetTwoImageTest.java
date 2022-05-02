package org.jmisb.st1601;

import static org.testng.Assert.*;

import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/**
 * Unit tests for two-image Geo-Registration Local Set.
 *
 * <p>The mandatory items are Document Version (Tag 1), Algorithm Name (Tag 2), and Algorithm
 * Version (Tag 3).
 *
 * <p>For two-image registration, add the four-row version of Correspondence Points - Row / Column
 * (Tag 4), the second image name (Tag 6), the algorithm configuration identifier (Tag 7), and the
 * error estimates for the correspondence points (Tag 9).
 */
public class GeoRegistrationLocalSetTwoImageTest {

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
                0x15,
                0x02,
                0x04,
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
                0x59,
                0x52,
                0x34,
                0x1b,
                0x7d,
                (byte) 0xb0,
                (byte) 0xcc,
                (byte) 0xd2,
                0x06,
                0x0e,
                0x69,
                0x6d,
                0x61,
                0x67,
                0x65,
                0x20,
                0x6e,
                0x61,
                0x6d,
                0x65,
                0x20,
                0x78,
                0x79,
                0x7a,
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
                0x09,
                0x4D,
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

    static final byte[] BYTES_NON_NESTED =
            new byte[] {
                0x06,
                0x0e,
                0x2b,
                0x34,
                0x02,
                0x0b,
                0x01,
                0x01,
                0x0e,
                0x01,
                0x03,
                0x03,
                0x01,
                0x00,
                0x00,
                0x00,
                (byte) 0x81,
                (byte) 0x9e,
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
                0x15,
                0x02,
                0x04,
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
                0x59,
                0x52,
                0x34,
                0x1b,
                0x7d,
                (byte) 0xb0,
                (byte) 0xcc,
                (byte) 0xd2,
                0x06,
                0x0e,
                0x69,
                0x6d,
                0x61,
                0x67,
                0x65,
                0x20,
                0x6e,
                0x61,
                0x6d,
                0x65,
                0x20,
                0x78,
                0x79,
                0x7a,
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
                0x09,
                0x4D,
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

    private static final long[][] POINTS =
            new long[][] {
                {133, 128, 97, 69},
                {31, 91, 122, 129},
                {89, 82, 52, 27},
                {125, 176, 204, 210}
            };

    private static final double[][] COEFFICIENTS =
            new double[][] {
                {1.5, 1.7, 0.3, 2.7},
                {1.4, 4.2, 0.6, 1.3},
                {0.2, 0.1, 0.8, -0.2},
                {2.5, 1.3, 1.6, 4.3},
                {1.3, 0.6, 3.1, 0.1},
                {-0.1, 0.1, 0.0, 0.8}
            };

    @Test
    public void checkFromBytesNested() throws KlvParseException {
        GeoRegistrationLocalSet uut =
                GeoRegistrationLocalSet.fromNestedBytes(BYTES, 0, BYTES.length);
        checkLocalSet(uut);
    }

    @Test
    public void checkFromBytesNonNested() throws KlvParseException {
        GeoRegistrationLocalSet uut = new GeoRegistrationLocalSet(BYTES_NON_NESTED);
        assertEquals(uut.getUniversalLabel(), GeoRegistrationLocalSet.GeoRegistrationLocalSetUl);
        assertEquals(
                uut.getUniversalLabel().getBytes(),
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x02, 0x0b, 0x01, 0x01, 0x0e, 0x01, 0x03, 0x03, 0x01,
                    0x00, 0x00, 0x00
                });
        checkLocalSet(uut);
    }

    static void checkLocalSet(GeoRegistrationLocalSet uut) {
        assertEquals(uut.displayHeader(), "ST 1601 Geo-Registration");
        assertEquals(uut.getDisplayName(), "Geo-Registration Local Set");
        assertEquals(uut.getDisplayableValue(), "Geo-Registration");
        assertEquals(uut.getIdentifiers().size(), 7);
        assertTrue(
                uut.getIdentifiers()
                        .containsAll(
                                Set.<GeoRegistrationKey>of(
                                        GeoRegistrationKey.DocumentVersion,
                                        GeoRegistrationKey.AlgorithmName,
                                        GeoRegistrationKey.AlgorithmVersion,
                                        GeoRegistrationKey.CorrespondencePointsRowColumn,
                                        GeoRegistrationKey.SecondImageName,
                                        GeoRegistrationKey.AlgorithmConfigurationIdentifier,
                                        GeoRegistrationKey.CorrespondencePointsRowColumnSDCC)));
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
        IGeoRegistrationValue secondImage = uut.getField(GeoRegistrationKey.SecondImageName);
        assertTrue(secondImage instanceof SecondImageName);
        assertEquals(secondImage.getDisplayName(), "Second Image Name");
        assertEquals(secondImage.getDisplayableValue(), "image name xyz");
        IGeoRegistrationValue configUuid =
                uut.getField(GeoRegistrationKey.AlgorithmConfigurationIdentifier);
        assertTrue(configUuid instanceof AlgorithmConfigurationIdentifier);
        assertEquals(configUuid.getDisplayName(), "Algorithm Configuration Identifier");
        assertEquals(configUuid.getDisplayableValue(), "5d9bbe5d-a2b9-4b48-83cf-0edf759b1509");
        IGeoRegistrationValue sdcc =
                uut.getField(GeoRegistrationKey.CorrespondencePointsRowColumnSDCC);
        assertTrue(sdcc instanceof CorrespondencePointsRowColumnSDCC);
        assertEquals(
                sdcc.getDisplayName(),
                "Correspondence Points – Row / Column Standard Deviation & Correlation Coefficients");
        assertEquals(sdcc.getDisplayableValue(), "[Coefficients]");
        CorrespondencePointsRowColumnSDCC correspondencePointsRowColumnSDCC =
                (CorrespondencePointsRowColumnSDCC) sdcc;
        assertEquals(
                correspondencePointsRowColumnSDCC.getCoefficients().length, COEFFICIENTS.length);
        for (int r = 0; r < correspondencePointsRowColumnSDCC.getCoefficients().length; r++) {
            assertEquals(
                    correspondencePointsRowColumnSDCC.getCoefficients()[r],
                    COEFFICIENTS[r],
                    0.0001);
        }
        assertEquals(uut.frameMessage(true), BYTES);
        assertEquals(uut.frameMessage(false), BYTES_NON_NESTED);
    }
}

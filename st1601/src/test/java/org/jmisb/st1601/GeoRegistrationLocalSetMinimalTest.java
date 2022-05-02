package org.jmisb.st1601;

import static org.testng.Assert.*;

import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/**
 * Unit tests for minimal Geo-Registration Local Set.
 *
 * <p>The mandatory items are Document Version, Algorithm Name, and Algorithm Version.
 */
public class GeoRegistrationLocalSetMinimalTest {

    private static final byte[] BYTES =
            new byte[] {
                0x02, 0x0a, 0x67, 0x6f, 0x6f, 0x64, 0x20, 0x74, 0x6f, 0x6f, 0x6c, 0x73, 0x03, 0x05,
                0x36, 0x2e, 0x37, 0x36, 0x61, 0x01, 0x01, 0x01
            };

    @Test
    public void checkFromBytes() throws KlvParseException {
        GeoRegistrationLocalSet uut =
                GeoRegistrationLocalSet.fromNestedBytes(BYTES, 0, BYTES.length);
        checkValues(uut);
    }

    private void checkValues(GeoRegistrationLocalSet uut) {
        assertEquals(uut.displayHeader(), "ST 1601 Geo-Registration");
        assertEquals(uut.getDisplayName(), "Geo-Registration Local Set");
        assertEquals(uut.getDisplayableValue(), "Geo-Registration");
        assertEquals(uut.getIdentifiers().size(), 3);
        assertTrue(
                uut.getIdentifiers()
                        .containsAll(
                                Set.<GeoRegistrationKey>of(
                                        GeoRegistrationKey.DocumentVersion,
                                        GeoRegistrationKey.AlgorithmName,
                                        GeoRegistrationKey.AlgorithmVersion)));
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
    }
}

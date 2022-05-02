package org.jmisb.st1601;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for encode and decode with unknown values. */
public class GeoRegistrationLocalSetWithUnknownTest extends LoggerChecks {

    public GeoRegistrationLocalSetWithUnknownTest() {
        super(GeoRegistrationLocalSet.class);
    }

    private static final byte[] BYTES =
            new byte[] {
                0x02, 0x0a, 0x67, 0x6f, 0x6f, 0x64, 0x20, 0x74, 0x6f, 0x6f, 0x6c, 0x73, 0x03, 0x05,
                0x36, 0x2e, 0x37, 0x36, 0x61, 0x60, 0x01, 0x02, 0x01, 0x01, 0x01
            };

    @Test
    public void checkFromBytes() throws KlvParseException {
        this.verifyNoLoggerMessages();
        GeoRegistrationLocalSet uut =
                GeoRegistrationLocalSet.fromNestedBytes(BYTES, 0, BYTES.length);
        this.verifySingleLoggerMessage("Unknown Geo-Registration tag: Undefined");
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

    @Test
    public void checkFrameUnknown() {
        Map<GeoRegistrationKey, IGeoRegistrationValue> values = new HashMap<>();
        values.put(GeoRegistrationKey.DocumentVersion, new ST1601DocumentVersion(2));
        values.put(
                GeoRegistrationKey.Undefined,
                new IGeoRegistrationValue() {
                    @Override
                    public byte[] getBytes() {
                        return new byte[] {0x00};
                    }

                    @Override
                    public String getDisplayName() {
                        return "Unknown item";
                    }

                    @Override
                    public String getDisplayableValue() {
                        return "Unknown";
                    }
                });
        GeoRegistrationLocalSet localSet = new GeoRegistrationLocalSet(values);
        this.verifyNoLoggerMessages();
        byte[] bytes = localSet.frameMessage(true);
        this.verifySingleLoggerMessage("Skipping undefined Geo-Registration tag: 0");
        assertEquals(bytes, new byte[] {0x01, 0x01, 0x02});
        bytes = localSet.frameMessage(false);
        this.verifySingleLoggerMessage("Skipping undefined Geo-Registration tag: 0");
        assertEquals(
                bytes,
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x02, 0x0b, 0x01, 0x01, 0x0e, 0x01, 0x03, 0x03, 0x01,
                    0x00, 0x00, 0x00, 0x03, 0x01, 0x01, 0x02
                });
    }
}

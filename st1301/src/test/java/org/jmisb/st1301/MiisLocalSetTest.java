package org.jmisb.st1301;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1204.CoreIdentifier;
import org.jmisb.api.klv.st1204.IdType;
import org.testng.annotations.Test;

/** Tests for the ST1301 MIIS Local Set. */
public class MiisLocalSetTest extends LoggerChecks {

    static final byte[] bytes =
            new byte[] {
                // UL
                (byte) 0x06,
                (byte) 0x0E,
                (byte) 0x2B,
                (byte) 0x34,
                (byte) 0x02,
                (byte) 0x0B,
                (byte) 0x01,
                (byte) 0x01,
                (byte) 0x0E,
                (byte) 0x01,
                (byte) 0x03,
                (byte) 0x05,
                (byte) 0x03,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                // Length
                (byte) 0x37,
                // Entry 1
                (byte) 0x03,
                (byte) 0x01,
                (byte) 0x02,
                // Entry 2
                (byte) 0x04,
                (byte) 0x32,
                (byte) 0x01,
                (byte) 0x54,
                (byte) 0xC7,
                (byte) 0xD1,
                (byte) 0x62,
                (byte) 0x53,
                (byte) 0x98,
                (byte) 0xA2,
                (byte) 0x41,
                (byte) 0xC2,
                (byte) 0xBA,
                (byte) 0x6E,
                (byte) 0x90,
                (byte) 0xF8,
                (byte) 0xFC,
                (byte) 0xC7,
                (byte) 0x39,
                (byte) 0x14,
                (byte) 0xE0,
                (byte) 0x47,
                (byte) 0xAB,
                (byte) 0x3E,
                (byte) 0x81,
                (byte) 0xBE,
                (byte) 0x41,
                (byte) 0xED,
                (byte) 0x96,
                (byte) 0x64,
                (byte) 0x09,
                (byte) 0xB0,
                (byte) 0x2F,
                (byte) 0x44,
                (byte) 0x5F,
                (byte) 0xAB,
                (byte) 0x5E,
                (byte) 0x71,
                (byte) 0xB0,
                (byte) 0xDC,
                (byte) 0x20,
                (byte) 0xFE,
                (byte) 0x49,
                (byte) 0x20,
                (byte) 0x82,
                (byte) 0x16,
                (byte) 0x26,
                (byte) 0xD6,
                (byte) 0x4F,
                (byte) 0x61,
                (byte) 0xD8,
                (byte) 0x63
            };

    public MiisLocalSetTest() {
        super(MiisLocalSet.class);
    }

    @Test
    public void parseTags() throws KlvParseException {
        MiisLocalSet localSet = new MiisLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1301 MIIS Augmentation Identifiers");
        assertEquals(localSet.getUniversalLabel(), MiisMetadataConstants.MiisLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 2);
        checkVersionExample(localSet);
        checkCoreIdentifierExample(localSet);
        assertEquals(localSet.frameMessage(false), bytes);
    }

    @Test
    public void fromValues() throws KlvParseException {
        Map<MiisMetadataKey, IMiisMetadataValue> map = new HashMap<>();
        map.put(MiisMetadataKey.Version, new ST1301Version(2));
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString(
                        "0154:C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB/5E71-B0DC-20FE-4920-8216-26D6-4F61-D863:C8");
        map.put(MiisMetadataKey.CoreIdentifier, new ST1301CoreIdentifier(coreIdentifier));
        MiisLocalSet localSet = new MiisLocalSet(map);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1301 MIIS Augmentation Identifiers");
        assertEquals(localSet.getUniversalLabel(), MiisMetadataConstants.MiisLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 2);
        checkVersionExample(localSet);
        checkCoreIdentifierExample(localSet);
        assertEquals(localSet.frameMessage(false), bytes);
    }

    @Test
    public void fromValuesDefaultVersion() throws KlvParseException {
        Map<MiisMetadataKey, IMiisMetadataValue> map = new HashMap<>();
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString(
                        "0154:C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB/5E71-B0DC-20FE-4920-8216-26D6-4F61-D863:C8");
        //
        // "0104:C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB/5E71-B0DC-20FE-4920-8216-26D6-4F61-D863:F7"
        map.put(MiisMetadataKey.CoreIdentifier, new ST1301CoreIdentifier(coreIdentifier));
        MiisLocalSet localSet = new MiisLocalSet(map);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1301 MIIS Augmentation Identifiers");
        assertEquals(localSet.getUniversalLabel(), MiisMetadataConstants.MiisLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 1);
        checkCoreIdentifierExample(localSet);
        assertEquals(localSet.frameMessage(false), bytes);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void frameNoCoreIdentifier() {
        Map<MiisMetadataKey, IMiisMetadataValue> map = new HashMap<>();
        map.put(MiisMetadataKey.Version, new ST1301Version(2));
        MiisLocalSet localSet = new MiisLocalSet(map);
        assertNotNull(localSet);
        localSet.frameMessage(false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void frameNested() {
        Map<MiisMetadataKey, IMiisMetadataValue> map = new HashMap<>();
        map.put(MiisMetadataKey.Version, new ST1301Version(2));
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString(
                        "0154:C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB/5E71-B0DC-20FE-4920-8216-26D6-4F61-D863:C8");
        map.put(MiisMetadataKey.CoreIdentifier, new ST1301CoreIdentifier(coreIdentifier));
        MiisLocalSet localSet = new MiisLocalSet(map);
        assertNotNull(localSet);
        localSet.frameMessage(true);
    }

    static void checkVersionExample(MiisLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(MiisMetadataKey.Version));
        IMiisMetadataValue v = (IMiisMetadataValue) localSet.getField(MiisMetadataKey.Version);
        assertEquals(v.getDisplayName(), "Version");
        assertEquals(v.getDisplayableValue(), "ST 1301.2");
        assertTrue(v instanceof ST1301Version);
        ST1301Version version = (ST1301Version) localSet.getField(MiisMetadataKey.Version);
        assertEquals(version.getVersion(), 2);
    }

    static void checkCoreIdentifierExample(MiisLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(MiisMetadataKey.CoreIdentifier));
        IMiisMetadataValue v =
                (IMiisMetadataValue) localSet.getField(MiisMetadataKey.CoreIdentifier);
        assertEquals(v.getDisplayName(), "Core Identifier");
        assertEquals(
                v.getDisplayableValue(),
                "0154:C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB/5E71-B0DC-20FE-4920-8216-26D6-4F61-D863:C8");
        assertTrue(v instanceof ST1301CoreIdentifier);
        ST1301CoreIdentifier uut =
                (ST1301CoreIdentifier) localSet.getField(MiisMetadataKey.CoreIdentifier);
        CoreIdentifier coreIdentifier = uut.getCoreIdentifier();
        assertEquals(coreIdentifier.getVersion(), 1);
        assertEquals(coreIdentifier.getSensorIdType(), IdType.Virtual);
        assertEquals(coreIdentifier.getPlatformIdType(), IdType.Virtual);
        UUID expectedSensorUUID = UUID.fromString("C7D16253-98A2-41C2-BA6E-90F8FCC73914");
        assertEquals(coreIdentifier.getSensorUUID(), expectedSensorUUID);
        UUID expectedPlatformUUID = UUID.fromString("E047AB3E-81BE-41ED-9664-09B02F445FAB");
        assertEquals(coreIdentifier.getPlatformUUID(), expectedPlatformUUID);
        UUID expectedWindowUUID = UUID.fromString("5E71B0DC-20FE-4920-8216-26D64F61D863");
        assertEquals(coreIdentifier.getWindowUUID(), expectedWindowUUID);
        assertEquals(coreIdentifier.getMinorUUID(), null);
        assertTrue(coreIdentifier.hasValidCheckValue());
    }

    @Test
    public void constructUnknown() throws KlvParseException {
        verifyNoLoggerMessages();
        IMiisMetadataValue unknown =
                MiisLocalSet.createValue(MiisMetadataKey.Undefined, new byte[] {0x01, 0x02});
        this.verifySingleLoggerMessage("Unknown MIIS Metadata tag: Undefined");
        assertNull(unknown);
    }

    @Test
    public void parseTagsWithUnknowns() throws KlvParseException {
        verifyNoLoggerMessages();
        MiisLocalSet localSet =
                new MiisLocalSet(
                        new byte[] {
                            // UL
                            (byte) 0x06,
                            (byte) 0x0E,
                            (byte) 0x2B,
                            (byte) 0x34,
                            (byte) 0x02,
                            (byte) 0x0B,
                            (byte) 0x01,
                            (byte) 0x01,
                            (byte) 0x0E,
                            (byte) 0x01,
                            (byte) 0x03,
                            (byte) 0x05,
                            (byte) 0x03,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            // Length
                            (byte) 0x3B,
                            // Entry 1
                            (byte) 0x03,
                            (byte) 0x01,
                            (byte) 0x02,
                            // Entry 2 - unknown
                            (byte) 0x08,
                            (byte) 0x02,
                            (byte) 0x01,
                            (byte) 0x02,
                            // Entry 3
                            (byte) 0x04,
                            (byte) 0x32,
                            (byte) 0x01,
                            (byte) 0x54,
                            (byte) 0xC7,
                            (byte) 0xD1,
                            (byte) 0x62,
                            (byte) 0x53,
                            (byte) 0x98,
                            (byte) 0xA2,
                            (byte) 0x41,
                            (byte) 0xC2,
                            (byte) 0xBA,
                            (byte) 0x6E,
                            (byte) 0x90,
                            (byte) 0xF8,
                            (byte) 0xFC,
                            (byte) 0xC7,
                            (byte) 0x39,
                            (byte) 0x14,
                            (byte) 0xE0,
                            (byte) 0x47,
                            (byte) 0xAB,
                            (byte) 0x3E,
                            (byte) 0x81,
                            (byte) 0xBE,
                            (byte) 0x41,
                            (byte) 0xED,
                            (byte) 0x96,
                            (byte) 0x64,
                            (byte) 0x09,
                            (byte) 0xB0,
                            (byte) 0x2F,
                            (byte) 0x44,
                            (byte) 0x5F,
                            (byte) 0xAB,
                            (byte) 0x5E,
                            (byte) 0x71,
                            (byte) 0xB0,
                            (byte) 0xDC,
                            (byte) 0x20,
                            (byte) 0xFE,
                            (byte) 0x49,
                            (byte) 0x20,
                            (byte) 0x82,
                            (byte) 0x16,
                            (byte) 0x26,
                            (byte) 0xD6,
                            (byte) 0x4F,
                            (byte) 0x61,
                            (byte) 0xD8,
                            (byte) 0x63
                        });
        this.verifySingleLoggerMessage("Unknown MIIS Metadata tag: 8");
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1301 MIIS Augmentation Identifiers");
        assertEquals(localSet.getUniversalLabel(), MiisMetadataConstants.MiisLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 2);
        checkVersionExample(localSet);
        checkCoreIdentifierExample(localSet);
        assertEquals(localSet.frameMessage(false), bytes);
    }
}

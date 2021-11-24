package org.jmisb.api.klv.st0903.vtrack;

import static org.testng.Assert.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st0903.ST0903Version;
import org.jmisb.api.klv.st0903.VmtiVerticalFieldOfView;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.api.klv.st0903.shared.IVTrackMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.testng.annotations.Test;

/** Tests for the ST0903 VTrack Local Set. */
public class VTrackLocalSetTest extends LoggerChecks {
    public VTrackLocalSetTest() {
        super(VTrackLocalSet.class);
    }

    @Test
    public void parseTag11() throws KlvParseException {
        final byte[] bytes = new byte[] {0x0b, 0x01, 0x04};
        VTrackLocalSet localSet = new VTrackLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 1);
        checkVersionNumberExample(localSet);
        assertEquals(localSet.frameMessage(true), new byte[] {0x0b, 0x01, 0x04, 0x0d, 0x01, 0x00});
    }

    @Test
    public void parseNoTagsFromBytes() throws KlvParseException {
        final byte[] bytes = new byte[] {};
        VTrackLocalSet localSet = new VTrackLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 0);
        assertEquals(localSet.displayHeader(), "ST0903 VTrack");
        assertEquals(localSet.frameMessage(true), new byte[] {0x0b, 0x01, 0x05, 0x0d, 0x01, 0x00});
    }

    @Test
    public void parseLegacyEncoding() throws KlvParseException {
        final byte[] bytes =
                new byte[] {0x65, 0x06, 0x05, 0x03, 0x17, 0x02, 0x0e, 0x39, 0x0b, 0x01, 0x03};
        VTrackLocalSet localSet = new VTrackLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 2);
        assertTrue(localSet.getIdentifiers().contains(VTrackMetadataKey.VersionNumber));
        IVTrackMetadataValue v = localSet.getField(VTrackMetadataKey.VersionNumber);
        assertEquals(v.getDisplayName(), "Version Number");
        assertEquals(v.getDisplayableValue(), "ST0903.3");
        assertEquals(v.getBytes(), new byte[] {0x03});
        assertTrue(v instanceof ST0903Version);
        ST0903Version version = (ST0903Version) localSet.getField(VTrackMetadataKey.VersionNumber);
        assertEquals(version.getVersion(), 3);
        assertTrue(localSet.getIdentifiers().contains(VTrackMetadataKey.VTrackItemSeries));
        IVTrackMetadataValue s = localSet.getField(VTrackMetadataKey.VTrackItemSeries);
        assertEquals(s.getDisplayName(), "Track Points");
        assertEquals(s.getDisplayableValue(), "[1 Points]");
        assertTrue(s instanceof VTrackItemSeries);
        VTrackItemSeries trackSeries = (VTrackItemSeries) s;
        assertEquals(trackSeries.getTrackItems().size(), 1);
        VTrackItem trackItem = trackSeries.getTrackItems().get(0);
        assertEquals(trackItem.getTargetIdentifier(), 3);
        assertEquals(trackItem.getDisplayName(), "VTrackItem");
        assertEquals(trackItem.getIdentifiers().size(), 1);
        assertTrue(trackItem.getIdentifiers().contains(VTrackItemMetadataKey.SensorVerticalFov));
        IVTrackItemMetadataValue itemValue =
                trackItem.getField(VTrackItemMetadataKey.SensorVerticalFov);
        assertTrue(itemValue instanceof VmtiVerticalFieldOfView);
        IKlvValue itemValueFromKey =
                trackItem.getField((IKlvKey) VTrackItemMetadataKey.SensorVerticalFov);
        assertTrue(itemValueFromKey instanceof VmtiVerticalFieldOfView);
        assertEquals(itemValueFromKey.getDisplayableValue(), itemValue.getDisplayableValue());
        VmtiVerticalFieldOfView verticalFoV = (VmtiVerticalFieldOfView) itemValue;
        assertEquals(verticalFoV.getDisplayName(), "Vertical Field of View");
        assertEquals(verticalFoV.getDisplayableValue(), "10.0\u00B0");
        assertEquals(verticalFoV.getFieldOfView(), 10.00, 0.003);
        assertEquals(
                localSet.frameMessage(false),
                new byte[] {
                    (byte) 0x06, // UL
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x1e,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    0x12, // LS length
                    0x0b, // Version - bumped to current
                    0x01,
                    0x05,
                    0x0d, // Num track items
                    0x01,
                    0x01,
                    0x65, // VTrackSeries
                    0x06, // series length
                    0x05, // item length
                    0x03, // target id
                    0x17, // vertical fov - note IMAP encoding
                    0x02,
                    0x05,
                    0x00,
                    0x01, // Checksum
                    0x02,
                    (byte) 0x91,
                    (byte) 0xe2
                });
    }

    @Test
    public void parseTagsWithUnknownTag() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x7F,
                    0x02,
                    (byte) 0x80,
                    (byte) 0xCA, // No such tag
                    0x0a,
                    0x0E,
                    0x44,
                    0x53,
                    0x54,
                    0x4F,
                    0x5F,
                    0x41,
                    0x44,
                    0x53,
                    0x53,
                    0x5F,
                    0x56,
                    0x4D,
                    0x54,
                    0x49,
                    0x0b,
                    0x01,
                    0x04
                };
        verifyNoLoggerMessages();
        VTrackLocalSet localSet = new VTrackLocalSet(bytes);
        verifySingleLoggerMessage("Unknown VTrack Metadata tag: 127");
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 2);
        checkVersionNumberExample(localSet);
        checkSystemNameExample(localSet);
    }

    @Test
    public void parseTagsWithChecksum() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x0a, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56,
                    0x4D, 0x54, 0x49, 0x0b, 0x01, 0x04, 0x01, 0x02, 0x55, 0x3b
                };
        VTrackLocalSet localSet = new VTrackLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 2);
        checkVersionNumberExample(localSet);
        checkSystemNameExample(localSet);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void parseTagsWithChecksumBad1() throws KlvParseException, URISyntaxException {
        final byte[] bytes =
                new byte[] {
                    0x0a, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56,
                    0x4D, 0x54, 0x49, 0x0b, 0x01, 0x04, 0x01, 0x02, 0x54, 0x3b
                };
        new VTrackLocalSet(bytes);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void parseTagsWithChecksumBad2() throws KlvParseException, URISyntaxException {
        final byte[] bytes =
                new byte[] {
                    0x0a, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56,
                    0x4D, 0x54, 0x49, 0x0b, 0x01, 0x04, 0x01, 0x02, 0x55, 0x3c
                };
        new VTrackLocalSet(bytes);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void parseItemWithBadFormat() throws KlvParseException, URISyntaxException {
        final byte[] bytes = new byte[] {0x0b, 0x03, 0x00, 0x00, 0x01};
        new VTrackLocalSet(bytes);
    }

    private void checkSystemNameExample(VTrackLocalSet localSet) {
        final String stringVal = "DSTO_ADSS_VMTI";
        assertTrue(localSet.getIdentifiers().contains(VTrackMetadataKey.SystemName));
        IVTrackMetadataValue v = localSet.getField(VTrackMetadataKey.SystemName);
        assertEquals(v.getDisplayName(), "System Name/Description");
        assertEquals(v.getDisplayName(), VmtiTextString.SYSTEM_NAME);
        assertEquals(v.getDisplayableValue(), stringVal);
        assertTrue(v instanceof VmtiTextString);
        VmtiTextString text = (VmtiTextString) localSet.getField(VTrackMetadataKey.SystemName);
        assertEquals(text.getValue(), stringVal);
    }

    private void checkVersionNumberExample(VTrackLocalSet localSet) {
        final String stringVal = "ST0903.4";
        assertTrue(localSet.getIdentifiers().contains(VTrackMetadataKey.VersionNumber));
        IVTrackMetadataValue v = localSet.getField(VTrackMetadataKey.VersionNumber);
        assertEquals(v.getDisplayName(), "Version Number");
        assertEquals(v.getDisplayableValue(), stringVal);
        assertEquals(v.getBytes(), new byte[] {0x04});
        assertTrue(v instanceof ST0903Version);
        ST0903Version version = (ST0903Version) localSet.getField(VTrackMetadataKey.VersionNumber);
        assertEquals(version.getVersion(), 4);
        ST0903Version versionFromIKlvKey =
                (ST0903Version) localSet.getField((IKlvKey) VTrackMetadataKey.VersionNumber);
        assertEquals(versionFromIKlvKey.getVersion(), 4);
        assertEquals(
                localSet.getUniversalLabel().getBytes(),
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x02, 0x03, 0x01, 0x01, 0x0e, 0x01, 0x03, 0x03, 0x1e,
                    0x00, 0x00, 0x00
                });
        assertEquals(localSet.displayHeader(), "ST0903 VTrack");
    }

    @Test
    public void constructFromMap() {
        VTrackLocalSet localSet = buildLocalSetValues();
        byte[] expectedBytes =
                new byte[] {
                    0x0a, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56,
                    0x4D, 0x54, 0x49, 0x0b, 0x01, 0x04, 0x0d, 0x01, 0x00
                };
        byte[] framedMessage = localSet.frameMessage(true);
        assertEquals(framedMessage, expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0903 VTrack");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.VTrackLocalSetUl);
        assertEquals(
                localSet.getUniversalLabel().getBytes(),
                new byte[] {
                    // ST0903.5 Section 9 says: 06.0E.2B.34.02.03.01.01.0E.01.03.03.1E.00.00.00
                    (byte) 0x06,
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x1e,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test
    public void constructFromMapNonNested() {
        VTrackLocalSet localSet = buildLocalSetValues();
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x06, // UL
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x1e,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    0x1a, // LS length
                    0x0a, // System Name
                    0x0E,
                    0x44,
                    0x53,
                    0x54,
                    0x4F,
                    0x5F,
                    0x41,
                    0x44,
                    0x53,
                    0x53,
                    0x5F,
                    0x56,
                    0x4D,
                    0x54,
                    0x49,
                    0x0b, // Version
                    0x01,
                    0x04,
                    0x0d, // Num track items
                    0x01,
                    0x00,
                    0x01, // Checksum
                    0x02,
                    (byte) 0xc8,
                    (byte) 0x9d
                };
        assertEquals(localSet.frameMessage(false), expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0903 VTrack");
    }

    @Test
    public void constructFromMapNonNestedWithChecksum() {
        Map<VTrackMetadataKey, IVTrackMetadataValue> values = new HashMap<>();
        IVTrackMetadataValue systemName =
                new VmtiTextString(VmtiTextString.SYSTEM_NAME, "DSTO_ADSS_VMTI");
        values.put(VTrackMetadataKey.SystemName, systemName);
        IVTrackMetadataValue version = new ST0903Version(4);
        values.put(VTrackMetadataKey.VersionNumber, version);
        IVTrackMetadataValue fakeChecksum =
                new IVTrackMetadataValue() {
                    @Override
                    public byte[] getBytes() {
                        return new byte[] {0x12, 0x34};
                    }

                    @Override
                    public String getDisplayableValue() {
                        return "x";
                    }

                    @Override
                    public String getDisplayName() {
                        return "y";
                    }
                };
        values.put(VTrackMetadataKey.Checksum, fakeChecksum);
        VTrackLocalSet localSet = new VTrackLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 3);
        checkSystemNameExample(localSet);
        checkVersionNumberExample(localSet);
        assertTrue(localSet.getIdentifiers().contains(VTrackMetadataKey.Checksum));
        // but the checksum should be ignored.
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x06, // UL
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x1e,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    0x1a, // LS length
                    0x0a, // System Name
                    0x0E,
                    0x44,
                    0x53,
                    0x54,
                    0x4F,
                    0x5F,
                    0x41,
                    0x44,
                    0x53,
                    0x53,
                    0x5F,
                    0x56,
                    0x4D,
                    0x54,
                    0x49,
                    0x0b, // Version
                    0x01,
                    0x04,
                    0x0d, // Num track items
                    0x01,
                    0x00,
                    0x01, // Checksum
                    0x02,
                    (byte) 0xc8,
                    (byte) 0x9d
                };
        assertEquals(localSet.frameMessage(false), expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0903 VTrack");
    }

    private VTrackLocalSet buildLocalSetValues() {
        Map<VTrackMetadataKey, IVTrackMetadataValue> values = new HashMap<>();
        IVTrackMetadataValue systemName =
                new VmtiTextString(VmtiTextString.SYSTEM_NAME, "DSTO_ADSS_VMTI");
        values.put(VTrackMetadataKey.SystemName, systemName);
        IVTrackMetadataValue version = new ST0903Version(4);
        values.put(VTrackMetadataKey.VersionNumber, version);
        VTrackLocalSet localSet = new VTrackLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 2);
        checkSystemNameExample(localSet);
        checkVersionNumberExample(localSet);
        return localSet;
    }

    @Test
    public void constructUnknown() throws KlvParseException {
        verifyNoLoggerMessages();
        IVTrackMetadataValue unknown =
                VTrackLocalSet.createValue(
                        VTrackMetadataKey.Undefined, new byte[] {0x01, 0x02}, EncodingMode.IMAPB);
        this.verifySingleLoggerMessage("Unknown VTrack Metadata tag: Undefined");
        assertNull(unknown);
    }
}

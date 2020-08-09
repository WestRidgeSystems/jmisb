package org.jmisb.api.klv.st0806;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st0806.poiaoi.IRvtPoiAoiMetadataValue;
import org.jmisb.api.klv.st0806.poiaoi.PoiAoiNumber;
import org.jmisb.api.klv.st0806.poiaoi.PoiAoiType;
import org.jmisb.api.klv.st0806.poiaoi.PoiLatitude;
import org.jmisb.api.klv.st0806.poiaoi.PoiLongitude;
import org.jmisb.api.klv.st0806.poiaoi.RvtAoiLocalSet;
import org.jmisb.api.klv.st0806.poiaoi.RvtAoiMetadataKey;
import org.jmisb.api.klv.st0806.poiaoi.RvtPoiAoiTextString;
import org.jmisb.api.klv.st0806.poiaoi.RvtPoiLocalSet;
import org.jmisb.api.klv.st0806.poiaoi.RvtPoiLocalSetTest;
import org.jmisb.api.klv.st0806.poiaoi.RvtPoiMetadataKey;
import org.jmisb.api.klv.st0806.userdefined.IRvtUserDefinedMetadataValue;
import org.jmisb.api.klv.st0806.userdefined.RvtNumericId;
import org.jmisb.api.klv.st0806.userdefined.RvtUserData;
import org.jmisb.api.klv.st0806.userdefined.RvtUserDefinedLocalSet;
import org.jmisb.api.klv.st0806.userdefined.RvtUserDefinedMetadataKey;
import org.testng.annotations.Test;

/** Tests for the ST0806 Remote Video Terminal Local Set. */
public class RvtLocalSetTest extends LoggerChecks {
    RvtLocalSetTest() {
        super(RvtLocalSet.class);
    }

    @Test
    public void parseTag3() throws KlvParseException {
        final byte[] bytes = new byte[] {0x03, 0x02, 0x01, 0x02};
        RvtLocalSet localSet = new RvtLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 1);
        checkPlatformTrueAirspeedExample(localSet);
        assertEquals(localSet.frameMessage(true), bytes);
    }

    @Test
    public void parseTag8() throws KlvParseException {
        final byte[] bytes = new byte[] {0x08, 0x01, 0x04};
        RvtLocalSet localSet = new RvtLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 1);
        checkVersionNumberExample(localSet);
        assertEquals(localSet.frameMessage(true), bytes);
    }

    @Test
    public void parseTagsWithUnknownTag() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x7F,
                    0x02,
                    (byte) 0x80,
                    (byte) 0xCA, // No such tag
                    0x03,
                    0x02,
                    0x01,
                    0x02,
                    0x08,
                    0x01,
                    0x04
                };
        verifyNoLoggerMessages();
        RvtLocalSet localSet = new RvtLocalSet(bytes);
        verifySingleLoggerMessage("Unknown RVT Metadata tag: 127");
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 2);
        checkPlatformTrueAirspeedExample(localSet);
        checkVersionNumberExample(localSet);
    }

    @Test
    public void parseTagsWithChecksum() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x03,
                    0x02,
                    0x01,
                    0x02,
                    0x08,
                    0x01,
                    0x04,
                    0x01,
                    0x04,
                    (byte) 0x0d,
                    (byte) 0xa8,
                    (byte) 0xdf,
                    (byte) 0x5d
                };
        RvtLocalSet localSet = new RvtLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 2);
        checkPlatformTrueAirspeedExample(localSet);
        checkVersionNumberExample(localSet);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void parseTagsWithBadChecksum() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x03,
                    0x02,
                    0x01,
                    0x02,
                    0x08,
                    0x01,
                    0x04,
                    0x01,
                    0x04,
                    (byte) 0x0d,
                    (byte) 0xa8,
                    (byte) 0xdf,
                    (byte) 0x5e
                };
        new RvtLocalSet(bytes);
    }

    private void checkVersionNumberExample(RvtLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(RvtMetadataKey.UASLSVersionNumber));
        IRvtMetadataValue v = localSet.getField(RvtMetadataKey.UASLSVersionNumber);
        assertEquals(v.getDisplayName(), "Version Number");
        assertEquals(v.getDisplayableValue(), "ST0806.4");
        assertEquals(v.getBytes(), new byte[] {0x04});
        assertTrue(v instanceof ST0806Version);
        ST0806Version version =
                (ST0806Version) localSet.getField(RvtMetadataKey.UASLSVersionNumber);
        assertEquals(version.getVersion(), 4);
    }

    private void checkPlatformTrueAirspeedExample(RvtLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(RvtMetadataKey.PlatformTrueAirspeed));
        IRvtMetadataValue v = localSet.getField(RvtMetadataKey.PlatformTrueAirspeed);
        assertEquals(v.getDisplayName(), "Platform True Airspeed (TAS)");
        assertEquals(v.getDisplayableValue(), "258m/s");
        assertEquals(v.getBytes(), new byte[] {0x01, 0x02});
        assertTrue(v instanceof RvtPlatformTrueAirspeed);
        RvtPlatformTrueAirspeed tas =
                (RvtPlatformTrueAirspeed) localSet.getField(RvtMetadataKey.PlatformTrueAirspeed);
        assertEquals(tas.getMetersPerSecond(), 258);
    }

    @Test
    public void constructFromMap() {
        RvtLocalSet localSet = buildLocalSetValues();
        byte[] expectedBytes = new byte[] {0x03, 0x02, 0x01, 0x02, 0x08, 0x01, 0x04};
        assertEquals(localSet.frameMessage(true), expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0806 Remote Video Terminal");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.RvtLocalSetUl);
        assertEquals(
                localSet.getUniversalLabel().getBytes(),
                new byte[] {
                    (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x02, (byte) 0x0B,
                            (byte) 0x01, (byte) 0x01,
                    (byte) 0x0E, (byte) 0x01, (byte) 0x03, (byte) 0x01, (byte) 0x02, (byte) 0x00,
                            (byte) 0x00, (byte) 0x00
                });
    }

    @Test
    public void constructFromMapWithSubordinateLS() throws KlvParseException {
        RvtLocalSet localSet = buildLocalSetValues();
        Map<RvtPoiMetadataKey, IRvtPoiAoiMetadataValue> poiValues =
                RvtPoiLocalSetTest.buildPoiValues();
        localSet.addPointOfInterestLocalSet(new RvtPoiLocalSet(poiValues));
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 2);
        assertEquals(localSet.getPOIIndexes().size(), 1);
        checkPlatformTrueAirspeedExample(localSet);
        checkVersionNumberExample(localSet);
        checkPoiLocalSetExample(localSet);
        Map<RvtPoiMetadataKey, IRvtPoiAoiMetadataValue> poiValues2 = buildAnotherPoiValues();
        localSet.addPointOfInterestLocalSet(new RvtPoiLocalSet(poiValues2));
        Map<RvtUserDefinedMetadataKey, IRvtUserDefinedMetadataValue> userDefined = new HashMap<>();
        userDefined.put(RvtUserDefinedMetadataKey.NumericId, new RvtNumericId(2, 2));
        userDefined.put(
                RvtUserDefinedMetadataKey.UserData,
                new RvtUserData(new byte[] {0x01, 0x02, 0x03, 0x04}));
        RvtUserDefinedLocalSet userDefinedLocalSet = new RvtUserDefinedLocalSet(userDefined);
        localSet.addUserDefinedLocalSet(userDefinedLocalSet);
        Map<RvtAoiMetadataKey, IRvtPoiAoiMetadataValue> aoiValues = new HashMap<>();
        aoiValues.put(RvtAoiMetadataKey.PoiAoiNumber, new PoiAoiNumber(262));
        aoiValues.put(RvtAoiMetadataKey.PoiAoiType, new PoiAoiType((byte) 3));
        // Not really enough values for compliance, but enough for test
        localSet.addAreaOfInterestLocalSet(new RvtAoiLocalSet(aoiValues));
        assertEquals(localSet.getIdentifiers().size(), 2);
        assertEquals(localSet.getPOIIndexes().size(), 2);
        checkPoiLocalSetExample(localSet);
        assertTrue(localSet.getPOIIndexes().contains(3));
        RvtPoiLocalSet ls2 = localSet.getPOI(3);
        assertEquals(ls2.getTags().size(), 4);
        assertTrue(ls2.getTags().contains(RvtPoiMetadataKey.PoiAoiNumber));
        assertTrue(ls2.getTags().contains(RvtPoiMetadataKey.PoiLatitude));
        assertTrue(ls2.getTags().contains(RvtPoiMetadataKey.PoiLongitude));
        assertTrue(ls2.getTags().contains(RvtPoiMetadataKey.PoiAoiLabel));
        PoiAoiNumber num2 = (PoiAoiNumber) ls2.getField(RvtPoiMetadataKey.PoiAoiNumber);
        assertEquals(num2.getNumber(), 3);
        assertEquals(num2.getDisplayName(), "POI/AOI Number");
        RvtPoiAoiTextString text2 =
                (RvtPoiAoiTextString) ls2.getField(RvtPoiMetadataKey.PoiAoiLabel);
        assertEquals(text2.getDisplayName(), "POI/AOI Label");
        assertEquals(text2.getValue(), "Another Point");
        assertEquals(localSet.getAOIIndexes().size(), 1);
        assertTrue(localSet.getAOIIndexes().contains(262));
        assertEquals(localSet.getAOI(262).getTags().size(), 2);
        byte[] expectedBytes =
                new byte[] {
                    0x03,
                    0x02,
                    0x01,
                    0x02,
                    0x08,
                    0x01,
                    0x04,
                    (byte) 0x0B,
                    (byte) ((1 + 1 + 1) + (1 + 1 + 4)),
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0b10000010,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    0x0C,
                    (byte) ((1 + 1 + 2) + (1 + 1 + 4) + (1 + 1 + 4) + (1 + 1 + 13)),
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0xcd,
                    (byte) 0xa7,
                    (byte) 0x40,
                    (byte) 0xdb,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x60,
                    (byte) 0x48,
                    (byte) 0xd1,
                    (byte) 0x59,
                    (byte) 0x09,
                    (byte) 0x0D,
                    (byte) 0x41,
                    (byte) 0x6e,
                    (byte) 0x6f,
                    (byte) 0x74,
                    (byte) 0x68,
                    (byte) 0x65,
                    (byte) 0x72,
                    (byte) 0x20,
                    (byte) 0x50,
                    (byte) 0x6F,
                    (byte) 0x69,
                    (byte) 0x6e,
                    (byte) 0x74,
                    0x0C,
                    (byte) ((1 + 1 + 2) + (1 + 1 + 4) + (1 + 1 + 4) + (1 + 1 + 8)),
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x85,
                    (byte) 0xa1,
                    (byte) 0x5a,
                    (byte) 0x39,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x53,
                    (byte) 0x27,
                    (byte) 0x3F,
                    (byte) 0xD4,
                    (byte) 0x09,
                    (byte) 0x08,
                    (byte) 0x4D,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x50,
                    (byte) 0x6F,
                    (byte) 0x69,
                    (byte) 0x6e,
                    (byte) 0x74,
                    (byte) 0x0D,
                    (byte) ((1 + 1 + 2) + (1 + 1 + 1)),
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x06,
                    (byte) 0x06,
                    (byte) 0x01,
                    (byte) 0x03
                };
        assertEquals(localSet.frameMessage(true), expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0806 Remote Video Terminal");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.RvtLocalSetUl);
        assertEquals(
                localSet.getUniversalLabel().getBytes(),
                new byte[] {
                    (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x02, (byte) 0x0B,
                            (byte) 0x01, (byte) 0x01,
                    (byte) 0x0E, (byte) 0x01, (byte) 0x03, (byte) 0x01, (byte) 0x02, (byte) 0x00,
                            (byte) 0x00, (byte) 0x00
                });
    }

    @Test
    public void constructFromMapNonNested() {
        RvtLocalSet localSet = buildLocalSetValues();
        byte[] expectedBytes =
                new byte[] {
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
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    0x0D, // LS length
                    0x03,
                    0x02,
                    0x01,
                    0x02, // tag 3 - TAS
                    0x08,
                    0x01,
                    0x04, // tag 8 - version
                    0x01,
                    0x04,
                    (byte) 0xbd,
                    (byte) 0xd4,
                    (byte) 0x5d,
                    (byte) 0x45 // Tag 1 - CRC-32
                };
        assertEquals(localSet.frameMessage(false), expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0806 Remote Video Terminal");
    }

    @Test
    public void constructFromBytesWithSubordinateLS() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x03,
                    0x02,
                    0x01,
                    0x02, // tag 3 - TAS
                    0x08,
                    0x01,
                    0x04, // tag 8 - version
                    0x0C,
                    (byte) ((1 + 1 + 2) + (1 + 1 + 4) + (1 + 1 + 4) + (1 + 1 + 13)),
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0xcd,
                    (byte) 0xa7,
                    (byte) 0x40,
                    (byte) 0xdb,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x60,
                    (byte) 0x48,
                    (byte) 0xd1,
                    (byte) 0x59,
                    (byte) 0x09,
                    (byte) 0x0D,
                    (byte) 0x41,
                    (byte) 0x6e,
                    (byte) 0x6f,
                    (byte) 0x74,
                    (byte) 0x68,
                    (byte) 0x65,
                    (byte) 0x72,
                    (byte) 0x20,
                    (byte) 0x50,
                    (byte) 0x6F,
                    (byte) 0x69,
                    (byte) 0x6e,
                    (byte) 0x74,
                    (byte) 0x0C,
                    (byte) ((1 + 1 + 2) + (1 + 1 + 4) + (1 + 1 + 4) + (1 + 1 + 8)),
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x85,
                    (byte) 0xa1,
                    (byte) 0x5a,
                    (byte) 0x39,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x53,
                    (byte) 0x27,
                    (byte) 0x3F,
                    (byte) 0xD4,
                    (byte) 0x09,
                    (byte) 0x08,
                    (byte) 0x4D,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x50,
                    (byte) 0x6F,
                    (byte) 0x69,
                    (byte) 0x6e,
                    (byte) 0x74,
                    (byte) 0x0B,
                    (byte) ((1 + 1 + 1) + (1 + 1 + 4)),
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0b10000010,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x0D,
                    (byte)
                            ((1 + 1 + 2)
                                    + (1 + 1 + 4)
                                    + (1 + 1 + 4)
                                    + (1 + 1 + 4)
                                    + (1 + 1 + 4)
                                    + (1 + 1 + 1)),
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x06,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x85,
                    (byte) 0xa1,
                    (byte) 0x5a,
                    (byte) 0x39,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x53,
                    (byte) 0x27,
                    (byte) 0x3F,
                    (byte) 0xD4,
                    (byte) 0x04,
                    (byte) 0x04,
                    (byte) 0x85,
                    (byte) 0x92,
                    (byte) 0x5a,
                    (byte) 0x39,
                    (byte) 0x05,
                    (byte) 0x04,
                    (byte) 0x53,
                    (byte) 0x37,
                    (byte) 0x3F,
                    (byte) 0xD4,
                    (byte) 0x06,
                    (byte) 0x01,
                    (byte) 0x03
                };
        RvtLocalSet localSet = new RvtLocalSet(bytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0806 Remote Video Terminal");
        assertEquals(localSet.getIdentifiers().size(), 2);
        assertEquals(localSet.getUserDefinedIndexes().size(), 1);
        assertTrue(localSet.getUserDefinedIndexes().contains(0x82));
        assertEquals(localSet.getPOIIndexes().size(), 2);
        checkPoiLocalSetExample(localSet);
        assertTrue(localSet.getPOIIndexes().contains(3));
        RvtPoiLocalSet ls2 = localSet.getPOI(3);
        assertEquals(ls2.getTags().size(), 4);
        assertTrue(ls2.getTags().contains(RvtPoiMetadataKey.PoiAoiNumber));
        assertTrue(ls2.getTags().contains(RvtPoiMetadataKey.PoiLatitude));
        assertTrue(ls2.getTags().contains(RvtPoiMetadataKey.PoiLongitude));
        assertTrue(ls2.getTags().contains(RvtPoiMetadataKey.PoiAoiLabel));
        PoiAoiNumber num2 = (PoiAoiNumber) ls2.getField(RvtPoiMetadataKey.PoiAoiNumber);
        assertEquals(num2.getNumber(), 3);
        assertEquals(num2.getDisplayName(), "POI/AOI Number");
        RvtPoiAoiTextString text2 =
                (RvtPoiAoiTextString) ls2.getField(RvtPoiMetadataKey.PoiAoiLabel);
        assertEquals(text2.getDisplayName(), "POI/AOI Label");
        assertEquals(text2.getValue(), "Another Point");
        assertEquals(localSet.getAOIIndexes().size(), 1);
        byte[] expectedBytes =
                new byte[] {
                    0x03,
                    0x02,
                    0x01,
                    0x02,
                    0x08,
                    0x01,
                    0x04,
                    0x0B,
                    (byte) ((1 + 1 + 1) + (1 + 1 + 4)),
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0b10000010,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    0x0C,
                    (byte) ((1 + 1 + 2) + (1 + 1 + 4) + (1 + 1 + 4) + (1 + 1 + 13)),
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0xcd,
                    (byte) 0xa7,
                    (byte) 0x40,
                    (byte) 0xdb,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x60,
                    (byte) 0x48,
                    (byte) 0xd1,
                    (byte) 0x59,
                    (byte) 0x09,
                    (byte) 0x0D,
                    (byte) 0x41,
                    (byte) 0x6e,
                    (byte) 0x6f,
                    (byte) 0x74,
                    (byte) 0x68,
                    (byte) 0x65,
                    (byte) 0x72,
                    (byte) 0x20,
                    (byte) 0x50,
                    (byte) 0x6F,
                    (byte) 0x69,
                    (byte) 0x6e,
                    (byte) 0x74,
                    0x0C,
                    (byte) ((1 + 1 + 2) + (1 + 1 + 4) + (1 + 1 + 4) + (1 + 1 + 8)),
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x85,
                    (byte) 0xa1,
                    (byte) 0x5a,
                    (byte) 0x39,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x53,
                    (byte) 0x27,
                    (byte) 0x3F,
                    (byte) 0xD4,
                    (byte) 0x09,
                    (byte) 0x08,
                    (byte) 0x4D,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x50,
                    (byte) 0x6F,
                    (byte) 0x69,
                    (byte) 0x6e,
                    (byte) 0x74,
                    0x0D,
                    (byte)
                            ((1 + 1 + 2)
                                    + (1 + 1 + 4)
                                    + (1 + 1 + 4)
                                    + (1 + 1 + 4)
                                    + (1 + 1 + 4)
                                    + (1 + 1 + 1)),
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x06,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x85,
                    (byte) 0xa1,
                    (byte) 0x5a,
                    (byte) 0x39,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x53,
                    (byte) 0x27,
                    (byte) 0x3F,
                    (byte) 0xD4,
                    (byte) 0x04,
                    (byte) 0x04,
                    (byte) 0x85,
                    (byte) 0x92,
                    (byte) 0x5a,
                    (byte) 0x39,
                    (byte) 0x05,
                    (byte) 0x04,
                    (byte) 0x53,
                    (byte) 0x37,
                    (byte) 0x3F,
                    (byte) 0xD4,
                    (byte) 0x06,
                    (byte) 0x01,
                    (byte) 0x03
                };
        assertEquals(localSet.frameMessage(true), expectedBytes);
    }

    @Test
    public void constructFromBytesWithSubordinateLSthatDontHaveNumbers() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x03,
                    0x02,
                    0x01,
                    0x02, // tag 3 - TAS
                    0x08,
                    0x01,
                    0x04, // tag 8 - version
                    0x0C,
                    (byte) ((1 + 1 + 4) + (1 + 1 + 4) + (1 + 1 + 13)),
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0xcd,
                    (byte) 0xa7,
                    (byte) 0x40,
                    (byte) 0xdb,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x60,
                    (byte) 0x48,
                    (byte) 0xd1,
                    (byte) 0x59,
                    (byte) 0x09,
                    (byte) 0x0D,
                    (byte) 0x41,
                    (byte) 0x6e,
                    (byte) 0x6f,
                    (byte) 0x74,
                    (byte) 0x68,
                    (byte) 0x65,
                    (byte) 0x72,
                    (byte) 0x20,
                    (byte) 0x50,
                    (byte) 0x6F,
                    (byte) 0x69,
                    (byte) 0x6e,
                    (byte) 0x74,
                    (byte) 0x0C,
                    (byte) ((1 + 1 + 4) + (1 + 1 + 4) + (1 + 1 + 8)),
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x85,
                    (byte) 0xa1,
                    (byte) 0x5a,
                    (byte) 0x39,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x53,
                    (byte) 0x27,
                    (byte) 0x3F,
                    (byte) 0xD4,
                    (byte) 0x09,
                    (byte) 0x08,
                    (byte) 0x4D,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x50,
                    (byte) 0x6F,
                    (byte) 0x69,
                    (byte) 0x6e,
                    (byte) 0x74,
                    (byte) 0x0B,
                    (byte) ((1 + 1 + 4)),
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x0D,
                    (byte) ((1 + 1 + 4) + (1 + 1 + 4) + (1 + 1 + 4) + (1 + 1 + 4) + (1 + 1 + 1)),
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x85,
                    (byte) 0xa1,
                    (byte) 0x5a,
                    (byte) 0x39,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x53,
                    (byte) 0x27,
                    (byte) 0x3F,
                    (byte) 0xD4,
                    (byte) 0x04,
                    (byte) 0x04,
                    (byte) 0x85,
                    (byte) 0x92,
                    (byte) 0x5a,
                    (byte) 0x39,
                    (byte) 0x05,
                    (byte) 0x04,
                    (byte) 0x53,
                    (byte) 0x37,
                    (byte) 0x3F,
                    (byte) 0xD4,
                    (byte) 0x06,
                    (byte) 0x01,
                    (byte) 0x03
                };
        RvtLocalSet localSet = new RvtLocalSet(bytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0806 Remote Video Terminal");
        assertEquals(localSet.getIdentifiers().size(), 2);
        assertEquals(localSet.getUserDefinedIndexes().size(), 0);
        assertEquals(localSet.getPOIIndexes().size(), 0);
        assertEquals(localSet.getAOIIndexes().size(), 0);
        byte[] expectedBytes =
                new byte[] {
                    0x03, 0x02, 0x01, 0x02, 0x08, 0x01, 0x04,
                };
        assertEquals(localSet.frameMessage(true), expectedBytes);
    }

    @Test
    public void constructFromMapNonNestedWithChecksum() {
        Map<RvtMetadataKey, IRvtMetadataValue> values = new HashMap<>();
        IRvtMetadataValue platformTAS = new RvtPlatformTrueAirspeed(258);
        values.put(RvtMetadataKey.PlatformTrueAirspeed, platformTAS);
        IRvtMetadataValue version = new ST0806Version(4);
        values.put(RvtMetadataKey.UASLSVersionNumber, version);
        IRvtMetadataValue fakeChecksum =
                new IRvtMetadataValue() {
                    @Override
                    public byte[] getBytes() {
                        return new byte[] {0x12, 0x34, 0x56, 0x78};
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
        values.put(RvtMetadataKey.CRC32, fakeChecksum);
        RvtLocalSet localSet = new RvtLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 3);
        checkPlatformTrueAirspeedExample(localSet);
        checkVersionNumberExample(localSet);
        assertTrue(localSet.getIdentifiers().contains(RvtMetadataKey.CRC32));
        // but the checksum should be ignored.
        byte[] expectedBytes =
                new byte[] {
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
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    0x0D, // LS length
                    0x03,
                    0x02,
                    0x01,
                    0x02, // Tag 3
                    0x08,
                    0x01,
                    0x04, // Tag 8
                    0x01,
                    0x04,
                    (byte) 0xbd,
                    (byte) 0xd4,
                    (byte) 0x5d,
                    (byte) 0x45 // checksum
                };
        assertEquals(localSet.frameMessage(false), expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0806 Remote Video Terminal");
    }

    private RvtLocalSet buildLocalSetValues() {
        Map<RvtMetadataKey, IRvtMetadataValue> values = new HashMap<>();
        IRvtMetadataValue platformTAS = new RvtPlatformTrueAirspeed(258);
        values.put(RvtMetadataKey.PlatformTrueAirspeed, platformTAS);
        IRvtMetadataValue version = new ST0806Version(4);
        values.put(RvtMetadataKey.UASLSVersionNumber, version);
        RvtLocalSet localSet = new RvtLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 2);
        checkPlatformTrueAirspeedExample(localSet);
        checkVersionNumberExample(localSet);
        return localSet;
    }

    @Test
    public void testTimestampComesOutFirstIfUsed() {
        Map<RvtMetadataKey, IRvtMetadataValue> values = new HashMap<>();
        IRvtMetadataValue platformTAS = new RvtPlatformTrueAirspeed(258);
        values.put(RvtMetadataKey.PlatformTrueAirspeed, platformTAS);
        IRvtMetadataValue version = new ST0806Version(4);
        values.put(RvtMetadataKey.UASLSVersionNumber, version);
        // Put it in last, expect it to come out in order.
        IRvtMetadataValue timestamp = new UserDefinedTimeStampMicroseconds(1224807209913000L);
        values.put(RvtMetadataKey.UserDefinedTimeStampMicroseconds, timestamp);
        RvtLocalSet localSet = new RvtLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 3);
        checkPlatformTrueAirspeedExample(localSet);
        checkVersionNumberExample(localSet);
        byte[] expectedBytes =
                new byte[] {
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
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    0x17, // LS length
                    0x02,
                    0x08,
                    (byte) 0x00,
                    (byte) 0x04,
                    (byte) 0x59,
                    (byte) 0xf4,
                    (byte) 0xA6,
                    (byte) 0xaa,
                    (byte) 0x4a,
                    (byte) 0xa8,
                    0x03,
                    0x02,
                    0x01,
                    0x02, // tag 3 - TAS
                    0x08,
                    0x01,
                    0x04, // tag 8 - version
                    0x01,
                    0x04,
                    (byte) 0xC2,
                    (byte) 0x57,
                    (byte) 0xd3,
                    (byte) 0x1d // Tag 1 - CRC-32
                };
        assertEquals(localSet.frameMessage(false), expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0806 Remote Video Terminal");
    }

    public static Map<RvtPoiMetadataKey, IRvtPoiAoiMetadataValue> buildAnotherPoiValues()
            throws KlvParseException {
        Map<RvtPoiMetadataKey, IRvtPoiAoiMetadataValue> values = new HashMap<>();
        values.put(RvtPoiMetadataKey.PoiAoiNumber, new PoiAoiNumber(3));
        values.put(RvtPoiMetadataKey.PoiLatitude, new PoiLatitude(-35.4));
        values.put(RvtPoiMetadataKey.PoiLongitude, new PoiLongitude(135.4));
        RvtPoiAoiTextString label =
                new RvtPoiAoiTextString(RvtPoiAoiTextString.POI_AOI_LABEL, "Another Point");
        values.put(RvtPoiMetadataKey.PoiAoiLabel, label);
        return values;
    }

    private void checkPoiLocalSetExample(RvtLocalSet localSet) {
        assertTrue(localSet.getPOIIndexes().contains(516));
        RvtPoiLocalSet poiLocalSet = localSet.getPOI(516);
        RvtPoiLocalSetTest.checkPoiAoiNumberExample(poiLocalSet);
        RvtPoiLocalSetTest.checkPoiAoiLatitudeExample(poiLocalSet);
        RvtPoiLocalSetTest.checkPoiAoiLongitudeExample(poiLocalSet);
    }

    @Test
    public void constructUnknown() throws KlvParseException {
        verifyNoLoggerMessages();
        IRvtMetadataValue unknown =
                RvtLocalSet.createValue(RvtMetadataKey.Undefined, new byte[] {0x01, 0x02});
        this.verifySingleLoggerMessage("Unknown Remote Video Terminal Metadata tag: Undefined");
        assertNull(unknown);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddingPoiLocalSetWithoutNumber() throws KlvParseException {
        RvtLocalSet localSet = buildLocalSetValues();
        Map<RvtPoiMetadataKey, IRvtPoiAoiMetadataValue> values = new HashMap<>();
        // No PoiAoiNumber
        values.put(RvtPoiMetadataKey.PoiLatitude, new PoiLatitude(-35.4));
        RvtPoiLocalSet poiLocalSetNoNumber = new RvtPoiLocalSet(values);
        localSet.addPointOfInterestLocalSet(poiLocalSetNoNumber);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddingAoiLocalSetWithoutNumber() throws KlvParseException {
        RvtLocalSet localSet = buildLocalSetValues();
        Map<RvtAoiMetadataKey, IRvtPoiAoiMetadataValue> values = new HashMap<>();
        // No PoiAoiNumber
        values.put(RvtAoiMetadataKey.PoiAoiType, new PoiAoiType((byte) 1));
        RvtAoiLocalSet aoiLocalSetNoNumber = new RvtAoiLocalSet(values);
        localSet.addAreaOfInterestLocalSet(aoiLocalSetNoNumber);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddingUserDefinedLocalSetWithoutNumber() throws KlvParseException {
        RvtLocalSet localSet = buildLocalSetValues();
        Map<RvtUserDefinedMetadataKey, IRvtUserDefinedMetadataValue> values = new HashMap<>();
        // No NumbericID
        values.put(RvtUserDefinedMetadataKey.UserData, new RvtUserData(new byte[] {0x01, 0x02}));
        RvtUserDefinedLocalSet userDataLocalSetNoNumber = new RvtUserDefinedLocalSet(values);
        localSet.addUserDefinedLocalSet(userDataLocalSetNoNumber);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddingNullPoiLocalSet() throws KlvParseException {
        RvtLocalSet localSet = buildLocalSetValues();
        localSet.addPointOfInterestLocalSet(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddingNullAoiLocalSet() throws KlvParseException {
        RvtLocalSet localSet = buildLocalSetValues();
        localSet.addAreaOfInterestLocalSet(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddingNullUserDefinedLocalSet() throws KlvParseException {
        RvtLocalSet localSet = buildLocalSetValues();
        localSet.addUserDefinedLocalSet(null);
    }
}

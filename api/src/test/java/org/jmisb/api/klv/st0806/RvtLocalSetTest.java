package org.jmisb.api.klv.st0806;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st0806.poiaoi.IRvtPoiAoiMetadataValue;
import org.jmisb.api.klv.st0806.poiaoi.PoiAoiNumber;
import org.jmisb.api.klv.st0806.poiaoi.PoiLatitude;
import org.jmisb.api.klv.st0806.poiaoi.PoiLongitude;
import org.jmisb.api.klv.st0806.poiaoi.RvtPoiAoiTextString;
import org.jmisb.api.klv.st0806.poiaoi.RvtPoiLocalSet;
import org.jmisb.api.klv.st0806.poiaoi.RvtPoiLocalSetTest;
import org.jmisb.api.klv.st0806.poiaoi.RvtPoiMetadataKey;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for the ST0806 Remote Video Terminal Local Set.
 */
public class RvtLocalSetTest extends LoggerChecks
{
    RvtLocalSetTest()
    {
        super(RvtLocalSet.class);
    }

    @Test
    public void parseTag3() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x03, 0x02, 0x01, 0x02};
        RvtLocalSet localSet = new RvtLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 1);
        checkPlatformTrueAirspeedExample(localSet);
        assertEquals(localSet.frameMessage(true), bytes);
    }

    @Test
    public void parseTag8() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x08, 0x01, 0x04};
        RvtLocalSet localSet = new RvtLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 1);
        checkVersionNumberExample(localSet);
        assertEquals(localSet.frameMessage(true), bytes);
    }

    @Test
    public void parseTagsWithUnknownTag() throws KlvParseException
    {
        final byte[] bytes = new byte[]{
            0x7F, 0x02, (byte) 0x80, (byte) 0xCA, // No such tag
            0x03, 0x02, 0x01, 0x02,
            0x08, 0x01, 0x04};
        verifyNoLoggerMessages();
        RvtLocalSet localSet = new RvtLocalSet(bytes);
        verifySingleLoggerMessage("Unknown RVT Metadata tag: 127");
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 2);
        checkVersionNumberExample(localSet);
        checkVersionNumberExample(localSet);
    }
/*
    @Test
    public void parseTagsWithChecksum() throws KlvParseException
    {
        final byte[] bytes = new byte[]{
            0x03, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56, 0x4D, 0x54, 0x49,
            0x04, 0x01, 0x04,
            0x01, 0x02, 0x47, 0x3b
        };
        RvtLocalSet localSet = new RvtLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 2);
        checkVersionNumberExample(localSet);
        checkSystemNameExample(localSet);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void parseTagsWithChecksumBad1() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[]{
            0x03, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56, 0x4D, 0x54, 0x49,
            0x04, 0x01, 0x04,
            0x01, 0x02, 0x46, 0x3b
        };
        new RvtLocalSet(bytes);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void parseTagsWithChecksumBad2() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[]{
            0x03, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56, 0x4D, 0x54, 0x49,
            0x04, 0x01, 0x04,
            0x01, 0x02, 0x47, 0x3c
        };
        new RvtLocalSet(bytes);
    }
*/
    private void checkVersionNumberExample(RvtLocalSet localSet)
    {
        assertTrue(localSet.getTags().contains(RvtMetadataKey.UASLSVersionNumber));
        IRvtMetadataValue v = localSet.getField(RvtMetadataKey.UASLSVersionNumber);
        assertEquals(v.getDisplayName(), "Version Number");
        assertEquals(v.getDisplayableValue(), "ST0806.4");
        assertEquals(v.getBytes(), new byte[]{0x04});
        assertTrue(v instanceof ST0806Version);
        ST0806Version version = (ST0806Version) localSet.getField(RvtMetadataKey.UASLSVersionNumber);
        assertEquals(version.getVersion(), 4);
    }

    private void checkPlatformTrueAirspeedExample(RvtLocalSet localSet)
    {
        assertTrue(localSet.getTags().contains(RvtMetadataKey.PlatformTrueAirspeed));
        IRvtMetadataValue v = localSet.getField(RvtMetadataKey.PlatformTrueAirspeed);
        assertEquals(v.getDisplayName(), "Platform True Airspeed (TAS)");
        assertEquals(v.getDisplayableValue(), "258m/s");
        assertEquals(v.getBytes(), new byte[]{0x01, 0x02});
        assertTrue(v instanceof RvtPlatformTrueAirspeed);
        RvtPlatformTrueAirspeed tas = (RvtPlatformTrueAirspeed) localSet.getField(RvtMetadataKey.PlatformTrueAirspeed);
        assertEquals(tas.getMetersPerSecond(), 258);
    }

    @Test
    public void constructFromMap()
    {
        RvtLocalSet localSet = buildLocalSetValues();
        byte[] expectedBytes = new byte[]{
            0x03, 0x02, 0x01, 0x02,
            0x08, 0x01, 0x04};
        assertEquals(localSet.frameMessage(true), expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0806 Remote Video Terminal");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.RvtLocalSetUl);
        assertEquals(localSet.getUniversalLabel().getBytes(), new byte[]{
            (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x02, (byte) 0x0B, (byte) 0x01, (byte) 0x01,
            (byte) 0x0E, (byte) 0x01, (byte) 0x03, (byte) 0x01, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void constructFromMapWithSubordinateLS() throws KlvParseException
    {
        RvtLocalSet localSet = buildLocalSetWithPoiLSValues();
        Map<RvtPoiMetadataKey, IRvtPoiAoiMetadataValue> poiValues = RvtPoiLocalSetTest.buildPoiValues();
        localSet.addPointOfInterestLocalSet(new RvtPoiLocalSet(poiValues));
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 2);
        assertEquals(localSet.getPOIIndexes().size(), 1);
        checkPlatformTrueAirspeedExample(localSet);
        checkVersionNumberExample(localSet);
        checkPoiLocalSetExample(localSet);
        Map<RvtPoiMetadataKey, IRvtPoiAoiMetadataValue> poiValues2 = buildAnotherPoiValues();
        localSet.addPointOfInterestLocalSet(new RvtPoiLocalSet(poiValues2));
        assertEquals(localSet.getTags().size(), 2);
        assertEquals(localSet.getPOIIndexes().size(), 2);
        checkPoiLocalSetExample(localSet);
        assertTrue(localSet.getPOIIndexes().contains(3));
        RvtPoiLocalSet ls2 = localSet.getPOI(3);
        assertEquals(ls2.getTags().size(),4);
        assertTrue(ls2.getTags().contains(RvtPoiMetadataKey.PoiAoiNumber));
        assertTrue(ls2.getTags().contains(RvtPoiMetadataKey.PoiLatitude));
        assertTrue(ls2.getTags().contains(RvtPoiMetadataKey.PoiLongitude));
        assertTrue(ls2.getTags().contains(RvtPoiMetadataKey.PoiAoiLabel));
        PoiAoiNumber num2 = (PoiAoiNumber)ls2.getField(RvtPoiMetadataKey.PoiAoiNumber);
        assertEquals(num2.getNumber(), 3);
        assertEquals(num2.getDisplayName(), "POI/AOI Number");
        RvtPoiAoiTextString text2 = (RvtPoiAoiTextString)ls2.getField(RvtPoiMetadataKey.PoiAoiLabel);
        assertEquals(text2.getDisplayName(), "POI/AOI Label");
        assertEquals(text2.getValue(), "Another Point");
        byte[] expectedBytes = new byte[]{
            0x03, 0x02, 0x01, 0x02,
            0x08, 0x01, 0x04,
            0x0C, (byte)((1 + 1 + 2) + (1 + 1 + 4) + (1 + 1 + 4) + (1 + 1 + 13)),
            (byte)0x01, (byte)0x02, (byte)0x00, (byte)0x03,
            (byte)0x02, (byte)0x04, (byte)0xcd, (byte)0xa7, (byte)0x40, (byte)0xdb,
            (byte)0x03, (byte)0x04, (byte)0x60, (byte)0x48, (byte)0xd1, (byte)0x59,
            (byte)0x09, (byte)0x0D, (byte)0x41, (byte)0x6e, (byte)0x6f, (byte)0x74, (byte)0x68, (byte)0x65, (byte)0x72, (byte)0x20, (byte)0x50, (byte)0x6F, (byte)0x69, (byte)0x6e, (byte)0x74,
            0x0C, (byte)((1 + 1 + 2) + (1 + 1 + 4) + (1 + 1 + 4) + (1 + 1 + 8)),
            (byte)0x01, (byte)0x02, (byte)0x02, (byte)0x04, // T:1, L:2, V: 0x0204
            (byte)0x02, (byte)0x04, (byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39,
            (byte)0x03, (byte)0x04, (byte)0x53, (byte)0x27, (byte)0x3F, (byte)0xD4,
            (byte)0x09, (byte)0x08, (byte)0x4D, (byte)0x79, (byte)0x20, (byte)0x50, (byte)0x6F, (byte)0x69, (byte)0x6e, (byte)0x74
        };
        assertEquals(localSet.frameMessage(true), expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0806 Remote Video Terminal");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.RvtLocalSetUl);
        assertEquals(localSet.getUniversalLabel().getBytes(), new byte[]{
            (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x02, (byte) 0x0B, (byte) 0x01, (byte) 0x01,
            (byte) 0x0E, (byte) 0x01, (byte) 0x03, (byte) 0x01, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void constructFromMapNonNested()
    {
        RvtLocalSet localSet = buildLocalSetValues();
        byte[] expectedBytes = new byte[]{
            (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x02, (byte) 0x0B, (byte) 0x01, (byte) 0x01, (byte) 0x0E, (byte) 0x01, (byte) 0x03, (byte) 0x01, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            0x0D, // LS length
            0x03, 0x02, 0x01, 0x02, // tag 3 - TAS
            0x08, 0x01, 0x04,  // tag 8 - version
            0x01, 0x04, 0x00, 0x00, 0x00, 0x00  // Tag 1 - CRC-32
        };
        assertEquals(localSet.frameMessage(false), expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0806 Remote Video Terminal");
    }
/*
    @Test
    public void constructFromMapNonNestedWithChecksum()
    {
        Map<RvtMetadataKey, IRvtMetadataValue> values = new HashMap<>();
        IRvtMetadataValue systemName = new VmtiTextString(VmtiTextString.SYSTEM_NAME, "DSTO_ADSS_Remote Video Terrminal");
        values.put(RvtMetadataKey.SystemName, systemName);
        IRvtMetadataValue version = new ST0903Version(4);
        values.put(RvtMetadataKey.VersionNumber, version);
        IRvtMetadataValue fakeChecksum = new IRvtMetadataValue() {
            @Override
            public byte[] getBytes() {
                return new byte[]{0x12, 0x34};
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
        values.put(RvtMetadataKey.Checksum, fakeChecksum);
        RvtLocalSet localSet = new RvtLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 3);
        checkSystemNameExample(localSet);
        checkVersionNumberExample(localSet);
        assertTrue(localSet.getTags().contains(RvtMetadataKey.Checksum));
        // but the checksum should be ignored.
        byte[] expectedBytes = new byte[]{
            (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x02, (byte) 0x0B, (byte) 0x01, (byte) 0x01, (byte) 0x0E, (byte) 0x01, (byte) 0x03, (byte) 0x03, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            0x17, // LS length
            0x03, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56, 0x4D, 0x54, 0x49, // Tag 3
            0x04, 0x01, 0x04, // Tag 4
            0x01, 0x02, (byte)0x9f, (byte)0x97 // checksum
        };
        assertEquals(localSet.frameMessage(false), expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0903 Remote Video Terrminal");
    }
*/
    private RvtLocalSet buildLocalSetValues()
    {
        Map<RvtMetadataKey, IRvtMetadataValue> values = new HashMap<>();
        IRvtMetadataValue platformTAS = new RvtPlatformTrueAirspeed(258);
        values.put(RvtMetadataKey.PlatformTrueAirspeed, platformTAS);
        IRvtMetadataValue version = new ST0806Version(4);
        values.put(RvtMetadataKey.UASLSVersionNumber, version);
        RvtLocalSet localSet = new RvtLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 2);
        checkPlatformTrueAirspeedExample(localSet);
        checkVersionNumberExample(localSet);
        return localSet;
    }

    private RvtLocalSet buildLocalSetWithPoiLSValues() throws KlvParseException
    {
        Map<RvtMetadataKey, IRvtMetadataValue> values = new HashMap<>();
        IRvtMetadataValue platformTAS = new RvtPlatformTrueAirspeed(258);
        values.put(RvtMetadataKey.PlatformTrueAirspeed, platformTAS);
        IRvtMetadataValue version = new ST0806Version(4);
        values.put(RvtMetadataKey.UASLSVersionNumber, version);
        RvtLocalSet localSet = new RvtLocalSet(values);
        return localSet;
    }

        public static Map<RvtPoiMetadataKey, IRvtPoiAoiMetadataValue> buildAnotherPoiValues() throws KlvParseException {
        Map<RvtPoiMetadataKey, IRvtPoiAoiMetadataValue> values = new HashMap<>();
        values.put(RvtPoiMetadataKey.PoiAoiNumber, new PoiAoiNumber(3));
        values.put(RvtPoiMetadataKey.PoiLatitude, new PoiLatitude(-35.4));
        values.put(RvtPoiMetadataKey.PoiLongitude, new PoiLongitude(135.4));
        RvtPoiAoiTextString label = new RvtPoiAoiTextString(RvtPoiAoiTextString.POI_AOI_LABEL, "Another Point");
        values.put(RvtPoiMetadataKey.PoiAoiLabel, label);
        return values;
    }

    private void checkPoiLocalSetExample(RvtLocalSet localSet)
    {
        assertTrue(localSet.getPOIIndexes().contains(516));
        RvtPoiLocalSet poiLocalSet = localSet.getPOI(516);
        RvtPoiLocalSetTest.checkPoiAoiNumberExample(poiLocalSet);
        RvtPoiLocalSetTest.checkPoiAoiLatitudeExample(poiLocalSet);
        RvtPoiLocalSetTest.checkPoiAoiLongitudeExample(poiLocalSet);
    }

    @Test
    public void constructUnknown() throws KlvParseException
    {
        verifyNoLoggerMessages();
        IRvtMetadataValue unknown = RvtLocalSet.createValue(RvtMetadataKey.Undefined, new byte[]{0x01, 0x02});
        this.verifySingleLoggerMessage("Unknown Remote Video Terminal Metadata tag: Undefined");
        assertNull(unknown);
    }
}

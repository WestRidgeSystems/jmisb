package org.jmisb.api.klv.st0806;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LoggerChecks;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for the ST0806 Remote Video Terrminal Local Set.
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
/*
    @Test
    public void parseTag4() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x04, 0x01, 0x04};
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
            0x03, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56, 0x4D, 0x54, 0x49,
            0x04, 0x01, 0x04};
        verifyNoLoggerMessages();
        RvtLocalSet localSet = new RvtLocalSet(bytes);
        verifySingleLoggerMessage("Unknown Remote Video Terrminal Metadata tag: 127");
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 2);
        checkVersionNumberExample(localSet);
        checkSystemNameExample(localSet);
    }

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

    private void checkSystemNameExample(RvtLocalSet localSet)
    {
        final String stringVal = "DSTO_ADSS_Remote Video Terrminal";
        assertTrue(localSet.getTags().contains(RvtMetadataKey.SystemName));
        IRvtMetadataValue v = localSet.getField(RvtMetadataKey.SystemName);
        assertEquals(v.getDisplayName(), "System Name/Description");
        assertEquals(v.getDisplayName(), VmtiTextString.SYSTEM_NAME);
        assertEquals(v.getDisplayableValue(), stringVal);
        assertTrue(v instanceof VmtiTextString);
        VmtiTextString text = (VmtiTextString) localSet.getField(RvtMetadataKey.SystemName);
        assertEquals(text.getValue(), stringVal);
    }

    private void checkVersionNumberExample(RvtLocalSet localSet)
    {
        final String stringVal = "ST0903.4";
        assertTrue(localSet.getTags().contains(RvtMetadataKey.VersionNumber));
        IRvtMetadataValue v = localSet.getField(RvtMetadataKey.VersionNumber);
        assertEquals(v.getDisplayName(), "Version Number");
        assertEquals(v.getDisplayableValue(), stringVal);
        assertEquals(v.getBytes(), new byte[]{0x04});
        assertTrue(v instanceof ST0903Version);
        ST0903Version version = (ST0903Version) localSet.getField(RvtMetadataKey.VersionNumber);
        assertEquals(version.getVersion(), 4);
    }
*/
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

    /*
    @Test
    public void constructFromMap()
    {
        RvtLocalSet localSet = buildLocalSetValues();
        byte[] expectedBytes = new byte[]{
            0x03, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56, 0x4D, 0x54, 0x49,
            0x04, 0x01, 0x04};
        assertEquals(localSet.frameMessage(true), expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0903 Remote Video Terrminal");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.RvtLocalSetUl);
        assertEquals(localSet.getUniversalLabel().getBytes(), new byte[]{
            (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x02, (byte) 0x0B, (byte) 0x01, (byte) 0x01,
            (byte) 0x0E, (byte) 0x01, (byte) 0x03, (byte) 0x03, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void constructFromMapNonNested()
    {
        RvtLocalSet localSet = buildLocalSetValues();
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

    private RvtLocalSet buildLocalSetValues()
    {
        Map<RvtMetadataKey, IRvtMetadataValue> values = new HashMap<>();
        IRvtMetadataValue systemName = new VmtiTextString(VmtiTextString.SYSTEM_NAME, "DSTO_ADSS_Remote Video Terrminal");
        values.put(RvtMetadataKey.SystemName, systemName);
        IRvtMetadataValue version = new ST0903Version(4);
        values.put(RvtMetadataKey.VersionNumber, version);
        RvtLocalSet localSet = new RvtLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 2);
        checkSystemNameExample(localSet);
        checkVersionNumberExample(localSet);
        return localSet;
    }
*/
    @Test
    public void constructUnknown() throws KlvParseException
    {
        verifyNoLoggerMessages();
        IRvtMetadataValue unknown = RvtLocalSet.createValue(RvtMetadataKey.Undefined, new byte[]{0x01, 0x02});
        this.verifySingleLoggerMessage("Unknown Remote Video Terminal Metadata tag: Undefined");
        assertNull(unknown);
    }
}

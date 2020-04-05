package org.jmisb.api.klv.st0903;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.api.klv.LoggerChecks;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for the ST0903 VMTI Local Set.
 */
public class VmtiLocalSetTest extends LoggerChecks
{
    VmtiLocalSetTest()
    {
        super(VmtiLocalSet.class);
    }

    @Test
    public void parseTag3() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x03, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56, 0x4D, 0x54, 0x49};
        VmtiLocalSet localSet = new VmtiLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 1);
        checkSystemNameExample(localSet);
        assertEquals(localSet.frameMessage(true), bytes);
    }

    @Test
    public void parseTag4() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x04, 0x01, 0x04};
        VmtiLocalSet localSet = new VmtiLocalSet(bytes);
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
        VmtiLocalSet localSet = new VmtiLocalSet(bytes);
        verifySingleLoggerMessage("Unknown VMTI Metadata tag: 127");
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
        VmtiLocalSet localSet = new VmtiLocalSet(bytes);
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
        new VmtiLocalSet(bytes);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void parseTagsWithChecksumBad2() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[]{
            0x03, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56, 0x4D, 0x54, 0x49,
            0x04, 0x01, 0x04,
            0x01, 0x02, 0x47, 0x3c
        };
        new VmtiLocalSet(bytes);
    }

    private void checkSystemNameExample(VmtiLocalSet localSet)
    {
        final String stringVal = "DSTO_ADSS_VMTI";
        assertTrue(localSet.getTags().contains(VmtiMetadataKey.SystemName));
        IVmtiMetadataValue v = localSet.getField(VmtiMetadataKey.SystemName);
        assertEquals(v.getDisplayName(), "System Name/Description");
        assertEquals(v.getDisplayName(), VmtiTextString.SYSTEM_NAME);
        assertEquals(v.getDisplayableValue(), stringVal);
        assertTrue(v instanceof VmtiTextString);
        VmtiTextString text = (VmtiTextString) localSet.getField(VmtiMetadataKey.SystemName);
        assertEquals(text.getValue(), stringVal);
    }

    private void checkVersionNumberExample(VmtiLocalSet localSet)
    {
        final String stringVal = "ST0903.4";
        assertTrue(localSet.getTags().contains(VmtiMetadataKey.VersionNumber));
        IVmtiMetadataValue v = localSet.getField(VmtiMetadataKey.VersionNumber);
        assertEquals(v.getDisplayName(), "Version Number");
        assertEquals(v.getDisplayableValue(), stringVal);
        assertEquals(v.getBytes(), new byte[]{0x04});
        assertTrue(v instanceof ST0903Version);
        ST0903Version version = (ST0903Version) localSet.getField(VmtiMetadataKey.VersionNumber);
        assertEquals(version.getVersion(), 4);
    }

    @Test
    public void constructFromMap()
    {
        VmtiLocalSet localSet = buildLocalSetValues();
        byte[] expectedBytes = new byte[]{
            0x03, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56, 0x4D, 0x54, 0x49,
            0x04, 0x01, 0x04};
        assertEquals(localSet.frameMessage(true), expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0903 VMTI");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.VmtiLocalSetUl);
        assertEquals(localSet.getUniversalLabel().getBytes(), new byte[]{
            (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x02, (byte) 0x0B, (byte) 0x01, (byte) 0x01,
            (byte) 0x0E, (byte) 0x01, (byte) 0x03, (byte) 0x03, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void constructFromMapNonNested()
    {
        VmtiLocalSet localSet = buildLocalSetValues();
        byte[] expectedBytes = new byte[]{
            (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x02, (byte) 0x0B, (byte) 0x01, (byte) 0x01, (byte) 0x0E, (byte) 0x01, (byte) 0x03, (byte) 0x03, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            0x17, // LS length
            0x03, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56, 0x4D, 0x54, 0x49, // Tag 3
            0x04, 0x01, 0x04, // Tag 4
            0x01, 0x02, (byte)0x9f, (byte)0x97 // checksum
        };
        assertEquals(localSet.frameMessage(false), expectedBytes);
        assertTrue(localSet instanceof IMisbMessage);
        assertEquals(localSet.displayHeader(), "ST0903 VMTI");
    }

    @Test
    public void constructFromMapNonNestedWithChecksum()
    {
        Map<VmtiMetadataKey, IVmtiMetadataValue> values = new HashMap<>();
        IVmtiMetadataValue systemName = new VmtiTextString(VmtiTextString.SYSTEM_NAME, "DSTO_ADSS_VMTI");
        values.put(VmtiMetadataKey.SystemName, systemName);
        IVmtiMetadataValue version = new ST0903Version(4);
        values.put(VmtiMetadataKey.VersionNumber, version);
        IVmtiMetadataValue fakeChecksum = new IVmtiMetadataValue() {
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
        values.put(VmtiMetadataKey.Checksum, fakeChecksum);
        VmtiLocalSet localSet = new VmtiLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 3);
        checkSystemNameExample(localSet);
        checkVersionNumberExample(localSet);
        assertTrue(localSet.getTags().contains(VmtiMetadataKey.Checksum));
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
        assertEquals(localSet.displayHeader(), "ST0903 VMTI");
    }

    private VmtiLocalSet buildLocalSetValues()
    {
        Map<VmtiMetadataKey, IVmtiMetadataValue> values = new HashMap<>();
        IVmtiMetadataValue systemName = new VmtiTextString(VmtiTextString.SYSTEM_NAME, "DSTO_ADSS_VMTI");
        values.put(VmtiMetadataKey.SystemName, systemName);
        IVmtiMetadataValue version = new ST0903Version(4);
        values.put(VmtiMetadataKey.VersionNumber, version);
        VmtiLocalSet localSet = new VmtiLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 2);
        checkSystemNameExample(localSet);
        checkVersionNumberExample(localSet);
        return localSet;
    }

    @Test
    public void constructUnknown() throws KlvParseException
    {
        verifyNoLoggerMessages();
        IVmtiMetadataValue unknown = VmtiLocalSet.createValue(VmtiMetadataKey.Undefined, new byte[]{0x01, 0x02});
        this.verifySingleLoggerMessage("Unknown VMTI Metadata tag: Undefined");
        assertNull(unknown);
    }
}

package org.jmisb.api.klv.st0903;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for the ST0903 VMTI Local Set.
 */
public class VmtiLocalSetTest
{
    @Test
    public void parseTag3() throws KlvParseException, URISyntaxException, IOException
    {
        final byte[] bytes = new byte[]{0x03, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56, 0x4D, 0x54, 0x49};
        VmtiLocalSet localSet = new VmtiLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 1);
        checkSystemNameExample(localSet);
        assertEquals(localSet.getBytes(), bytes);
    }

    @Test
    public void parseTag4() throws KlvParseException, URISyntaxException, IOException
    {
        final byte[] bytes = new byte[]{0x04, 0x01, 0x04};
        VmtiLocalSet localSet = new VmtiLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 1);
        checkVersionNumberExample(localSet);
        assertEquals(localSet.getBytes(), bytes);
    }

    @Test
    public void parseTagsWithUnknownTag() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[]{
            0x7F, 0x02, (byte) 0x80, (byte) 0xCA, // No such tag
            0x03, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56, 0x4D, 0x54, 0x49,
            0x04, 0x01, 0x04};
        VmtiLocalSet localSet = new VmtiLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 2);
        checkVersionNumberExample(localSet);
        checkSystemNameExample(localSet);
    }

    private void checkSystemNameExample(VmtiLocalSet localSet) throws URISyntaxException
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

    private void checkVersionNumberExample(VmtiLocalSet localSet) throws URISyntaxException
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
    public void constructFromMap() throws KlvParseException, URISyntaxException, IOException
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
        byte[] expectedBytes = new byte[]{
            0x03, 0x0E, 0x44, 0x53, 0x54, 0x4F, 0x5F, 0x41, 0x44, 0x53, 0x53, 0x5F, 0x56, 0x4D, 0x54, 0x49,
            0x04, 0x01, 0x04};
        assertEquals(localSet.getBytes(), expectedBytes);
    }
    
    @Test
    public void constructUnknown() throws KlvParseException
    {
        IVmtiMetadataValue unknown = VmtiLocalSet.createValue(VmtiMetadataKey.Undefined, new byte[]{0x01, 0x02});
        assertNull(unknown);
    }
}

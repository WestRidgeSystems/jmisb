package org.jmisb.api.klv.st0806.userdefined;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for the ST0806 User Defined LS.
 */
public class RvtUserDefinedLocalSetTest extends LoggerChecks
{
    public RvtUserDefinedLocalSetTest()
    {
        super(RvtUserDefinedLocalSet.class);
    }

    @Test
    public void parseTag1() throws KlvParseException
    {
        byte[] bytes = new byte[]{0x01, 0x01, (byte)0b10000001};
        RvtUserDefinedLocalSet aoiLocalSet = new RvtUserDefinedLocalSet(bytes, 0, bytes.length);
        assertNotNull(aoiLocalSet);
        assertEquals(aoiLocalSet.getTags().size(), 1);
        checkNumericIdExample(aoiLocalSet);
    }

    @Test
    public void parseRequiredTags() throws KlvParseException
    {
        final byte[] bytes = new byte[]{
            0x01, 0x01, (byte)0b10000001,
            0x02, 0x04, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01};
        RvtUserDefinedLocalSet aoiLocalSet = new RvtUserDefinedLocalSet(bytes, 0, bytes.length);
        assertNotNull(aoiLocalSet);
        assertEquals(aoiLocalSet.getTags().size(), 2);
        checkNumericIdExample(aoiLocalSet);
        checkUserDataExample(aoiLocalSet);
    }

    @Test
    public void parseTagsWithUnknownTag() throws KlvParseException
    {
        final byte[] bytes = new byte[]{
            0x0B, 0x02, (byte) 0x80, (byte) 0xCA, // No such tag
            0x01, 0x01, (byte)0b10000001,
            0x02, 0x04, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01};
        verifyNoLoggerMessages();
        RvtUserDefinedLocalSet aoiLocalSet = new RvtUserDefinedLocalSet(bytes, 0, bytes.length);
        verifySingleLoggerMessage("Unknown RVT User Defined Metadata tag: 11");
        assertNotNull(aoiLocalSet);
        assertEquals(aoiLocalSet.getTags().size(), 2);
        checkNumericIdExample(aoiLocalSet);
        checkUserDataExample(aoiLocalSet);
    }

    private void checkNumericIdExample(RvtUserDefinedLocalSet aoiLocalSet)
    {
        assertTrue(aoiLocalSet.getTags().contains(RvtUserDefinedMetadataKey.NumericId));
        IRvtUserDefinedMetadataValue v = aoiLocalSet.getField(RvtUserDefinedMetadataKey.NumericId);
        assertEquals(v.getDisplayName(), "Numeric ID");
        assertEquals(v.getDisplayableValue(), "Unsigned Integer: 1");
        assertTrue(v instanceof RvtNumericId);
        RvtNumericId id = (RvtNumericId)v;
        assertNotNull(id);
        assertEquals(id.getDataType(), 2);
        assertEquals(id.getId(), 1);
    }

    private void checkUserDataExample(RvtUserDefinedLocalSet aoiLocalSet)
    {
        assertTrue(aoiLocalSet.getTags().contains(RvtUserDefinedMetadataKey.UserData));
        IRvtUserDefinedMetadataValue v = aoiLocalSet.getField(RvtUserDefinedMetadataKey.UserData);
        assertEquals(v.getDisplayName(), "User Data");
        assertEquals(v.getDisplayableValue(), "[User Data]");
        assertTrue(v instanceof RvtUserData);
        RvtUserData data = (RvtUserData)v;
        assertEquals(data.getBytes(), new byte[]{(byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01});
    }

    @Test
    public void createUnknownTag() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x03};
        verifyNoLoggerMessages();
        IRvtUserDefinedMetadataValue value = RvtUserDefinedLocalSet.createValue(RvtUserDefinedMetadataKey.Undefined, bytes);
        verifySingleLoggerMessage("Unrecognized RVT User Defined Data tag: Undefined");
        assertNull(value);
    }

    @Test
    public void constructFromMap() throws KlvParseException
    {
        Map<RvtUserDefinedMetadataKey, IRvtUserDefinedMetadataValue> values = new HashMap<>();
        IRvtUserDefinedMetadataValue tag1bytes = RvtUserDefinedLocalSet.createValue(RvtUserDefinedMetadataKey.NumericId, new byte[]{(byte)0b10000001});
        values.put(RvtUserDefinedMetadataKey.NumericId, tag1bytes);
        final byte[] dataBytes = new byte[]{(byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01};
        IRvtUserDefinedMetadataValue data = RvtUserDefinedLocalSet.createValue(RvtUserDefinedMetadataKey.UserData, dataBytes);
        values.put(RvtUserDefinedMetadataKey.UserData, data);
        RvtUserDefinedLocalSet userDefinedLocalSet = new RvtUserDefinedLocalSet(values);
        assertNotNull(userDefinedLocalSet);
        assertEquals(userDefinedLocalSet.getDisplayName(), "User Data");
        assertEquals(userDefinedLocalSet.getDisplayableValue(), "[User Defined Local Set]");
        assertEquals(userDefinedLocalSet.getTags().size(), 2);
        checkNumericIdExample(userDefinedLocalSet);
        checkUserDataExample(userDefinedLocalSet);
        byte[] expectedBytes = new byte[] {
            (byte)0x01, (byte)0x01, (byte)0x81,
            (byte)0x02, (byte)0x04, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01};
        assertEquals(userDefinedLocalSet.getBytes(), expectedBytes);
    }
}

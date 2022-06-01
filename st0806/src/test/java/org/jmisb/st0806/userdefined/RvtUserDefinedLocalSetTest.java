package org.jmisb.st0806.userdefined;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.st0806.LoggerChecks;
import org.testng.annotations.Test;

/** Tests for the ST0806 User Defined LS. */
public class RvtUserDefinedLocalSetTest extends LoggerChecks {
    public RvtUserDefinedLocalSetTest() {
        super(RvtUserDefinedLocalSet.class);
    }

    @Test
    public void parseTag1() throws KlvParseException {
        byte[] bytes = new byte[] {0x01, 0x01, (byte) 0b10000001};
        RvtUserDefinedLocalSet localSet = new RvtUserDefinedLocalSet(bytes, 0, bytes.length);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 1);
        checkNumericIdExample(localSet);
        IKlvValue v = localSet.getField((IKlvKey) RvtUserDefinedMetadataKey.NumericId);
        assertTrue(v instanceof RvtNumericId);
        RvtNumericId n = (RvtNumericId) v;
        assertEquals(n.getId(), 1);
    }

    @Test
    public void parseDisplayNoContent() throws KlvParseException {
        byte[] bytes = new byte[] {};
        RvtUserDefinedLocalSet localSet = new RvtUserDefinedLocalSet(bytes, 0, bytes.length);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 0);
        assertEquals(localSet.getDisplayableValue(), "[User Defined Local Set]");
    }

    @Test
    public void parseRequiredTags() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x01,
                    0x01,
                    (byte) 0b10000001,
                    0x02,
                    0x04,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x01
                };
        RvtUserDefinedLocalSet localSet = new RvtUserDefinedLocalSet(bytes, 0, bytes.length);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 2);
        checkNumericIdExample(localSet);
        checkUserDataExample(localSet);
    }

    @Test
    public void parseTagsWithUnknownTag() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x0B,
                    0x02,
                    (byte) 0x80,
                    (byte) 0xCA, // No such tag
                    0x01,
                    0x01,
                    (byte) 0b10000001,
                    0x02,
                    0x04,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x01
                };
        verifyNoLoggerMessages();
        RvtUserDefinedLocalSet localSet = new RvtUserDefinedLocalSet(bytes, 0, bytes.length);
        verifySingleLoggerMessage("Unknown RVT User Defined Metadata tag: 11");
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 2);
        checkNumericIdExample(localSet);
        checkUserDataExample(localSet);
    }

    private void checkNumericIdExample(RvtUserDefinedLocalSet localSet) {
        assertTrue(localSet.getTags().contains(RvtUserDefinedMetadataKey.NumericId));
        IRvtUserDefinedMetadataValue v = localSet.getField(RvtUserDefinedMetadataKey.NumericId);
        assertEquals(v.getDisplayName(), "Numeric ID");
        assertEquals(v.getDisplayableValue(), "Unsigned Integer: 1");
        assertTrue(v instanceof RvtNumericId);
        RvtNumericId id = (RvtNumericId) v;
        assertNotNull(id);
        assertEquals(id.getDataType(), 2);
        assertEquals(id.getId(), 1);
    }

    private void checkUserDataExample(RvtUserDefinedLocalSet localSet) {
        assertTrue(localSet.getTags().contains(RvtUserDefinedMetadataKey.UserData));
        IRvtUserDefinedMetadataValue v = localSet.getField(RvtUserDefinedMetadataKey.UserData);
        assertEquals(v.getDisplayName(), "User Data");
        assertEquals(v.getDisplayableValue(), "[User Data]");
        assertTrue(v instanceof RvtUserData);
        RvtUserData data = (RvtUserData) v;
        assertEquals(
                data.getBytes(), new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01});
    }

    @Test
    public void createUnknownTag() throws KlvParseException {
        final byte[] bytes = new byte[] {0x03};
        verifyNoLoggerMessages();
        IRvtUserDefinedMetadataValue value =
                RvtUserDefinedLocalSet.createValue(RvtUserDefinedMetadataKey.Undefined, bytes);
        verifySingleLoggerMessage("Unrecognized RVT User Defined Data tag: Undefined");
        assertNull(value);
    }

    @Test
    public void constructFromMap() throws KlvParseException {
        Map<RvtUserDefinedMetadataKey, IRvtUserDefinedMetadataValue> values = new HashMap<>();
        IRvtUserDefinedMetadataValue tag1bytes =
                RvtUserDefinedLocalSet.createValue(
                        RvtUserDefinedMetadataKey.NumericId, new byte[] {(byte) 0b10000001});
        values.put(RvtUserDefinedMetadataKey.NumericId, tag1bytes);
        final byte[] dataBytes = new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01};
        IRvtUserDefinedMetadataValue data =
                RvtUserDefinedLocalSet.createValue(RvtUserDefinedMetadataKey.UserData, dataBytes);
        values.put(RvtUserDefinedMetadataKey.UserData, data);
        RvtUserDefinedLocalSet userDefinedLocalSet = new RvtUserDefinedLocalSet(values);
        assertNotNull(userDefinedLocalSet);
        assertEquals(userDefinedLocalSet.getDisplayName(), "User Data");
        assertEquals(userDefinedLocalSet.getDisplayableValue(), "Unsigned Integer: 1");
        assertEquals(userDefinedLocalSet.getTags().size(), 2);
        checkNumericIdExample(userDefinedLocalSet);
        checkUserDataExample(userDefinedLocalSet);
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x81,
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x01
                };
        assertEquals(userDefinedLocalSet.getBytes(), expectedBytes);
    }
}

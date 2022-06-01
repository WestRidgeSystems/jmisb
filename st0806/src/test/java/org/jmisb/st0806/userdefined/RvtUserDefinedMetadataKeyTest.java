package org.jmisb.st0806.userdefined;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for RvtUserDefinedMetadataKey. */
public class RvtUserDefinedMetadataKeyTest {

    @Test
    public void Enum0Test() {
        RvtUserDefinedMetadataKey key = RvtUserDefinedMetadataKey.getKey(0);
        assertEquals(key, RvtUserDefinedMetadataKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        RvtUserDefinedMetadataKey key = RvtUserDefinedMetadataKey.getKey(999);
        assertEquals(key, RvtUserDefinedMetadataKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void Enum1Test() {
        RvtUserDefinedMetadataKey key = RvtUserDefinedMetadataKey.getKey(1);
        assertEquals(key, RvtUserDefinedMetadataKey.NumericId);
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void Enum2Test() {
        RvtUserDefinedMetadataKey key = RvtUserDefinedMetadataKey.getKey(2);
        assertEquals(key, RvtUserDefinedMetadataKey.UserData);
        assertEquals(key.getIdentifier(), 2);
    }
}

package org.jmisb.api.klv.st0806;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for RvtMetadataKey. */
public class RvtMetadataKeyTest {

    @Test
    public void Enum0Test() {
        RvtMetadataKey key = RvtMetadataKey.getKey(0);
        assertEquals(key, RvtMetadataKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        RvtMetadataKey key = RvtMetadataKey.getKey(999);
        assertEquals(key, RvtMetadataKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void Enum1Test() {
        RvtMetadataKey key = RvtMetadataKey.getKey(1);
        assertEquals(key, RvtMetadataKey.CRC32);
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void Enum2Test() {
        RvtMetadataKey key = RvtMetadataKey.getKey(2);
        assertEquals(key, RvtMetadataKey.UserDefinedTimeStampMicroseconds);
        assertEquals(key.getIdentifier(), 2);
    }

    @Test
    public void Enum20Test() {
        RvtMetadataKey key = RvtMetadataKey.getKey(20);
        assertEquals(key, RvtMetadataKey.MGRSEastingSecondValue);
        assertEquals(key.getIdentifier(), 20);
    }

    public void Enum21Test() {
        RvtMetadataKey key = RvtMetadataKey.getKey(21);
        assertEquals(key, RvtMetadataKey.MGRSNorthingSecondValue);
        assertEquals(key.getIdentifier(), 21);
    }
}

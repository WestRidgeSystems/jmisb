package org.jmisb.st0806.poiaoi;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for RvtAoiMetadataKey. */
public class RvtAoiMetadataKeyTest {

    @Test
    public void Enum0Test() {
        RvtAoiMetadataKey key = RvtAoiMetadataKey.getKey(0);
        assertEquals(key, RvtAoiMetadataKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        RvtAoiMetadataKey key = RvtAoiMetadataKey.getKey(999);
        assertEquals(key, RvtAoiMetadataKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void Enum1Test() {
        RvtAoiMetadataKey key = RvtAoiMetadataKey.getKey(1);
        assertEquals(key, RvtAoiMetadataKey.PoiAoiNumber);
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void Enum2Test() {
        RvtAoiMetadataKey key = RvtAoiMetadataKey.getKey(2);
        assertEquals(key, RvtAoiMetadataKey.CornerLatitudePoint1);
        assertEquals(key.getIdentifier(), 2);
    }

    @Test
    public void Enum5Test() {
        RvtAoiMetadataKey key = RvtAoiMetadataKey.getKey(5);
        assertEquals(key, RvtAoiMetadataKey.CornerLongitudePoint3);
        assertEquals(key.getIdentifier(), 5);
    }

    @Test
    public void Enum10Test() {
        RvtAoiMetadataKey key = RvtAoiMetadataKey.getKey(10);
        assertEquals(key, RvtAoiMetadataKey.OperationId);
        assertEquals(key.getIdentifier(), 10);
    }
}

package org.jmisb.api.klv.st0806.poiaoi;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Unit tests for RvtPoiMetadataKey.
 */
public class RvtPoiMetadataKeyTest {

    @Test
    public void Enum0Test()
    {
        RvtPoiMetadataKey key = RvtPoiMetadataKey.getKey(0);
        assertEquals(key, RvtPoiMetadataKey.Undefined);
        assertEquals(key.getTag(), 0);
    }

    @Test
    public void EnumUnknownTest()
    {
        RvtPoiMetadataKey key = RvtPoiMetadataKey.getKey(999);
        assertEquals(key, RvtPoiMetadataKey.Undefined);
        assertEquals(key.getTag(), 0);
    }

    @Test
    public void Enum1Test()
    {
        RvtPoiMetadataKey key = RvtPoiMetadataKey.getKey(1);
        assertEquals(key, RvtPoiMetadataKey.PoiAoiNumber);
        assertEquals(key.getTag(), 1);
    }

    @Test
    public void Enum2Test()
    {
        RvtPoiMetadataKey key = RvtPoiMetadataKey.getKey(2);
        assertEquals(key, RvtPoiMetadataKey.PoiLatitude);
        assertEquals(key.getTag(), 2);
    }

    @Test
    public void Enum10Test()
    {
        RvtPoiMetadataKey key = RvtPoiMetadataKey.getKey(10);
        assertEquals(key, RvtPoiMetadataKey.OperationId);
        assertEquals(key.getTag(), 10);
    }
}

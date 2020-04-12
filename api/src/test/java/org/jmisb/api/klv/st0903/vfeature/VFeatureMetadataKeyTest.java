package org.jmisb.api.klv.st0903.vfeature;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Unit tests for VFeatureMetadataKey.
 */
public class VFeatureMetadataKeyTest
{
    @Test
    public void Enum0Test()
    {
        VFeatureMetadataKey key = VFeatureMetadataKey.getKey(0);
        assertEquals(key, VFeatureMetadataKey.Undefined);
        assertEquals(key.getTagCode(), 0);
    }

    @Test
    public void EnumUnknownTest()
    {
        VFeatureMetadataKey key = VFeatureMetadataKey.getKey(999);
        assertEquals(key, VFeatureMetadataKey.Undefined);
        assertEquals(key.getTagCode(), 0);
    }

    @Test
    public void Enum1Test()
    {
        VFeatureMetadataKey key = VFeatureMetadataKey.getKey(1);
        assertEquals(key, VFeatureMetadataKey.schema);
        assertEquals(key.getTagCode(), 1);
    }

    @Test
    public void Enum2Test()
    {
        VFeatureMetadataKey key = VFeatureMetadataKey.getKey(2);
        assertEquals(key, VFeatureMetadataKey.schemaFeature);
        assertEquals(key.getTagCode(), 2);
    }
}

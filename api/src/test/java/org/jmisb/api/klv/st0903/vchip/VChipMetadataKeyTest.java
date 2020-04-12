package org.jmisb.api.klv.st0903.vchip;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Unit tests for VChipMetadataKey.
 */
public class VChipMetadataKeyTest {

    @Test
    public void Enum0Test()
    {
        VChipMetadataKey key = VChipMetadataKey.getKey(0);
        assertEquals(key, VChipMetadataKey.Undefined);
        assertEquals(key.getTagCode(), 0);
    }

    @Test
    public void EnumUnknownTest()
    {
        VChipMetadataKey key = VChipMetadataKey.getKey(999);
        assertEquals(key, VChipMetadataKey.Undefined);
        assertEquals(key.getTagCode(), 0);
    }

    @Test
    public void Enum1Test()
    {
        VChipMetadataKey key = VChipMetadataKey.getKey(1);
        assertEquals(key, VChipMetadataKey.imageType);
        assertEquals(key.getTagCode(), 1);
    }

    @Test
    public void Enum2Test()
    {
        VChipMetadataKey key = VChipMetadataKey.getKey(2);
        assertEquals(key, VChipMetadataKey.imageUri);
        assertEquals(key.getTagCode(), 2);
    }

    @Test
    public void Enum3Test()
    {
        VChipMetadataKey key = VChipMetadataKey.getKey(3);
        assertEquals(key, VChipMetadataKey.embeddedImage);
        assertEquals(key.getTagCode(), 3);
    }
}

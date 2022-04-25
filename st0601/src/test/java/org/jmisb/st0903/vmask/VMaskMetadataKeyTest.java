package org.jmisb.st0903.vmask;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for VMaskMetadataKey. */
public class VMaskMetadataKeyTest {
    @Test
    public void Enum0Test() {
        VMaskMetadataKey key = VMaskMetadataKey.getKey(0);
        assertEquals(key, VMaskMetadataKey.Undefined);
        assertEquals(key.getTag(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        VMaskMetadataKey key = VMaskMetadataKey.getKey(999);
        assertEquals(key, VMaskMetadataKey.Undefined);
        assertEquals(key.getTag(), 0);
    }

    @Test
    public void Enum1Test() {
        VMaskMetadataKey key = VMaskMetadataKey.getKey(1);
        assertEquals(key, VMaskMetadataKey.polygon);
        assertEquals(key.getTag(), 1);
    }

    @Test
    public void Enum2Test() {
        VMaskMetadataKey key = VMaskMetadataKey.getKey(2);
        assertEquals(key, VMaskMetadataKey.bitMaskSeries);
        assertEquals(key.getTag(), 2);
    }
}

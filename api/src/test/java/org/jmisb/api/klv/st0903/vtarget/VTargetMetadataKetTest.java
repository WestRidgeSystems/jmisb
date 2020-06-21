package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for VTargetMetadataKey. */
public class VTargetMetadataKetTest {

    @Test
    public void Enum0Test() {
        VTargetMetadataKey key = VTargetMetadataKey.getKey(0);
        assertEquals(key, VTargetMetadataKey.Undefined);
        assertEquals(key.getTag(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        VTargetMetadataKey key = VTargetMetadataKey.getKey(127);
        assertEquals(key, VTargetMetadataKey.Undefined);
        assertEquals(key.getTag(), 0);
    }

    @Test
    public void Enum1Test() {
        VTargetMetadataKey key = VTargetMetadataKey.getKey(1);
        assertEquals(key, VTargetMetadataKey.TargetCentroid);
        assertEquals(key.getTag(), 1);
    }

    @Test
    public void Enum2Test() {
        VTargetMetadataKey key = VTargetMetadataKey.getKey(2);
        assertEquals(key, VTargetMetadataKey.BoundaryTopLeft);
        assertEquals(key.getTag(), 2);
    }

    @Test
    public void Enum107Test() {
        VTargetMetadataKey key = VTargetMetadataKey.getKey(107);
        assertEquals(key, VTargetMetadataKey.VObjectSeries);
        assertEquals(key.getTag(), 107);
    }
}

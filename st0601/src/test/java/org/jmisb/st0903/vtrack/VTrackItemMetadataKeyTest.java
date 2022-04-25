package org.jmisb.st0903.vtrack;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for VTrackItemMetadataKey. */
public class VTrackItemMetadataKeyTest {

    @Test
    public void Enum0Test() {
        VTrackItemMetadataKey key = VTrackItemMetadataKey.getKey(0);
        assertEquals(key, VTrackItemMetadataKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        VTrackItemMetadataKey key = VTrackItemMetadataKey.getKey(999);
        assertEquals(key, VTrackItemMetadataKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void Enum1Test() {
        VTrackItemMetadataKey key = VTrackItemMetadataKey.getKey(1);
        assertEquals(key, VTrackItemMetadataKey.TargetTimeStamp);
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void Enum2Test() {
        VTrackItemMetadataKey key = VTrackItemMetadataKey.getKey(2);
        assertEquals(key, VTrackItemMetadataKey.TargetCentroidPixNum);
        assertEquals(key.getIdentifier(), 2);
    }

    @Test
    public void Enum24Test() {
        VTrackItemMetadataKey key = VTrackItemMetadataKey.getKey(24);
        assertEquals(key, VTrackItemMetadataKey.MotionImageryUrl);
        assertEquals(key.getIdentifier(), 24);
    }

    @Test
    public void Enum101Test() {
        VTrackItemMetadataKey key = VTrackItemMetadataKey.getKey(101);
        assertEquals(key, VTrackItemMetadataKey.VMask);
        assertEquals(key.getIdentifier(), 101);
    }

    @Test
    public void Enum107Test() {
        VTrackItemMetadataKey key = VTrackItemMetadataKey.getKey(107);
        assertEquals(key, VTrackItemMetadataKey.VObjectSeries);
        assertEquals(key.getIdentifier(), 107);
    }
}

package org.jmisb.st0903vtrack;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for VTrackMetadataKey. */
public class VTrackMetadataKeyTest {

    @Test
    public void Enum0Test() {
        VTrackMetadataKey key = VTrackMetadataKey.getKey(0);
        assertEquals(key, VTrackMetadataKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        VTrackMetadataKey key = VTrackMetadataKey.getKey(999);
        assertEquals(key, VTrackMetadataKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void Enum1Test() {
        VTrackMetadataKey key = VTrackMetadataKey.getKey(1);
        assertEquals(key, VTrackMetadataKey.Checksum);
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void Enum2Test() {
        VTrackMetadataKey key = VTrackMetadataKey.getKey(2);
        assertEquals(key, VTrackMetadataKey.TrackTimeStamp);
        assertEquals(key.getIdentifier(), 2);
    }

    @Test
    public void Enum13Test() {
        VTrackMetadataKey key = VTrackMetadataKey.getKey(13);
        assertEquals(key, VTrackMetadataKey.NumTrackPoints);
        assertEquals(key.getIdentifier(), 13);
    }

    @Test
    public void Enum101Test() {
        VTrackMetadataKey key = VTrackMetadataKey.getKey(101);
        assertEquals(key, VTrackMetadataKey.VTrackItemSeries);
        assertEquals(key.getIdentifier(), 101);
    }

    @Test
    public void Enum103Test() {
        VTrackMetadataKey key = VTrackMetadataKey.getKey(103);
        assertEquals(key, VTrackMetadataKey.OntologySeries);
        assertEquals(key.getIdentifier(), 103);
    }
}

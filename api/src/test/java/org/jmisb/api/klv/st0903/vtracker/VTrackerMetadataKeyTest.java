package org.jmisb.api.klv.st0903.vtracker;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for VTrackerMetadataKey. */
public class VTrackerMetadataKeyTest {

    @Test
    public void Enum0Test() {
        VTrackerMetadataKey key = VTrackerMetadataKey.getKey(0);
        assertEquals(key, VTrackerMetadataKey.Undefined);
        assertEquals(key.getTag(), 0);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        VTrackerMetadataKey key = VTrackerMetadataKey.getKey(999);
        assertEquals(key, VTrackerMetadataKey.Undefined);
        assertEquals(key.getTag(), 0);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void Enum1Test() {
        VTrackerMetadataKey key = VTrackerMetadataKey.getKey(1);
        assertEquals(key, VTrackerMetadataKey.trackId);
        assertEquals(key.getTag(), 1);
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void Enum12Test() {
        VTrackerMetadataKey key = VTrackerMetadataKey.getKey(12);
        assertEquals(key, VTrackerMetadataKey.algorithmId);
        assertEquals(key.getTag(), 12);
        assertEquals(key.getIdentifier(), 12);
    }
}

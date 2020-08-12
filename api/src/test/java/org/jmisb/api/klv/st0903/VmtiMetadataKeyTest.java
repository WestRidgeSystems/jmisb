package org.jmisb.api.klv.st0903;

import static org.testng.Assert.*;

import org.jmisb.api.klv.st0903.vchip.*;
import org.testng.annotations.Test;

/** Unit tests for VmtiMetadataKey. */
public class VmtiMetadataKeyTest {

    @Test
    public void Enum0Test() {
        VmtiMetadataKey key = VmtiMetadataKey.getKey(0);
        assertEquals(key, VmtiMetadataKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        VmtiMetadataKey key = VmtiMetadataKey.getKey(999);
        assertEquals(key, VmtiMetadataKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void Enum1Test() {
        VmtiMetadataKey key = VmtiMetadataKey.getKey(1);
        assertEquals(key, VmtiMetadataKey.Checksum);
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void Enum2Test() {
        VmtiMetadataKey key = VmtiMetadataKey.getKey(2);
        assertEquals(key, VmtiMetadataKey.PrecisionTimeStamp);
        assertEquals(key.getIdentifier(), 2);
    }

    @Test
    public void Enum13Test() {
        VmtiMetadataKey key = VmtiMetadataKey.getKey(13);
        assertEquals(key, VmtiMetadataKey.MiisId);
        assertEquals(key.getIdentifier(), 13);
    }

    @Test
    public void Enum101Test() {
        VmtiMetadataKey key = VmtiMetadataKey.getKey(101);
        assertEquals(key, VmtiMetadataKey.VTargetSeries);
        assertEquals(key.getIdentifier(), 101);
    }

    @Test
    public void Enum103Test() {
        VmtiMetadataKey key = VmtiMetadataKey.getKey(103);
        assertEquals(key, VmtiMetadataKey.OntologySeries);
        assertEquals(key.getIdentifier(), 103);
    }
}

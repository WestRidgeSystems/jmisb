package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ST 1602 CompositeImagingKey. */
public class CompositeImagingKeyTest {

    @Test
    public void Enum0Test() {
        CompositeImagingKey key = CompositeImagingKey.getKey(0);
        assertEquals(key, CompositeImagingKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        CompositeImagingKey key = CompositeImagingKey.getKey(999);
        assertEquals(key, CompositeImagingKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void Enum1Test() {
        CompositeImagingKey key = CompositeImagingKey.getKey(1);
        assertEquals(key, CompositeImagingKey.PrecisionTimeStamp);
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void Enum2Test() {
        CompositeImagingKey key = CompositeImagingKey.getKey(2);
        assertEquals(key, CompositeImagingKey.DocumentVersion);
        assertEquals(key.getIdentifier(), 2);
    }

    @Test
    public void Enum18Test() {
        CompositeImagingKey key = CompositeImagingKey.getKey(18);
        assertEquals(key, CompositeImagingKey.ZOrder);
        assertEquals(key.getIdentifier(), 18);
    }
}

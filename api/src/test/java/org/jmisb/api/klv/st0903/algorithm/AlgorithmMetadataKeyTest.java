package org.jmisb.api.klv.st0903.algorithm;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for AlgorithmMetadataKey. */
public class AlgorithmMetadataKeyTest {

    @Test
    public void Enum0Test() {
        AlgorithmMetadataKey key = AlgorithmMetadataKey.getKey(0);
        assertEquals(key, AlgorithmMetadataKey.Undefined);
        assertEquals(key.getTag(), 0);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        AlgorithmMetadataKey key = AlgorithmMetadataKey.getKey(999);
        assertEquals(key, AlgorithmMetadataKey.Undefined);
        assertEquals(key.getTag(), 0);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void Enum1Test() {
        AlgorithmMetadataKey key = AlgorithmMetadataKey.getKey(1);
        assertEquals(key, AlgorithmMetadataKey.id);
        assertEquals(key.getTag(), 1);
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void Enum2Test() {
        AlgorithmMetadataKey key = AlgorithmMetadataKey.getKey(2);
        assertEquals(key, AlgorithmMetadataKey.name);
        assertEquals(key.getTag(), 2);
        assertEquals(key.getIdentifier(), 2);
    }

    @Test
    public void Enum3Test() {
        AlgorithmMetadataKey key = AlgorithmMetadataKey.getKey(3);
        assertEquals(key, AlgorithmMetadataKey.version);
        assertEquals(key.getTag(), 3);
        assertEquals(key.getIdentifier(), 3);
    }

    @Test
    public void Enum4Test() {
        AlgorithmMetadataKey key = AlgorithmMetadataKey.getKey(4);
        assertEquals(key, AlgorithmMetadataKey.algorithmClass);
        assertEquals(key.getTag(), 4);
        assertEquals(key.getIdentifier(), 4);
    }

    @Test
    public void Enum5Test() {
        AlgorithmMetadataKey key = AlgorithmMetadataKey.getKey(5);
        assertEquals(key, AlgorithmMetadataKey.nFrames);
        assertEquals(key.getTag(), 5);
        assertEquals(key.getIdentifier(), 5);
    }
}

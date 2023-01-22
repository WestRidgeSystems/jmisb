package org.jmisb.st1601;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/** Unit tests for GeoRegistrationKey. */
public class GeoRegistrationKeyTest {

    @Test
    public void Enum0Test() {
        GeoRegistrationKey key = GeoRegistrationKey.getKey(0);
        assertEquals(key, GeoRegistrationKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        GeoRegistrationKey key = GeoRegistrationKey.getKey(999);
        assertEquals(key, GeoRegistrationKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void Enum1Test() {
        GeoRegistrationKey key = GeoRegistrationKey.getKey(1);
        assertEquals(key, GeoRegistrationKey.DocumentVersion);
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void Enum2Test() {
        GeoRegistrationKey key = GeoRegistrationKey.getKey(2);
        assertEquals(key, GeoRegistrationKey.AlgorithmName);
        assertEquals(key.getIdentifier(), 2);
    }

    @Test
    public void Enum3Test() {
        GeoRegistrationKey key = GeoRegistrationKey.getKey(3);
        assertEquals(key, GeoRegistrationKey.AlgorithmVersion);
        assertEquals(key.getIdentifier(), 3);
    }

    @Test
    public void Enum4Test() {
        GeoRegistrationKey key = GeoRegistrationKey.getKey(4);
        assertEquals(key, GeoRegistrationKey.CorrespondencePointsRowColumn);
        assertEquals(key.getIdentifier(), 4);
    }

    @Test
    public void Enum5Test() {
        GeoRegistrationKey key = GeoRegistrationKey.getKey(5);
        assertEquals(key, GeoRegistrationKey.CorrespondencePointsLatLon);
        assertEquals(key.getIdentifier(), 5);
    }

    @Test
    public void Enum6Test() {
        GeoRegistrationKey key = GeoRegistrationKey.getKey(6);
        assertEquals(key, GeoRegistrationKey.SecondImageName);
        assertEquals(key.getIdentifier(), 6);
    }

    @Test
    public void Enum7Test() {
        GeoRegistrationKey key = GeoRegistrationKey.getKey(7);
        assertEquals(key, GeoRegistrationKey.AlgorithmConfigurationIdentifier);
        assertEquals(key.getIdentifier(), 7);
    }

    @Test
    public void Enum8Test() {
        GeoRegistrationKey key = GeoRegistrationKey.getKey(8);
        assertEquals(key, GeoRegistrationKey.CorrespondencePointsElevation);
        assertEquals(key.getIdentifier(), 8);
    }

    @Test
    public void Enum9Test() {
        GeoRegistrationKey key = GeoRegistrationKey.getKey(9);
        assertEquals(key, GeoRegistrationKey.CorrespondencePointsRowColumnSDCC);
        assertEquals(key.getIdentifier(), 9);
    }

    @Test
    public void Enum10Test() {
        GeoRegistrationKey key = GeoRegistrationKey.getKey(10);
        assertEquals(key, GeoRegistrationKey.CorrespondencePointsLatLonElevSDCC);
        assertEquals(key.getIdentifier(), 10);
    }
}

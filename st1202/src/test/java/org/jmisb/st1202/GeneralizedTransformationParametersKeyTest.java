package org.jmisb.st1202;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/** Unit tests for GeneralizedTransformationParametersKey. */
public class GeneralizedTransformationParametersKeyTest {

    @Test
    public void Enum0Test() {
        GeneralizedTransformationParametersKey key =
                GeneralizedTransformationParametersKey.getKey(0);
        assertEquals(key, GeneralizedTransformationParametersKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        GeneralizedTransformationParametersKey key =
                GeneralizedTransformationParametersKey.getKey(999);
        assertEquals(key, GeneralizedTransformationParametersKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void Enum1Test() {
        GeneralizedTransformationParametersKey key =
                GeneralizedTransformationParametersKey.getKey(1);
        assertEquals(key, GeneralizedTransformationParametersKey.X_Numerator_x);
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void Enum2Test() {
        GeneralizedTransformationParametersKey key =
                GeneralizedTransformationParametersKey.getKey(2);
        assertEquals(key, GeneralizedTransformationParametersKey.X_Numerator_y);
        assertEquals(key.getIdentifier(), 2);
    }

    @Test
    public void Enum3Test() {
        GeneralizedTransformationParametersKey key =
                GeneralizedTransformationParametersKey.getKey(3);
        assertEquals(key, GeneralizedTransformationParametersKey.X_Numerator_Constant);
        assertEquals(key.getIdentifier(), 3);
    }

    @Test
    public void Enum4Test() {
        GeneralizedTransformationParametersKey key =
                GeneralizedTransformationParametersKey.getKey(4);
        assertEquals(key, GeneralizedTransformationParametersKey.Y_Numerator_x);
        assertEquals(key.getIdentifier(), 4);
    }

    @Test
    public void Enum5Test() {
        GeneralizedTransformationParametersKey key =
                GeneralizedTransformationParametersKey.getKey(5);
        assertEquals(key, GeneralizedTransformationParametersKey.Y_Numerator_y);
        assertEquals(key.getIdentifier(), 5);
    }

    @Test
    public void Enum6Test() {
        GeneralizedTransformationParametersKey key =
                GeneralizedTransformationParametersKey.getKey(6);
        assertEquals(key, GeneralizedTransformationParametersKey.Y_Numerator_Constant);
        assertEquals(key.getIdentifier(), 6);
    }

    @Test
    public void Enum7Test() {
        GeneralizedTransformationParametersKey key =
                GeneralizedTransformationParametersKey.getKey(7);
        assertEquals(key, GeneralizedTransformationParametersKey.Denominator_x);
        assertEquals(key.getIdentifier(), 7);
    }

    @Test
    public void Enum8Test() {
        GeneralizedTransformationParametersKey key =
                GeneralizedTransformationParametersKey.getKey(8);
        assertEquals(key, GeneralizedTransformationParametersKey.Denominator_y);
        assertEquals(key.getIdentifier(), 8);
    }

    @Test
    public void Enum9Test() {
        GeneralizedTransformationParametersKey key =
                GeneralizedTransformationParametersKey.getKey(9);
        assertEquals(key, GeneralizedTransformationParametersKey.SDCC);
        assertEquals(key.getIdentifier(), 9);
    }

    @Test
    public void Enum10Test() {
        GeneralizedTransformationParametersKey key =
                GeneralizedTransformationParametersKey.getKey(10);
        assertEquals(key, GeneralizedTransformationParametersKey.DocumentVersion);
        assertEquals(key.getIdentifier(), 10);
    }

    @Test
    public void Enum11Test() {
        GeneralizedTransformationParametersKey key =
                GeneralizedTransformationParametersKey.getKey(11);
        assertEquals(key, GeneralizedTransformationParametersKey.TransformationEnumeration);
        assertEquals(key.getIdentifier(), 11);
    }
}

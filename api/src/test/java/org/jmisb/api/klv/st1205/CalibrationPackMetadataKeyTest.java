package org.jmisb.api.klv.st1205;

import static org.testng.Assert.*;

import org.jmisb.api.klv.st0806.*;
import org.testng.annotations.Test;

/** Unit tests for CalibrationPackMetadataKey. */
public class CalibrationPackMetadataKeyTest {

    @Test
    public void Enum0Test() {
        CalibrationPackMetadataKey key = CalibrationPackMetadataKey.getKey(0);
        assertEquals(key, CalibrationPackMetadataKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        CalibrationPackMetadataKey key = CalibrationPackMetadataKey.getKey(999);
        assertEquals(key, CalibrationPackMetadataKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void Enum1Test() {
        CalibrationPackMetadataKey key = CalibrationPackMetadataKey.getKey(1);
        assertEquals(key, CalibrationPackMetadataKey.TimeStampOfLastFrameInSequence);
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void Enum2Test() {
        CalibrationPackMetadataKey key = CalibrationPackMetadataKey.getKey(2);
        assertEquals(key, CalibrationPackMetadataKey.SequenceDuration);
        assertEquals(key.getIdentifier(), 2);
    }

    @Test
    public void Enum3Test() {
        CalibrationPackMetadataKey key = CalibrationPackMetadataKey.getKey(3);
        assertEquals(key, CalibrationPackMetadataKey.TimeStampOfCalibrationPackCreation);
        assertEquals(key.getIdentifier(), 3);
    }

    public void Enum4Test() {
        CalibrationPackMetadataKey key = CalibrationPackMetadataKey.getKey(4);
        assertEquals(key, CalibrationPackMetadataKey.CalibrationSequenceIdentifier);
        assertEquals(key.getIdentifier(), 4);
    }
}

package org.jmisb.api.klv.st1603.localset;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for TimeTransferKey. */
public class TimeTransferKeyTest {

    @Test
    public void Enum0Test() {
        TimeTransferKey key = TimeTransferKey.getKey(0);
        assertEquals(key, TimeTransferKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void EnumUnknownTest() {
        TimeTransferKey key = TimeTransferKey.getKey(999);
        assertEquals(key, TimeTransferKey.Undefined);
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void Enum1Test() {
        TimeTransferKey key = TimeTransferKey.getKey(1);
        assertEquals(key, TimeTransferKey.DocumentVersion);
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void Enum2Test() {
        TimeTransferKey key = TimeTransferKey.getKey(2);
        assertEquals(key, TimeTransferKey.UTCLeapSecondOffset);
        assertEquals(key.getIdentifier(), 2);
    }

    @Test
    public void Enum3Test() {
        TimeTransferKey key = TimeTransferKey.getKey(3);
        assertEquals(key, TimeTransferKey.TimeTransferParameters);
        assertEquals(key.getIdentifier(), 3);
    }

    public void Enum4Test() {
        TimeTransferKey key = TimeTransferKey.getKey(4);
        assertEquals(key, TimeTransferKey.SynchronizationPulseFrequency);
        assertEquals(key.getIdentifier(), 4);
    }

    public void Enum5Test() {
        TimeTransferKey key = TimeTransferKey.getKey(5);
        assertEquals(key, TimeTransferKey.UnlockTime);
        assertEquals(key.getIdentifier(), 5);
    }

    public void Enum6Test() {
        TimeTransferKey key = TimeTransferKey.getKey(6);
        assertEquals(key, TimeTransferKey.LastSynchronizationDifference);
        assertEquals(key.getIdentifier(), 6);
    }

    public void Enum7Test() {
        TimeTransferKey key = TimeTransferKey.getKey(7);
        assertEquals(key, TimeTransferKey.DriftRate);
        assertEquals(key.getIdentifier(), 7);
    }

    public void Enum8Test() {
        TimeTransferKey key = TimeTransferKey.getKey(8);
        assertEquals(key, TimeTransferKey.SignalSourceDelay);
        assertEquals(key.getIdentifier(), 8);
    }

    public void Enum9Test() {
        TimeTransferKey key = TimeTransferKey.getKey(9);
        assertEquals(key, TimeTransferKey.ReceptorClockUncertainty);
        assertEquals(key.getIdentifier(), 9);
    }
}

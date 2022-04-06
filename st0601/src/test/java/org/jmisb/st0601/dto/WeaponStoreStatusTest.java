package org.jmisb.st0601.dto;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for WeaponStoreStatus enumeration. */
public class WeaponStoreStatusTest {
    @Test
    public void testOff() {
        assertEquals(WeaponStoreStatus.Off.getCode(), (byte) 0x00);
        assertEquals(WeaponStoreStatus.getStatus((byte) 0x00), WeaponStoreStatus.Off);
    }

    @Test
    public void testUnknown() {
        assertEquals(WeaponStoreStatus.NoStatusAvailable.getCode(), (byte) 0x0B);
        assertEquals(WeaponStoreStatus.getStatus((byte) 11), WeaponStoreStatus.NoStatusAvailable);
        assertEquals(WeaponStoreStatus.getStatus((byte) 0x0B), WeaponStoreStatus.NoStatusAvailable);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOutOfRangeLow() {
        WeaponStoreStatus.getStatus((byte) -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOutOfRangeHigh() {
        WeaponStoreStatus.getStatus((byte) 0x0C);
    }
}

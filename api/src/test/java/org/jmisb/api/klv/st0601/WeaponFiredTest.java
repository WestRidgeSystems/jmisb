package org.jmisb.api.klv.st0601;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class WeaponFiredTest {
    private final byte[] ST_EXAMPLE_BYTES = new byte[] {(byte) 0xBA};

    @Test
    public void testConstructFromValue() {
        // From ST:
        WeaponFired weaponFired = new WeaponFired(11, 10);
        checkValuesForExample(weaponFired);
    }

    @Test
    public void testConstructFromEncoded() {
        WeaponFired weaponFired = new WeaponFired(ST_EXAMPLE_BYTES);
        checkValuesForExample(weaponFired);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.WeaponFired, ST_EXAMPLE_BYTES);
        assertTrue(v instanceof WeaponFired);
        WeaponFired weaponFired = (WeaponFired) v;
        checkValuesForExample(weaponFired);
    }

    private void checkValuesForExample(WeaponFired weaponFired) {
        assertEquals(weaponFired.getBytes(), ST_EXAMPLE_BYTES);
        assertEquals(weaponFired.getStationNumber(), 11);
        assertEquals(weaponFired.getSubstationNumber(), 10);
        assertEquals(weaponFired.getDisplayableValue(), "11.10");
        assertEquals(weaponFired.getDisplayName(), "Weapon Fired");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testStationTooSmall() {
        new WeaponFired(0, 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testStationTooBig() {
        new WeaponFired(16, 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSubStationTooSmall() {
        new WeaponFired(1, -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSubStationTooBig() {
        new WeaponFired(1, 16);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new WeaponFired(new byte[] {0x00, 0x00});
    }
}

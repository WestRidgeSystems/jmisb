package org.jmisb.st0601;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class WeaponLoadTest {
    private final byte[] ST_EXAMPLE_BYTES = new byte[] {(byte) 0xAF, (byte) 0xD8};

    @Test
    public void testConstructFromValue() {
        // From ST:
        WeaponLoad weaponLoad = new WeaponLoad(10, 15, 13, 8);
        checkValuesForExample(weaponLoad);
    }

    @Test
    public void testConstructFromEncoded() {
        WeaponLoad weaponLoad = new WeaponLoad(ST_EXAMPLE_BYTES);
        checkValuesForExample(weaponLoad);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.WeaponLoad, ST_EXAMPLE_BYTES);
        assertTrue(v instanceof WeaponLoad);
        WeaponLoad weaponLoad = (WeaponLoad) v;
        checkValuesForExample(weaponLoad);
    }

    private void checkValuesForExample(WeaponLoad weaponLoad) {
        assertEquals(weaponLoad.getBytes(), ST_EXAMPLE_BYTES);
        assertEquals(weaponLoad.getStationNumber(), 10);
        assertEquals(weaponLoad.getSubstationNumber(), 15);
        assertEquals(weaponLoad.getWeaponType(), 13);
        assertEquals(weaponLoad.getWeaponVariant(), 8);
        assertEquals(weaponLoad.getDisplayableValue(), "10.15: 13/8");
        assertEquals(weaponLoad.getDisplayName(), "Weapon Load");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testStationTooSmall() {
        new WeaponLoad(0, 1, 1, 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testStationTooBig() {
        new WeaponLoad(16, 1, 1, 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSubStationTooSmall() {
        new WeaponLoad(1, -1, 1, 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSubStationTooBig() {
        new WeaponLoad(1, 16, 1, 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testWeaponTypeTooSmall() {
        new WeaponLoad(1, 0, -1, 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testWeaponTypeTooBig() {
        new WeaponLoad(1, 1, 16, 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testWeaponVariantTooSmall() {
        new WeaponLoad(1, 0, 1, -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testWeaponVariantTooBig() {
        new WeaponLoad(1, 1, 1, 16);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new WeaponLoad(new byte[] {0x00, 0x00, 0x00});
    }
}

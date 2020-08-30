package org.jmisb.api.klv.st0601;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0601.dto.WeaponStore;
import org.jmisb.api.klv.st0601.dto.WeaponStoreStatus;
import org.testng.annotations.Test;

public class WeaponsStoresTest {
    private final byte[] ST_EXAMPLE_BYTES =
            new byte[] {
                (byte) 0x0E,
                (byte) 0x01,
                (byte) 0x02,
                (byte) 0x03,
                (byte) 0x01,
                (byte) 0x82,
                (byte) 0x03,
                (byte) 0x07,
                (byte) 0x48,
                (byte) 0x61,
                (byte) 0x72,
                (byte) 0x70,
                (byte) 0x6F,
                (byte) 0x6F,
                (byte) 0x6E,
                (byte) 0x0D,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x01,
                (byte) 0x82,
                (byte) 0x03,
                (byte) 0x06,
                (byte) 0x47,
                (byte) 0x42,
                (byte) 0x55,
                (byte) 0x2D,
                (byte) 0x31,
                (byte) 0x35,
                (byte) 0x0F,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x02,
                (byte) 0x88,
                (byte) 0x04,
                (byte) 0x08,
                (byte) 0x48,
                (byte) 0x65,
                (byte) 0x6C,
                (byte) 0x6C,
                (byte) 0x66,
                (byte) 0x69,
                (byte) 0x72,
                (byte) 0x65
            };

    @Test
    public void testConstructFromValue() {
        // From ST:
        List<WeaponStore> weaponsArrayList = new ArrayList<>();
        WeaponStore w1 = new WeaponStore();
        // (1, 2, 3, 1,(1, 3), "Harpoon")
        w1.setStationId(1);
        w1.setHardpointId(2);
        w1.setCarriageId(3);
        w1.setStoreId(1);
        w1.setFuzeEnabled(true);
        w1.setLaserEnabled(false);
        w1.setTargetEnabled(false);
        w1.setWeaponArmed(false);
        w1.setStatus(WeaponStoreStatus.ReadyAllUpRound);
        w1.setStoreType("Harpoon");
        weaponsArrayList.add(w1);
        // (0, 0, 0, 1,(1, 3), "GBU-15")
        WeaponStore w2 = new WeaponStore();
        w2.setStationId(0);
        w2.setHardpointId(0);
        w2.setCarriageId(0);
        w2.setStoreId(1);
        w2.setFuzeEnabled(true);
        w2.setLaserEnabled(false);
        w2.setTargetEnabled(false);
        w2.setWeaponArmed(false);
        w2.setStatus(WeaponStoreStatus.ReadyAllUpRound);
        w2.setStoreType("GBU-15");
        weaponsArrayList.add(w2);
        // (0, 0, 0, 2,(4, 4), "Hellfire")
        WeaponStore w3 = new WeaponStore();
        w3.setStationId(0);
        w3.setHardpointId(0);
        w3.setCarriageId(0);
        w3.setStoreId(2);
        w3.setFuzeEnabled(false);
        w3.setLaserEnabled(false);
        w3.setTargetEnabled(true);
        w3.setWeaponArmed(false);
        w3.setStatus(WeaponStoreStatus.Launch);
        w3.setStoreType("Hellfire");
        weaponsArrayList.add(w3);
        WeaponsStores weaponsStores = new WeaponsStores(weaponsArrayList);
        checkValuesForExample(weaponsStores);
    }

    @Test
    public void testConstructFromEncoded() throws KlvParseException {
        WeaponsStores weaponsStores = new WeaponsStores(ST_EXAMPLE_BYTES);
        checkValuesForExample(weaponsStores);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.WeaponsStores, ST_EXAMPLE_BYTES);
        assertTrue(v instanceof WeaponsStores);
        WeaponsStores weaponsStores = (WeaponsStores) v;
        checkValuesForExample(weaponsStores);
    }

    private void checkValuesForExample(WeaponsStores weaponsStores) {
        assertEquals(weaponsStores.getWeaponsStores().size(), 3);
        WeaponStore weaponStore0 = weaponsStores.getWeaponsStores().get(0);
        //  (1, 2, 3, 1,(1, 3), "Harpoon")
        assertEquals(weaponStore0.getStationId(), 1);
        assertEquals(weaponStore0.getHardpointId(), 2);
        assertEquals(weaponStore0.getCarriageId(), 3);
        assertEquals(weaponStore0.getStoreId(), 1);
        assertEquals(weaponStore0.getStatus(), WeaponStoreStatus.ReadyAllUpRound);
        assertTrue(weaponStore0.isFuzeEnabled());
        assertFalse(weaponStore0.isLaserEnabled());
        assertFalse(weaponStore0.isTargetEnabled());
        assertFalse(weaponStore0.isWeaponArmed());
        assertEquals(weaponStore0.getStoreType(), "Harpoon");
        WeaponStore weaponStore1 = weaponsStores.getWeaponsStores().get(1);
        // (0, 0, 0, 1,(1, 3), "GBU-15")
        assertEquals(weaponStore1.getStationId(), 0);
        assertEquals(weaponStore1.getHardpointId(), 0);
        assertEquals(weaponStore1.getCarriageId(), 0);
        assertEquals(weaponStore1.getStoreId(), 1);
        assertEquals(weaponStore1.getStatus(), WeaponStoreStatus.ReadyAllUpRound);
        assertTrue(weaponStore1.isFuzeEnabled());
        assertFalse(weaponStore1.isLaserEnabled());
        assertFalse(weaponStore1.isTargetEnabled());
        assertFalse(weaponStore1.isWeaponArmed());
        assertEquals(weaponStore1.getStoreType(), "GBU-15");
        WeaponStore weaponStore2 = weaponsStores.getWeaponsStores().get(2);
        // (0, 0, 0, 2,(4, 4), "Hellfire")
        assertEquals(weaponStore2.getStationId(), 0);
        assertEquals(weaponStore2.getHardpointId(), 0);
        assertEquals(weaponStore2.getCarriageId(), 0);
        assertEquals(weaponStore2.getStoreId(), 2);
        assertEquals(weaponStore2.getStatus(), WeaponStoreStatus.Launch);
        assertFalse(weaponStore2.isFuzeEnabled());
        assertFalse(weaponStore2.isLaserEnabled());
        assertTrue(weaponStore2.isTargetEnabled());
        assertFalse(weaponStore2.isWeaponArmed());
        assertEquals(weaponStore2.getStoreType(), "Hellfire");
        assertEquals(weaponsStores.getBytes(), ST_EXAMPLE_BYTES);
        assertEquals(weaponsStores.getDisplayableValue(), "[Weapons Stores List]");
        assertEquals(weaponsStores.getDisplayName(), "Weapons Stores");
    }

    @Test
    public void testConstructFromEncodedEngagementTwoFlags() throws KlvParseException {
        WeaponsStores weaponsStores =
                new WeaponsStores(
                        new byte[] {
                            (byte) 0x0E,
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x03,
                            (byte) 0x04,
                            (byte) 0x94,
                            (byte) 0x03,
                            (byte) 0x07,
                            (byte) 0x48,
                            (byte) 0x61,
                            (byte) 0x72,
                            (byte) 0x70,
                            (byte) 0x6F,
                            (byte) 0x6F,
                            (byte) 0x6E
                        });
        assertEquals(weaponsStores.getWeaponsStores().size(), 1);
        WeaponStore weaponStore0 = weaponsStores.getWeaponsStores().get(0);
        //  (1, 2, 3, 4,(1, 3), "Harpoon")
        assertEquals(weaponStore0.getStationId(), 1);
        assertEquals(weaponStore0.getHardpointId(), 2);
        assertEquals(weaponStore0.getCarriageId(), 3);
        assertEquals(weaponStore0.getStoreId(), 4);
        assertEquals(weaponStore0.getStatus(), WeaponStoreStatus.ReadyAllUpRound);
        assertFalse(weaponStore0.isFuzeEnabled());
        assertTrue(weaponStore0.isLaserEnabled());
        assertFalse(weaponStore0.isTargetEnabled());
        assertTrue(weaponStore0.isWeaponArmed());
        assertEquals(weaponStore0.getStoreType(), "Harpoon");
        assertEquals(
                weaponsStores.getBytes(),
                new byte[] {
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x94,
                    (byte) 0x03,
                    (byte) 0x07,
                    (byte) 0x48,
                    (byte) 0x61,
                    (byte) 0x72,
                    (byte) 0x70,
                    (byte) 0x6F,
                    (byte) 0x6F,
                    (byte) 0x6E
                });
    }

    @Test
    public void testConstructFromEncodedEngagementNoFlags() throws KlvParseException {
        // This checks that the "short form" status encoding works.
        WeaponsStores weaponsStores =
                new WeaponsStores(
                        new byte[] {
                            (byte) 0x0D,
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x03,
                            (byte) 0x04,
                            (byte) 0x01,
                            (byte) 0x07,
                            (byte) 0x48,
                            (byte) 0x61,
                            (byte) 0x72,
                            (byte) 0x70,
                            (byte) 0x6F,
                            (byte) 0x6F,
                            (byte) 0x6E
                        });
        assertEquals(weaponsStores.getWeaponsStores().size(), 1);
        WeaponStore weaponStore0 = weaponsStores.getWeaponsStores().get(0);
        //  (1, 2, 3, 4,(1, 1), "Harpoon")
        assertEquals(weaponStore0.getStationId(), 1);
        assertEquals(weaponStore0.getHardpointId(), 2);
        assertEquals(weaponStore0.getCarriageId(), 3);
        assertEquals(weaponStore0.getStoreId(), 4);
        assertEquals(weaponStore0.getStatus(), WeaponStoreStatus.Initialization);
        assertFalse(weaponStore0.isFuzeEnabled());
        assertFalse(weaponStore0.isLaserEnabled());
        assertFalse(weaponStore0.isTargetEnabled());
        assertFalse(weaponStore0.isWeaponArmed());
        assertEquals(weaponStore0.getStoreType(), "Harpoon");
        assertEquals(
                weaponsStores.getBytes(),
                new byte[] {
                    (byte) 0x0D,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x07,
                    (byte) 0x48,
                    (byte) 0x61,
                    (byte) 0x72,
                    (byte) 0x70,
                    (byte) 0x6F,
                    (byte) 0x6F,
                    (byte) 0x6E
                });
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fuzz1() throws KlvParseException {
        new WeaponsStores(
                new byte[] {
                    0x55,
                    0x72,
                    0x01,
                    0x71,
                    (byte) 0xe8,
                    0x72,
                    0x02,
                    0x30,
                    (byte) 0xce,
                    0x1e,
                    (byte) 0x86,
                    (byte) 0xf7
                });
    }
}

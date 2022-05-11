package org.jmisb.st0601;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.st0601.dto.WeaponStore;
import org.jmisb.st0601.dto.WeaponStoreStatus;

/**
 * Weapons Stores (ST 0601 Item 140).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * List of weapon stores and status.
 *
 * <p>The Weapons Stores is a list of Weapons Records. Each record contains Weapon Location, Weapons
 * Status, and Weapons Identity encoded as a Variable Length Pack (VLP). The Weapon Location is a
 * physical address on the platform using Station Number, Hardpoint ID, Carriage ID and Store ID.
 * The Weapon Status contains two parts: General Status and Engagement Status of the weapon.
 *
 * </blockquote>
 *
 * <p>See the WeaponStore data transfer object documentation for description of the components for a
 * specific weapon / store.
 */
public class WeaponsStores implements IUasDatalinkValue {
    private final List<WeaponStore> weaponStores = new ArrayList<>();

    /**
     * Create from value.
     *
     * @param weapons list of WeaponStore values
     */
    public WeaponsStores(List<WeaponStore> weapons) {
        this.weaponStores.addAll(weapons);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes WeaponStore list - byte array with Variable Length Pack encoding
     * @throws KlvParseException if there is a problem parsing the encoded data
     */
    public WeaponsStores(byte[] bytes) throws KlvParseException {
        int idx = 0;
        while (idx < bytes.length) {
            BerField lengthField = BerDecoder.decode(bytes, idx, false);
            idx += lengthField.getLength();
            WeaponStore weaponStore = new WeaponStore();
            BerField stationIdField = BerDecoder.decode(bytes, idx, true);
            idx += stationIdField.getLength();
            weaponStore.setStationId(stationIdField.getValue());
            BerField hardpointIdField = BerDecoder.decode(bytes, idx, true);
            idx += hardpointIdField.getLength();
            weaponStore.setHardpointId(hardpointIdField.getValue());
            BerField carriageIdField = BerDecoder.decode(bytes, idx, true);
            idx += carriageIdField.getLength();
            weaponStore.setCarriageId(carriageIdField.getValue());
            BerField storeIdField = BerDecoder.decode(bytes, idx, true);
            idx += storeIdField.getLength();
            weaponStore.setStoreId(storeIdField.getValue());
            BerField statusField = BerDecoder.decode(bytes, idx, true);
            idx += statusField.getLength();
            int statusValue = statusField.getValue();
            byte generalStatus = (byte) (statusValue & 0x7F);
            WeaponStoreStatus status = WeaponStoreStatus.getStatus(generalStatus);
            weaponStore.setStatus(status);
            weaponStore.setFuzeEnabled((statusValue & 0x0100) == 0x0100);
            weaponStore.setLaserEnabled((statusValue & 0x0200) == 0x0200);
            weaponStore.setTargetEnabled((statusValue & 0x0400) == 0x0400);
            weaponStore.setWeaponArmed((statusValue & 0x0800) == 0x0800);
            BerField typeLengthField = BerDecoder.decode(bytes, idx, false);
            idx += typeLengthField.getLength();
            int typeLength = typeLengthField.getValue();
            if ((idx + typeLength) > bytes.length) {
                throw new KlvParseException(
                        "Insufficient bytes available for specified string length");
            }
            weaponStore.setStoreType(new String(bytes, idx, typeLength, StandardCharsets.UTF_8));
            idx += typeLength;
            weaponStores.add(weaponStore);
        }
    }

    /**
     * Get the weapons / stores that make up this weapon store list.
     *
     * @return the ordered list of weapon stores.
     */
    public List<WeaponStore> getWeaponsStores() {
        return this.weaponStores;
    }

    @Override
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (WeaponStore weaponStore : getWeaponsStores()) {
            byte[] stationIdBytes = BerEncoder.encode(weaponStore.getStationId(), Ber.OID);
            byte[] hardpointIdBytes = BerEncoder.encode(weaponStore.getHardpointId(), Ber.OID);
            byte[] carriageIdBytes = BerEncoder.encode(weaponStore.getCarriageId(), Ber.OID);
            byte[] storeIdBytes = BerEncoder.encode(weaponStore.getStoreId(), Ber.OID);
            int status = weaponStore.getStatus().getCode();
            if (weaponStore.isFuzeEnabled()) {
                status += 0x0100;
            }
            if (weaponStore.isLaserEnabled()) {
                status += 0x0200;
            }
            if (weaponStore.isTargetEnabled()) {
                status += 0x0400;
            }
            if (weaponStore.isWeaponArmed()) {
                status += 0x0800;
            }
            byte[] statusBytes = BerEncoder.encode(status, Ber.OID);
            byte[] typeBytes = weaponStore.getStoreType().getBytes(StandardCharsets.UTF_8);
            byte[] typeLengthBytes = BerEncoder.encode(typeBytes.length);
            int len =
                    stationIdBytes.length
                            + hardpointIdBytes.length
                            + carriageIdBytes.length
                            + storeIdBytes.length
                            + statusBytes.length
                            + typeBytes.length
                            + typeLengthBytes.length;
            arrayBuilder.appendAsBerLength(len);
            arrayBuilder.append(stationIdBytes);
            arrayBuilder.append(hardpointIdBytes);
            arrayBuilder.append(carriageIdBytes);
            arrayBuilder.append(storeIdBytes);
            arrayBuilder.append(statusBytes);
            arrayBuilder.append(typeLengthBytes);
            arrayBuilder.append(typeBytes);
        }
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayableValue() {
        return "[Weapons Stores List]";
    }

    @Override
    public String getDisplayName() {
        return "Weapons Stores";
    }
}

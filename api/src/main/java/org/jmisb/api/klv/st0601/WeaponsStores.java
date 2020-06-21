package org.jmisb.api.klv.st0601;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0601.dto.WeaponStore;
import org.jmisb.api.klv.st0601.dto.WeaponStoreStatus;
import org.jmisb.core.klv.ArrayUtils;

/**
 * Weapons Stores (ST 0601 tag 140).
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
     * Create from value
     *
     * @param weapons list of WeaponStore values
     */
    public WeaponsStores(List<WeaponStore> weapons) {
        this.weaponStores.clear();
        this.weaponStores.addAll(weapons);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes WeaponStore list - byte array with Variable Length Pack encoding
     */
    public WeaponsStores(byte[] bytes) {
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
        List<byte[]> chunks = new ArrayList<>();
        int totalLength = 0;
        for (WeaponStore weaponStore : getWeaponsStores()) {
            int len = 0;
            byte[] stationIdBytes = BerEncoder.encode(weaponStore.getStationId(), Ber.OID);
            len += stationIdBytes.length;
            byte[] hardpointIdBytes = BerEncoder.encode(weaponStore.getHardpointId(), Ber.OID);
            len += hardpointIdBytes.length;
            byte[] carriageIdBytes = BerEncoder.encode(weaponStore.getCarriageId(), Ber.OID);
            len += carriageIdBytes.length;
            byte[] storeIdBytes = BerEncoder.encode(weaponStore.getStoreId(), Ber.OID);
            len += storeIdBytes.length;
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
            len += statusBytes.length;
            byte[] typeBytes = weaponStore.getStoreType().getBytes(StandardCharsets.UTF_8);
            len += typeBytes.length;
            byte[] typeLengthBytes = BerEncoder.encode(typeBytes.length);
            len += typeLengthBytes.length;
            byte[] lenBytes = BerEncoder.encode(len);
            chunks.add(lenBytes);
            chunks.add(stationIdBytes);
            chunks.add(hardpointIdBytes);
            chunks.add(carriageIdBytes);
            chunks.add(storeIdBytes);
            chunks.add(statusBytes);
            chunks.add(typeLengthBytes);
            chunks.add(typeBytes);
            totalLength += len;
            totalLength += lenBytes.length;
        }
        return ArrayUtils.arrayFromChunks(chunks, totalLength);
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

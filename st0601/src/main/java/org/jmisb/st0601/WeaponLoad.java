package org.jmisb.st0601;

/**
 * Weapon Load (ST 0601 Item 60).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Current weapons stored on aircraft.
 *
 * <p>Note: the Weapon Stores (Item 140) replaces the Weapon Load (Item 60) and Weapon Fired (Item
 * 61) for providing information about Weapons and their status.
 *
 * <p>The Weapon Load item is composed of two bytes: the first byte indicates the aircraft store
 * location, and the second byte indicates the store type. Each byte is composed of two nibbles with
 * [nib1] being the most significant nibble with bit order [3210] where 3=msb.
 *
 * <p>Aircraft store location is indicated by station number which starts its numbering at the
 * outboard left wing as store location 1 and increases towards the outboard right wing. Each
 * station can have a different weapon installed, or multiple weapons on the same station. For
 * multiple weapons per station, the substation number begins at 1. A substation number of 0
 * indicates a single store located at the station. The aircraft store location byte has two
 * nibbles: the first most significant nibble indicates Station Number; the second nibble the
 * Substation Number.
 *
 * <p>The weapon type byte is also composed of two nibbles: the first most significant nibble
 * indicates Weapon Type; the second nibble indicates Weapon Variant. A list of available weapons is
 * undefined.
 *
 * </blockquote>
 */
public class WeaponLoad implements IUasDatalinkValue {
    private final int stationNumber;
    private final int substationNumber;
    private final int weaponType;
    private final int weaponVariant;
    /**
     * Create from value.
     *
     * @param stationNumber the station number, in the range 1..15.
     * @param substationNumber the substation number, in the range 0..15
     * @param weaponType the weapon type, in the range 0..15.
     * @param weaponVariant the weapon variant, in the range 0..15
     */
    public WeaponLoad(int stationNumber, int substationNumber, int weaponType, int weaponVariant) {
        if ((stationNumber < 1) || (stationNumber > 15)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " station number must be in the range [1, 15]");
        }
        if ((substationNumber < 0) || (substationNumber > 15)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " sub-station number must be in the range [0, 15]");
        }
        if ((weaponType < 0) || (weaponType > 15)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " weapon type must be in the range [0, 15]");
        }
        if ((weaponVariant < 0) || (weaponVariant > 15)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " weapon variant must be in the range [0, 15]");
        }
        this.stationNumber = stationNumber;
        this.substationNumber = substationNumber;
        this.weaponType = weaponType;
        this.weaponVariant = weaponVariant;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 2
     */
    public WeaponLoad(byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is a 2-byte array");
        }
        byte msb = bytes[0];
        substationNumber = msb & 0x0F;
        stationNumber = (msb & 0xF0) >> 4;
        byte lsb = bytes[1];
        weaponVariant = lsb & 0x0F;
        weaponType = (lsb & 0xF0) >> 4;
    }

    @Override
    public byte[] getBytes() {
        byte msb = (byte) ((stationNumber << 4) + substationNumber);
        byte lsb = (byte) ((weaponType << 4) + weaponVariant);
        return new byte[] {msb, lsb};
    }

    @Override
    public String getDisplayableValue() {
        return String.format(
                "%d.%d: %d/%d", stationNumber, substationNumber, weaponType, weaponVariant);
    }

    @Override
    public final String getDisplayName() {
        return "Weapon Load";
    }

    /**
     * Get the store station number.
     *
     * @return integer value, where 1 is the left-most pylon.
     */
    public int getStationNumber() {
        return stationNumber;
    }

    /**
     * Get the store substation number.
     *
     * @return integer value, in the range [0..15], where 0 means no-substation.
     */
    public int getSubstationNumber() {
        return substationNumber;
    }

    /**
     * Get the weapon type number.
     *
     * @return weapon type number, in the range [0..15]
     */
    public int getWeaponType() {
        return weaponType;
    }

    /**
     * Get the weapon variant number.
     *
     * @return weapon variant number, in the range [0..15]
     */
    public int getWeaponVariant() {
        return weaponVariant;
    }
}

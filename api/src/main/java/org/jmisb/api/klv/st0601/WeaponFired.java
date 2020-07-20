package org.jmisb.api.klv.st0601;

/**
 * Weapon Fired (ST 0601 Item 61).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Indication when a particular weapon is released.
 *
 * <p>Note: the Weapon Stores (Item 140) replaces the Weapon Load (Item 60) and Weapon Fired (Item
 * 61) for providing information about Weapons and their status.
 *
 * <p>The Weapon Fired metadata item has the same format as the first byte of the Weapon Load
 * metadata item indicating station and substation location of a store. Byte 1 is composed of two
 * nibbles with [nib1] being the most significant nibble with bit order [3210] where 3=msb.
 *
 * <p>When included in a KLV packet, correlate the Weapon Fired item with the mandatory timestamp to
 * determine the release time of a weapon.
 *
 * </blockquote>
 */
public class WeaponFired implements IUasDatalinkValue {
    private int stationNumber;
    private int substationNumber;

    /**
     * Create from value.
     *
     * @param stationNumber the station number, in the range 1..15.
     * @param substationNumber the substation number, in the range 0..15
     */
    public WeaponFired(int stationNumber, int substationNumber) {
        if ((stationNumber < 1) || (stationNumber > 15)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " station number must be in the range [1, 15]");
        }
        if ((substationNumber < 0) || (substationNumber > 15)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " sub-station number must be in the range [0, 15]");
        }
        this.stationNumber = stationNumber;
        this.substationNumber = substationNumber;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 1
     */
    public WeaponFired(byte[] bytes) {
        if (bytes.length != 1) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is a 1-byte array");
        }
        byte b = bytes[0];
        substationNumber = b & 0x0F;
        stationNumber = (b & 0xF0) >> 4;
        ;
    }

    @Override
    public byte[] getBytes() {
        byte b = (byte) ((stationNumber << 4) + substationNumber);
        return new byte[] {b};
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d.%d", stationNumber, substationNumber);
    }

    @Override
    public final String getDisplayName() {
        return "Weapon Fired";
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
}

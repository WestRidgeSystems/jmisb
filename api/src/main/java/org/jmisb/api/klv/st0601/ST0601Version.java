package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * UAS Datalink LS Version Number (ST 0601 tag 65).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Version number of the UAS LS document used to generate a source of UAS LS KLV metadata. 0 is
 * pre-release, initial release (0601.0), or test data. 1..255 corresponds to document revisions ST
 * 0601.1 through ST 0601.255.
 *
 * </blockquote>
 */
public class ST0601Version implements IUasDatalinkValue {
    private short version;

    /**
     * Create from value.
     *
     * @param version The version number, in the range 0 to 255.
     */
    public ST0601Version(short version) {
        if ((version < 0) || (version > 255)) {
            throw new IllegalArgumentException("Version Number valid range is [0,255]");
        }
        this.version = version;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 1
     */
    public ST0601Version(byte[] bytes) {
        if (bytes.length != 1) {
            throw new IllegalArgumentException("Version Number encoding is a single unsigned byte");
        }
        version = (short) PrimitiveConverter.toUint8(bytes);
    }

    /**
     * Get the version number.
     *
     * @return The version number
     */
    public short getVersion() {
        return version;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint8ToBytes(version);
    }

    @Override
    public String getDisplayableValue() {
        return "" + version;
    }

    @Override
    public String getDisplayName() {
        return "Version Number";
    }
}

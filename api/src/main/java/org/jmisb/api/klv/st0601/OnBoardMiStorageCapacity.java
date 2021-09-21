package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * On-Board MI Storage Capacity (ST 0601 Item 133).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Total capacity of on-board Motion Imagery storage.
 *
 * <p>KLV format: uint, Min: 0, Max: (2^32)-1
 *
 * <p>Length: variable, Max Length: 4, Required Length: N/A
 *
 * <p>Resolution: 1 Gigabyte
 *
 * </blockquote>
 */
public class OnBoardMiStorageCapacity implements IUasDatalinkValue {
    private final long gigabytes;
    private static final long MIN_VAL = 0;
    private static final long MAX_VAL = 4294967295L; // 2^32-1
    private static final int MAX_BYTES = 4;

    /**
     * Create from value.
     *
     * @param gigabytes Capacity in gigabytes. Legal values are in [0,2^31-1].
     */
    public OnBoardMiStorageCapacity(long gigabytes) {
        if (gigabytes > MAX_VAL || gigabytes < MIN_VAL) {
            throw new IllegalArgumentException("Capacity must be in range [0,2^32-1]");
        }
        this.gigabytes = gigabytes;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array, maximum four bytes
     */
    public OnBoardMiStorageCapacity(byte[] bytes) {
        if (bytes.length > MAX_BYTES) {
            throw new IllegalArgumentException("Storage capacity field length must be 1 - 4 bytes");
        }
        this.gigabytes = PrimitiveConverter.variableBytesToUint32(bytes);
    }

    /**
     * Get the capacity.
     *
     * @return The capacity, in gigabytes
     */
    public long getGigabytes() {
        return gigabytes;
    }

    @Override
    public byte[] getBytes() {
        if (gigabytes > 65535) {
            return PrimitiveConverter.uint32ToBytes(gigabytes);
        } else if (gigabytes > 255) {
            return PrimitiveConverter.uint16ToBytes((int) gigabytes);
        } else {
            return PrimitiveConverter.uint8ToBytes((short) gigabytes);
        }
    }

    @Override
    public String getDisplayableValue() {
        return gigabytes + "GB";
    }

    @Override
    public String getDisplayName() {
        return "On-Board MI Storage Capacity";
    }
}

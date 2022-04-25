package org.jmisb.st0102;

import java.util.Arrays;

/**
 * Security Metadata item designator ID (ST 0102 tag 21).
 *
 * <p>This was historically used to link specific security markings to particular metadata elements,
 * sets and packs by identifying the universal key associated with the value, set or pack.
 *
 * <p>As of ST0102.12, this is no longer accepted practice. The current MISB position is:
 *
 * <blockquote>
 *
 * a transport stream or file is governed by one overall classification; there is no allowance for
 * individual markings of elementary streams, groups of metadata, or individual metadata elements.
 *
 * </blockquote>
 *
 * <p>ItemDesignatorId should not be used in newly generated files, and is not valid as of
 * ST0102.12. It is provided for backwards compatibility only. As such, there is no value-based
 * constructor for this class.
 */
public class ItemDesignatorId implements ISecurityMetadataValue {
    private final byte[] itemDesignatorId;

    /**
     * Create from encoded bytes or value.
     *
     * @param bytes Byte array of length 16
     */
    public ItemDesignatorId(byte[] bytes) {
        if (bytes.length != 16) {
            throw new IllegalArgumentException("Item Designator Id encoding is a 16 byte array");
        }
        itemDesignatorId = bytes.clone();
    }

    /**
     * Get the item designator id.
     *
     * @return The id as an array
     */
    public byte[] getItemDesignatorId() {
        return getBytes();
    }

    @Override
    public byte[] getBytes() {
        return itemDesignatorId.clone();
    }

    @Override
    public String getDisplayableValue() {
        return Arrays.toString(this.itemDesignatorId);
    }

    @Override
    public String getDisplayName() {
        return "Item Designator Id";
    }
}

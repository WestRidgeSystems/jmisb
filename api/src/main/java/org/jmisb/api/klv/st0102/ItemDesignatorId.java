package org.jmisb.api.klv.st0102;

import java.util.Arrays;

/**
 * Security Metadata item designator ID (ST 0102 tag 21)
 */
public class ItemDesignatorId implements ISecurityMetadataValue
{
    private byte[] itemDesignatorId;

    /**
     * Create from encoded bytes or value
     * @param bytes Byte array of length 16
     */
    public ItemDesignatorId(byte[] bytes)
    {
        if (bytes.length != 16)
        {
            throw new IllegalArgumentException("Item Designator Id encoding is a 16 byte array");
        }
        itemDesignatorId = bytes.clone();
    }

    /**
     * Get the item designator id
     * @return The id as an array
     */
    public byte[] getItemDesignatorId()
    {
        return getBytes();
    }

    @Override
    public byte[] getBytes()
    {
        return itemDesignatorId.clone();
    }

    @Override
    public String getDisplayableValue()
    {
        return Arrays.toString(this.itemDesignatorId);
    }
}

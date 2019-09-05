package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * On-Board MI Storage Capacity (ST 0601 tag 133)
 * <p>
 * From ST:
 * <blockquote>
 * Total capacity of on-board Motion Imagery storage.
 * <p>
 * KLV format: uint, Min: 0, Max: (2^32)-1
 * <p>
 * Length: variable, Max Length: 4, Required Length: N/A
 * <p>
 * Resolution: 1 Gigabyte
 * </blockquote>
 */
public class OnBoardMiStorageCapacity implements IUasDatalinkValue
{
    private long gigabytes;
    private static long MIN_VAL = 0;
    private static long MAX_VAL = 4294967295L; // 2^32-1

    /**
     * Create from value
     * @param gigabytes Capacity in gigabytes. Legal values are in [0,2^31-1].
     */
    public OnBoardMiStorageCapacity(long gigabytes)
    {
        if (gigabytes > MAX_VAL || gigabytes < MIN_VAL)
        {
            throw new IllegalArgumentException("Capacity must be in range [0,2^32-1]");
        }
        this.gigabytes = gigabytes;
    }

    /**
     * Create from encoded bytes
     * @param bytes Byte array of length 1, 2, or 4
     */
    public OnBoardMiStorageCapacity(byte[] bytes)
    {
        if (bytes.length == 4)
        {
            this.gigabytes = PrimitiveConverter.toUint32(bytes);
        }
        else if (bytes.length == 2)
        {
            this.gigabytes = PrimitiveConverter.toUint16(bytes);
        }
        else if (bytes.length == 1)
        {
            this.gigabytes = PrimitiveConverter.toUint8(bytes);
        }
        else
        {
            throw new IllegalArgumentException("Storage capacity field length must be 1, 2, or 4 bytes");
        }
    }

    /**
     * Get the capacity
     * @return The capacity, in gigabytes
     */
    public long getGigabytes()
    {
        return gigabytes;
    }

    @Override
    public byte[] getBytes()
    {
        if (gigabytes > 65535)
        {
            return PrimitiveConverter.uint32ToBytes(gigabytes);
        }
        else if (gigabytes > 255)
        {
            return PrimitiveConverter.uint16ToBytes((int)gigabytes);
        }
        else
        {
            return PrimitiveConverter.uint8ToBytes((short)gigabytes);
        }
    }

    @Override
    public String getDisplayableValue()
    {
        return gigabytes + "GB";
    }
}

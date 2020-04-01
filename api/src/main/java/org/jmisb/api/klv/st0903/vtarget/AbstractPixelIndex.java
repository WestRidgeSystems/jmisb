package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Shared pixel index (row column) implementation.
 * 
 * Used by Tag 19 and Tag 20.
 */
public abstract class AbstractPixelIndex implements IVmtiMetadataValue
{
    protected static long MIN_VAL = 1;
    protected static long MAX_VAL = 4294967295L;
    protected long value;

    /**
     * Create from value.
     *
     * @param index the pixel index (min 1, max 2^32-1)
     */
    public AbstractPixelIndex(long index)
    {
        if (index < MIN_VAL || index > MAX_VAL)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [1, 4294967295]");
        }
        this.value = index;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public AbstractPixelIndex(byte[] bytes)
    {
        value = PrimitiveConverter.variableBytesToUint32(bytes);
    }

    @Override
    public byte[] getBytes()
    {
        // TODO: move this to PrimitiveConverter once the PRs settle down.
        if (value < 256)
        {
            return PrimitiveConverter.uint8ToBytes((short)value);
        }
        else if (value < 65536)
        {  
            return PrimitiveConverter.uint16ToBytes((int)value);
        }

        byte[] bytes = PrimitiveConverter.uint32ToBytes(value);
        if (bytes[0] == 0x00)
        {
            return new byte[]{bytes[1], bytes[2], bytes[3]};
        }
        else
        {
            return bytes;
        }
    }

    @Override
    public String getDisplayableValue()
    {
        return "" + value;
    }

    /**
     * Get the value.
     *
     * @return the value in pixels (1 base)
     */
    public long getValue()
    {
        return this.value;
    }
}

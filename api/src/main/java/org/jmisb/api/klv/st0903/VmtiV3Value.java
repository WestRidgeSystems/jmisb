package org.jmisb.api.klv.st0903;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Shared implementation of the VMTI (ST0903) types that can hold unsigned integer variable length values, three bytes maximum.
 */
public abstract class VmtiV3Value implements IVmtiMetadataValue
{
    protected int value;
    private static int MIN_VALUE = 0;
    private static int MAX_VALUE = 16777215;

    public VmtiV3Value(final int intValue)
    {
        if (intValue < MIN_VALUE || intValue > MAX_VALUE)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " value must be in range [0,16777215]");
        }

        this.value = intValue;
    }
    
    public VmtiV3Value(final byte[] bytes)
    {
        if (bytes.length > 3) {
            throw new IllegalArgumentException(this.getDisplayName() + " encoding is maximum three byte unsigned integer");
        }
        parse(bytes);
    }

    protected final void parse(byte[] bytes) {
        value = 0;
        for (int i = 0; i < bytes.length; ++i) {
            value = value << 8;
            value += ((int) bytes[i] & 0xFF);
        }
    }
    
    /**
     * Get the value.
     *
     * @return the value as an unsigned integer.
     */
    public int getValue()
    {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        if (value < 256)
        {
            return PrimitiveConverter.uint8ToBytes((short)value);
        } else if (value < 65536)
        {  
            return PrimitiveConverter.uint16ToBytes(value);
        }
        byte[] bytes = PrimitiveConverter.uint32ToBytes(value);
        return new byte[]{bytes[1], bytes[2], bytes[3]};
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", value);
    }
    
}

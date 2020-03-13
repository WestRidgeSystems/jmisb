package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Shared implementation of variable length pixel.
 */
public abstract class PixelNumber implements IVmtiMetadataValue
{

    private long pixelNumber;

    public PixelNumber(long num)
    {
        pixelNumber = num;
    }

    public PixelNumber(byte[] bytes)
    {
        if (bytes.length > 7)
        {
            throw new IllegalArgumentException("Pixel number encoding is up to 6 bytes");
        }
        pixelNumber = 0;
        for (int i = 0; i < bytes.length; ++i)
        {
            pixelNumber = pixelNumber << 8;
            pixelNumber += ((int) bytes[i] & 0xFF);
        }
    }

    @Override
    public byte[] getBytes()
    {
        return PrimitiveConverter.int64ToBytes(pixelNumber);
    }

    @Override
    public String getDisplayableValue()
    {
        return String.format("%l", pixelNumber);
    }
    
    public long getPixelNumber()
    {
        return pixelNumber;
    }

}

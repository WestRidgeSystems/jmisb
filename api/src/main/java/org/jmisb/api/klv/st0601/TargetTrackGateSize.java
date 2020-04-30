package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Target Track Gate Size (used by ST 0601 tag 43 and 44)
 * <p>
 * Map 0..(2^8-1) to 0..510 pixels.
 * <p>
 * Resolution: 2 pixels.
 */
abstract public class TargetTrackGateSize implements IUasDatalinkValue
{
    private short pixels;
    private static short MIN_VALUE = 0;
    private static short MAX_VALUE = 510;

    /**
     * Create from value
     *
     * @param px Size in pixels. Legal values are in [0, 510].
     */
    public TargetTrackGateSize(final short px)
    {
        if (px > MAX_VALUE || px < MIN_VALUE)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [0,510]");
        }
        pixels = px;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 1
     */
    public TargetTrackGateSize(byte[] bytes)
    {
        if (bytes.length != 1)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " encoding is a 1-byte unsigned int");
        }

        pixels = (short)(PrimitiveConverter.toUint8(bytes) * 2);
    }

    /**
     * Get the size in pixels
     *
     * @return The size in pixels
     */
    public double getPixels()
    {
        return pixels;
    }

    @Override
    public byte[] getBytes()
    {
        short intVal = (short) (pixels / 2);
        return PrimitiveConverter.uint8ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue()
    {
        return String.format("%d px", pixels);
    }
}

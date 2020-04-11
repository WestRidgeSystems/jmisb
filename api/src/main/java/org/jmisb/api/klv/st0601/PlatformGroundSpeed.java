package org.jmisb.api.klv.st0601;

/**
 * Platform Ground Speed (ST 0601 tag 56)
 * <p>
 * From ST:
 * <blockquote>
 * Speed projected to the ground of an airborne platform passing overhead.
 * <p>
 * Map 0..(2^8-1) to 0..255 meters/second.
 * <p>
 * Resolution: 1 metre/second.
 * </blockquote>
 */
public class PlatformGroundSpeed extends UasDatalinkSpeed implements IUasDatalinkValue
{
    /**
     * Create from value
     *
     * @param speed Ground speed in meters/second. Legal values are in [0, 255].
     */
    public PlatformGroundSpeed(int speed)
    {
        super(speed);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 1
     */
    public PlatformGroundSpeed(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public String getDisplayName()
    {
        return "Platform Ground Speed";
    }
}

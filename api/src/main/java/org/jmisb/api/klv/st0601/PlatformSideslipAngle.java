package org.jmisb.api.klv.st0601;

/**
 * Platform Sideslip Angle (ST 0601 tag 52)
 * <p>
 * From ST:
 * <blockquote>
 * Angle between the platform longitudinal axis and relative wind.
 * Positive angles to right wing, neg to left.
 * <p>
 * Map (-2^15-1)..(2^15-1) to +/-20. Use -2^15 as an "out of range" indicator.
 * -2^15 = 0x8000.
 * <p>
 * Resolution: ~610 micro degrees
 * </blockquote>
 */
public class PlatformSideslipAngle extends UasDatalinkAngle
{
    /**
     * Create from value
     *
     * @param degrees The value in degrees, or {@code Double.POSITIVE_INFINITY}
     *                to represent an error condition
     */
    public PlatformSideslipAngle(double degrees)
    {
        super(degrees);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 2
     */
    public PlatformSideslipAngle(byte[] bytes)
    {
        super(bytes);
    }
}

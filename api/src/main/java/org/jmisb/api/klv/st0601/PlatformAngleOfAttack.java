package org.jmisb.api.klv.st0601;

/**
 * Platform Angle of Attack (ST 0601 Item 50).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Platform attack angle. Angle between platform longitudinal axis and relative wind. Positive
 * angles for upward relative wind
 *
 * <p>Map (-2^15-1)..(2^15-1) to +/-20. Use -2^15 as an "out of range" indicator. -2^15 = 0x8000.
 *
 * <p>Resolution: ~610 micro degrees
 *
 * </blockquote>
 */
public class PlatformAngleOfAttack extends UasDatalinkAngle {

    /**
     * Create from value
     *
     * @param degrees The value in degrees, or {@code Double.POSITIVE_INFINITY} to represent an
     *     error condition
     */
    public PlatformAngleOfAttack(double degrees) {
        super(degrees);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 2
     */
    public PlatformAngleOfAttack(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Platform Angle of Attack";
    }
}

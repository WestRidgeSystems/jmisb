package org.jmisb.api.klv.st0601;

/**
 * Platform Pitch Angle (ST 0601 Item 6).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Aircraft pitch angle. Angle between longitudinal axis and horizontal plane. Positive angles are
 * above horizontal plane.
 *
 * <p>Map (-2^15-1)..(2^15-1) to +/-20. Use -2^15 as an "out of range" indicator. -2^15 = 0x8000.
 *
 * <p>Resolution: ~610 micro degrees
 *
 * </blockquote>
 */
public class PlatformPitchAngle extends UasDatalinkAngle {

    /**
     * Create from value.
     *
     * @param degrees The value in degrees, or {@code Double.POSITIVE_INFINITY} to represent an
     *     error condition
     */
    public PlatformPitchAngle(double degrees) {
        super(degrees);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 2
     */
    public PlatformPitchAngle(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Platform Pitch Angle";
    }
}

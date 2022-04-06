package org.jmisb.st0601;

/**
 * Platform Heading Angle (ST 0601 Item 5).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Aircraft heading angle. Relative between longitudinal axis and True North measured in the
 * horizontal plane.
 *
 * <p>Map 0..(2^16-1) to 0..360
 *
 * <p>Resolution: ~5.5 milli degrees
 *
 * </blockquote>
 */
public class PlatformHeadingAngle extends UasDatalinkAngle360 {

    /**
     * Create from value.
     *
     * @param degrees Angle in degrees, in range [0, 360]
     */
    public PlatformHeadingAngle(double degrees) {
        super(degrees);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 2
     */
    public PlatformHeadingAngle(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Platform Heading Angle";
    }
}

package org.jmisb.api.klv.st0601;

/**
 * Platform Magnetic Heading (ST 0601 tag 64)
 * <p>
 * From ST:
 * <blockquote>
 * Relative between longitudinal axis and Magnetic North measured in the horizontal plane.
 * <p>
 * Map 0..(2^16-1) to 0..360
 * <p>
 * Resolution: ~5.5 milli degrees
 * </blockquote>
 */
public class PlatformMagneticHeading extends UasDatalinkAngle360
{
    /**
     * Create from value
     * @param degrees angle, in degrees
     */
    public PlatformMagneticHeading(double degrees) {
        super(degrees);
    }

    /**
     * Create from encoded bytes
     * @param bytes Encoded byte array
     */
    public PlatformMagneticHeading(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Platform Magnetic Heading";
    }
}

package org.jmisb.api.klv.st0601;

/**
 * Alternate Platform Heading (ST 0601 Item 71).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Heading angle of alternate platform connected to UAS. Heading angle is defined as the angle
 * between the alternate platform longitudinal axis (line made by the fuselage) and true north
 * measured in the horizontal plane. Angles increase in a clockwise direction when looking from
 * above the platform. North is 0 degrees, east is 90, south is 180, and west is 270 degrees from
 * true north.
 *
 * <p>The alternate platform is an airborne or ground based platform connected via direct datalink
 * to a UAS generating Motion Imagery and metadata.
 *
 * <p>Map 0..(2^16-1) to 0..360
 *
 * <p>Resolution: ~5.5 milli degrees
 *
 * </blockquote>
 */
public class AlternatePlatformHeading extends UasDatalinkAngle360 {
    /**
     * Create from value
     *
     * @param degrees angle, in degrees
     */
    public AlternatePlatformHeading(double degrees) {
        super(degrees);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes Encoded byte array
     */
    public AlternatePlatformHeading(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Alternate Platform Heading";
    }
}

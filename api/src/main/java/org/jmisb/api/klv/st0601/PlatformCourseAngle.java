package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;

/**
 * Platform Course Angle (ST 0601 Item 112).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Direction the aircraft is moving relative to True North.
 *
 * <p>Resolution: 2 bytes = 16.625 milli-degrees
 *
 * <p>Length is variable based on users desired accuracy.
 *
 * <p>0 (or 360) is true north, east is 90, south is 180, west is 270
 *
 * <p>The Platform Course is the direction the platform is moving (not necessarily the direction the
 * platform is pointing).
 *
 * <p>Platform Course is directly measurable by on-board navigation or estimated computationally by
 * comparing the last known position to current position.
 *
 * </blockquote>
 */
public class PlatformCourseAngle implements IUasDatalinkValue {

    private static double MIN_VAL = 0.0;
    private static double MAX_VAL = 360.0;
    private static int RECOMMENDED_BYTES = 2;
    private static int MAX_BYTES = 8;
    private double angle;

    /**
     * Create from value.
     *
     * @param angle the course angle in degrees. Valid range is [0, 360]
     */
    public PlatformCourseAngle(double angle) {
        if (angle < MIN_VAL || angle > MAX_VAL) {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [0,360]");
        }
        this.angle = angle;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes IMAPB Encoded byte array
     */
    public PlatformCourseAngle(byte[] bytes) {
        if (bytes.length > MAX_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " cannot be longer than " + MAX_BYTES + " bytes");
        }
        FpEncoder decoder =
                new FpEncoder(MIN_VAL, MAX_VAL, bytes.length, OutOfRangeBehaviour.Default);
        this.angle = decoder.decode(bytes);
    }

    /**
     * Get the platform course angle.
     *
     * @return The platform course angle in degrees
     */
    public double getAngle() {
        return this.angle;
    }

    @Override
    public byte[] getBytes() {
        FpEncoder encoder =
                new FpEncoder(MIN_VAL, MAX_VAL, RECOMMENDED_BYTES, OutOfRangeBehaviour.Default);
        return encoder.encode(this.angle);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.3f\u00B0", this.angle);
    }

    @Override
    public final String getDisplayName() {
        return "Platform Course Angle";
    }
}

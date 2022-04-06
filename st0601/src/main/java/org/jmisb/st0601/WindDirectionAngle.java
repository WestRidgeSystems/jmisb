package org.jmisb.st0601;

/**
 * Wind Direction (ST 0601 Item 35).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Wind Direction. The direction the air body around the aircraft is coming from relative to true
 * north
 *
 * <p>Map 0..(2^16-1) to 0..360
 *
 * <p>Resolution: ~5.5 milli degrees
 *
 * </blockquote>
 */
public class WindDirectionAngle extends UasDatalinkAngle360 implements IUasDatalinkValue {
    /**
     * Create from value.
     *
     * @param degrees wind direction, in degrees
     */
    public WindDirectionAngle(double degrees) {
        super(degrees);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 2
     */
    public WindDirectionAngle(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Wind Direction";
    }
}

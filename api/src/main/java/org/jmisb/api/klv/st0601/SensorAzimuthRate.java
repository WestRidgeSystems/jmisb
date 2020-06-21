package org.jmisb.api.klv.st0601;

/**
 * Sensor Azimuth Rate (ST0601 Tag 117)
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * The rate the sensors azimuth angle is changing.
 *
 * <p>Resolution: 2 bytes = 0.0625 degrees/second, 3 bytes = 0.000244 degrees/second
 *
 * <p>Uses the same orientation as Sensor Relative Azimuth Angle (Tag 18) Refer to Tag 18's diagram:
 * From above the aircraft looking down, when the sensor is moving clockwise the rate is positive
 * and negative when its moving counter-clockwise.
 *
 * </blockquote>
 */
public class SensorAzimuthRate extends SensorAngleRate {
    /**
     * Create from value
     *
     * @param rate Sensor angle rate in degrees per second. Valid range is [-1000,1000]
     */
    public SensorAzimuthRate(double rate) {
        super(rate);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes IMAPB Encoded byte array, 4 bytes maximum
     */
    public SensorAzimuthRate(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Sensor Azimuth Rate";
    }
}

package org.jmisb.api.klv.st0601;

/**
 * Sensor Elevation Rate (ST0601 Tag 118)
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * The rate the sensors elevation angle is changing.
 *
 * <p>Resolution: 2 bytes = 0.0625 degrees/second, 3 bytes = 0.000244 degrees/second
 *
 * <p>Uses the same orientation as Sensor Relative Elevation Angle (Tag 19). Refer to Tag 19's
 * diagram: From the side view of the aircraft shown, when the sensor is moving clockwise the rate
 * is positive and negative when its moving counter-clockwise.
 *
 * </blockquote>
 */
public class SensorElevationRate extends SensorAngleRate {
    /**
     * Create from value
     *
     * @param rate Sensor angle rate in degrees per second. Valid range is [-1000,1000]
     */
    public SensorElevationRate(double rate) {
        super(rate);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes IMAPB Encoded byte array, 4 bytes maximum
     */
    public SensorElevationRate(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Sensor Elevation Rate";
    }
}

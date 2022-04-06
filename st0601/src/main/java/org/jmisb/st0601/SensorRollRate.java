package org.jmisb.st0601;

/**
 * Sensor Roll Rate (ST0601 Item 119).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * The rate the sensors roll angle is changing.
 *
 * <p>Resolution: 2 bytes = 0.0625 degrees/second, 3 bytes = 0.000244 degrees/second
 *
 * <p>Uses the same orientation as Sensor Relative Roll Angle (Item 20). Refer to Item 20's
 * description: From behind the sensor, when the sensor is moving clockwise the rate is positive and
 * negative when its moving counter-clockwise.
 *
 * </blockquote>
 */
public class SensorRollRate extends SensorAngleRate {
    /**
     * Create from value.
     *
     * @param rate Sensor angle rate in degrees per second. Valid range is [-1000,1000]
     */
    public SensorRollRate(double rate) {
        super(rate);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes IMAPB Encoded byte array, 4 bytes maximum
     */
    public SensorRollRate(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Sensor Roll Rate";
    }
}

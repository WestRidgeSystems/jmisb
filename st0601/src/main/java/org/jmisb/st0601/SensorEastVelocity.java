package org.jmisb.st0601;

/**
 * Sensor East Velocity (ST 0601 Item 80).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Easting velocity of the sensor or platform. Sensor movement rate in the east direction. Positive
 * towards East.
 *
 * <p>Map (-2^15-1)..(2^15-1) to +/-327 m/sec. Use -2^15 as an "out of range" indicator. -2^15 =
 * 0x8000.
 *
 * <p>Resolution: ~1 cm/sec
 *
 * </blockquote>
 */
public class SensorEastVelocity extends UasDatalinkSensorVelocity {
    /**
     * Create from value.
     *
     * @param velocity The value in m/sec, or {@code Double.POSITIVE_INFINITY} to represent an error
     *     condition
     */
    public SensorEastVelocity(double velocity) {
        super(velocity);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 2
     */
    public SensorEastVelocity(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Sensor East Velocity";
    }
}

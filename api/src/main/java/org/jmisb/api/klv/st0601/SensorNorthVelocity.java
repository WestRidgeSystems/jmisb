package org.jmisb.api.klv.st0601;

/**
 * Sensor North Velocity (ST 0601 tag 79)
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Northing velocity of the sensor or platform. Sensor movement rate in the north direction.
 * Positive towards True North
 *
 * <p>Map (-2^15-1)..(2^15-1) to +/-327 m/sec. Use -2^15 as an "out of range" indicator. -2^15 =
 * 0x8000.
 *
 * <p>Resolution: ~1 cm/sec
 *
 * </blockquote>
 */
public class SensorNorthVelocity extends UasDatalinkSensorVelocity {
    public SensorNorthVelocity(double velocity) {
        super(velocity);
    }

    public SensorNorthVelocity(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Sensor North Velocity";
    }
}

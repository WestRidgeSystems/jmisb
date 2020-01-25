package org.jmisb.api.klv.st0601;

/**
 * Sensor East Velocity (ST 0601 tag 80)
 * <p>
 * From ST:
 * <blockquote>
 * Easting velocity of the sensor or platform.
 * Sensor movement rate in the east direction. Positive towards East.
 * <p>
 * Map (-2^15-1)..(2^15-1) to +/-327 m/sec. Use -2^15 as an "out of range" indicator.
 * -2^15 = 0x8000.
 * <p>
 * Resolution: ~1 cm/sec
 * </blockquote>
 */
public class SensorEastVelocity extends UasDatalinkSensorVelocity
{
    public SensorEastVelocity(double velocity)
    {
        super(velocity);
    }

    public SensorEastVelocity(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public String getDisplayName()
    {
        return "Sensor East Velocity";
    }
}

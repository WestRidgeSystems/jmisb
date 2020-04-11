package org.jmisb.api.klv.st0601;

/**
 * Sensor True Altitude (ST 0601 tag 15)
 * <p>
 * From ST:
 * <blockquote>
 * Altitude of sensor as measured from Mean Sea Level (MSL).
 * <p>
 * Map 0..(2^16-1) to -900..19000 meters.
 * <p>
 * Resolution: ~0.3 meters.
 * </blockquote>
 */
public class SensorTrueAltitude extends UasDatalinkAltitude
{
    /**
     * Create from value
     * @param meters Altitude in meters. Legal values are in [-900,19000].
     */
    public SensorTrueAltitude(double meters)
    {
        super(meters);
    }

    /**
     * Create from encoded bytes
     * @param bytes The byte array of length 2
     */
    public SensorTrueAltitude(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public String getDisplayName()
    {
        return "Sensor True Altitude";
    }
}

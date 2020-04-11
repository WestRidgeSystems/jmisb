package org.jmisb.api.klv.st0601;

/**
 * Altitude AGL (ST 0601 tag 113).
 * <p>
 * From ST:
 * <blockquote>
 * Above Ground Level (AGL) height above the ground/water.
 * <p>
 * Max Altitude: 40,000m for airborne systems
 * <p>
 * Resolution: 2 bytes = 2.0 meters, 3 bytes = 0.7 cm
 * <p>
 * Altitude - AGL (Above Ground Level) is the distance measured from the ground
 * (or terrain) to the aircraft.
 * </blockquote>
 */
public class AltitudeAGL extends UasDatalinkAltitudeExtended
{
    /**
     * Create from value
     *
     * @param meters Altitude in meters. Valid range is [-900,40000]
     */
    public AltitudeAGL(double meters)
    {
        super(meters);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes IMAPB Encoded byte array, 4 bytes maximum
     */
    public AltitudeAGL(byte[] bytes)
    {
        super(bytes, 4);
    }

    @Override
    public String getDisplayName()
    {
        return "Altitude AGL";
    }

}

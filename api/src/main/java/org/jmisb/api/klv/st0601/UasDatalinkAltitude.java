package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Abstract base class for altitude values in ST 0601
 * <p>
 * Used by tags: 15, 25, 38, 42, 69, 78. Note that some derived types use MSL, others HAE.
 * <blockquote>
 * Map 0..(2^16-1) to -900..19000 meters.
 * <p>
 * Resolution: ~0.3 meters.
 * </blockquote>
 */
public abstract class UasDatalinkAltitude implements IUasDatalinkValue
{
    private double meters;
    private static double MIN_VALUE = -900;
    private static double MAX_VALUE = 19000;
    private static double RANGE = 19900;
    private static double MAXINT = 65535.0; // 2^16 - 1
    public static double DELTA = 0.15; // +/- 0.15 meters

    /**
     * Create from value
     * @param meters Altitude in meters. Legal values are in [-900,19000].
     */
    public UasDatalinkAltitude(double meters)
    {
        if (meters > MAX_VALUE || meters < MIN_VALUE)
        {
            throw new IllegalArgumentException("Altitude must be in range [-900,19000]");
        }
        this.meters = meters;
    }

    /**
     * Create from encoded bytes
     * @param bytes The byte array of length 2
     */
    public UasDatalinkAltitude(byte[] bytes)
    {
        if (bytes.length != 2)
        {
            throw new IllegalArgumentException("Altitude encoding is a 2-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint16(bytes);
        meters = ((intVal / MAXINT) * RANGE) + MIN_VALUE;
    }

    /**
     * Get the altitude
     * @return The altitude in meters
     */
    public double getMeters()
    {
        return meters;
    }

    @Override
    public byte[] getBytes()
    {
        int intVal = (int) Math.round(((meters - MIN_VALUE) / RANGE) * MAXINT);
        return PrimitiveConverter.uint16ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue()
    {
        return String.format("%.1fm", meters);
    }
}

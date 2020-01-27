package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Relative Humidity (ST 0601 tag 55)
 * <p>
 * From ST:
 * <blockquote>
 * Relative humidity at aircraft location. Relative Humidity is the ratio between
 * the water vapor density and the saturation point of water vapor density
 * expressed as a percentage.
 * <p>
 * Map 0..(2^8-1) to 0..100%.
 * <p>
 * Resolution: ~0.4%.
 * </blockquote>
 */
public class RelativeHumidity implements IUasDatalinkValue
{
    private double relativeHumidity;
    private static double MIN_VALUE = 0;
    private static double MAX_VALUE = 100;
    private static double RANGE = 100;
    private static double MAXINT = 255.0; // 2^8 - 1

    /**
     * Create from value
     *
     * @param humidity relative humidity as a percentage. Legal values are in [0, 100].
     */
    public RelativeHumidity(double humidity)
    {
        if (humidity > MAX_VALUE || humidity < MIN_VALUE)
        {
            throw new IllegalArgumentException("Relative humidity must be in range [0,100]");
        }
        relativeHumidity = humidity;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 1
     */
    public RelativeHumidity(byte[] bytes)
    {
        if (bytes.length != 1)
        {
            throw new IllegalArgumentException("Relative humidity encoding is a 1-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint8(bytes);
        relativeHumidity = ((intVal / MAXINT) * RANGE) + MIN_VALUE;
    }

    /**
     * Get the relative humidity
     *
     * @return The relative humidity as a percentage
     */
    public double getRelativeHumidity()
    {
        return relativeHumidity;
    }

    @Override
    public byte[] getBytes()
    {
        short intVal = (short) Math.round(((relativeHumidity - MIN_VALUE) / RANGE) * MAXINT);
        return PrimitiveConverter.uint8ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue()
    {
        return String.format("%.1f%%", relativeHumidity);
    }

    @Override
    public String getDisplayName()
    {
        return "Relative Humidity";
    }
}

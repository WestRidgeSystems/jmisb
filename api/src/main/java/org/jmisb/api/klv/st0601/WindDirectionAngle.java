package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Wind Direction (ST 0601 tag 35)
 * <p>
 * From ST:
 * <blockquote>
 * Wind Direction. The direction the air body around the aircraft is coming from relative to true north
 * <p>
 * Map 0..(2^16-1) to 0..360
 * <p>
 * Resolution: ~5.5 milli degrees
 * </blockquote>
 */
public class WindDirectionAngle implements IUasDatalinkValue
{
    private double degrees;
    private static double RANGE = 360.0;
    private static double MAXINT = 65535.0; // 2^16 - 1

    /**
     * Create from value
     * @param degrees wind direction, in degrees
     */
    public WindDirectionAngle(double degrees)
    {
        if (degrees < 0 || degrees > 360)
        {
            throw new IllegalArgumentException("Wind Direction must be in range [0,360]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes
     * @param bytes Encoded byte array
     */
    public WindDirectionAngle(byte[] bytes)
    {
        if (bytes.length != 2)
        {
            throw new IllegalArgumentException("Wind Direction encoding is a 2-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint16(bytes);
        this.degrees = (intVal / MAXINT) * RANGE;
    }

    /**
     * Get the value in degrees
     * @return wind direction, in degrees
     */
    public double getDegrees()
    {
        return degrees;
    }

    @Override
    public byte[] getBytes()
    {
        int intVal = (int) Math.round((degrees / RANGE) * MAXINT);
        return PrimitiveConverter.uint16ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue()
    {
        return String.format("%.4f\u00B0", degrees);
    }
}

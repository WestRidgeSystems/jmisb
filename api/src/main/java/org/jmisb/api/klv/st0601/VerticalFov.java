package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Sensor Vertical field of view (ST 0601 tag 17)
 * <p>
 * From ST:
 * <blockquote>
 * Vertical field of view of selected imaging sensor.
 * <p>
 * Map 0..(2^16-1) to 0..180.
 * <p>
 * Resolution: ~2.7 milli degrees.
 * </blockquote>
 */
public class VerticalFov implements UasDatalinkValue
{
    private double degrees;
    private static double MIN_VALUE = 0;
    private static double MAX_VALUE = 180;
    private static double RANGE = 180;
    private static double MAXINT = 65535.0; // 2^16 - 1

    /**
     * Create from value
     * @param degrees Sensor Vertical field of view, in degrees. Legal values are in [0,180].
     */
    public VerticalFov(double degrees)
    {
        if (degrees < MIN_VALUE || degrees > MAX_VALUE)
        {
            throw new IllegalArgumentException("Vertical field of view must be in range [0,180]");
        }
        this.degrees = degrees;

    }

    /**
     * Create from encoded bytes
     * @param bytes The byte array of length 2
     */
    public VerticalFov(byte[] bytes)
    {
        if (bytes.length != 2)
        {
            throw new IllegalArgumentException("Vertical field of view encoding is a 2-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint16(bytes);
        degrees = ((intVal / MAXINT) * RANGE);
    }

    /**
     * Get the vertical field of view
     * @return The vertical field of view, in degrees
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
}

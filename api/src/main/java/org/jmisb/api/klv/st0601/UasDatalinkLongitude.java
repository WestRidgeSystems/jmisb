package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

import java.util.Arrays;

/**
 * Abstract base class for longitude values in ST 0601
 * <p>
 * Used by tags: 14, 24, 41, 68, 83, 85, 87, 89
 * <blockquote>
 * Map -(2^31-1)..(2^31-1) to +/-180. Use -(2^31) as an "error" indicator.
 * -(2^31) = 0x80000000.
 * <p>
 * Resolution: ~84 nano degrees.
 * </blockquote>
 */
public abstract class UasDatalinkLongitude implements IUasDatalinkValue
{
    private double degrees;
    private static byte[] invalidBytes = new byte[]{(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    private static double FLOAT_RANGE = 180.0;
    private static double MAX_INT = 2147483647.0;
    public static double DELTA = 42e-9; // +/- 42 nano degrees

    /**
     * Create from value
     *
     * @param degrees Longitude, in degrees [-180,180], or {@code Double.POSITIVE_INFINITY}
     *                to represent an error condition
     */
    public UasDatalinkLongitude(double degrees)
    {
        if (degrees != Double.POSITIVE_INFINITY && (degrees < -180 || degrees > 180))
        {
            throw new IllegalArgumentException("Longitude must be in range [-180,180]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes Longitude, encoded as a 4-byte int
     */
    public UasDatalinkLongitude(byte[] bytes)
    {
        if (bytes.length != 4)
        {
            throw new IllegalArgumentException("Longitude encoding is a 4-byte int");
        }

        if (Arrays.equals(bytes, invalidBytes))
        {
            degrees = Double.POSITIVE_INFINITY;
        } else
        {
            int intVal = PrimitiveConverter.toInt32(bytes);
            this.degrees = (intVal / MAX_INT) * FLOAT_RANGE;
        }
    }

    /**
     * Get the longitude in degrees
     *
     * @return Degrees in range [-180,180], or Double.POSITIVE_INFINITY if error condition was specified.
     */
    public double getDegrees()
    {
        return degrees;
    }

    @Override
    public byte[] getBytes()
    {
        if (degrees == Double.POSITIVE_INFINITY)
        {
            return invalidBytes;
        }

        int intVal = (int) Math.round((degrees / FLOAT_RANGE) * MAX_INT);
        return PrimitiveConverter.int32ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue()
    {
        return "" + degrees;
    }
}

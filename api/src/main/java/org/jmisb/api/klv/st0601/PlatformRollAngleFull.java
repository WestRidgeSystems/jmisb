package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

import java.util.Arrays;

/**
 * Platform Roll Angle (Full) (ST 0601 tag 91)
 * <p>
 * From ST:
 * <blockquote>
 * Platform roll angle. Angle between transverse axis and transverse-longitudinal plane.
 * Positive angles for lowered right wing.
 * <p>
 * Map -(2^31-1)..(2^31-1) to +/-90. Use -(2^31) = 0x80000000 as an "error" indicator.
 * <p>
 * Resolution: ~42 nano degrees
 * </blockquote>
 */
public class PlatformRollAngleFull implements IUasDatalinkValue
{
    private double degrees;
    private static byte[] invalidBytes = new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00};
    private static double FLOAT_RANGE = 90.0;
    private static double INT_RANGE = 2147483647.0; // 2^31-1

    /**
     * Create from value
     * @param degrees The value in degrees, or {@code Double.POSITIVE_INFINITY} to represent an error condition
     */
    public PlatformRollAngleFull(double degrees)
    {
        if (degrees != Double.POSITIVE_INFINITY && (degrees < -90 || degrees > 90))
        {
            throw new IllegalArgumentException("Platform Roll Angle (Full) must be in range [-90,90]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes
     * @param bytes The byte array of length 4
     */
    public PlatformRollAngleFull(byte[] bytes)
    {
        if (bytes.length != 4)
        {
            throw new IllegalArgumentException("Platform Roll Angle (Full) encoding is a 4-byte signed int");
        }

        if (Arrays.equals(bytes, invalidBytes))
        {
            degrees = Double.POSITIVE_INFINITY;
        }
        else
        {
            int intVal = PrimitiveConverter.toInt32(bytes);
            this.degrees = (intVal / INT_RANGE) * FLOAT_RANGE;
        }
    }

    /**
     * Get the value in degrees
     * @return The value in degrees, or {@code Double.POSITIVE_INFINITY} to indicate an error condition
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

        int intVal = (int) Math.round((degrees / FLOAT_RANGE) * INT_RANGE);
        return PrimitiveConverter.int32ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue()
    {
        return "" + degrees;
    }
}

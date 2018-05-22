package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

import java.util.Arrays;

/**
 * Platform Roll Angle (ST 0601 tag 7)
 * <p>
 * From ST:
 * <blockquote>
 * Platform roll angle. Angle between transverse axis and transverse-longitudinal plane.
 * Positive angles for lowered right wing.
 * <p>
 * Map (-2^15-1)..(2^15-1) to +/-50. Use -(2^15) as "out of range" indicator. -(2^15) = 0x8000.
 * <p>
 * Resolution: ~1525 micro degrees.
 * </blockquote>
 */
public class PlatformRollAngle implements UasDatalinkValue
{
    private double degrees;
    private static byte[] invalidBytes = new byte[]{(byte)0x80, (byte)0x00};
    private static double FLOAT_RANGE = 100.0;
    private static double INT_RANGE = 65534.0;

    /**
     * Create from value
     * @param degrees The value in degrees, or {@code Double.POSITIVE_INFINITY} to represent "out of range"
     */
    public PlatformRollAngle(double degrees)
    {
        if (degrees != Double.POSITIVE_INFINITY && (degrees < -50 || degrees > 50))
        {
            throw new IllegalArgumentException("Platform Roll Angle must be in range [-50,50]");
        }

        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes
     * @param bytes The byte array of length 2
     */
    public PlatformRollAngle(byte[] bytes)
    {
        if (bytes.length != 2)
        {
            throw new IllegalArgumentException("Platform Roll Angle encoding is a 2-byte signed int");
        }

        if (Arrays.equals(bytes, invalidBytes))
        {
            degrees = Double.POSITIVE_INFINITY;
        }
        else
        {
            int intVal = PrimitiveConverter.toInt16(bytes);
            this.degrees = (intVal / INT_RANGE) * FLOAT_RANGE;
        }
    }

    /**
     * Get the value in degrees
     * @return The value in degrees, or {@code Double.POSITIVE_INFINITY} if "out of range"
     */
    public double getDegrees()
    {
        return degrees;
    }

    @Override
    public byte[] getBytes()
    {
        short shortVal = (short) Math.round((degrees / FLOAT_RANGE) * INT_RANGE);
        return PrimitiveConverter.int16ToBytes(shortVal);
    }
}

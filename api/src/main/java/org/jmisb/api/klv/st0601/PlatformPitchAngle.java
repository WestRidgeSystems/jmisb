package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

import java.util.Arrays;

/**
 * Platform Pitch Angle (ST 0601 tag 6)
 * <p>
 * From ST:
 * <blockquote>
 * Aircraft pitch angle. Angle between longitudinal axis and horizontal plane.
 * Positive angles are above horizontal plane.
 * <p>
 * Map (-2^15-1)..(2^15-1) to +/-20. Use -2^15 as an "out of range" indicator.
 * -2^15 = 0x8000.
 * <p>
 * Resolution: ~610 micro degrees
 * </blockquote>
 */
public class PlatformPitchAngle implements IUasDatalinkValue
{
    private double degrees;
    private static byte[] invalidBytes = new byte[]{(byte)0x80, (byte)0x00};
    private static double FLOAT_RANGE = 40.0;
    private static double INT_RANGE = 65534.0; // 2^15-1

    /**
     * Create from value
     * @param degrees The value in degrees, or {@code Double.POSITIVE_INFINITY} to represent an error condition
     */
    public PlatformPitchAngle(double degrees)
    {
        if (degrees != Double.POSITIVE_INFINITY && (degrees < -20 || degrees > 20))
        {
            throw new IllegalArgumentException("Platform Pitch Angle must be in range [-20,20]");
        }

        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes
     * @param bytes The byte array of length 2
     */
    public PlatformPitchAngle(byte[] bytes)
    {
        if (bytes.length != 2)
        {
            throw new IllegalArgumentException("Platform Pitch Angle encoding is a 2-byte signed int");
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

        short shortVal = (short) Math.round((degrees / FLOAT_RANGE) * INT_RANGE);
        return PrimitiveConverter.int16ToBytes(shortVal);
    }

    @Override
    public String getDisplayableValue()
    {
        return String.format("%.4f\u00B0", degrees);
    }
}

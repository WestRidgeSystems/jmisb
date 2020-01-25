package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Platform Heading Angle (ST 0601 tag 5)
 * <p>
 * From ST:
 * <blockquote>
 * Aircraft heading angle. Relative between longitudinal axis and True North measured
 * in the horizontal plane.
 * <p>
 * Map 0..(2^16-1) to 0..360
 * <p>
 * Resolution: ~5.5 milli degrees
 * </blockquote>
 */
public class PlatformHeadingAngle implements IUasDatalinkValue
{
    private double degrees;
    private static double RANGE = 360.0;
    private static double MAXINT = 65535.0; // 2^16 - 1

    /**
     * Create from value
     * @param degrees Platform heading, in degrees
     */
    public PlatformHeadingAngle(double degrees)
    {
        if (degrees < 0 || degrees > 360)
        {
            throw new IllegalArgumentException("Platform Heading Angle must be in range [0,360]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes
     * @param bytes Encoded byte array
     */
    public PlatformHeadingAngle(byte[] bytes)
    {
        if (bytes.length != 2)
        {
            throw new IllegalArgumentException("Platform Heading Angle encoding is a 2-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint16(bytes);
        this.degrees = (intVal / MAXINT) * RANGE;
    }

    /**
     * Get the value in degrees
     * @return Platform heading, in degrees
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

    @Override
    public String getDisplayName() {
        return "Platform Heading Angle";
    }
}

package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Slant Range (ST 0601 tag 21)
 * <p>
 * From ST:
 * <blockquote>
 * Slant range in meters. Distance to target.
 * <p>
 * Map 0..(2^32-1) to 0..5000000 meters.
 * <p>
 * Resolution: ~1.2 milli meters.
 * </blockquote>
 */
public class SlantRange implements IUasDatalinkValue
{
    private double meters;
    private static double MIN_VAL = 0.0;
    private static double MAX_VAL = 5000000.0;
    private static double MAXINT = 4294967295.0; // 2^32-1
    public static double DELTA = 0.6e-3; // +/- 0.6 mm

    /**
     * Create from value
     * @param meters Slant range, in meters
     */
    public SlantRange(double meters)
    {
        if (meters < MIN_VAL || meters > MAX_VAL)
        {
            throw new IllegalArgumentException("Slant range must be in range [0,5000000]");
        }
        this.meters = meters;
    }

    /**
     * Create from encoded bytes
     * @param bytes Slant range, encoded as a 4-byte unsigned int
     */
    public SlantRange(byte[] bytes)
    {
        if (bytes.length != 4)
        {
            throw new IllegalArgumentException("Slant range encoding is a 4-byte unsigned int");
        }
        long longVal = PrimitiveConverter.toUint32(bytes);
        this.meters = (longVal / MAXINT) * MAX_VAL;

    }

    /**
     * Get the value in meters
     * @return Meters in range [0,5000000]
     */
    public double getMeters()
    {
        return meters;
    }

    @Override
    public byte[] getBytes()
    {
        long longVal = Math.round((meters / MAX_VAL) * MAXINT);
        return PrimitiveConverter.uint32ToBytes(longVal);
    }

    @Override
    public String getDisplayableValue()
    {
        return "" + meters;
    }
}

package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Shared Range - used by ST 0601 tag 21 (Slant Range) and 57 (Ground Range)
 * <p>
 * From ST:
 * <blockquote>
 * Map 0..(2^32-1) to 0..5000000 meters.
 * <p>
 * Resolution: ~1.2 milli meters.
 * </blockquote>
 */
public abstract class UasRange implements IUasDatalinkValue
{

    protected static double MIN_VAL = 0.0;
    protected static double MAX_VAL = 5000000.0;
    protected static double MAXINT = 4294967295.0; // 2^32-1
    public static double DELTA = 0.6e-3; // +/- 0.6 mm
    protected double meters;

    /**
     * Create from value
     * @param meters Range, in meters
     */
    public UasRange(double meters)
    {
        if (meters < MIN_VAL || meters > MAX_VAL)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [0,5000000]");
        }
        this.meters = meters;
    }

    /**
     * Create from encoded bytes
     * @param bytes Range, encoded as a 4-byte unsigned int
     */
    public UasRange(byte[] bytes)
    {
        if (bytes.length != 4)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " encoding is a 4-byte unsigned int");
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
        return String.format("%.3fm", meters);
    }
}

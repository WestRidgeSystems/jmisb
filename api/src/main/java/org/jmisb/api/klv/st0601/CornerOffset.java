package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

import java.util.Arrays;

/**
 * Corner Offset (ST 0601 tags 26-33)
 * <p>
 * Tags 26-33 encode the corner locations of the sensor footprint as offsets from the frame center (tag 23/24).
 * These values use only two bytes each, so their resolution is limited (~0.25m at equator). For higher precision,
 * see tags 82-89, Corner Latitude/Longitude Points (Full).
 * <p>
 * From ST:
 * <blockquote>
 * Frame Latitude/Longitude, offset for X corner. Based on WGS84 ellipsoid. Use with Frame Center Latitude/Longitude.
 * <p>
 * Map (-2^15-1)..(2^15-1) to +/-0.075. Use -2^15 as an "error" indicator. -2^15 = 0x8000.
 * <p>
 * Resolution: ~1.2 micro degrees, ~0.25 meters at equator.
 * </blockquote>
 */
public class CornerOffset implements IUasDatalinkValue
{
    private double degrees;
    private static byte[] invalidBytes = new byte[]{(byte)0x80, (byte)0x00};
    private static double FLOAT_RANGE = 0.15;
    private static double INT_RANGE = 65534.0; // 2^15-1

    /**
     * Create from value
     * @param degrees The value in degrees, in range [-0.075,0.075]
     */
    public CornerOffset(double degrees)
    {
        if (degrees != Double.POSITIVE_INFINITY && (degrees < -0.075 || degrees > 0.075))
        {
            throw new IllegalArgumentException("Corner Offset must be in range [-0.075,0.075]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes
     * @param bytes The byte array of length 2
     */
    public CornerOffset(byte[] bytes)
    {
        if (bytes.length != 2)
        {
            throw new IllegalArgumentException("Corner Offset encoding is a 2-byte signed int");
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
        return "" + degrees;
    }
}

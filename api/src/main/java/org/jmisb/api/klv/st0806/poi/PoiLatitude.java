package org.jmisb.api.klv.st0806.poi;

import org.jmisb.core.klv.PrimitiveConverter;

import java.util.Arrays;

/**
 * POI Latitude (ST 0806 POI tag 2)
 * <blockquote>
 * Map -(2^31-1)..(2^31-1) to +/-90. Use -(2^31) as an "error" indicator.
 * -(2^31) = 0x80000000.
 * <p>
 * Resolution: ~42 nano degrees.
 * <p>
 * Required when sending a POI.
 * </blockquote>
 */
public class PoiLatitude implements IRvtPoiMetadataValue
{
    private double degrees;
    private static final byte[] INVALID_BYTES = new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00};
    private static final double FLOAT_RANGE = 90.0;
    private static final double MAX_INT = 2147483647.0;

    /**
     * Create from value.
     *
     * @param degrees Latitude, in degrees [-90,90], or
     * {@code Double.POSITIVE_INFINITY} to represent an error condition
     */
    public PoiLatitude(double degrees)
    {
        if (degrees != Double.POSITIVE_INFINITY && (degrees < -90 || degrees > 90))
        {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [-90,90]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Latitude, encoded as a 4-byte int
     */
    public PoiLatitude(byte[] bytes)
    {
        if (bytes.length != 4)
        {
            throw new IllegalArgumentException(getDisplayName() + " encoding is a 4-byte int");
        }
        if (Arrays.equals(bytes, INVALID_BYTES))
        {
            degrees = Double.POSITIVE_INFINITY;
        }
        else
        {
            int intVal = PrimitiveConverter.toInt32(bytes);
            this.degrees = (intVal / MAX_INT) * FLOAT_RANGE;
        }
    }

    /**
     * Get the latitude in degrees
     *
     * @return Latitude, in range [-90,90], or Double.POSITIVE_INFINITY if error
     * condition was specified.
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
            return INVALID_BYTES.clone();
        }

        int intVal = (int) Math.round((degrees / FLOAT_RANGE) * MAX_INT);
        return PrimitiveConverter.int32ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue()
    {
        return String.format("%.4f\u00B0", degrees);
    }

    @Override
    public final String getDisplayName()
    {
        return "POI Latitude";
    }
}

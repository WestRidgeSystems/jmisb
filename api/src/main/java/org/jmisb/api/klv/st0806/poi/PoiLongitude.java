package org.jmisb.api.klv.st0806.poi;

import org.jmisb.api.klv.st0601.*;
import org.jmisb.core.klv.PrimitiveConverter;

import java.util.Arrays;

/**
 * POI Longitude (ST 0806 POI tag 3)
 *
 * <blockquote>
 * Map -(2^31-1)..(2^31-1) to +/-180. Use -(2^31) as an "error" indicator.
 * -(2^31) = 0x80000000.
 * <p>
 * Resolution: ~84 nano degrees.
 * <p>
 * Required when sending a POI.
 * </blockquote>
 */
public class PoiLongitude implements IRvtPoiMetadataValue
{
    private double degrees;
    private static final byte[] INVALID_BYTES = new byte[]{(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    private static final double FLOAT_RANGE = 180.0;
    private static final double MAX_INT = 2147483647.0;

    /**
     * Create from value.
     *
     * @param degrees Longitude, in degrees [-180,180], or
     * {@code Double.POSITIVE_INFINITY} to represent an error condition
     */
    public PoiLongitude(double degrees)
    {
        if (degrees != Double.POSITIVE_INFINITY && (degrees < -180 || degrees > 180))
        {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [-180,180]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Longitude, encoded as a 4-byte int
     */
    public PoiLongitude(byte[] bytes)
    {
        if (bytes.length != 4)
        {
            throw new IllegalArgumentException(getDisplayName() + " encoding is a 4-byte int");
        }

        if (Arrays.equals(bytes, INVALID_BYTES))
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
        return "POI Longitude";
    }
}

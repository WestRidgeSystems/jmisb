package org.jmisb.api.klv.st0806.poiaoi;

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
public class PoiLatitude extends AbstractRvtPoiAoiLatitude
{

    /**
     * Create from value.
     *
     * @param degrees Latitude, in degrees [-90,90], or
     * {@code Double.POSITIVE_INFINITY} to represent an error condition
     */
    public PoiLatitude(double degrees)
    {
        super(degrees);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Latitude, encoded as a 4-byte int
     */
    public PoiLatitude(byte[] bytes)
    {
        super(bytes);
    }


    @Override
    public final String getDisplayName()
    {
        return "POI Latitude";
    }
}

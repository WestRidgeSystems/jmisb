package org.jmisb.api.klv.st0806.poiaoi;

/**
 * Corner Latitude Point 3 (ST 0806 AOI tag 4).
 *
 * <blockquote>
 * SE corner of AOI.
 * <p>
 * Map -(2^31-1)..(2^31-1) to +/-90. Use -(2^31) as an "error" indicator.
 * -(2^31) = 0x80000000.
 * <p>
 * Resolution: ~42 nano degrees.
 * <p>
 * Required when sending an AOI.
 * </blockquote>
 * <p>
 * This is the lower right corner latitude.
 */
public class CornerLatitudePoint3 extends AbstractRvtPoiAoiLatitude
{

    /**
     * Create from value.
     *
     * @param degrees Latitude, in degrees [-90,90], or
     * {@code Double.POSITIVE_INFINITY} to represent an error condition
     */
    public CornerLatitudePoint3(double degrees)
    {
        super(degrees);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Latitude, encoded as a 4-byte integer
     */
    public CornerLatitudePoint3(byte[] bytes)
    {
        super(bytes);
    }


    @Override
    public final String getDisplayName()
    {
        return "Corner Latitude Point 3";
    }
}

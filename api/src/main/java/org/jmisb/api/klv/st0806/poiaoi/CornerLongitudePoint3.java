package org.jmisb.api.klv.st0806.poiaoi;

/**
 * Corner Longitude Point 3 (ST 0806 AOI tag 5)
 *
 * <blockquote>
 * SE corner of AOI.
 * <p>
 * Map -(2^31-1)..(2^31-1) to +/-180. Use -(2^31) as an "error" indicator.
 * -(2^31) = 0x80000000.
 * <p>
 * Resolution: ~84 nano degrees.
 * <p>
 * Required when sending an AOI.
 * </blockquote>
 * <p>
 * This is the lower right corner longitude.
 */
public class CornerLongitudePoint3 extends AbstractRvtPoiAoiLongitude
{
    /**
     * Create from value.
     *
     * @param degrees Longitude, in degrees [-180,180], or
     * {@code Double.POSITIVE_INFINITY} to represent an error condition
     */
    public CornerLongitudePoint3(double degrees)
    {
        super(degrees);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Longitude, encoded as a 4-byte integer
     */
    public CornerLongitudePoint3(byte[] bytes)
    {
        super(bytes);
    }


    @Override
    public final String getDisplayName()
    {
        return "Corner Longitude Point 3";
    }
}

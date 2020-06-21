package org.jmisb.api.klv.st0806.poiaoi;

/**
 * POI Longitude (ST 0806 POI tag 3)
 *
 * <blockquote>
 *
 * Map -(2^31-1)..(2^31-1) to +/-180. Use -(2^31) as an "error" indicator. -(2^31) = 0x80000000.
 *
 * <p>Resolution: ~84 nano degrees.
 *
 * <p>Required when sending a POI.
 *
 * </blockquote>
 */
public class PoiLongitude extends AbstractRvtPoiAoiLongitude {

    /**
     * Create from value.
     *
     * @param degrees Longitude, in degrees [-180,180], or {@code Double.POSITIVE_INFINITY} to
     *     represent an error condition
     */
    public PoiLongitude(double degrees) {
        super(degrees);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Longitude, encoded as a 4-byte integer
     */
    public PoiLongitude(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "POI Longitude";
    }
}

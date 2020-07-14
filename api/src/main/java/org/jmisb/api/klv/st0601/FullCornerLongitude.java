package org.jmisb.api.klv.st0601;

/**
 * Corner Longitude Point (ST 0601 Items 83/85/87/89).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Frame longitude for image corner. Full Range. Based on WGS84 ellipsoid.
 *
 * <p>Map -(2^31-1)..(2^31-1) to +/-180. Use -(2^31) as an "error" indicator. -(2^31) = 0x80000000.
 *
 * <p>Resolution: ~84 nano degrees.
 *
 * </blockquote>
 */
public class FullCornerLongitude extends UasDatalinkLongitude {
    /** Display name for first corner longitude. Point 1 is the upper left corner. */
    public static final String CORNER_LON_1 = "Corner Longitude Point 1 (Full)";
    /** Display name for second corner longitude. Point 2 is the upper right corner. */
    public static final String CORNER_LON_2 = "Corner Longitude Point 2 (Full)";
    /** Display name for third corner longitude. Point 3 is the lower right corner. */
    public static final String CORNER_LON_3 = "Corner Longitude Point 3 (Full)";
    /** Display name for fourth corner longitude. Point 4 is the lower left corner. */
    public static final String CORNER_LON_4 = "Corner Longitude Point 4 (Full)";

    private final String displayName;

    /**
     * Create from value
     *
     * @param degrees Longitude, in degrees [-180,180], or {@code Double.POSITIVE_INFINITY} to
     *     represent an error condition
     * @param displayName human readable (display) name for this type - see static values
     */
    public FullCornerLongitude(double degrees, String displayName) {
        super(degrees);
        this.displayName = displayName;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes Longitude, encoded as a 4-byte int
     * @param displayName human readable (display) name for this type - see static values
     */
    public FullCornerLongitude(byte[] bytes, String displayName) {
        super(bytes);
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}

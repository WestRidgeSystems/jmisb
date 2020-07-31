package org.jmisb.api.klv.st0601;

/**
 * Corner Latitude Point (ST 0601 Items 82/84/86/88).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Frame latitude for image corner. Full Range. Based on WGS84 ellipsoid.
 *
 * <p>Map -(2^31-1)..(2^31-1) to +/-90. Use -(2^31) as an "error" indicator. -(2^31) = 0x80000000.
 *
 * <p>Resolution: ~42 nano degrees.
 *
 * </blockquote>
 */
public class FullCornerLatitude extends UasDatalinkLatitude {
    /** Display name for first corner latitude. Point 1 is the upper left corner. */
    public static final String CORNER_LAT_1 = "Corner Latitude Point 1 (Full)";
    /** Display name for second corner latitude. Point 2 is the upper right corner. */
    public static final String CORNER_LAT_2 = "Corner Latitude Point 2 (Full)";
    /** Display name for third corner latitude. Point 3 is the lower right corner. */
    public static final String CORNER_LAT_3 = "Corner Latitude Point 3 (Full)";
    /** Display name for fourth corner latitude. Point 4 is the lower left corner. */
    public static final String CORNER_LAT_4 = "Corner Latitude Point 4 (Full)";

    private final String displayName;

    /**
     * Create from value.
     *
     * @param degrees Latitude, in degrees [-90,90], or {@code Double.POSITIVE_INFINITY} to
     *     represent an error condition
     * @param displayName human readable (display) name for this type - see static values
     */
    public FullCornerLatitude(double degrees, String displayName) {
        super(degrees);
        this.displayName = displayName;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Latitude, encoded as a 4-byte int
     * @param displayName human readable (display) name for this type - see static values
     */
    public FullCornerLatitude(byte[] bytes, String displayName) {
        super(bytes);
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}

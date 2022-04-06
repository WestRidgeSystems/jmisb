package org.jmisb.st0601;

/**
 * Target Location Latitude (ST 0601 Item 40).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Calculated Target latitude. This is the crosshair location if different from frame center. Based
 * on WGS84 ellipsoid.
 *
 * <p>Map -(2^31-1)..(2^31-1) to +/-90. Use -(2^31) as an "error" indicator. -(2^31) = 0x80000000.
 *
 * <p>Resolution: ~42 nano degrees.
 *
 * </blockquote>
 */
public class TargetLocationLatitude extends UasDatalinkLatitude {
    private static final String DISPLAY_NAME = "Target Location Latitude";

    /**
     * Create from value.
     *
     * @param degrees Latitude, in degrees [-90,90], or {@code Double.POSITIVE_INFINITY} to
     *     represent an error condition
     */
    public TargetLocationLatitude(double degrees) {
        super(degrees, DISPLAY_NAME);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 4
     */
    public TargetLocationLatitude(byte[] bytes) {
        super(bytes, DISPLAY_NAME);
    }

    @Override
    public String getDisplayName() {
        return "Target Location Latitude";
    }
}

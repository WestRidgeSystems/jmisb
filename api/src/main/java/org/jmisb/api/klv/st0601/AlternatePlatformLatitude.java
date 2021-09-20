package org.jmisb.api.klv.st0601;

/**
 * Alternate Platform Latitude (ST 0601 Item 67).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Alternate Platform Latitude. Represents latitude of platform connected with UAS. Based on WGS84
 * ellipsoid.
 *
 * <p>Map -(2^31-1)..(2^31-1) to +/-90. Use -(2^31) as an "error" indicator. -(2^31) = 0x80000000.
 *
 * <p>Resolution: ~42 nano degrees.
 *
 * </blockquote>
 */
public class AlternatePlatformLatitude extends UasDatalinkLatitude {
    private static final String DISPLAY_NAME = "Alternate Platform Latitude";

    /**
     * Create from value.
     *
     * @param degrees Latitude, in degrees [-90,90], or {@code Double.POSITIVE_INFINITY} to
     *     represent an error condition
     */
    public AlternatePlatformLatitude(double degrees) {
        super(degrees, DISPLAY_NAME);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Latitude, encoded as a 4-byte int
     */
    public AlternatePlatformLatitude(byte[] bytes) {
        super(bytes, DISPLAY_NAME);
    }
}

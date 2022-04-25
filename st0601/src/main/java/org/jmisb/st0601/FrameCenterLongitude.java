package org.jmisb.st0601;

/**
 * Frame Center Longitude (ST 0601 Item 24).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Terrain Longitude of frame center. Based on WGS84 ellipsoid.
 *
 * <p>Map -(2^31-1)..(2^31-1) to +/-180. Use -(2^31) as an "error" indicator. -(2^31) = 0x80000000.
 *
 * <p>Resolution: ~84 nano degrees.
 *
 * </blockquote>
 */
public class FrameCenterLongitude extends UasDatalinkLongitude {
    private static final String DISPLAY_NAME = "Frame Center Longitude";

    /**
     * Create from value.
     *
     * @param degrees Longitude, in degrees [-180,180], or {@code Double.POSITIVE_INFINITY} to
     *     represent an error condition
     */
    public FrameCenterLongitude(double degrees) {
        super(degrees, DISPLAY_NAME);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Longitude, encoded as a 4-byte int
     */
    public FrameCenterLongitude(byte[] bytes) {
        super(bytes, DISPLAY_NAME);
    }
}

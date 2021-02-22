package org.jmisb.api.klv.st0601;

/**
 * Frame Center Latitude (ST 0601 Item 23).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Terrain Latitude of frame center. Based on WGS84 ellipsoid.
 *
 * <p>Map -(2^31-1)..(2^31-1) to +/-90. Use -(2^31) as an "error" indicator. -(2^31) = 0x80000000.
 *
 * <p>Resolution: ~42 nano degrees.
 *
 * </blockquote>
 */
public class FrameCenterLatitude extends UasDatalinkLatitude {
    /**
     * Create from value.
     *
     * @param degrees Latitude, in degrees [-90,90], or {@code Double.POSITIVE_INFINITY} to
     *     represent an error condition
     */
    public FrameCenterLatitude(double degrees) {
        super(degrees);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Latitude, encoded as a 4-byte int
     */
    public FrameCenterLatitude(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Frame Center Latitude";
    }
}

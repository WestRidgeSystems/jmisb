package org.jmisb.st0601;

/**
 * Sensor Longitude (ST 0601 Item 14).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Sensor Longitude. Based on WGS84 ellipsoid.
 *
 * <p>Map -(2^31-1)..(2^31-1) to +/-180. Use -(2^31) as an "error" indicator. -(2^31) = 0x80000000.
 *
 * <p>Resolution: ~84 nano degrees.
 *
 * </blockquote>
 */
public class SensorLongitude extends UasDatalinkLongitude {
    private static final String DISPLAY_NAME = "Sensor Longitude";

    /**
     * Create from value.
     *
     * @param degrees Longitude, in degrees [-180,180], or {@code Double.POSITIVE_INFINITY} to
     *     represent an error condition
     */
    public SensorLongitude(double degrees) {
        super(degrees, DISPLAY_NAME);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Longitude, encoded as a 4-byte int
     */
    public SensorLongitude(byte[] bytes) {
        super(bytes, DISPLAY_NAME);
    }
}

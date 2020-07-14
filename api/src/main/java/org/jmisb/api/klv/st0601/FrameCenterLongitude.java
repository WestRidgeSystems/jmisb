package org.jmisb.api.klv.st0601;

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
    public FrameCenterLongitude(double degrees) {
        super(degrees);
    }

    public FrameCenterLongitude(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Frame Center Longitude";
    }
}

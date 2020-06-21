package org.jmisb.api.klv.st0601;

/**
 * Target Location Longitude (ST 0601 tag 41)
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Calculated Target longitude. This is the crosshair location if different from the frame center.
 * Based on WGS84 ellipsoid.
 *
 * <p>Map -(2^31-1)..(2^31-1) to +/-180. Use -(2^31) as an "error" indicator. -(2^31) = 0x80000000.
 *
 * <p>Resolution: ~84 nano degrees.
 *
 * </blockquote>
 */
public class TargetLocationLongitude extends UasDatalinkLongitude {
    public TargetLocationLongitude(double degrees) {
        super(degrees);
    }

    public TargetLocationLongitude(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Target Location Longitude";
    }
}

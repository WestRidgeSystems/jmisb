package org.jmisb.api.klv.st0601;

/**
 * Alternate Platform Latitude (ST 0601 tag 67)
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
    public AlternatePlatformLatitude(double degrees) {
        super(degrees);
    }

    public AlternatePlatformLatitude(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Alternate Platform Latitude";
    }
}

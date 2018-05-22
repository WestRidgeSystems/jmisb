package org.jmisb.api.klv.st0601;

/**
 * Alternate Platform Longitude (ST 0601 tag 68)
 * <p>
 * From ST:
 * <blockquote>
 * Alternate Platform Longitude. Represents longitude of platform connected with UAS. Based on WGS84 ellipsoid.
 * <p>
 * Map -(2^31-1)..(2^31-1) to +/-180. Use -(2^31) as an "error" indicator. -(2^31) = 0x80000000.
 * <p>
 * Resolution: ~84 nano degrees.
 * </blockquote>
 */
public class AlternatePlatformLongitude extends UasDatalinkLongitude
{
    public AlternatePlatformLongitude(double degrees)
    {
        super(degrees);
    }

    public AlternatePlatformLongitude(byte[] bytes)
    {
        super(bytes);
    }
}

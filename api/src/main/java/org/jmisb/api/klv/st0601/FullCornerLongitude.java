package org.jmisb.api.klv.st0601;

/**
 * Corner Longitude Point (ST 0601 tags 83/85/87/89)
 * <p>
 * From ST:
 * <blockquote>
 * Frame longitude for image corner. Full Range. Based on WGS84 ellipsoid.
 * <p>
 * Map -(2^31-1)..(2^31-1) to +/-180. Use -(2^31) as an "error" indicator.
 * -(2^31) = 0x80000000.
 * <p>
 * Resolution: ~84 nano degrees.
 * </blockquote>
 */
public class FullCornerLongitude extends UasDatalinkLongitude
{
    public FullCornerLongitude(double degrees)
    {
        super(degrees);
    }

    public FullCornerLongitude(byte[] bytes)
    {
        super(bytes);
    }
}

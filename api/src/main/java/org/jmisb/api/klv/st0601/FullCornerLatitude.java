package org.jmisb.api.klv.st0601;

/**
 * Corner Latitude Point (ST 0601 tags 82/84/86/88)
 * <p>
 * From ST:
 * <blockquote>
 * Frame latitude for image corner. Full Range. Based on WGS84 ellipsoid.
 * <p>
 * Map -(2^31-1)..(2^31-1) to +/-90. Use -(2^31) as an "error" indicator.
 * -(2^31) = 0x80000000.
 * <p>
 * Resolution: ~42 nano degrees.
 * </blockquote>
 */
public class FullCornerLatitude extends UasDatalinkLatitude
{
    public FullCornerLatitude(double degrees)
    {
        super(degrees);
    }

    public FullCornerLatitude(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public String getDisplayName()
    {
        return "Corner Latitude Point";
    }
}

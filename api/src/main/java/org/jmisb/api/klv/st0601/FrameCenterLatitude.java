package org.jmisb.api.klv.st0601;

/**
 * Frame Center Latitude (ST 0601 tag 23)
 * <p>
 * From ST:
 * <blockquote>
 * Terrain Latitude of frame center. Based on WGS84 ellipsoid.
 * <p>
 * Map -(2^31-1)..(2^31-1) to +/-90. Use -(2^31) as an "error" indicator.
 * -(2^31) = 0x80000000.
 * <p>
 * Resolution: ~42 nano degrees.
 * </blockquote>
 */
public class FrameCenterLatitude extends UasDatalinkLatitude
{
    public FrameCenterLatitude(double degrees)
    {
        super(degrees);
    }

    public FrameCenterLatitude(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public String getDisplayName()
    {
        return "Frame Center Latitude";
    }
}

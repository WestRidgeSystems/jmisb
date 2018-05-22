package org.jmisb.api.klv.st0601;

/**
 * Target Location Latitude (ST 0601 tag 40)
 * <p>
 * From ST:
 * <blockquote>
 * Calculated Target latitude. This is the crosshair location if different from frame center.
 * Based on WGS84 ellipsoid.
 * <p>
 * Map -(2^31-1)..(2^31-1) to +/-90. Use -(2^31) as an "error" indicator.
 * -(2^31) = 0x80000000.
 * <p>
 * Resolution: ~42 nano degrees.
 * </blockquote>
 */
public class TargetLocationLatitude extends UasDatalinkLatitude
{
    public TargetLocationLatitude(double degrees)
    {
        super(degrees);
    }

    public TargetLocationLatitude(byte[] bytes)
    {
        super(bytes);
    }
}

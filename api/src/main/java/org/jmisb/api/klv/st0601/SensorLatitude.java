package org.jmisb.api.klv.st0601;

/**
 * Sensor Latitude (ST 0601 tag 13)
 * <p>
 * From ST:
 * <blockquote>
 * Sensor Latitude. Based on WGS84 ellipsoid.
 * <p>
 * Map -(2^31-1)..(2^31-1) to +/-90. Use -(2^31) as an "error" indicator.
 * -(2^31) = 0x80000000.
 * <p>
 * Resolution: ~42 nano degrees.
 * </blockquote>
 */
public class SensorLatitude extends UasDatalinkLatitude
{
    public SensorLatitude(double degrees)
    {
        super(degrees);
    }

    public SensorLatitude(byte[] bytes)
    {
        super(bytes);
    }
}

package org.jmisb.api.klv.st0601;

/**
 * Target Location Elevation (ST 0601 tag 42)
 * <p>
 * From ST:
 * <blockquote>
 * Calculated target elevation. This is the crosshair location if different from frame center.
 * <p>
 * Map 0..(2^16-1) to -900..19000 meters.
 * <p>
 * Resolution: ~0.3 meters.
 * </blockquote>
 */
public class TargetLocationElevation extends UasDatalinkAltitude
{
    /**
     * Create from value
     * @param meters Elevation in meters. Legal values are in [-900,19000].
     */
    public TargetLocationElevation(double meters)
    {
        super(meters);
    }

    /**
     * Create from encoded bytes
     * @param bytes The byte array of length 2
     */
    public TargetLocationElevation(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public String getDisplayName()
    {
        return "Target Location Elevation";
    }
}

package org.jmisb.api.klv.st0601;

/**
 * Frame Center Elevation (ST 0601 tag 25)
 * <p>
 * From ST:
 * <blockquote>
 * Terrain elevation at frame center relative to Mean Sea Level (MSL).
 * <p>
 * Map 0..(2^16-1) to -900..19000 meters.
 * <p>
 * Resolution: ~0.3 meters.
 * </blockquote>
 * <p>
 * Additional note from ST:
 * <blockquote>
 * For legacy purposes, both MSL (Tag 25) and HAE (Tag 78) representations of Frame Center Elevation MAY appear in the
 * same ST 0601 packet. A single representation is preferred favoring the HAE version (Tag 78).
 * </blockquote>
 * See {@link FrameCenterHae}.
 */
public class FrameCenterElevation extends UasDatalinkAltitude
{
    public FrameCenterElevation(double meters)
    {
        super(meters);
    }

    public FrameCenterElevation(byte[] bytes)
    {
        super(bytes);
    }
}

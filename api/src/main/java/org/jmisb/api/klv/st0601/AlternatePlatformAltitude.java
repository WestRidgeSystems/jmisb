package org.jmisb.api.klv.st0601;

/**
 * Alternative Platform Altitude (ST 0601 tag 69)
 * <p>
 * From ST:
 * <blockquote>
 * Altitude of alternate platform as measured from Mean Sea Level (MSL). Represents altitude of platform connected with UAS.
 * <p>
 * Map 0..(2^16-1) to -900..19000 meters.
 * <p>
 * Resolution: ~0.3 meters.
 * </blockquote>
 */
public class AlternatePlatformAltitude extends UasDatalinkAltitude
{
    public AlternatePlatformAltitude(double meters)
    {
        super(meters);
    }

    public AlternatePlatformAltitude(byte[] bytes)
    {
        super(bytes);
    }
}

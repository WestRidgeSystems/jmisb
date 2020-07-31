package org.jmisb.api.klv.st0601;

/**
 * Alternative Platform Altitude (ST 0601 Item 69).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Altitude of alternate platform as measured from Mean Sea Level (MSL). Represents altitude of
 * platform connected with UAS.
 *
 * <p>Map 0..(2^16-1) to -900..19000 meters.
 *
 * <p>Resolution: ~0.3 meters.
 *
 * </blockquote>
 */
public class AlternatePlatformAltitude extends UasDatalinkAltitude {
    /**
     * Create from value.
     *
     * @param meters Altitude in meters. Legal values are in [-900,19000].
     */
    public AlternatePlatformAltitude(double meters) {
        super(meters);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 2
     */
    public AlternatePlatformAltitude(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Alternate Platform Altitude";
    }
}

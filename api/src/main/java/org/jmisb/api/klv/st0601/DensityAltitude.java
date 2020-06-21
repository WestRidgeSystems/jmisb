package org.jmisb.api.klv.st0601;

/**
 * Density Altitude (ST 0601 tag 38)
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Density altitude at aircraft location. Relative aircraft performance metric based on outside air
 * temperature, static pressure, and humidity.
 *
 * <p>Map 0..(2^16-1) to -900..19000 meters.
 *
 * <p>Resolution: ~0.3 meters.
 *
 * </blockquote>
 */
public class DensityAltitude extends UasDatalinkAltitude {
    /**
     * Create from value
     *
     * @param meters Altitude in meters. Legal values are in [-900,19000].
     */
    public DensityAltitude(double meters) {
        super(meters);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 2
     */
    public DensityAltitude(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Density Altitude";
    }
}

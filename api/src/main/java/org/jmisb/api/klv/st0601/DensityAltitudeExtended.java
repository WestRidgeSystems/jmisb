package org.jmisb.api.klv.st0601;

/**
 * Density Altitude Extended (ST 0601 Item 103).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Density altitude above MSL at aircraft location.
 *
 * <p>Relative aircraft performance metric based on outside air temperature, static pressure, and
 * humidity
 *
 * <p>Max Altitude: 40,000m for airborne systems
 *
 * <p>The purpose of Density Altitude Extended is to increase the range of altitude values currently
 * defined in Item 38 Density Altitude to support all CONOPs for airborne systems.
 *
 * </blockquote>
 */
public class DensityAltitudeExtended extends UasDatalinkAltitudeExtended {
    /**
     * Create from value.
     *
     * @param meters Altitude in meters. Valid range is [-900,40000]
     */
    public DensityAltitudeExtended(double meters) {
        super(meters);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes IMAPB Encoded byte array, 8 bytes maximum
     */
    public DensityAltitudeExtended(byte[] bytes) {
        super(bytes, 8);
    }

    @Override
    public String getDisplayName() {
        return "Density Altitude Extended";
    }
}

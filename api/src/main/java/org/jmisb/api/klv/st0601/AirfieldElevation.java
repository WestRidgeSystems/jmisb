package org.jmisb.api.klv.st0601;

/**
 * Airfield Elevation (ST 0601 Item 54).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Elevation of airfield corresponding to Airfield Barometric Pressure.
 *
 * <p>Airfield Elevation is measured at the airfield location. This relates to the Airfield
 * Barometric Pressure metadata item.
 *
 * <p>Map 0..(2^16-1) to -900..19000 meters.
 *
 * <p>Resolution: ~0.3 meters.
 *
 * </blockquote>
 */
public class AirfieldElevation extends UasDatalinkAltitude {
    /**
     * Create from value
     *
     * @param meters Altitude in meters. Legal values are in [-900,19000].
     */
    public AirfieldElevation(double meters) {
        super(meters);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 2
     */
    public AirfieldElevation(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Airfield Elevation";
    }
}

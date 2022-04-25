package org.jmisb.st0601;

/**
 * Sensor Ellipsoid Height (ST 0601 Item 75).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Sensor ellipsoid height as measured from the reference WGS84 ellipsoid.
 *
 * <p>Sensor Ellipsoid Height is the vertical distance between the sensor and the WGS84 Reference
 * Ellipsoid. Measurement is GPS derived.
 *
 * <p>Map 0..(2^16-1) to -900..19000 meters.
 *
 * <p>Resolution: ~0.3 meters.
 *
 * </blockquote>
 */
public class SensorEllipsoidHeight extends UasDatalinkAltitude {
    /**
     * Create from value.
     *
     * @param meters Altitude in meters. Legal values are in [-900,19000].
     */
    public SensorEllipsoidHeight(double meters) {
        super(meters);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 2
     */
    public SensorEllipsoidHeight(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Sensor Ellipsoid Height";
    }
}

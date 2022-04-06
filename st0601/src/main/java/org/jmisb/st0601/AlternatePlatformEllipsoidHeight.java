package org.jmisb.st0601;

/**
 * Alternative Platform Ellipsoid Height (ST 0601 Item 76).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Alternate platform ellipsoid height as measured from the reference WGS84 Ellipsoid.
 *
 * <p>The Alternate Platform Ellipsoid Height is the vertical distance between the sensor and the
 * WGS84 Reference Ellipsoid. Measurement is GPS derived.
 *
 * <p>An alternate platform is an airborne or ground based platform that is connected via direct
 * datalink to a UAS generating Motion Imagery and metadata.
 *
 * <p>Map 0..(2^16-1) to -900..19000 meters.
 *
 * <p>Resolution: ~0.3 meters.
 *
 * </blockquote>
 */
public class AlternatePlatformEllipsoidHeight extends UasDatalinkAltitude {
    /**
     * Create from value.
     *
     * @param meters Altitude in meters. Legal values are in [-900,19000].
     */
    public AlternatePlatformEllipsoidHeight(double meters) {
        super(meters);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 2
     */
    public AlternatePlatformEllipsoidHeight(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Alternate Platform Ellipsoid Height";
    }
}

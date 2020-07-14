package org.jmisb.api.klv.st0601;

/**
 * Alternate Platform Ellipsoid Height Extended (ST 0601 Item 105).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Alternate platform ellipsoid height extended as measured from the reference WGS84 ellipsoid.
 *
 * <p>Resolution: 2 bytes = 2 meters, 3 bytes = 78.125 mm
 *
 * <p>Max Altitude: 40,000m for airborne systems
 *
 * <p>The purpose of Alternate Platform Ellipsoid Height Extended is to increase the range of
 * altitude values currently defined in Item 76 Alternate Platform Ellipsoid Height to support all
 * CONOPs for airborne systems.
 *
 * </blockquote>
 */
public class AlternatePlatformEllipsoidHeightExtended extends UasDatalinkAltitudeExtended {
    /**
     * Create from value
     *
     * @param meters Ellipsoid height in meters. Valid range is [-900,40000]
     */
    public AlternatePlatformEllipsoidHeightExtended(double meters) {
        super(meters);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes IMAPB Encoded byte array, 8 bytes maximum
     */
    public AlternatePlatformEllipsoidHeightExtended(byte[] bytes) {
        super(bytes, 8);
    }

    @Override
    public String getDisplayName() {
        return "Alternate Platform Ellipsoid Height Extended";
    }
}

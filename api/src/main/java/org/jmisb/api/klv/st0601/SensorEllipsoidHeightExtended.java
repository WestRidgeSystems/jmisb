package org.jmisb.api.klv.st0601;

/**
 * Sensor Ellipsoid Height Extended (ST 0601 Item 104).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Sensor ellipsoid height extended as measured from the reference WGS84 ellipsoid.
 *
 * <p>Max Altitude: 40,000m for airborne systems
 *
 * <p>Resolution: 2 bytes = 2 meters, 3 bytes = 78.125 mm
 *
 * <p>The purpose of Sensor Ellipsoid Height Extended is to increase the range of altitude values
 * currently defined in Item 75 Sensor Ellipsoid Height to support all CONOPs for airborne systems.
 *
 * </blockquote>
 */
public class SensorEllipsoidHeightExtended extends UasDatalinkAltitudeExtended {
    /**
     * Create from value
     *
     * @param meters Ellipsoid height in meters. Valid range is [-900,40000]
     */
    public SensorEllipsoidHeightExtended(double meters) {
        super(meters);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes IMAPB Encoded byte array, 8 bytes maximum
     */
    public SensorEllipsoidHeightExtended(byte[] bytes) {
        super(bytes, 8);
    }

    @Override
    public String getDisplayName() {
        return "Sensor Ellipsoid Height Extended";
    }
}

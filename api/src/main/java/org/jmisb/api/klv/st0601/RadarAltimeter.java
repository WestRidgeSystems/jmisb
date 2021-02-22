package org.jmisb.api.klv.st0601;

/**
 * Radar Altimeter (ST 0601 Item 114).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Height above the ground/water as reported by a RADAR altimeter.
 *
 * <p>Max Altitude: 40,000m for airborne systems
 *
 * <p>Resolution: 2 bytes = 2.0 meters, 3 bytes = 0.7 cm
 *
 * </blockquote>
 */
public class RadarAltimeter extends UasDatalinkAltitudeExtended {
    /**
     * Create from value.
     *
     * @param meters Altitude in meters. Valid range is [-900,40000]
     */
    public RadarAltimeter(double meters) {
        super(meters);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes IMAPB Encoded byte array, 4 bytes maximum
     */
    public RadarAltimeter(byte[] bytes) {
        super(bytes, 4);
    }

    @Override
    public String getDisplayName() {
        return "Radar Altimeter";
    }
}

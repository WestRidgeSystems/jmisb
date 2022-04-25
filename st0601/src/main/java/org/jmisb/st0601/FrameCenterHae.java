package org.jmisb.st0601;

/**
 * Frame Center Height Above Ellipsoid (ST 0601 Item 78).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Frame Center Ellipsoid Height as measured from the reference WGS84 Ellipsoid.
 *
 * <p>Map 0..(2^16-1) to -900..19000 meters.
 *
 * <p>Resolution: ~0.3 meters.
 *
 * </blockquote>
 *
 * <p>Additional note from ST:
 *
 * <blockquote>
 *
 * For legacy purposes, both MSL (Item 25) and HAE (Item 78) representations of Frame Center
 * Elevation MAY appear in the same ST 0601 packet. A single representation is preferred favoring
 * the HAE version (Item 78).
 *
 * </blockquote>
 *
 * See {@link FrameCenterElevation}.
 */
public class FrameCenterHae extends UasDatalinkAltitude {
    /**
     * Create from value.
     *
     * @param meters Elevation in meters. Legal values are in [-900,19000].
     */
    public FrameCenterHae(double meters) {
        super(meters);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 2
     */
    public FrameCenterHae(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Frame Center Height Above Ellipsoid";
    }
}

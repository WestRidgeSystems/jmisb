package org.jmisb.api.klv.st0601;

/**
 * Platform True Airspeed (ST 0601 Item 8).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * True airspeed (TAS) of platform. Indicated Airspeed adjusted for temperature and altitude.
 *
 * <p>Map 0..(2^8-1) to 0..255 meters/second.
 *
 * <p>Resolution: 1 metre/second.
 *
 * </blockquote>
 */
public class PlatformTrueAirspeed extends UasDatalinkSpeed implements IUasDatalinkValue {
    /**
     * Create from value.
     *
     * @param speed Air speed in meters/second. Legal values are in [0, 255].
     */
    public PlatformTrueAirspeed(int speed) {
        super(speed);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 1
     */
    public PlatformTrueAirspeed(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Platform True Airspeed";
    }
}

package org.jmisb.api.klv.st0601;

/**
 * Platform Indicated Airspeed (ST 0601 Item 9).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Indicated airspeed (IAS) of platform. Derived from Pitot tube and static pressure sensors.
 *
 * <p>Map 0..(2^8-1) to 0..255 meters/second.
 *
 * <p>Resolution: 1 meter/second.
 *
 * </blockquote>
 */
public class PlatformIndicatedAirspeed extends UasDatalinkSpeed implements IUasDatalinkValue {
    /**
     * Create from value.
     *
     * @param speed Air speed in meters/second. Legal values are in [0, 255].
     */
    public PlatformIndicatedAirspeed(int speed) {
        super(speed);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 1
     */
    public PlatformIndicatedAirspeed(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Platform Indicated Airspeed";
    }
}

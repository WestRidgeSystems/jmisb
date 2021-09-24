package org.jmisb.api.klv.st0806.poiaoi;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * POI Altitude (ST 0806 POI tag 4).
 *
 * <p>Altitude of POI as measured from Mean Sea Level (MSL).
 *
 * <p>Map 0..(2^16-1) to -900..19000 meters. 1 meter = 3.2808399 feet.
 *
 * <p>Resolution: ~0.3 meters.
 */
public class PoiAltitude implements IRvtPoiAoiMetadataValue {
    private final double meters;
    private static final double MIN_VALUE = -900;
    private static final double MAX_VALUE = 19000;
    private static final double RANGE = 19900;
    private static final double MAXINT = 65535.0; // 2^16 - 1

    /**
     * Create from value.
     *
     * @param meters Altitude in meters. Legal values are in [-900,19000].
     */
    public PoiAltitude(double meters) {
        if (meters > MAX_VALUE || meters < MIN_VALUE) {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [-900,19000]");
        }
        this.meters = meters;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 2
     */
    public PoiAltitude(byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException(
                    getDisplayName() + " encoding is a 2-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint16(bytes);
        meters = ((intVal / MAXINT) * RANGE) + MIN_VALUE;
    }

    /**
     * Get the altitude.
     *
     * @return The altitude in meters
     */
    public double getMeters() {
        return meters;
    }

    @Override
    public byte[] getBytes() {
        int intVal = (int) Math.round(((meters - MIN_VALUE) / RANGE) * MAXINT);
        return PrimitiveConverter.uint16ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.1fm", meters);
    }

    @Override
    public final String getDisplayName() {
        return "POI Altitude";
    }
}

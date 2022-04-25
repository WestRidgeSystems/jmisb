package org.jmisb.st0601;

import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;

/**
 * Shared implementation of IMAPB encoded altitude.
 *
 * <p>Used by Alternate Platform Ellipsoid Height Extended (ST 0601 Item 105), Altitude AGL (ST 0601
 * Item 113), Density Altitude Extended (ST 0601 Item 103), Radar Altimeter (ST 0601 Item 114) and
 * Sensor Ellipsoid Height Extended (ST 0601 Item 104).
 */
public abstract class UasDatalinkAltitudeExtended implements IUasDatalinkValue {
    private static final double MIN_VAL = -900.0;
    private static final double MAX_VAL = 40000.0;
    private static final int RECOMMENDED_BYTES = 3;
    private final double meters;

    /**
     * Create from value.
     *
     * @param meters Altitude in meters. Valid range is [-900,40000]
     */
    public UasDatalinkAltitudeExtended(double meters) {
        if (meters < MIN_VAL || meters > MAX_VAL) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " must be in range [0,40000]");
        }
        this.meters = meters;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes IMAPB Encoded byte array
     * @param maxBytes the maximum length of the array that is allowed (typically 8, sometimes 4)
     */
    public UasDatalinkAltitudeExtended(byte[] bytes, int maxBytes) {
        if (bytes.length > maxBytes) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " cannot be longer than " + maxBytes + " bytes");
        }
        FpEncoder decoder =
                new FpEncoder(MIN_VAL, MAX_VAL, bytes.length, OutOfRangeBehaviour.Default);
        meters = decoder.decode(bytes);
    }

    /**
     * Get the altitude.
     *
     * @return The density altitude in meters
     */
    public double getMeters() {
        return this.meters;
    }

    @Override
    public byte[] getBytes() {
        FpEncoder encoder =
                new FpEncoder(MIN_VAL, MAX_VAL, RECOMMENDED_BYTES, OutOfRangeBehaviour.Default);
        return encoder.encode(meters);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.1fm", this.meters);
    }
}

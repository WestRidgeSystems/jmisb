package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.st1201.FpEncoder;

/**
 * Target Width Extended (ST 0601 Item 96).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Target width within sensor field of view.
 *
 * <p>Resolution: 2 bytes = 64 meters, 3 bytes = 0.25 meters
 *
 * <p>Range of 0 to 1,500,000 m established as maximum distance visible from an altitude of 40,000
 * m.
 *
 * </blockquote>
 */
public class TargetWidthExtended implements IUasDatalinkValue {
    private double metres;
    private static double MIN_VAL = 0.0;
    private static double MAX_VAL = 1500000.0;
    private static int RECOMMENDED_BYTES = 3;
    private static int MAX_BYTES = 8;

    /**
     * Create from value.
     *
     * @param meters Target width, in meters. Valid range is [0,1500000]
     */
    public TargetWidthExtended(double meters) {
        if (meters < MIN_VAL || meters > MAX_VAL) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " must be in range [0,1500000]");
        }
        this.metres = meters;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes IMAPB Encoded byte array, 8 bytes maximum
     */
    public TargetWidthExtended(byte[] bytes) {
        if (bytes.length > MAX_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " cannot be longer than " + MAX_BYTES + " bytes");
        }
        FpEncoder decoder = new FpEncoder(MIN_VAL, MAX_VAL, bytes.length);
        metres = decoder.decode(bytes);
    }

    /**
     * Get the value in meters.
     *
     * @return Target width in meters
     */
    public double getMeters() {
        return metres;
    }

    @Override
    public byte[] getBytes() {
        FpEncoder encoder = new FpEncoder(MIN_VAL, MAX_VAL, RECOMMENDED_BYTES);
        return encoder.encode(metres);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.1fm", metres);
    }

    @Override
    public final String getDisplayName() {
        return "Target Width Extended";
    }
}

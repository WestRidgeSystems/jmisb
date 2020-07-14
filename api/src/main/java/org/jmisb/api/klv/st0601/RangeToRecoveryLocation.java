package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.st1201.FpEncoder;

/**
 * Range To Recovery Location (Item 109).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Distance from current position to airframe recovery position.
 *
 * <p>The Range To Recovery Location is the minimum distance from the current aircraft position to
 * the aircraft recovery position. The distance is computed over the surface of the earth at the
 * given altitude of the aircraft (i.e., not a straight-line distance potentially through the
 * earth). The furtherst distance is a point on the opposite side of the earth, at the given
 * altitude.
 *
 * <p>Resolution: 2 bytes = 1 KM, 3 bytes = 3.9 meters, 4 bytes = 1.525 cm
 *
 * </blockquote>
 */
public class RangeToRecoveryLocation implements IUasDatalinkValue {
    private static double MIN_VAL = 0.0;
    private static double MAX_VAL = 21000.0;
    private static int RECOMMENDED_BYTES = 3;
    private static int MAX_BYTES = 4;
    private double range;

    /**
     * Create from value
     *
     * @param range range to recovery location in km. Valid range is [0..21000]
     */
    public RangeToRecoveryLocation(double range) {
        if (range < MIN_VAL || range > MAX_VAL) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " must be in range [0,21000]");
        }
        this.range = range;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes IMAPB Encoded byte array
     */
    public RangeToRecoveryLocation(byte[] bytes) {
        if (bytes.length > MAX_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " cannot be longer than " + MAX_BYTES + " bytes");
        }
        FpEncoder decoder = new FpEncoder(MIN_VAL, MAX_VAL, bytes.length);
        this.range = decoder.decode(bytes);
    }

    /**
     * Get range.
     *
     * @return the range in kilometres
     */
    public double getRange() {
        return this.range;
    }

    @Override
    public byte[] getBytes() {
        FpEncoder encoder = new FpEncoder(MIN_VAL, MAX_VAL, RECOMMENDED_BYTES);
        return encoder.encode(this.range);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.3fkm", this.range);
    }

    @Override
    public final String getDisplayName() {
        return "Range to Recovery Location";
    }
}

package org.jmisb.api.klv.st1206;

import org.jmisb.api.klv.st1201.FpEncoder;

/**
 * Pulse Repetition Frequency Scale Factor (ST 1206 Item 19).
 *
 * <p>The PRF scale factor is used to indicate when the effective PRF of a SAR image differs from
 * that of the true system PRF described in @link{TruePulseRepetitionFrequency}. The effective PRF
 * is useful for determining the location of ambiguous returns in range and/or cross-range that may
 * appear in SARMI data.
 *
 * <p>The effective PRF is defined as Effective PRF = PRF ∗ PRF Scale Factor.
 */
public class PulseRepetitionFrequencyScaleFactor implements ISARMIMetadataValue {

    protected static final double MIN_VAL = 0.0;
    protected static final double MAX_VAL = 1.0;
    protected static final int NUM_BYTES = 2;
    protected double value;

    /**
     * Create from value.
     *
     * <p>The valid range is 0 to 1.0.
     *
     * @param scaleFactor the PRF scale factor.
     */
    public PulseRepetitionFrequencyScaleFactor(double scaleFactor) {
        if (scaleFactor < MIN_VAL || scaleFactor > MAX_VAL) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s must be in range [%f, %f]",
                            this.getDisplayName(), MIN_VAL, MAX_VAL));
        }
        this.value = scaleFactor;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from (2 bytes).
     */
    public PulseRepetitionFrequencyScaleFactor(byte[] bytes) {
        if (bytes.length != NUM_BYTES) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s encoding is %d byte IMAPB", this.getDisplayName(), NUM_BYTES));
        }
        FpEncoder decoder = new FpEncoder(MIN_VAL, MAX_VAL, bytes.length);
        this.value = decoder.decode(bytes);
    }

    @Override
    public byte[] getBytes() {
        FpEncoder encoder = new FpEncoder(MIN_VAL, MAX_VAL, NUM_BYTES);
        return encoder.encode(this.value);
    }

    @Override
    public final String getDisplayableValue() {
        return String.format("%.5f", value);
    }

    @Override
    public final String getDisplayName() {
        return "Pulse Repetition Frequency Scale Factor";
    }

    /**
     * Get the Pulse Repetition Frequency Scale Factor.
     *
     * @return scale factor as a floating point (unitless) value.
     */
    public double getScaleFactor() {
        return value;
    }
}

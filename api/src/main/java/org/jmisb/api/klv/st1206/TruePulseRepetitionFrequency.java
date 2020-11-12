package org.jmisb.api.klv.st1206;

import org.jmisb.api.klv.st1201.FpEncoder;

/**
 * True Pulse Repetition Frequency (ST 1206 Item 18).
 *
 * <p>The pulse repetition frequency (PRF) is defined as the time interval between successively
 * transmitted pulses. Each pulse corresponds to a unique sensor along-track position over the
 * synthetic aperture. For SARMI, assume the PRF is fixed during the collection phase for a single
 * SAR image. Note that the effective PRF of as a result frame formation may be significantly less
 * than the true system PRF.
 */
public class TruePulseRepetitionFrequency implements ISARMIMetadataValue {

    protected static final double MIN_VAL = 0.0;
    protected static final double MAX_VAL = 1000000.0;
    protected static final int NUM_BYTES = 4;
    protected double value;

    /**
     * Create from value.
     *
     * <p>The valid range is 0 to 1000000 Hertz.
     *
     * @param prf pulse repetition frequency in Hz.
     */
    public TruePulseRepetitionFrequency(double prf) {
        if (prf < MIN_VAL || prf > MAX_VAL) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s must be in range [%f, %f]",
                            this.getDisplayName(), MIN_VAL, MAX_VAL));
        }
        this.value = prf;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from (4 bytes).
     */
    public TruePulseRepetitionFrequency(byte[] bytes) {
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
        return String.format("%.1fHz", value);
    }

    @Override
    public final String getDisplayName() {
        return "True Pulse Repetition Frequency";
    }

    /**
     * Get the True Pulse Repetition Frequency.
     *
     * @return PRF in Hertz
     */
    public double getPulseRepetitionFrequency() {
        return value;
    }
}

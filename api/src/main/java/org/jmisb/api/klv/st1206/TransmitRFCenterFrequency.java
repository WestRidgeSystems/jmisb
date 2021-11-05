package org.jmisb.api.klv.st1206;

import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;

/**
 * Transmit RF Center Frequency (ST 1206 Item 20).
 *
 * <p>The transmit RF center frequency is the center frequency of the RF band when linear FM
 * waveforms are employed. For most chirped waveform SARMI systems, the set of transmitted
 * frequencies is fixed for every pulse. For systems that adjust the transmitted frequency band
 * during the synthetic aperture, the RF center frequency is the frequency at the center of the band
 * defined by the minimum overall RF starting frequency and the maximum overall RF ending frequency
 * during the synthetic aperture.
 */
public class TransmitRFCenterFrequency implements ISARMIMetadataValue {

    protected static final double MIN_VAL = 0.0;
    protected static final double MAX_VAL = 1.0e12;
    protected static final int NUM_BYTES = 4;
    protected double value;

    /**
     * Create from value.
     *
     * @param frequency frequency in Hertz.
     */
    public TransmitRFCenterFrequency(double frequency) {
        if (frequency < MIN_VAL || frequency > MAX_VAL) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s must be in range [%f, %f]",
                            this.getDisplayName(), MIN_VAL, MAX_VAL));
        }
        this.value = frequency;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public TransmitRFCenterFrequency(byte[] bytes) {
        if (bytes.length != NUM_BYTES) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s encoding is %d byte IMAPB", this.getDisplayName(), NUM_BYTES));
        }
        FpEncoder decoder =
                new FpEncoder(MIN_VAL, MAX_VAL, bytes.length, OutOfRangeBehaviour.Default);
        this.value = decoder.decode(bytes);
    }

    @Override
    public byte[] getBytes() {
        FpEncoder encoder = new FpEncoder(MIN_VAL, MAX_VAL, NUM_BYTES, OutOfRangeBehaviour.Default);
        return encoder.encode(this.value);
    }

    @Override
    public final String getDisplayableValue() {
        return String.format("%.1fHz", value);
    }

    @Override
    public final String getDisplayName() {
        return "Transmit RF Center Frequency";
    }

    /**
     * Get the Transmit RF Center Frequency.
     *
     * @return frequency in Hertz
     */
    public double getFrequency() {
        return value;
    }
}

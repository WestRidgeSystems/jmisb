package org.jmisb.st1206;

import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;

/**
 * Transmit RF Bandwidth(ST 1206 Item 21).
 *
 * <p>The RF bandwidth dictates the range resolution in the slant plane independent of aperture
 * size. The transmit RF bandwidth may be defined by a single, fixed waveform or a sequence of
 * waveforms, such as a step chirp waveform. The transmit RF bandwidth is defined as the difference
 * between the maximum and minimum transmit frequencies for a single or sequence of waveforms, if
 * applicable.
 */
public class TransmitRFBandwidth implements ISARMIMetadataValue {

    private static final double MIN_VAL = 0.0;
    private static final double MAX_VAL = 1.0e11;
    private static final int NUM_BYTES = 4;
    private double value;

    /**
     * Create from value.
     *
     * @param bandwidth bandwidth in Hertz.
     */
    public TransmitRFBandwidth(double bandwidth) {
        if (bandwidth < MIN_VAL || bandwidth > MAX_VAL) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s must be in range [%f, %f]",
                            this.getDisplayName(), MIN_VAL, MAX_VAL));
        }
        this.value = bandwidth;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public TransmitRFBandwidth(byte[] bytes) {
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
        return "Transmit RF Bandwidth";
    }

    /**
     * Get the Transmit RF Bandwidth.
     *
     * @return bandwidth in Hertz
     */
    public double getBandwidth() {
        return value;
    }
}

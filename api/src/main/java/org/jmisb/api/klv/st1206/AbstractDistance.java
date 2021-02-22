package org.jmisb.api.klv.st1206;

import org.jmisb.api.klv.st1201.FpEncoder;

/**
 * Shared ST1206 implementation of a distance (resolution or pixel size) in metres.
 *
 * <p>This is encoded as IMAPB(0, 1e6, 4).
 */
public abstract class AbstractDistance implements ISARMIMetadataValue {

    protected static final double MIN_VAL = 0.0;
    protected static final double MAX_VAL = 1.0e6;
    protected static final int NUM_BYTES = 4;
    protected double value;

    /**
     * Create from value.
     *
     * @param distance pixel size in metres.
     */
    public AbstractDistance(double distance) {
        if (distance < MIN_VAL || distance > MAX_VAL) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s must be in range [%f, %f]",
                            this.getDisplayName(), MIN_VAL, MAX_VAL));
        }
        this.value = distance;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public AbstractDistance(byte[] bytes) {
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
        return String.format("%.2fm", value);
    }
}

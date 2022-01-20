package org.jmisb.api.klv.st1603.localset;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Drift Rate (ST 1603 Time Transfer Local Set Tag 7).
 *
 * <p>From ST 1603:
 *
 * <blockquote>
 *
 * <p>The Drift Rate is the known maximum drift rate of the receptor clock when freewheeling. When
 * this value is provided, it must appear in the stream at least once every 30 seconds. When this
 * value is not included in the stream, it means the drift rate is unknown, i.e. there is not a
 * default value. This value can be a positive or negative floating-point value, and the units are
 * in microseconds per second (µs/s).
 *
 * <p>When the Drift Rate is included in the Time Transfer Local Set, the reported time’s
 * uncertainty is usable in real time when the reference source is lost.
 *
 * </blockquote>
 */
public class DriftRate implements ITimeTransferValue {
    private final double driftRate;

    /**
     * Create from value.
     *
     * @param driftRate The drift rate in microseconds per second
     */
    public DriftRate(double driftRate) {
        this.driftRate = driftRate;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array encoding of the float value (variable number of bytes).
     * @throws KlvParseException if the encoded bytes could not be deserialised (usually bad length)
     */
    public DriftRate(byte[] bytes) throws KlvParseException {
        try {
            driftRate = PrimitiveConverter.toFloat64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException("Unable to deserialise Drift Rate: " + ex.getMessage());
        }
    }

    /**
     * Get the drift rate.
     *
     * @return drift rate in microseconds per second
     */
    public double getDriftRate() {
        return driftRate;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float64ToBytes(driftRate);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.4f µs/s", driftRate);
    }

    @Override
    public final String getDisplayName() {
        return "Drift Rate";
    }
}

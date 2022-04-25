package org.jmisb.st1603.localset;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Signal Source Delay (ST 1603 Time Transfer Local Set Tag 8).
 *
 * <p>From ST 1603:
 *
 * <blockquote>
 *
 * The Signal Source Delay is the estimated or measured latency of the time signal from the
 * reference clock to the receptor clock. For example, on-board a ship a GPS antenna could be 200
 * meters from a receiver, where the signal incurs a non-zero latency. When this value is provided,
 * it must appear in the stream at least once every 30 seconds. When this value is not included in
 * the stream, the default delay is zero (0). The units for this measurement are nanoseconds.
 *
 * </blockquote>
 */
public class SignalSourceDelay implements ITimeTransferValue {
    private final long delay;
    private static final int MIN_VALUE = 0;

    /**
     * Create from value.
     *
     * @param delay The signal source delay in nanoseconds.
     */
    public SignalSourceDelay(long delay) {
        if (delay < MIN_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be non-negative");
        }
        this.delay = delay;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array encoding of the unsigned integer value (variable number of bytes).
     * @throws KlvParseException if the encoded bytes could not be deserialised (usually bad length)
     */
    public SignalSourceDelay(byte[] bytes) throws KlvParseException {
        try {
            delay = PrimitiveConverter.variableBytesToUint64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise Signal Source Delay: " + ex.getMessage());
        }
    }

    /**
     * Get the signal source delay.
     *
     * @return The signal source delay in nanoseconds
     */
    public long getDelay() {
        return delay;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uintToVariableBytes(delay);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d ns", delay);
    }

    @Override
    public final String getDisplayName() {
        return "Signal Source Delay";
    }
}

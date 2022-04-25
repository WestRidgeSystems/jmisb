package org.jmisb.st1603.localset;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Unlock Time (ST 1603 Time Transfer Local Set Tag 5).
 *
 * <p>From ST 1603:
 *
 * <blockquote>
 *
 * The Unlock Time is the duration of time since a receptor clock was last locked to the reference
 * clock (see the Motion Imagery Handbook for details). This value is required for every time report
 * during which lock is lost. The default value is zero (0). When the reference and receptor clocks
 * are locked, this value is zero (0), and therefore, does not need to be reported. A non-zero value
 * is an indicator the receptor clock is not locked to the reference clock; this replaces the
 * locked/unlocked bit used in other standards. The units for this measurement are the same as the
 * units of the parent time.
 *
 * </blockquote>
 */
public class UnlockTime implements ITimeTransferValue {
    private final long time;
    private static final int MIN_VALUE = 0;

    /**
     * Create from value.
     *
     * @param time The unlock time, in the same units as the parent time.
     */
    public UnlockTime(long time) {
        if (time < MIN_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be non-negative");
        }
        this.time = time;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array encoding of the unsigned integer value (variable number of bytes).
     * @throws KlvParseException if the encoded bytes could not be deserialised (usually bad length)
     */
    public UnlockTime(byte[] bytes) throws KlvParseException {
        try {
            time = PrimitiveConverter.variableBytesToUint64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException("Unable to deserialise Unlock Time: " + ex.getMessage());
        }
    }

    /**
     * Get the unlock time.
     *
     * @return the unlock time in the same units as the parent time.
     */
    public long getUnlockTime() {
        return time;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uintToVariableBytes(time);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", time);
    }

    @Override
    public final String getDisplayName() {
        return "Unlock Time";
    }
}

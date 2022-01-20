package org.jmisb.api.klv.st1603.localset;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Last Synchronization Difference (ST 1603 Time Transfer Local Set Tag 6).
 *
 * <p>From ST 1603:
 *
 * <blockquote>
 *
 * When performing Slew Correction, the Last Synchronization Difference is either the current
 * difference in time between a receptor clock and its reference clock during slewing, or the last
 * known greater-than-zero difference during slewing (see the Motion Imagery Handbook for a
 * description). When using Slew Correction, this value is required every time report its value is
 * greater than zero (0). When the value is zero, it is not included in the Local Set, i.e. the
 * default value is zero (0). The units for this measurement are the same as the units of the parent
 * time.
 *
 * </blockquote>
 */
public class LastSynchronizationDifference implements ITimeTransferValue {
    private final long difference;
    private static final int MIN_VALUE = 0;

    /**
     * Create from value.
     *
     * @param difference The last synchronization difference, in the same units as the parent time.
     */
    public LastSynchronizationDifference(long difference) {
        if (difference < MIN_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be non-negative");
        }
        this.difference = difference;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array encoding of the unsigned integer value (variable number of bytes).
     * @throws KlvParseException if the encoded bytes could not be deserialised (usually bad length)
     */
    public LastSynchronizationDifference(byte[] bytes) throws KlvParseException {
        try {
            difference = PrimitiveConverter.variableBytesToUint64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise Last Synchronization Difference: " + ex.getMessage());
        }
    }

    /**
     * Get the last synchronization difference.
     *
     * @return the last synchronization difference in the same units as the parent time.
     */
    public long getDifference() {
        return difference;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uintToVariableBytes(difference);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", difference);
    }

    @Override
    public final String getDisplayName() {
        return "Last Synchronization Difference";
    }
}

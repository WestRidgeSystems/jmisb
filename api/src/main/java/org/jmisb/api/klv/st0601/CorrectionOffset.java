package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * CorrectionOffset (ST 0601 Item 137).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Post-flight time adjustment to correct Precision Time Stamp (Item 2) as needed.
 *
 * <p>KLV format: int64, Min: -(2^63), Max: (2^63)-1.
 *
 * <p>Length: variable, Max Length: 8, Required Length: N/A.
 *
 * <p>Resolution: 1 microsecond.
 *
 * <p>Add value to Precision Time Stamp (Item 2) to correct time.
 *
 * <p>This value DOES NOT INCLUDE leap seconds offset. See Leap Seconds (Item 136) to add leap
 * second offset.
 *
 * <p>See "Packet Timestamp" section for more information on the use of this item.
 *
 * </blockquote>
 */
public class CorrectionOffset implements IUasDatalinkValue {
    private long microseconds;
    private static int MAX_BYTES = 8;

    /**
     * Create from value.
     *
     * @param microseconds time in microseconds. Legal values are in [-2^63,2^63-1].
     */
    public CorrectionOffset(long microseconds) {
        this.microseconds = microseconds;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array, maximum eight bytes
     */
    public CorrectionOffset(byte[] bytes) {
        if (bytes.length > MAX_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " field length must be 1 - 8 bytes");
        }
        this.microseconds = PrimitiveConverter.variableBytesToInt64(bytes);
    }

    /**
     * Get the correction offset.
     *
     * @return The correction offset, in microseconds.
     */
    public long getOffset() {
        return microseconds;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.int64ToVariableBytes(microseconds);
    }

    @Override
    public String getDisplayableValue() {
        return microseconds + "\u00B5s";
    }

    @Override
    public final String getDisplayName() {
        return "Correction Offset";
    }
}

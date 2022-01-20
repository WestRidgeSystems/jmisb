package org.jmisb.api.klv.st0603;

import org.jmisb.api.klv.IKlvValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Nano Precision Time Stamp.
 *
 * <p>The Nano Precision Time Stamp represents a sampled and quantized time value of the MISP Time
 * System specified to nanosecond resolution. The Nano Precision Time Stamp represented as a 64-bit
 * unsigned integer and is specified to a resolution of 1 nanosecond. Sub-nanosecond measurements
 * are truncated to the nearest nanosecond.
 */
public class NanoPrecisionTimeStamp implements IKlvValue {
    public static final int BYTES = Long.BYTES;

    // this is conceptually unsigned, so be careful when manipulating it.
    protected final long nanoseconds;
    /**
     * Create from value.
     *
     * @param nanoseconds nanoseconds since the epoch
     */
    public NanoPrecisionTimeStamp(long nanoseconds) {
        if (nanoseconds < 0) {
            throw new IllegalArgumentException("Timestamp must be in range [0,2^64-1]");
        }
        this.nanoseconds = nanoseconds;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes byte array containing the encoded value.
     * @param offset zero-based index into the byte array where the decoding should start.
     */
    public NanoPrecisionTimeStamp(byte[] bytes, int offset) {
        this.nanoseconds = PrimitiveConverter.variableBytesToUint64(bytes, offset, BYTES);
    }

    /**
     * Get the byte array corresponding to this timestamp.
     *
     * @return byte array corresponding to the timestamp.
     */
    public byte[] getBytes() {
        return PrimitiveConverter.int64ToBytes(nanoseconds);
    }

    /**
     * Get the value.
     *
     * @return Number of nanoseconds since the epoch
     */
    public long getNanoseconds() {
        return nanoseconds;
    }

    @Override
    public String getDisplayName() {
        return "Nano Precision Time Stamp";
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%s ns", Long.toUnsignedString(nanoseconds));
    }

    /**
     * Get an equivalent ST 0603 Precision Time Stamp.
     *
     * <p>This converts the nanosecond based value of this instance to a microsecond based Precision
     * Time Stamp.
     *
     * @return the equivalent microsecond-based time stamp
     */
    public ST0603TimeStamp toPrecisionTimeStamp() {
        long microseconds = (nanoseconds + 500) / Constants.NANOS_PER_MICRO;
        return new ST0603TimeStamp(microseconds);
    }
}

package org.jmisb.api.klv.st0603;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import org.jmisb.core.klv.PrimitiveConverter;

/** ST0603 Timestamp. */
public class ST0603TimeStamp {
    // this is conceptually unsigned, so be careful when manipulating it.
    protected long microseconds;

    /**
     * Create from value.
     *
     * @param microseconds Microseconds since the epoch
     */
    public ST0603TimeStamp(long microseconds) {
        if (microseconds < 0) {
            throw new IllegalArgumentException("Timestamp must be in range [0,2^64-1]");
        }
        this.microseconds = microseconds;
    }

    /**
     * Create from encoded bytes.
     *
     * <p>This method does not check that the array is exactly 8 bytes, only that it is not more
     * than 8 bytes. Missing bytes are assumed be leading bytes, value zero.
     *
     * @param bytes Encoded byte array (1-8 bytes).
     */
    public ST0603TimeStamp(byte[] bytes) {
        if (bytes.length <= 8) {
            microseconds = PrimitiveConverter.variableBytesToUint64(bytes);
        } else {
            throw new IllegalArgumentException("Timestamp encoding is an 1 to 8-byte unsigned int");
        }
    }

    /**
     * Create from {@code DateTime}.
     *
     * @param dateTime The date and time
     */
    public ST0603TimeStamp(LocalDateTime dateTime) {
        try {
            // TODO: Not really UTC...
            microseconds =
                    ChronoUnit.MICROS.between(Instant.EPOCH, dateTime.toInstant(ZoneOffset.UTC));
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException(
                    "Timestamp must be before April 11, 2262 23:47:16.854Z");
        }
    }

    /**
     * Get the value.
     *
     * @return Number of microseconds since the epoch
     */
    public long getMicroseconds() {
        return microseconds;
    }

    /**
     * Get the byte array corresponding to this timestamp.
     *
     * <p>This version always returns an 8 byte array
     *
     * @return full byte array corresponding to the timestamp.
     */
    public byte[] getBytesFull() {
        return PrimitiveConverter.int64ToBytes(microseconds);
    }

    /**
     * Get the byte array corresponding to this timestamp.
     *
     * <p>This version will drop leading zero bytes. The maximum length is 8 bytes.
     *
     * @return 1-8 byte array corresponding to the timestamp.
     */
    public byte[] getBytesVariable() {
        return PrimitiveConverter.uintToVariableBytes(microseconds);
    }

    /**
     * Get the value as a {@code LocalDateTime}.
     *
     * @return The date and time
     */
    public LocalDateTime getDateTime() {
        // TODO: not really UTC
        return LocalDateTime.ofEpochSecond(
                microseconds / 1000000, (int) (microseconds % 1000000) * 1000, ZoneOffset.UTC);
    }

    /**
     * Get a displayable representation of the timestamp.
     *
     * @return String representation of this timestamp, in microseconds after the epoch.
     */
    public String getDisplayableValue() {
        return "" + microseconds;
    }

    /**
     * Get a displayable representation of the timestamp.
     *
     * @return String representation of this timestamp, date / time format.
     */
    public String getDisplayableValueDateTime() {
        LocalDateTime ldt = getDateTime();
        String displayableLocalDateTime = ldt.format(DateTimeFormatter.ISO_DATE_TIME);
        return displayableLocalDateTime;
    }
}

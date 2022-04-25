package org.jmisb.st0809;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * Precision Time Stamp Microseconds (ST 0809 Local Set Tag 1).
 *
 * <p>Microsecond count of time as defined in MISB ST 0603.
 *
 * <p>Resolution is 1 microsecond.
 */
public class PrecisionTimeStamp extends ST0603TimeStamp implements IMeteorologicalMetadataValue {
    /**
     * Create from value.
     *
     * @param microseconds Microseconds since the epoch
     */
    public PrecisionTimeStamp(long microseconds) {
        super(microseconds);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, length of 8 bytes.
     */
    public PrecisionTimeStamp(byte[] bytes) {
        super(bytes);
        if (bytes.length < 8) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is an 8-byte unsigned int");
        }
    }

    /**
     * Create from {@code LocalDateTime}.
     *
     * @param dateTime The date and time
     */
    public PrecisionTimeStamp(LocalDateTime dateTime) {
        super(dateTime);
    }

    @Override
    public final String getDisplayName() {
        return "Precision Time Stamp";
    }

    @Override
    public byte[] getBytes() {
        return getBytesFull();
    }
}

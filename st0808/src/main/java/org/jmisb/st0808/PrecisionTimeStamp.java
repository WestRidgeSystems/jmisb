package org.jmisb.st0808;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * Precision Time Stamp Microseconds (ST0808 Ancillary Text Local Set Tag 2).
 *
 * <p>The Precision Time Stamp ties the metadata set to a particular frame of Motion Imagery within
 * the Motion Imagery stream. The PrecisionTime Stamp provides an absolute marker within the stream
 * that correlates the message to the described event within the Motion Imagery
 *
 * <p>The Precision Time Stamp is a mandatory element in the ancillary text metadata set.
 *
 * <p>Resolution is 1 microsecond.
 */
public class PrecisionTimeStamp extends ST0603TimeStamp implements IAncillaryTextMetadataValue {
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

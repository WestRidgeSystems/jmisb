package org.jmisb.examples.parserplugin.timemessage;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * Precision Time Stamp Microseconds.
 *
 * <p>This is the only field in our Time Message example, but most messages would be more complex.
 */
public class PrecisionTimeStamp extends ST0603TimeStamp implements ITimeMessageValue {
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

    @Override
    public final String getDisplayableValue() {
        return this.getDisplayableValueDateTime();
    }
}

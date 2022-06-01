package org.jmisb.st0806;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * User Defined Time Stamp Microseconds (ST0806 RVT LS Tag 2).
 *
 * <p>Resolution is 1 microsecond. This is probably best interpreted as MISP microseconds, although
 * ST0806.4 says UTC and Unix Epoch.
 */
public class UserDefinedTimeStampMicroseconds extends ST0603TimeStamp implements IRvtMetadataValue {
    /**
     * Create from value.
     *
     * @param microseconds Microseconds since the epoch
     */
    public UserDefinedTimeStampMicroseconds(long microseconds) {
        super(microseconds);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, length of 8 bytes.
     */
    public UserDefinedTimeStampMicroseconds(byte[] bytes) {
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
    public UserDefinedTimeStampMicroseconds(LocalDateTime dateTime) {
        super(dateTime);
    }

    @Override
    public final String getDisplayName() {
        return "User Defined Time Stamp";
    }

    @Override
    public byte[] getBytes() {
        return getBytesFull();
    }
}

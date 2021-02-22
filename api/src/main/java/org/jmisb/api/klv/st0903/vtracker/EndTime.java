package org.jmisb.api.klv.st0903.vtracker;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.IVTrackMetadataValue;

/**
 * End Time (ST0903 VTracker Pack Tag 4)
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Captures the time of the most recent observation of the entity in microseconds elapsed since
 * midnight (00:00:00), January 1, 1970 (MISB ST 0603).
 *
 * </blockquote>
 */
public class EndTime extends ST0603TimeStamp implements IVmtiMetadataValue, IVTrackMetadataValue {
    /**
     * Create from value.
     *
     * @param microseconds Microseconds since the epoch
     */
    public EndTime(long microseconds) {
        super(microseconds);
    }

    /**
     * Create from encoded bytes.
     *
     * <p>In ST0903.4 and ST0903.5, this needs to be 8 bytes. However earlier versions allowed it to
     * be up to 8 bytes, so we tolerate that.
     *
     * @param bytes Encoded byte array
     */
    public EndTime(byte[] bytes) {
        super(bytes);
    }

    /**
     * Create from {@code LocalDateTime}.
     *
     * @param dateTime The date and time
     */
    public EndTime(LocalDateTime dateTime) {
        super(dateTime);
    }

    @Override
    public final String getDisplayName() {
        return "End Time";
    }

    @Override
    public byte[] getBytes() {
        // On generation, we return the full 8 bytes for compliance.
        return getBytesFull();
    }
}

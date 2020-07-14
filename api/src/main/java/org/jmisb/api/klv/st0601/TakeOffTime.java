package org.jmisb.api.klv.st0601;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * Take Off Time (ST 0601 Item 131).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Time when aircraft became airborne.
 *
 * <p>Represented in the number of microseconds elapsed since midnight (00:00:00), January 1, 1970
 * not including leap seconds.
 *
 * <p>See MISB ST 0603.
 *
 * <p>See details for Time Airborne (Item 110) for description and usage.
 *
 * <p>Resolution: 1 microsecond.
 *
 * </blockquote>
 */
public class TakeOffTime extends ST0603TimeStamp implements IUasDatalinkValue {
    /**
     * Create from value
     *
     * @param microseconds Microseconds since the epoch.
     */
    public TakeOffTime(long microseconds) {
        super(microseconds);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public TakeOffTime(byte[] bytes) {
        super(bytes);
    }

    /**
     * Create from {@code LocalDateTime}
     *
     * @param localDateTime The date and time
     */
    public TakeOffTime(LocalDateTime localDateTime) {
        super(localDateTime);
    }

    @Override
    public final String getDisplayName() {
        return "Take Off Time";
    }

    @Override
    public byte[] getBytes() {
        // This doesn't need to be 8 bytes.
        return getBytesVariable();
    }

    @Override
    public String getDisplayableValue() {
        return getDisplayableValueDateTime();
    }
}

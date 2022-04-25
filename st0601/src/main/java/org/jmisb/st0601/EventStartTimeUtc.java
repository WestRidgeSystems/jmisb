package org.jmisb.st0601;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * Event Start Time - UTC (ST 0601 Item 72).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Start time of scene, project, event, mission, editing event, license, publication, etc.
 *
 * <p>Represented in the number of microseconds elapsed since midnight (00:00:00), January 1, 1970.
 *
 * <p>The Event Start Time - UTC metadata item is used to represent the start time of a mission, or
 * other event related to the Motion Imagery collection. Event Start Time – UTC is to be interpreted
 * as an arbitrary time hack indicating the start of some event.
 *
 * </blockquote>
 *
 * Note: if you are looking to represent takeoff time, see TakeOffTime (Item 131).
 */
public class EventStartTimeUtc extends ST0603TimeStamp implements IUasDatalinkValue {
    /**
     * Create from value.
     *
     * @param microseconds Microseconds since the epoch.
     */
    public EventStartTimeUtc(long microseconds) {
        super(microseconds);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public EventStartTimeUtc(byte[] bytes) {
        super(bytes);
    }

    /**
     * Create from {@code LocalDateTime}.
     *
     * @param localDateTime The date and time
     */
    public EventStartTimeUtc(LocalDateTime localDateTime) {
        super(localDateTime);
    }

    @Override
    public final String getDisplayName() {
        return "Event Start Time UTC";
    }

    @Override
    public byte[] getBytes() {
        // Always 8 bytes.
        return getBytesFull();
    }

    @Override
    public String getDisplayableValue() {
        return getDisplayableValueDateTime();
    }
}

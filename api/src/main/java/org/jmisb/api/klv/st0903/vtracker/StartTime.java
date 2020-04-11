package org.jmisb.api.klv.st0903.vtracker;

import org.jmisb.api.klv.st0903.shared.ST0603TimeStamp;
import java.time.ZonedDateTime;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;

/**
 * Start Time (ST0903 VTracker Pack Tag 3)
 * <p>
 * From ST0903:
 * <blockquote>
 * Captures the time of the first observation of the entity in microseconds
 * elapsed since midnight (00:00:00), January 1, 1970 (see MISB ST 0603).
 * </blockquote>
 */
public class StartTime extends ST0603TimeStamp implements IVmtiMetadataValue
{
    /**
     * Create from value.
     *
     * @param microseconds Microseconds since the epoch
     */
    public StartTime(long microseconds)
    {
        super(microseconds);
    }

    /**
     * Create from encoded bytes
     * @param bytes Encoded byte array
     */
    public StartTime(byte[] bytes)
    {
        super(bytes);
    }

    /**
     * Create from {@code ZonedDateTime}
     * @param dateTime The UTC date and time
     */
    public StartTime(ZonedDateTime dateTime)
    {
        super(dateTime);
    }

    @Override
    public final String getDisplayName()
    {
        return "Start Time";
    }
}

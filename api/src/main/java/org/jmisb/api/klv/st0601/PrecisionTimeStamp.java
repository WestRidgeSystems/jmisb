package org.jmisb.api.klv.st0601;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * Precision Time Stamp (ST 0601 tag 2)
 * <p>
 * From ST:
 * <blockquote>
 * Represented in the number of microseconds elapsed since midnight (00:00:00), January 1,
 * 1970 not including leap seconds. See MISB ST 0603.
 * <p>
 * Resolution: 1 microsecond.
 * </blockquote>
 */
public class PrecisionTimeStamp extends ST0603TimeStamp implements IUasDatalinkValue
{
    /**
     * Create from value
     * @param microseconds Microseconds since the epoch
     */
    public PrecisionTimeStamp(long microseconds)
    {
        super(microseconds);
    }

    /**
     * Create from encoded bytes
     * @param bytes Encoded byte array
     */
    public PrecisionTimeStamp(byte[] bytes)
    {
        super(bytes);
        if (bytes.length < 8)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " encoding is an 8-byte unsigned int");
        }
    }

    /**
     * Create from {@code LocalDateTime}
     * @param localDateTime The date and time
     */
    public PrecisionTimeStamp(LocalDateTime localDateTime)
    {
        super(localDateTime);
    }

    @Override
    public final String getDisplayName()
    {
        return "Precision Time Stamp";
    }

    @Override
    public byte[] getBytes()
    {
        // On generation, we return the full 8 bytes for compliance.
        return getBytesFull();
    }
}
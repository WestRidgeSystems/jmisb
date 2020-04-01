package org.jmisb.api.klv.st0903.shared;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * ST0603 Timestamp.
 */
public abstract class ST0603TimeStamp implements IVmtiMetadataValue
{
    // this is unsigned, so  methods.
    protected long microseconds;

    /**
     * Create from value.
     *
     * @param microseconds Microseconds since the epoch
     */
    public ST0603TimeStamp(long microseconds)
    {
        if (microseconds < 0)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [0,2^64-1]");
        }
        this.microseconds = microseconds;
    }

    /**
     * Create from encoded bytes
     * @param bytes Encoded byte array
     */
    public ST0603TimeStamp(byte[] bytes)
    {
        if (bytes.length != 8)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " encoding is an 8-byte unsigned int");
        }
        microseconds = PrimitiveConverter.toInt64(bytes);
    }

    /**
     * Create from {@code ZonedDateTime}
     * @param dateTime The UTC date and time
     */
    public ST0603TimeStamp(ZonedDateTime dateTime)
    {
        try
        {
            microseconds = ChronoUnit.MICROS.between(Instant.EPOCH, dateTime.toInstant());
        }
        catch (ArithmeticException e)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " must be before April 11, 2262 23:47:16.854Z");
        }
    }

    /**
     * Get the value.
     *
     * @return Number of microseconds since the epoch
     */
    public long getMicroseconds()
    {
        return microseconds;
    }

    @Override
    public byte[] getBytes()
    {
        return PrimitiveConverter.int64ToBytes(microseconds);
    }

    /**
     * Get the value as a {@code ZonedDateTime}
     * @return The UTC date and time
     */
    public ZonedDateTime getDateTime()
    {
        Instant instant = Instant.ofEpochSecond(microseconds / 1000000, (int) (microseconds % 1000000) * 1000);
        return ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }

    @Override
    public String getDisplayableValue()
    {
        return "" + microseconds;
    }
}

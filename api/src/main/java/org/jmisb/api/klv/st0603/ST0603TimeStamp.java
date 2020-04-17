package org.jmisb.api.klv.st0603;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * ST0603 Timestamp.
 */
public class ST0603TimeStamp
{
    // this is conceptually unsigned, so be careful when manipulating it.
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
            throw new IllegalArgumentException("Timestamp must be in range [0,2^64-1]");
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
            throw new IllegalArgumentException("Timestamp encoding is an 8-byte unsigned int");
        }
        microseconds = PrimitiveConverter.toInt64(bytes);
    }

    /**
     * Create from {@code DateTime}
     * @param dateTime The date and time
     */
    public ST0603TimeStamp(LocalDateTime dateTime)
    {
        try
        {
            // TODO: Not really UTC...
            microseconds = ChronoUnit.MICROS.between(Instant.EPOCH, dateTime.toInstant(ZoneOffset.UTC));
        }
        catch (ArithmeticException e)
        {
            throw new IllegalArgumentException("Timestamp must be before April 11, 2262 23:47:16.854Z");
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

    public byte[] getBytes()
    {
        return PrimitiveConverter.int64ToBytes(microseconds);
    }

    /**
     * Get the value as a {@code LocalDateTime}
     * @return The date and time
     */
    public LocalDateTime getDateTime()
    {
        // TODO: not really UTC
        return LocalDateTime.ofEpochSecond(microseconds / 1000000, (int)(microseconds % 1000000) * 1000, ZoneOffset.UTC);
    }

    public String getDisplayableValue()
    {
        return "" + microseconds;
    }
}

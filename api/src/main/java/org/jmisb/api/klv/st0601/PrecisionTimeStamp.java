package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

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
public class PrecisionTimeStamp implements IUasDatalinkValue
{
    // Yes, using a long here means overflow in the year 2262, but there's no unsigned long in java
    // and the cost of using something like BigInteger seems unnecessary
    private long microseconds;

    /**
     * Create from value
     * @param microseconds Microseconds since the epoch
     */
    public PrecisionTimeStamp(long microseconds)
    {
        if (microseconds < 0)
        {
            throw new IllegalArgumentException("Precision Timestamp must be in range [0,2^64-1]");
        }
        this.microseconds = microseconds;
    }

    /**
     * Create from encoded bytes
     * @param bytes Encoded byte array
     */
    public PrecisionTimeStamp(byte[] bytes)
    {
        if (bytes.length != 8)
        {
            throw new IllegalArgumentException("Precision Time Stamp encoding is an 8-byte unsigned int");
        }
        microseconds = PrimitiveConverter.toInt64(bytes);
    }

    /**
     * Create from {@code LocalDateTime}
     * @param localDateTime The UTC date and time
     */
    public PrecisionTimeStamp(LocalDateTime localDateTime)
    {
        try
        {
            microseconds = ChronoUnit.MICROS.between(Instant.EPOCH, localDateTime.toInstant(ZoneOffset.UTC));
        }
        catch (ArithmeticException e)
        {
            throw new IllegalArgumentException("Precision Timestamp must be before April 11, 2262 23:47:16.854Z");
        }
    }

    /**
     * Get the value
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
     * Get the value as a {@code LocalDateTime}
     * @return The UTC date and time
     */
    LocalDateTime getLocalDateTime()
    {
        return LocalDateTime.ofEpochSecond(microseconds / 1000000,
            (int)(microseconds % 1000000) * 1000, ZoneOffset.UTC);
    }

    @Override
    public String getDisplayableValue()
    {
        return "" + microseconds;
    }
}

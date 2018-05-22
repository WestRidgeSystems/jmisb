package org.jmisb.api.klv.st0601;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
public class PrecisionTimeStamp implements UasDatalinkValue
{
    private BigInteger microseconds;
    private final static BigInteger oneThousand = BigInteger.valueOf(1000);
    private final static BigInteger maxValue = BigInteger.valueOf(2).pow(64).subtract(BigInteger.ONE);

    /**
     * Create from value
     * @param microseconds Microseconds since the epoch
     */
    public PrecisionTimeStamp(BigInteger microseconds)
    {
        if (microseconds.compareTo(BigInteger.ZERO) < 0 || microseconds.compareTo(maxValue) > 0)
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
        microseconds = new BigInteger(bytes);
    }

    /**
     * Create from {@code LocalDateTime}
     * @param localDateTime The UTC date and time
     */
    public PrecisionTimeStamp(LocalDateTime localDateTime)
    {
        this(BigInteger.valueOf(localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()).
                multiply(BigInteger.valueOf(1000)));
    }

    /**
     * Get the value
     * @return Number of microseconds since the epoch
     */
    BigInteger getMicroseconds()
    {
        return microseconds;
    }

    @Override
    public byte[] getBytes()
    {
        byte[] array = microseconds.toByteArray();

        // Since array.length will not in general be 8 bytes, need to pad with zeros from the left
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.position(8 - array.length);
        buffer.put(array);
        return buffer.array();
    }

    /**
     * Get the value as a {@code LocalDateTime}
     * @return The UTC date and time
     */
    LocalDateTime getLocalDateTime()
    {
        BigInteger milliseconds = microseconds.divide(oneThousand);
        return Instant.ofEpochMilli(milliseconds.longValue()).atZone(ZoneOffset.UTC).toLocalDateTime();
    }
}

package org.jmisb.api.klv.st0903;

import org.jmisb.core.klv.PrimitiveConverter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Precision Time Stamp (ST0903 VMTI LS Tag 2).
 * <p>
 * From ST0903:
 * <blockquote>
 * Defined in MISB ST 0603 the Precision Time Stamp is the number of
 * microseconds elapsed since midnight (00:00:00), January 1,1970 not including
 * leap seconds. If the VMTI LS is subordinate to a MISB ST 0601 LS as Tag 74, a
 * timestamp will already be present in the MISB ST 0601 LS, and in this case,
 * the VMTI LS Precision Time Stamp is optional – but recommended. Although not
 * required (as some systems may not have a time source), when included a
 * Precision Time Stamp is at the beginning of the value portion of a VMTI LS.
 * <p>
 * Some VMTI systems may not have access to a time reference; for this reason,
 * the Precision Time Stamp is not mandatory. Data from such systems is still
 * useful, however, even if only aligned with the Motion Imagery by time of
 * arrival.
 * <p>
 * Note: In the absence of a Precision Time Stamp, the VMTI system should use
 * the source (sensor) frame number to populate the Motion Imagery Frame Number
 * metadata item – if known. When recording the frame number, it is important to
 * account for timing differences in the sensor/compression signal path. The
 * frame number at the sensor and the frame number after compression may be
 * different. In most cases a frame number in a sequence of frames begins at
 * some arbitrary number (i.e., not expected to begin at 1). If the number
 * monotonically increases for successive frames, it will provide a metric for
 * establishing a correspondence.
 * </blockquote>
 */
public class PrecisionTimeStamp implements IVmtiMetadataValue
{
    // Java can treat a long as unsigned, as long as we use the right methods.
    private long microseconds;

    /**
     * Create from value.
     *
     * @param microseconds Microseconds since the epoch
     */
    public PrecisionTimeStamp(long microseconds)
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
    public PrecisionTimeStamp(byte[] bytes)
    {
        if (bytes.length != 8)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " encoding is an 8-byte unsigned int");
        }
        microseconds = PrimitiveConverter.toInt64(bytes);
    }

    /**
     * Create from {@code LocalDateTime}
     * @param dateTime The UTC date and time
     */
    public PrecisionTimeStamp(ZonedDateTime dateTime)
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
        Instant instant = Instant.ofEpochSecond(microseconds / 1000000, (int)(microseconds % 1000000) * 1000);
        return ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }

    @Override
    public String getDisplayableValue()
    {
        return "" + microseconds;
    }

    @Override
    public final String getDisplayName()
    {
        return "Precision Time Stamp";
    }
}

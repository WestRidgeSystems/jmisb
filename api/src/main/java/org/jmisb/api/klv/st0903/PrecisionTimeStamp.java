package org.jmisb.api.klv.st0903;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

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
public class PrecisionTimeStamp extends ST0603TimeStamp implements IVmtiMetadataValue
{
    /**
     * Create from value.
     *
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
    }

    /**
     * Create from {@code LocalDateTime}
     * @param dateTime The date and time
     */
    public PrecisionTimeStamp(LocalDateTime dateTime)
    {
        super(dateTime);
    }

    @Override
    public final String getDisplayName()
    {
        return "Precision Time Stamp";
    }

}

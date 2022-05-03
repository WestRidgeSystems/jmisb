package org.jmisb.st1602;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * Precision Time Stamp (ST 1602 Composite Imaging Local Set Tag 1).
 *
 * <p>From ST 1602:
 *
 * <blockquote>
 *
 * <p>MISB ST 0603 defines the Precision Time Stamp. This item is mandated by the MISP within a
 * Motion Imagery stream. The item is listed as Optional in the Composite Imaging Local Set because
 * it is assumed to be present at the parent level set in which the Composite Imaging Local Set is
 * embedded. The item may be included within the Composite Imaging Local Set in cases where a Source
 * Image, or subsequent process, has a different timestamp than the parent timestamp. The use case
 * will determine if additional timestamps are important to the application.
 *
 * </blockquote>
 *
 * <p>This item is optional within the Composite Imaging Local Set.
 */
public class PrecisionTimeStamp extends ST0603TimeStamp implements ICompositeImagingValue {
    /**
     * Create from value.
     *
     * @param microseconds Microseconds since the epoch
     */
    public PrecisionTimeStamp(long microseconds) {
        super(microseconds);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array (length 8 bytes)
     */
    public PrecisionTimeStamp(byte[] bytes) {
        super(bytes);
        if (bytes.length < Long.BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is an 8-byte unsigned int");
        }
    }

    /**
     * Create from {@code LocalDateTime}.
     *
     * @param dateTime The date and time
     */
    public PrecisionTimeStamp(LocalDateTime dateTime) {
        super(dateTime);
    }

    @Override
    public final String getDisplayName() {
        return "Precision Time Stamp";
    }

    @Override
    public byte[] getBytes() {
        return getBytesFull();
    }
}

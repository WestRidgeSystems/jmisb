package org.jmisb.st1002;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * Range Image Precision Time Stamp (ST 1002 Range Image Local Set Tag 1).
 *
 * <p>The Range Image Precision Time Stamp is the time when the measurements of the Range Image
 * occurred. This time information is used to coordinate the Range Image with other sources of data,
 * such as a collaborative sensors image or other sensor data. The time value is an invocation of
 * the MISP Precision Time Stamp, a 64-bit unsigned integer that represents the number of
 * microseconds since midnight of January 1st 1970 without leap-seconds, as defined in MISB ST 0603
 * and detailed in the Motion Imagery Handbook.
 *
 * <p>The Range Image Precision Time Stamp is required in all Range Image Local Sets, and must be
 * the first item. Positioning the Precision Time Stamp tag as the first item facilitates rapidly
 * checking whether the Local Set matches the desired time for processing a collaborative image.
 */
public class ST1002PrecisionTimeStamp extends ST0603TimeStamp implements IRangeImageMetadataValue {
    /**
     * Create from value.
     *
     * @param microseconds Microseconds since the epoch
     */
    public ST1002PrecisionTimeStamp(long microseconds) {
        super(microseconds);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, length of 8 bytes.
     */
    public ST1002PrecisionTimeStamp(byte[] bytes) {
        super(bytes);
        if (bytes.length < 8) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is an 8-byte unsigned int");
        }
    }

    /**
     * Create from {@code LocalDateTime}.
     *
     * @param dateTime The date and time
     */
    public ST1002PrecisionTimeStamp(LocalDateTime dateTime) {
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

package org.jmisb.st0808;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * Message Creation Time (ST0808 Ancillary Text Local Set Tag 5).
 *
 * <p>The Message Creation Time element identifies the time at which the ancillary text message was
 * created (e.g. if added to the stream during analysis subsequent to the initial recording).
 *
 * <p>This element is encoded in the same manner as the Precision Time Stamp defined in MISB ST
 * 0603. Whereas the mandatory {@link PrecisionTimeStamp} marks wherein the Motion Imagery stream
 * the message applies, the Message Creation Time marks when the message is generated. For example,
 * if a message applies to Motion Imagery with frame time X, but is being created at a later time,
 * such as in a latter phase of exploitation, the message would have a Message Creation Time of Y.
 * In the case where the message is made in real time with respect to the Motion Imagery, then the
 * times X and Y would be the same.
 *
 * <p>The Precision Time Stamp is an optional element in the ancillary text metadata set.
 *
 * <p>Resolution is 1 microsecond.
 */
public class MessageCreationTime extends ST0603TimeStamp implements IAncillaryTextMetadataValue {
    /**
     * Create from value.
     *
     * @param microseconds Microseconds since the epoch
     */
    public MessageCreationTime(long microseconds) {
        super(microseconds);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, length of 8 bytes.
     */
    public MessageCreationTime(byte[] bytes) {
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
    public MessageCreationTime(LocalDateTime dateTime) {
        super(dateTime);
    }

    @Override
    public final String getDisplayName() {
        return "Message Creation Time";
    }

    @Override
    public byte[] getBytes() {
        return getBytesFull();
    }

    @Override
    public String getDisplayableValue() {
        return getDisplayableValueDateTime();
    }
}

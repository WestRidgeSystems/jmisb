package org.jmisb.api.klv.st1108.st1108_2;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;

/**
 * Most Recent Frame Time.
 *
 * <p>From ST 1108.2:
 *
 * <blockquote>
 *
 * <p>The time stamp of the last frame in the rating sequence is the frame with the largest time
 * stamp value.
 *
 * <p>If a chip, or feature from a single frame is sent, then the time stamp is that frame and the
 * duration is set to 1.
 *
 * </blockquote>
 */
public class MostRecentFrameTime implements IInterpretabilityQualityMetadataValue {

    private ST0603TimeStamp time;
    private static final int REQUIRED_BYTES = 8;

    /**
     * Create from value.
     *
     * @param frameTime the frame time of the most recent frame
     */
    public MostRecentFrameTime(final ST0603TimeStamp frameTime) {
        this.time = frameTime;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 8 bytes.
     */
    public MostRecentFrameTime(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(getDisplayName() + " encoding is 8 byte");
        }
        time = new ST0603TimeStamp(bytes);
    }

    @Override
    public String getDisplayableValue() {
        return time.getDisplayableValue();
    }

    @Override
    public final String getDisplayName() {
        return "Most Recent Frame Time";
    }

    /**
     * Get the time.
     *
     * @return the time as a timestamp
     */
    public ST0603TimeStamp getTime() {
        return time;
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(LegacyIQMetadataKey.MostRecentFrameTime.getIdentifier());
        byte[] valueBytes = time.getBytesFull();
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}

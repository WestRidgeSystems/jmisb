package org.jmisb.api.klv.st1205;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * Time Stamp of Last Frame in Sequence (ST1205 Item 1).
 *
 * <p>The POSIX microsecond time stamp of the last frame in the calibration sequence.
 */
public class TimeStampOfLastFrameInSequence extends ST0603TimeStamp
        implements ICalibrationMetadataValue {
    /**
     * Create from value.
     *
     * @param microseconds Microseconds since the epoch
     */
    public TimeStampOfLastFrameInSequence(long microseconds) {
        super(microseconds);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, 8 bytes length
     */
    public TimeStampOfLastFrameInSequence(byte[] bytes) {
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
    public TimeStampOfLastFrameInSequence(LocalDateTime dateTime) {
        super(dateTime);
    }

    @Override
    public final String getDisplayName() {
        return "Time Stamp of Last Frame in Sequence";
    }

    @Override
    public byte[] getBytes() {
        return getBytesFull();
    }
}

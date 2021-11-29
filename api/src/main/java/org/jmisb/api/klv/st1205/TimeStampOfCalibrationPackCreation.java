package org.jmisb.api.klv.st1205;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * Time Stamp of Calibration Pack Creation (ST1205 Item 3).
 *
 * <p>The Calibration Pack Creation time is the current time (POSIX microsecond time) when the
 * calibration key value(s) are inserted into the stream. The reason for this key is to indicate the
 * delay between the metadata calibration indication and the calibration data. Note the delay can be
 * negative when the metadata is inserted before the calibration event happens. That is, if the
 * Calibration Pack Creation Time (Tag 3) is less than the Time Stamp of the Last Frame in Sequence
 * (Tag 1), then the metadata message is a prediction of when the calibration sequence will end.
 * This alternative is provided to enable receiving equipment to prepare for receipt of motion
 * imagery test sequences in real-time. The predicted start time of the test sequence can be
 * calculated by taking into account the “Sequence Duration” metadata value.
 */
public class TimeStampOfCalibrationPackCreation extends ST0603TimeStamp
        implements ICalibrationMetadataValue {
    /**
     * Create from value.
     *
     * @param microseconds Microseconds since the epoch
     */
    public TimeStampOfCalibrationPackCreation(long microseconds) {
        super(microseconds);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, 8 bytes length
     */
    public TimeStampOfCalibrationPackCreation(byte[] bytes) {
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
    public TimeStampOfCalibrationPackCreation(LocalDateTime dateTime) {
        super(dateTime);
    }

    @Override
    public final String getDisplayName() {
        return "Time Stamp of Calibration Pack Creation";
    }

    @Override
    public byte[] getBytes() {
        return getBytesFull();
    }
}

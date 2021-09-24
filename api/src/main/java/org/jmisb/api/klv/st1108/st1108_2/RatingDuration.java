package org.jmisb.api.klv.st1108.st1108_2;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Rating Duration (ST 1108.2 Tag 7).
 *
 * <p>From ST 1108.2:
 *
 * <blockquote>
 *
 * <p>The rating duration is expressed in frames. The maximum value is 65535. When chips or features
 * are inserted from a single frame, the duration is set to one.
 *
 * </blockquote>
 */
public class RatingDuration implements IInterpretabilityQualityMetadataValue {
    private final int duration;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 65535;
    private static final int REQUIRED_BYTES = 2;

    /**
     * Create from value.
     *
     * @param duration The rating duration, in number of frames.
     */
    public RatingDuration(int duration) {
        if (duration < MIN_VALUE || duration > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [0,65535]");
        }
        this.duration = duration;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 2 bytes.
     */
    public RatingDuration(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is two byte unsigned integer");
        }
        duration = PrimitiveConverter.toUint16(bytes);
    }

    /**
     * Get the rating duration.
     *
     * @return The duration, in number of frames.
     */
    public int getDuration() {
        return duration;
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d frames", duration);
    }

    @Override
    public final String getDisplayName() {
        return "Rating Duration";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(LegacyIQMetadataKey.RatingDuration.getIdentifier());
        byte[] valueBytes = PrimitiveConverter.uint16ToBytes(duration);
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}

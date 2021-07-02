package org.jmisb.api.klv.st1108.st1108_2;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Video Interpretability (ST 1108.2 Tag 2).
 *
 * <p>From ST 1108.2:
 *
 * <blockquote>
 *
 * <p>The estimated interpretability is for an interval of video, nominally at least 10-seconds,
 * played at the sample frame rate speed.
 *
 * <p>The estimated interpretability can be either manual or automatic as indicated by the
 * Interpretability-Quality (IQ) Method ID.
 *
 * </blockquote>
 */
public class VideoInterpretability implements IInterpretabilityQualityMetadataValue {
    private int interpretability;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 14;
    private static final int REQUIRED_BYTES = 1;

    /**
     * Create from value.
     *
     * @param estimatedInterpretability The estimated interpretability.
     */
    public VideoInterpretability(int estimatedInterpretability) {
        if (estimatedInterpretability < MIN_VALUE || estimatedInterpretability > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [0,14]");
        }
        this.interpretability = estimatedInterpretability;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 1 byte.
     */
    public VideoInterpretability(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is one byte unsigned integer");
        }
        interpretability = PrimitiveConverter.toUint8(bytes);
    }

    /**
     * Get the estimated interpretability.
     *
     * @return The interpretability metric.
     */
    public int getInterpretability() {
        return interpretability;
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", interpretability);
    }

    @Override
    public final String getDisplayName() {
        return "Video Interpretability";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(LegacyIQMetadataKey.VideoInterpretability.getIdentifier());
        byte[] valueBytes = PrimitiveConverter.uint8ToBytes((short) interpretability);
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}

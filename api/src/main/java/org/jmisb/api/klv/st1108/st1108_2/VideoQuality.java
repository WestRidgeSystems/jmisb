package org.jmisb.api.klv.st1108.st1108_2;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Video Quality (ST 1108.2 Tag 3).
 *
 * <p>From ST 1108.2:
 *
 * <blockquote>
 *
 * <p>The estimated quality is expressed using a score in the range of 0-100, where the following
 * adjectives describe the rating: Excellent 100-80; Good 79-60; Fair 59-40; Poor 39-20; Bad 19-0.
 *
 * <p>The estimated quality can be either manual or automatic as indicated by the Interpretability-
 * Quality (IQ) Method ID.
 *
 * </blockquote>
 */
public class VideoQuality implements IInterpretabilityQualityMetadataValue {
    private int quality;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 100;
    private static final int REQUIRED_BYTES = 1;

    /**
     * Create from value.
     *
     * @param estimatedQuality The estimated quality.
     */
    public VideoQuality(int estimatedQuality) {
        if (estimatedQuality < MIN_VALUE || estimatedQuality > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [0,100]");
        }
        this.quality = estimatedQuality;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 1 byte.
     */
    public VideoQuality(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is one byte unsigned integer");
        }
        quality = PrimitiveConverter.toUint8(bytes);
    }

    /**
     * Get the estimated quality.
     *
     * @return The quality metric.
     */
    public int getQuality() {
        return quality;
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", quality);
    }

    @Override
    public final String getDisplayName() {
        return "Video Quality";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(LegacyIQMetadataKey.VideoQuality.getIdentifier());
        byte[] valueBytes = PrimitiveConverter.uint8ToBytes((short) quality);
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}

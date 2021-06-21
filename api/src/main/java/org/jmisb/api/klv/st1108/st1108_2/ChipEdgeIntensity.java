package org.jmisb.api.klv.st1108.st1108_2;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Chip Edge Intensity (ST 1108.2 Tag 12).
 *
 * <p>Edge intensity (EI) is calculated from the Sobel horizontal and vertical filtered versions of
 * the input sub-image (chip) rows and columns.
 *
 * <p>See ST 1108.2 Section 7.12.
 */
public class ChipEdgeIntensity implements IInterpretabilityQualityMetadataValue {
    private int intensity;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 1000;
    private static final int REQUIRED_BYTES = 2;

    /**
     * Create from value.
     *
     * @param intensity The edge intensity.
     */
    public ChipEdgeIntensity(int intensity) {
        if (intensity < MIN_VALUE || intensity > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [0,1000]");
        }
        this.intensity = intensity;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 2 bytes.
     */
    public ChipEdgeIntensity(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is two byte unsigned integer");
        }
        intensity = PrimitiveConverter.toUint16(bytes);
    }

    /**
     * Get the intensity.
     *
     * @return The intensity.
     */
    public int getIntensity() {
        return intensity;
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", intensity);
    }

    @Override
    public final String getDisplayName() {
        return "Chip Edge Intensity";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(LegacyIQMetadataKey.ChipEdgeIntensity.getIdentifier());
        byte[] valueBytes = PrimitiveConverter.uint16ToBytes(intensity);
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}

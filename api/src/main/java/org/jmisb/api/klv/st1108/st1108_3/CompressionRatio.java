package org.jmisb.api.klv.st1108.st1108_3;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Compression Ratio.
 *
 * <p>From ST 1108.3:
 *
 * <blockquote>
 *
 * The Compression Ratio mandatory item, expressed as an IEEE floating point value, is the ratio of
 * source to compressed signal data quantity. For no compression (i.e., uncompressed) specify a
 * value of 1.0.
 *
 * </blockquote>
 */
public class CompressionRatio implements IInterpretabilityQualityMetadataValue {
    private double compressionRatio;
    private static final int MIN_VALUE = 0;

    /**
     * Create from value.
     *
     * @param ratio The compression ratio (source to compressed signal). Typically greater than 1.0.
     *     1.0 for uncompressed imagery.
     */
    public CompressionRatio(double ratio) {
        if (ratio <= MIN_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value be greater than 0.0");
        }
        this.compressionRatio = ratio;
    }

    /**
     * Create from value.
     *
     * @param ratio The compression ratio (source to compressed signal). Typically greater than 1.0.
     *     1.0 for uncompressed imagery.
     */
    public CompressionRatio(float ratio) {
        this((double) ratio);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 4 or 8 bytes, IEEE-754 format.
     */
    public CompressionRatio(byte[] bytes) {
        if ((bytes.length != Float.BYTES) && (bytes.length != Double.BYTES)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is four or eight byte floating point value");
        }
        this.compressionRatio = PrimitiveConverter.toFloat64(bytes);
    }

    /**
     * Get the compression ratio.
     *
     * @return The compression ratio.
     */
    public double getCompressionRatio() {
        return compressionRatio;
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.2f", compressionRatio);
    }

    @Override
    public final String getDisplayName() {
        return "Compression Ratio";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(IQMetadataKey.CompressionRatio.getIdentifier());
        byte[] valueBytes = PrimitiveConverter.float32ToBytes((float) compressionRatio);
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}

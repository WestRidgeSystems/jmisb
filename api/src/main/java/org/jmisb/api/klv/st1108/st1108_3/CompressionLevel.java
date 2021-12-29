package org.jmisb.api.klv.st1108.st1108_3;

import java.nio.charset.StandardCharsets;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;

/**
 * Compression Level.
 *
 * <p>From ST 1108.3:
 *
 * <blockquote>
 *
 * The Compression Level mandatory item is a valid MISP-approved level for H.262, H.264 and H.265
 * specified as a string value. Example typical values for a compression level are 4, 4.1, 4.2, 5
 * for H.264/H.265 and ML, HL for H.262. Thus, the string length for this item is three characters
 * or less. See the MISP for allowed levels. For no compression (i.e., uncompressed) specify a value
 * of “N/A”.
 *
 * </blockquote>
 */
public class CompressionLevel implements IInterpretabilityQualityMetadataValue {
    private String level;
    private static final int MAX_LENGTH = 3;

    /**
     * Create from value.
     *
     * @param compressionLevel the compression level as a string value.
     */
    public CompressionLevel(String compressionLevel) {
        if (compressionLevel.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " maximum length is " + MAX_LENGTH);
        }
        this.level = compressionLevel;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 3 bytes.
     */
    public CompressionLevel(byte[] bytes) {
        if (bytes.length > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " maximum length is " + MAX_LENGTH);
        }
        this.level = new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Get the Compression Level.
     *
     * @return The compression level as a String
     */
    public String getCompressionLevel() {
        return level;
    }

    @Override
    public String getDisplayableValue() {
        return getCompressionLevel();
    }

    @Override
    public final String getDisplayName() {
        return "Compression Level";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(IQMetadataKey.CompressionLevel.getIdentifier());
        byte[] valueBytes = level.getBytes(StandardCharsets.UTF_8);
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}

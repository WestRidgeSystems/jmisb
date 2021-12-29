package org.jmisb.api.klv.st1108.st1108_2;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;

/**
 * Chip Luminosity Values Uncompressed (ST 1108.2 Tag 10).
 *
 * <p>The chip values can be 8-bit or 16-bit depending on the bit depth. Imagery greater than 8-bits
 * is represented in two-bytes with the first byte being the most significant.
 *
 * <p>See ST 1108.2 Section 7.10.
 */
public class ChipValuesUncompressed implements IInterpretabilityQualityMetadataValue {
    private final byte[] bytes;

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array.
     */
    public ChipValuesUncompressed(byte[] bytes) {
        this.bytes = bytes.clone();
    }

    /**
     * Get the bytes.
     *
     * @return The bytes for the uncompressed values.
     */
    public byte[] getBytes() {
        return bytes.clone();
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d bytes", bytes.length);
    }

    @Override
    public final String getDisplayName() {
        return "Chip Y-values Uncompressed";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(LegacyIQMetadataKey.ChipYvaluesUncompressed.getIdentifier());
        arrayBuilder.appendAsBerLength(bytes.length);
        arrayBuilder.append(bytes);
    }
}

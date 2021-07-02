package org.jmisb.api.klv.st1108.st1108_2;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;

/**
 * Chip Luminosity Values PNG (ST 1108.2 Tag 11).
 *
 * <p>Chip image luminosity values can be losslessly compressed using PNG format and inserted into
 * the metadata stream.
 *
 * <p>See ST 1108.2 Section 7.11.
 */
public class ChipValuesPNG implements IInterpretabilityQualityMetadataValue {
    private final byte[] bytes;

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array.
     */
    public ChipValuesPNG(byte[] bytes) {
        this.bytes = bytes.clone();
    }

    /**
     * Get the bytes.
     *
     * @return The bytes for the PNG image.
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
        return "Chip Y-values PNG";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(LegacyIQMetadataKey.ChipYvaluesPNG.getIdentifier());
        arrayBuilder.appendAsBerLength(bytes.length);
        arrayBuilder.append(bytes);
    }
}

package org.jmisb.api.klv.st0602;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Active Samples per Line.
 *
 * <p>Total number of active samples (columns) in a line of an image matrix.
 */
public class ActiveSamplesPerLine implements IAnnotationMetadataValue {
    private int number;

    /**
     * Create from value.
     *
     * @param samples the number of samples per line
     */
    public ActiveSamplesPerLine(int samples) {
        number = samples;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 2
     */
    public ActiveSamplesPerLine(byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException(
                    "Active Samples Per Line encoding is a two-byte unsigned int");
        }
        number = PrimitiveConverter.toUint16(bytes);
    }

    /**
     * Get the number of samples per line.
     *
     * @return The number of samples (columns) as an unsigned integer
     */
    public int getNumber() {
        return number;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint16ToBytes(number);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", number);
    }

    @Override
    public String getDisplayName() {
        return "Active Samples Per Line";
    }
}

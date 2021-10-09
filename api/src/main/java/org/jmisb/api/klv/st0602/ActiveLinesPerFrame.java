package org.jmisb.api.klv.st0602;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Active Lines per Frame.
 *
 * <p>Total number of active lines (rows) in a frame of an image matrix.
 */
public class ActiveLinesPerFrame implements IAnnotationMetadataValue {
    private int number;

    /**
     * Create from value.
     *
     * @param lines the number of lines per frame
     */
    public ActiveLinesPerFrame(int lines) {
        number = lines;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 2
     */
    public ActiveLinesPerFrame(byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException(
                    "Active Lines Per Frame encoding is a two-byte unsigned int");
        }
        number = PrimitiveConverter.toUint16(bytes);
    }

    /**
     * Get the number of lines per frame.
     *
     * @return The number of lines (rows) as an unsigned integer
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
        return "Active Lines Per Frame";
    }
}

package org.jmisb.api.klv.st0602;

import org.jmisb.api.common.KlvParseException;
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
     * @param lines the number of lines per frame (in the range [0, 65535]).
     * @throws KlvParseException if {@code lines} is not in the valid range
     */
    public ActiveLinesPerFrame(int lines) throws KlvParseException {
        if ((lines < 0) || (lines > 65535)) {
            throw new KlvParseException("Active Lines Per Frame must be in the range [0, 65535]");
        }
        number = lines;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 2
     * @throws KlvParseException if the length is not valid
     */
    public ActiveLinesPerFrame(byte[] bytes) throws KlvParseException {
        if (bytes.length != 2) {
            throw new KlvParseException(
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

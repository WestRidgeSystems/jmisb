package org.jmisb.st1602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Source Image Columns (ST 1602 Composite Imaging Local Set Tag 4).
 *
 * <p>From ST 1602:
 *
 * <blockquote>
 *
 * <p>The Source Image is the image directly produced by the sensor imager. The Source Image Rows
 * and Source Image Columns follow from the number of samples in the vertical and horizontal
 * dimensions respectively.
 *
 * <p>Source Image Columns is the number of image samples in the horizontal direction (i.e. image
 * width) of the source image for the described sub-image.
 *
 * </blockquote>
 *
 * <p>This item is optional within the Composite Imaging Local Set.
 */
public class SourceImageColumns implements ICompositeImagingValue {
    private final long columnCount;
    private static final int MIN_VALUE = 0;

    /**
     * Create from value.
     *
     * @param columns the number of columns
     */
    public SourceImageColumns(long columns) {
        if (columns < MIN_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be non-negative");
        }
        this.columnCount = columns;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes byte array encoding of the unsigned integer value
     * @throws KlvParseException if the encoded bytes could not be deserialised
     */
    public SourceImageColumns(byte[] bytes) throws KlvParseException {
        try {
            this.columnCount = PrimitiveConverter.variableBytesToUint32(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise ST 1602 SourceImageColumns: " + ex.getMessage());
        }
    }

    /**
     * Get the number of columns.
     *
     * @return The number of columns
     */
    public long getColumns() {
        return columnCount;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uintToVariableBytes(columnCount);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%dpx", getColumns());
    }

    @Override
    public final String getDisplayName() {
        return "Source Image Columns";
    }
}

package org.jmisb.st1602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Source Image Rows (ST 1602 Composite Imaging Local Set Tag 3).
 *
 * <p>From ST 1602:
 *
 * <blockquote>
 *
 * <p>The Source Image is the image directly produced by the sensor imager. The Source Image Rows
 * and Source Image Columns follow from the number of samples in the vertical and horizontal
 * dimensions respectively.
 *
 * <p>Source Image Rows is the number of image samples in the vertical direction (i.e. image height)
 * of the source image for the described sub-image.
 *
 * </blockquote>
 *
 * <p>This item is optional within the Composite Imaging Local Set.
 */
public class SourceImageRows implements ICompositeImagingValue {
    private final long rowCount;
    private static final int MIN_VALUE = 0;

    /**
     * Create from value.
     *
     * @param rows the number of rows
     */
    public SourceImageRows(long rows) {
        if (rows < MIN_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be non-negative");
        }
        this.rowCount = rows;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes byte array encoding of the unsigned integer value
     * @throws KlvParseException if the encoded bytes could not be deserialised
     */
    public SourceImageRows(byte[] bytes) throws KlvParseException {
        try {
            this.rowCount = PrimitiveConverter.variableBytesToUint32(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise ST 1602 SourceImageRows: " + ex.getMessage());
        }
    }

    /**
     * Get the number of rows.
     *
     * @return The number of rows
     */
    public long getRows() {
        return rowCount;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uintToVariableBytes(rowCount);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%dpx", getRows());
    }

    @Override
    public final String getDisplayName() {
        return "Source Image Rows";
    }
}

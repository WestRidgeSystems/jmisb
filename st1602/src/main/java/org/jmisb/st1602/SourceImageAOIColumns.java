package org.jmisb.st1602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Source Image AOI Columns (ST 1602 Composite Imaging Local Set Tag 6).
 *
 * <p>From ST 1602:
 *
 * <blockquote>
 *
 * <p>The Source Image AOI is a rectangular area-of-interest extracted from the Source Image. The
 * following parameters describe the number of samples vertically (Source Image AOI Rows), the
 * number of samples horizontally (Source Image AOI Columns). In addition, the offset position of
 * the AOI relative to the Source Image AOI position of (0,0) is described with the Source Image AOI
 * X Position and Source Image AOI Y Position parameters.
 *
 * <p>Source Image AOI Columns is the number of image samples in the horizontal direction (i.e.
 * image width) of the Source Image AOI. The Source Image AOI is the image used to create a
 * Sub-Image of the Source Image. In cases where the entire Source Image is mapped directly to its
 * Sub-Image (i.e. Source Image AOI identical to the Source Image), this parameter is not necessary
 * to specify.
 *
 * </blockquote>
 *
 * <p>This item is optional within the Composite Imaging Local Set.
 */
public class SourceImageAOIColumns implements ICompositeImagingValue {
    private final long columnCount;
    private static final int MIN_VALUE = 0;

    /**
     * Create from value.
     *
     * @param columns the number of columns
     */
    public SourceImageAOIColumns(long columns) {
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
    public SourceImageAOIColumns(byte[] bytes) throws KlvParseException {
        try {
            this.columnCount = PrimitiveConverter.variableBytesToUint32(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise ST 1602 SourceImageAOIColumns: " + ex.getMessage());
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
        return "Source Image AOI Columns";
    }
}

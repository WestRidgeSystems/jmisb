package org.jmisb.st1602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Source Image AOI Rows (ST 1602 Composite Imaging Local Set Tag 5).
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
 * <p>Source Image AOI Rows is the number of image samples in the vertical direction (i.e. image
 * height) of the Source Image AOI. The Source Image AOI is the image used to create a Sub- Image of
 * the Source Image. In cases where the entire Source Image is mapped directly to its Sub-Image
 * (i.e. Source Image AOI identical to the Source Image), this parameter is not necessary to
 * specify.
 *
 * </blockquote>
 *
 * <p>This item is optional within the Composite Imaging Local Set.
 */
public class SourceImageAOIRows implements ICompositeImagingValue {
    private final long rowCount;
    private static final int MIN_VALUE = 0;

    /**
     * Create from value.
     *
     * @param rows the number of rows
     */
    public SourceImageAOIRows(long rows) {
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
    public SourceImageAOIRows(byte[] bytes) throws KlvParseException {
        try {
            this.rowCount = PrimitiveConverter.variableBytesToUint32(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise ST 1602 SourceImageAOIRows: " + ex.getMessage());
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
        return "Source Image AOI Rows";
    }
}

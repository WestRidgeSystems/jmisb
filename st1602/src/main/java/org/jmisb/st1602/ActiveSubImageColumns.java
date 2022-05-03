package org.jmisb.st1602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Active Sub-Image Columns (ST 1602 Composite Imaging Local Set Tag 14).
 *
 * <p>From ST 1602:
 *
 * <blockquote>
 *
 * <p>A Sub-Image may represent a processed version of its Source Image (i.e. same pixel density but
 * different image characteristics), or an area of interest extracted from a Source Image, which may
 * also have been further processed. The dimensions of the Sub-Image are specified with the Sub-
 * Image Columns and Sub-Image Rows parameters. The position of the Sub-Image is with respect to the
 * (0,0) location of composite image created; the Sub-Image Position X and Sub-Image Position Y
 * parameters specify this offset.
 *
 * <p>In the event a Sub-Image does not contain all active pixels, the Active Sub-Image Rows and
 * Active Sub-Image Columns parameters enable specifying the rectangular region containing only
 * active image samples. The parameters Active Sub-Image Offset X and Active Sub-Image Offset Y
 * provide position information of where the active image lies with respect the Sub-Image.
 *
 * <p>Active Sub-Image Columns is the number of samples in the horizontal direction representing
 * Source Image content; that is, containing the portion of the Source Image of interest.
 *
 * </blockquote>
 *
 * <p>This item is optional within the Composite Imaging Local Set.
 */
public class ActiveSubImageColumns implements ICompositeImagingValue {
    private final long columnCount;
    private static final int MIN_VALUE = 0;

    /**
     * Create from value.
     *
     * @param columns the number of columns
     */
    public ActiveSubImageColumns(long columns) {
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
    public ActiveSubImageColumns(byte[] bytes) throws KlvParseException {
        try {
            this.columnCount = PrimitiveConverter.variableBytesToUint32(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise ST 1602 ActiveSubImageColumns: " + ex.getMessage());
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
        return "Active Sub-Image Columns";
    }
}

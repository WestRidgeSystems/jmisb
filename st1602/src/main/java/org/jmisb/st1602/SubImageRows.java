package org.jmisb.st1602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Sub-Image Rows (ST 1602 Composite Imaging Local Set Tag 9).
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
 * <p>Sub-Image Rows (Tag 9) is the number of image samples in the vertical direction (i.e. image
 * height) of the sub-image derived from its Source Image AOI.
 *
 * </blockquote>
 *
 * <p>This item is mandatory within the Composite Imaging Local Set.
 */
public class SubImageRows implements ICompositeImagingValue {
    private final long rowCount;
    private static final int MIN_VALUE = 0;

    /**
     * Create from value.
     *
     * @param rows the number of rows
     */
    public SubImageRows(long rows) {
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
    public SubImageRows(byte[] bytes) throws KlvParseException {
        try {
            this.rowCount = PrimitiveConverter.variableBytesToUint32(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise ST 1602 SubImageRows: " + ex.getMessage());
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
        return "Sub-Image Rows";
    }
}

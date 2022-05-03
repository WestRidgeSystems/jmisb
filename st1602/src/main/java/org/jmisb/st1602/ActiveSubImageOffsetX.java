package org.jmisb.st1602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Active Sub-Image Offset X (ST 1602 Composite Imaging Local Set Tag 15).
 *
 * <p>From ST 1602:
 *
 * <blockquote>
 *
 * <p>Active Sub-Image Offset X provides the x-coordinate of the upper left corner of the active
 * image within the Sub-Image referenced to Sub-Image Position X, where x-coordinate = Sub-Image
 * Position X + Active Sub-Image Position X.
 *
 * <p>Non-image areas, such as black borders when an image is letter-boxed, can be described by
 * knowledge of the Sub-Image Rows/Columns, Sub-Image Position X/Y, Active Image Rows/Columns, and
 * Active Sub-Image Offset X/Y.
 *
 * </blockquote>
 *
 * <p>This item is optional within the Composite Imaging Local Set.
 */
public class ActiveSubImageOffsetX implements ICompositeImagingValue {
    private final int xOffset;

    /**
     * Create from value.
     *
     * <p>Note that the X offset can be negative.
     *
     * @param x the X offset.
     */
    public ActiveSubImageOffsetX(int x) {
        this.xOffset = x;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes byte array encoding of the signed integer value (length 1 to 4 bytes)
     * @throws KlvParseException if the encoded bytes could not be deserialised
     */
    public ActiveSubImageOffsetX(byte[] bytes) throws KlvParseException {
        try {
            this.xOffset = PrimitiveConverter.toInt32(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise ST 1602 ActiveSubImageOffsetX: " + ex.getMessage());
        }
    }

    /**
     * Get the offset.
     *
     * <p>Note that the X offset can be negative.
     *
     * @return The active image X offset.
     */
    public int getOffset() {
        return xOffset;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.int32ToVariableBytes(xOffset);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%dpx", getOffset());
    }

    @Override
    public final String getDisplayName() {
        return "Active Sub-Image Offset X";
    }
}

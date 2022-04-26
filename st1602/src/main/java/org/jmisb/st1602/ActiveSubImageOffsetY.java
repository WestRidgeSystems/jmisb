package org.jmisb.st1602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Active Sub-Image Offset Y (ST 1602 Composite Imaging Local Set Tag 16).
 *
 * <p>From ST 1602:
 *
 * <blockquote>
 *
 * <p>Active Sub-Image Offset Y provides the y-coordinate of the upper left corner of the active
 * image within the Sub-Image described by the Local Set referenced to Sub-Image Position Y, where
 * y-coordinate = Sub-Image Position Y + Active Sub-Image Position Y.
 *
 * <p>Non-image areas, such as black borders when an image is letter-boxed, can be described by
 * knowledge of the Sub-Image Rows/Columns, Sub-Image Position X/Y, Active Sub-Image Rows/Columns,
 * and Active Sub-Image Offset X/Y.
 *
 * </blockquote>
 *
 * <p>This item is optional within the Composite Imaging Local Set.
 */
public class ActiveSubImageOffsetY implements ICompositeImagingValue {
    private final int yOffset;

    /**
     * Create from value.
     *
     * <p>Note that the Y offset can be negative.
     *
     * @param y the Y offset.
     */
    public ActiveSubImageOffsetY(int y) {
        this.yOffset = y;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes byte array encoding of the signed integer value (length 1 to 4 bytes)
     * @throws KlvParseException if the encoded bytes could not be deserialised
     */
    public ActiveSubImageOffsetY(byte[] bytes) throws KlvParseException {
        try {
            this.yOffset = PrimitiveConverter.toInt32(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise ST 1602 ActiveSubImageOffsetY: " + ex.getMessage());
        }
    }

    /**
     * Get the offset.
     *
     * <p>Note that the Y offset can be negative.
     *
     * @return The active image Y offset.
     */
    public int getOffset() {
        return yOffset;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.int32ToVariableBytes(yOffset);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%dpx", getOffset());
    }

    @Override
    public final String getDisplayName() {
        return "Active Sub-Image Offset Y";
    }
}

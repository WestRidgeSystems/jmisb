package org.jmisb.st1602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Sub-Image Position X (ST 1602 Composite Imaging Local Set Tag 11).
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
 * <p>Sub-Image Position X (Tag 11) is the x-coordinate of the upper left corner of a Sub-Image
 * referenced to the upper left corner, i.e. coordinates (0, 0) of the Composite Image.
 *
 * </blockquote>
 *
 * <p>This item is mandatory within the Composite Imaging Local Set.
 */
public class SubImagePositionX implements ICompositeImagingValue {
    private final int xPosition;

    /**
     * Create from value.
     *
     * <p>Note that the X position can be negative.
     *
     * @param x the X position.
     */
    public SubImagePositionX(int x) {
        this.xPosition = x;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes byte array encoding of the signed integer value (length 1 to 4 bytes)
     * @throws KlvParseException if the encoded bytes could not be deserialised
     */
    public SubImagePositionX(byte[] bytes) throws KlvParseException {
        try {
            this.xPosition = PrimitiveConverter.toInt32(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise ST 1602 SubImagePositionX: " + ex.getMessage());
        }
    }

    /**
     * Get the position.
     *
     * <p>Note that the X position can be negative.
     *
     * @return The Sub-Image X position.
     */
    public int getPosition() {
        return xPosition;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.int32ToVariableBytes(xPosition);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%dpx", getPosition());
    }

    @Override
    public final String getDisplayName() {
        return "Sub-Image Position X";
    }
}

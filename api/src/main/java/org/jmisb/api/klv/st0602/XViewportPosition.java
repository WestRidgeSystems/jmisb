package org.jmisb.api.klv.st0602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * X Viewport position coordinate value.
 *
 * <p>The X and Y Viewport Position in Pixels is the location of the MIME Data reference point. The
 * X and Y position is referenced based upon a (0, 0) origin in the upper-left corner of the
 * original essence data image.
 */
public class XViewportPosition implements IAnnotationMetadataValue {

    private final short coord;

    /**
     * Create from value.
     *
     * @param coord the x coordinate part of the annotation origin. (0,0) is top left pixel.
     */
    public XViewportPosition(short coord) {
        this.coord = coord;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of signed integer value, 2 bytes.
     * @throws KlvParseException if the length is not valid
     */
    public XViewportPosition(byte[] bytes) throws KlvParseException {
        if (bytes.length != 2) {
            throw new KlvParseException("X Viewport Position encoding is two-byte signed integer");
        }
        coord = PrimitiveConverter.toInt16(bytes);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.int16ToBytes(coord);
    }

    @Override
    public String getDisplayName() {
        return "X Viewport Position";
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d pixels", coord);
    }

    /**
     * Get the coordinate value.
     *
     * @return the coordinate value in pixels from the top left
     */
    public int getPosition() {
        return coord;
    }
}

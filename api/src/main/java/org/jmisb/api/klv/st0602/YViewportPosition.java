package org.jmisb.api.klv.st0602;

import org.jmisb.core.klv.PrimitiveConverter;

public class YViewportPosition implements IAnnotationMetadataValue {

    private final short coord;
    /**
     * Create from value.
     *
     * @param coord the y coordinate part of the annotation origin. (0,0) is top left pixel.
     */
    public YViewportPosition(short coord) {
        this.coord = coord;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of signed integer value, 2 bytes.
     */
    public YViewportPosition(byte[] bytes) {
        if (bytes.length > 127) {
            throw new IllegalArgumentException(
                    "Y Viewport Position encoding is two-byte signed integer");
        }
        coord = PrimitiveConverter.toInt16(bytes);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.int16ToBytes(coord);
    }

    @Override
    public String getDisplayName() {
        return "Y Viewport Position";
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

package org.jmisb.st1602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Transparency (ST 1602 Composite Imaging Local Set Tag 17).
 *
 * <p>The Transparency parameter provides 256 levels of image transparency. Consider an Image A,
 * with Image B superimposed on top of Image A - Image A is the background image and Image B is the
 * foreground image.
 *
 * <p>A transparency value of zero (0) for Image B represents fully opaqueness, where the overlapped
 * part of Image A is not visible and Image B is fully visible. As the transparency value increases,
 * Image A becomes more visible. A transparency value of 255 represents full transparency, where
 * Image B is not visible and Image A is fully visible. Values between zero and 255 represent levels
 * of transparency for blending two images together.
 *
 * <p>The default value for transparency is 0 when not included in the Local Set.
 *
 * <p>Note that transparency is the reverse direction to alpha values.
 *
 * <p>This item is optional within the Composite Imaging Local Set.
 */
public class Transparency implements ICompositeImagingValue {
    private final int transparency;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 255;

    /**
     * Create from value.
     *
     * @param transparency The transparency value in the valid range 0 to 255.
     */
    public Transparency(int transparency) {
        if ((transparency < MIN_VALUE) || (transparency > MAX_VALUE)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in the range [0,255]");
        }
        this.transparency = transparency;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes byte encoding of the unsigned integer value (single byte)
     * @throws KlvParseException if the encoded bytes could not be deserialised
     */
    public Transparency(byte[] bytes) throws KlvParseException {
        try {
            this.transparency = PrimitiveConverter.toUint8(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException("Unable to deserialise Transparency: " + ex.getMessage());
        }
    }

    /**
     * Get the transparency value.
     *
     * @return The transparency
     */
    public int getTransparency() {
        return transparency;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint8ToBytes((short) transparency);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", transparency);
    }

    @Override
    public final String getDisplayName() {
        return "Transparency";
    }
}

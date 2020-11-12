package org.jmisb.api.klv.st1206;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Image Rows (ST 1206 SARMI Local Set Item 9).
 *
 * <p>For SARMI, the number of image rows and columns combined with range and cross-range pixel
 * sizes give the swath size of the image patch. Pixels are assumed to appear in row-major order.
 * Image rows correspond to the height of the image and image columns correspond to the number of
 * pixels in a row.
 *
 * <p>Also, even though both measurements allow 0 as a valid value, it should not be used as it is
 * meaningless in practical applications.
 */
public class ImageRows implements ISARMIMetadataValue {
    private static final int MIN_VALUE = 1; // or 0?
    private static final int MAX_VALUE = 65535;
    private final int value;

    /**
     * Create from value.
     *
     * @param rows the number of rows in the motion imagery frame, in pixels.
     */
    public ImageRows(int rows) {
        if ((rows < MIN_VALUE) || (rows > MAX_VALUE)) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s value must be in range [%d, %d]",
                            this.getDisplayName(), MIN_VALUE, MAX_VALUE));
        }
        this.value = rows;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes number of rows, encoded as 2 byte unsigned integer
     */
    public ImageRows(byte[] bytes) {
        this.value = PrimitiveConverter.toUint16(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Image Rows";
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%dpx", value);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint16ToBytes(value);
    }

    /**
     * The number of image rows.
     *
     * @return the number of rows, in pixels.
     */
    public int getImageRows() {
        return this.value;
    }
}

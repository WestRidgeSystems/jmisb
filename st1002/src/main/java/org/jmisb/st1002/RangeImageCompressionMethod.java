package org.jmisb.st1002;

/**
 * Range Image Compression Method.
 *
 * <p>This enumeration describes the available compression used for reducing the number of bytes of
 * the range image. There are currently only two valid values - no compression, and planar fit. See
 * ST 1002.2 Section 7 for a description of planar fit.
 *
 * @see RangeImageEnumerations
 */
public enum RangeImageCompressionMethod {
    /**
     * Unknown value.
     *
     * <p>This is not a valid compression method, and indicates a problem with decoding.
     */
    UNKNOWN(-1, "Unknown"),
    /** No compression. */
    NO_COMPRESSION(0, "No Compression"),
    /** Planar fit. */
    PLANAR_FIT(1, "Planar Fit");

    /**
     * Look up Range Image Compression Method by encoded value.
     *
     * @param value the encoded (integer) value
     * @return the corresponding enumeration value
     */
    public static RangeImageCompressionMethod lookup(int value) {
        for (RangeImageCompressionMethod method : values()) {
            if (method.getEncodedValue() == value) {
                return method;
            }
        }
        return UNKNOWN;
    }

    private final int encodedValue;
    private final String textDescription;

    private RangeImageCompressionMethod(int value, String description) {
        this.encodedValue = value;
        this.textDescription = description;
    }

    /**
     * Encoded value.
     *
     * @return integer representation of the enumeration
     */
    public int getEncodedValue() {
        return encodedValue;
    }

    /**
     * Text description.
     *
     * @return human readable description of the enumeration meaning.
     */
    public String getTextDescription() {
        return textDescription;
    }
}

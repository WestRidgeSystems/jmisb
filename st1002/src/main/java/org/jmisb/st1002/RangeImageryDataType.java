package org.jmisb.st1002;

/**
 * Range Imagery Data Type.
 *
 * <p>This enumeration describes the range imagery, either Perspective Range Image or Depth Range
 * Image. Other types may be added in the future, however these two are the only valid values as of
 * ST 1002.2.
 *
 * <p>*
 *
 * <p><img src="../../../../org/jmisb/st1002/doc-files/perspectiverangeimage.png" alt="Perspective
 * Range Image Example">
 *
 * <p><img src="../../../../org/jmisb/st1002/doc-files/depthrangeimage.png" alt="Depth Range Image
 * Example">
 *
 * @see RangeImageEnumerations
 */
public enum RangeImageryDataType {
    /**
     * Unknown value.
     *
     * <p>This is not a valid data type, and indicates a problem with decoding.
     */
    UNKNOWN(-1, "Unknown"),
    /** Perspective range image. */
    PERSPECTIVE(0, "Perspective Range Image"),
    /** Depth range image. */
    DEPTH(1, "Depth Range Image");

    /**
     * Look up Range Imagery Data Type by encoded value.
     *
     * <p>This looks up the basic value (without any bit shifting applied, so Depth is {@code
     * 0b001}).
     *
     * @param value the encoded (integer) value
     * @return the corresponding enumeration value
     */
    public static RangeImageryDataType lookup(int value) {
        for (RangeImageryDataType dataType : values()) {
            if (dataType.getEncodedValue() == value) {
                return dataType;
            }
        }
        return UNKNOWN;
    }

    private final int encodedValue;
    private final String textDescription;

    private RangeImageryDataType(int value, String description) {
        this.encodedValue = value;
        this.textDescription = description;
    }

    /**
     * Encoded value.
     *
     * <p>This looks up the basic value (without any bit shifting applied, so Depth is {@code
     * 0b001}).
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

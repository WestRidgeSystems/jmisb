package org.jmisb.st1002;

/**
 * Range Image Source.
 *
 * <p>This enumeration describes how the Range Imagery was created, either from a range sensor (e.g.
 * laser range finder, or LIDAR) or computationally extracted.
 *
 * @see RangeImageEnumerations
 */
public enum RangeImageSource {
    /**
     * Unknown value.
     *
     * <p>This is not a valid image source, and indicates a problem with decoding.
     */
    UNKNOWN(-1, "Unknown"),
    /** Computationally extracted. */
    COMPUTATIONALLY_EXTRACTED(0, "Computationally Extracted"),
    /** Range sensor. */
    RANGE_SENSOR(1, "Range Sensor");

    /**
     * Look up Range Image Source by encoded value.
     *
     * <p>This looks up the basic value (without any bit shifting applied, so Range Sensor is {@code
     * 0b1}).
     *
     * @param value the encoded (integer) value
     * @return the corresponding enumeration value
     */
    public static RangeImageSource lookup(int value) {
        for (RangeImageSource source : values()) {
            if (source.getEncodedValue() == value) {
                return source;
            }
        }
        return UNKNOWN;
    }

    private final int encodedValue;
    private final String textDescription;

    private RangeImageSource(int value, String description) {
        this.encodedValue = value;
        this.textDescription = description;
    }

    /**
     * Encoded value.
     *
     * <p>This looks up the basic value (without any bit shifting applied, so Range Sensor is {@code
     * 0b1}).
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

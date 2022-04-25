package org.jmisb.st0809;

import org.jmisb.api.klv.st0107.Utf8String;

/**
 * Precipitation Type (ST 0809 Local Set Item 33).
 *
 * <p>Qualitative description of precipitation ("rain", "snow", etc.).
 */
public class PrecipitationType implements IMeteorologicalMetadataValue {
    private static final String DISPLAY_NAME = "Precipitation Type";
    private static final int MAX_BYTES = 40;
    private final Utf8String value;

    /**
     * Create from value.
     *
     * @param type precipitation type (40 bytes maximum)
     */
    public PrecipitationType(String type) {
        this.value = new Utf8String(type);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes precipitation type, UTF-8 encoded string.
     */
    public PrecipitationType(byte[] bytes) {
        this.value = new Utf8String(bytes);
    }

    /**
     * Get the value as a string.
     *
     * @return precipitation type
     */
    public String getValue() {
        return value.getValue();
    }

    @Override
    public final String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public byte[] getBytes() {
        return value.getBytesWithLimit(MAX_BYTES);
    }

    @Override
    public String getDisplayableValue() {
        return value.getValue();
    }
}

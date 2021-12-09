package org.jmisb.api.klv.st0809;

import org.jmisb.api.klv.st0107.Utf8String;

/**
 * Visibility Conditions (ST 0809 Local Set Item 24).
 *
 * <p>Qualitative description of visibility conditions ("clear", "haze", etc.)
 */
public class VisibilityConditions implements IMeteorologicalMetadataValue {
    private static final String DISPLAY_NAME = "Visibility Conditions";
    private static final int MAX_BYTES = 40;
    private final Utf8String value;

    /**
     * Create from value.
     *
     * @param conditions the visibility conditions (40 bytes maximum)
     */
    public VisibilityConditions(String conditions) {
        this.value = new Utf8String(conditions);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes visibility conditions, UTF-8 encoded string.
     */
    public VisibilityConditions(byte[] bytes) {
        this.value = new Utf8String(bytes);
    }

    /**
     * Get the value as a string.
     *
     * @return visibility conditions
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

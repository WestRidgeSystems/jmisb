package org.jmisb.st0809;

import org.jmisb.api.klv.st0107.Utf8String;

/**
 * Lighting Conditions (ST 0809 Local Set Item 22).
 *
 * <p>Qualitative description of lighting conditions generally including time of day ("dawn",
 * "pre-dawn") and cloud conditions ("partly cloudy").
 */
public class LightingConditions implements IMeteorologicalMetadataValue {
    private static final String DISPLAY_NAME = "Lighting Conditions";
    private static final int MAX_BYTES = 40;
    private final Utf8String value;

    /**
     * Create from value.
     *
     * @param conditions the lighting conditions (40 bytes maximum)
     */
    public LightingConditions(String conditions) {
        this.value = new Utf8String(conditions);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes lighting conditions, UTF-8 encoded string.
     */
    public LightingConditions(byte[] bytes) {
        this.value = new Utf8String(bytes);
    }

    /**
     * Get the value as a string.
     *
     * @return lighting conditions
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

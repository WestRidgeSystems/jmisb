package org.jmisb.api.klv.st0809;

import org.jmisb.api.klv.st0107.Utf8String;

/**
 * Cloud Sky Cover Type (ST 0809 Local Set Item 10).
 *
 * <p>Qualitative description of cloud cover (e.g. "cirrus").
 */
public class CloudSkyCoverType implements IMeteorologicalMetadataValue {
    private static final String DISPLAY_NAME = "Cloud Sky Cover Type";
    private static final int MAX_BYTES = 40;
    private final Utf8String value;

    /**
     * Create from value.
     *
     * @param type the cloud sky cover type (40 bytes maximum)
     */
    public CloudSkyCoverType(String type) {
        this.value = new Utf8String(type);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes cloud sky cover type, UTF-8 encoded string.
     */
    public CloudSkyCoverType(byte[] bytes) {
        this.value = new Utf8String(bytes);
    }

    /**
     * Get the value as a string.
     *
     * @return cloud sky cover type
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

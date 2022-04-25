package org.jmisb.st0809;

import org.jmisb.api.klv.st0107.Utf8String;

/**
 * Reporting ID (ST 0809 Local Set Item 5).
 *
 * <p>The name/location of the entity providing the meteorological data.
 */
public class ReportingID implements IMeteorologicalMetadataValue {
    private static final String DISPLAY_NAME = "Reporting ID";
    private static final int MAX_BYTES = 40;
    private final Utf8String value;

    /**
     * Create from value.
     *
     * @param id the reporting ID (40 bytes maximum)
     */
    public ReportingID(String id) {
        this.value = new Utf8String(id);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes reporting identifier, UTF-8 encoded string.
     */
    public ReportingID(byte[] bytes) {
        this.value = new Utf8String(bytes);
    }

    /**
     * Get the value as a string.
     *
     * @return reporting ID
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

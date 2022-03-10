package org.jmisb.api.klv.st0808;

import java.nio.charset.StandardCharsets;

/** Base class for text string implementations. */
public abstract class NaturalText implements IAncillaryTextMetadataValue {
    private final String displayName;
    private final String stringValue;

    /**
     * Create from value.
     *
     * @param name The display name for the string
     * @param value The string value
     */
    public NaturalText(String name, String value) {
        this.displayName = name;
        this.stringValue = value;
    }

    /**
     * Create from encoded bytes.
     *
     * @param name The display name for the string
     * @param bytes Encoded byte array
     */
    public NaturalText(String name, byte[] bytes) {
        this.displayName = name;
        this.stringValue = new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public byte[] getBytes() {
        return stringValue.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public final String getDisplayableValue() {
        return stringValue;
    }

    @Override
    public final String getDisplayName() {
        return displayName;
    }

    /**
     * Get the value of this text value.
     *
     * @return the value as a String
     */
    String getValue() {
        return stringValue;
    }
}

package org.jmisb.api.klv.st0102;

import java.nio.charset.StandardCharsets;

/** Represents an Object Country Code value in ST 0102 */
public class ObjectCountryCodeString implements ISecurityMetadataValue {
    private String stringValue;

    /**
     * Create from value
     *
     * @param value The string value
     */
    public ObjectCountryCodeString(String value) {
        this.stringValue = value;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes Encoded byte array
     */
    public ObjectCountryCodeString(byte[] bytes) {
        this.stringValue = new String(bytes, StandardCharsets.UTF_16);
    }

    /**
     * Get the value
     *
     * @return The string value
     */
    public String getValue() {
        return stringValue;
    }

    @Override
    public byte[] getBytes() {
        return stringValue.getBytes(StandardCharsets.UTF_16BE);
    }

    @Override
    public String getDisplayableValue() {
        return stringValue;
    }

    @Override
    public String getDisplayName() {
        return "Object Country Codes";
    }
}

package org.jmisb.st0102;

import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Represents an Object Country Code value in ST 0102. */
public class ObjectCountryCodeString implements ISecurityMetadataValue {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectCountryCodeString.class);

    private final String stringValue;

    /**
     * Create from value.
     *
     * @param value The string value
     */
    public ObjectCountryCodeString(String value) {
        this.stringValue = value;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public ObjectCountryCodeString(byte[] bytes) {
        if (bytes.length < 4) {
            // This probably isn't going to be a valid UTF-16 country code
            LOGGER.warn(
                    "{} has too few bytes for required UTF-16 encoding. Trying UTF-8 workaround.",
                    getDisplayName());
            this.stringValue = new String(bytes, StandardCharsets.UTF_8);
        } else {
            this.stringValue = new String(bytes, StandardCharsets.UTF_16);
        }
    }

    /**
     * Get the value.
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

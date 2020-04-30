package org.jmisb.api.klv.st0102;

import java.nio.charset.StandardCharsets;

/**
 * Represents a string value in ST 0102
 */
public class SecurityMetadataString implements ISecurityMetadataValue
{
    private String stringValue;

    /**
     * Create from value
     * @param value The string value
     */
    public SecurityMetadataString(String value)
    {
        this.stringValue = value;
    }

    /**
     * Create from encoded bytes
     * @param bytes Encoded byte array
     */
    public SecurityMetadataString(byte[] bytes)
    {
        this.stringValue = new String(bytes, StandardCharsets.US_ASCII);
    }

    /**
     * Get the value
     * @return The string value
     */
    public String getValue()
    {
        return stringValue;
    }

    @Override
    public byte[] getBytes()
    {
        return stringValue.getBytes(StandardCharsets.US_ASCII);
    }

    @Override
    public String getDisplayableValue()
    {
        return stringValue;
    }
}

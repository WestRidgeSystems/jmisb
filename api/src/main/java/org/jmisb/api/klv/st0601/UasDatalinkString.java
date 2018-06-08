package org.jmisb.api.klv.st0601;

import java.nio.charset.StandardCharsets;

/**
 * Represents a string value in ST 0601
 */
public class UasDatalinkString implements IUasDatalinkValue
{
    private String stringValue;

    /**
     * Create from value
     * @param value The string value
     */
    public UasDatalinkString(String value)
    {
        this.stringValue = value;
    }

    /**
     * Create from encoded bytes
     * @param bytes Encoded byte array
     */
    public UasDatalinkString(byte[] bytes)
    {
        this.stringValue = new String(bytes);
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

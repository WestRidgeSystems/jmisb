package org.jmisb.api.klv.st0806;

import java.nio.charset.StandardCharsets;

/**
 * Base class for text string implementations.
 */
public abstract class RvtString implements IRvtMetadataValue
{
    protected final String displayName;
    protected final String stringValue;

    /**
     * Create from value
     * @param name The display name for the string
     * @param value The string value
     */
    public RvtString(String name, String value)
    {
        this.displayName = name;
        this.stringValue = value;
    }

    /**
     * Create from encoded bytes
     * @param name The display name for the string
     * @param bytes Encoded byte array
     */
    public RvtString(String name, byte[] bytes)
    {
        this.displayName = name;
        this.stringValue = new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public byte[] getBytes()
    {
        return stringValue.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public final String getDisplayableValue()
    {
        return stringValue;
    }

    @Override
    public final String getDisplayName()
    {
        return displayName;
    }
}

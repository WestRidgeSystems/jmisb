package org.jmisb.api.klv.st0903;

import org.jmisb.api.klv.st0601.*;
import java.nio.charset.StandardCharsets;

/**
 * Represents a string value in ST 0903
 */
public class VmtiTextString implements IVmtiMetadataValue
{
    public final static String SystemName = "System Name/Description";

    public final static String SourceSensor = "Source Sensor";
    private final String displayName;
    private final String stringValue;

    /**
     * Create from value
     * @param name The display name for the string
     * @param value The string value
     */
    public VmtiTextString(String name, String value)
    {
        this.displayName = name;
        this.stringValue = value;
    }

    /**
     * Create from encoded bytes
     * @param name The display name for the string
     * @param bytes Encoded byte array
     */
    public VmtiTextString(String name, byte[] bytes)
    {
        this.displayName = name;
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
        return stringValue.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getDisplayableValue()
    {
        return stringValue;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}

package org.jmisb.api.klv.st0903;

import java.nio.charset.StandardCharsets;

/**
 * Represents a string value in ST 0903 (Tag 3 - System Name and Tag 10 - Source Sensor).
 */
public class VmtiTextString implements IVmtiMetadataValue
{
    /**
     * Tag 3 - VMTI System Name.
     * The name or description of the VMTI system producing the VMTI targets.
     * The field is free text.
     */
    public final static String SYSTEM_NAME = "System Name/Description";

    /**
     * Tag 10 - VMTI Source Sensor.
     * Free text identifier of the image source sensor.
     */
    public final static String SOURCE_SENSOR = "Source Sensor";

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
    public String getDisplayName()
    {
        return displayName;
    }
}

package org.jmisb.api.klv.st0806.poi;

import java.nio.charset.StandardCharsets;

/**
 * Represents a string value in ST 0806 RVT POI Local Set.
 *
 * These should use the ASCII (strictly, ISO-7) subset of UTF-8.
 */
public class RvtPoiTextString implements IRvtPoiMetadataValue
{
    private final String displayName;
    private final String stringValue;

    /**
     * RVT POI Local Set Tag 6 - POI/AOI Text.
     *
     * User Defined String, maximum 2048 characters.
     */
    public static final String POI_AOI_TEXT = "POI/AOI Text";

    /**
     * RVT POI Local Set Tag 7 - POI Source Icon.
     *
     * Per MIL-STD-2525B. Maximum 127 bytes. Icon used in FalconView.
     */
    public static final String POI_SOURCE_ICON = "POI Source Icon";

    /**
     * RVT POI Local Set Tag 8 - POI/AOI Source ID.
     *
     * User Defined String, maximum 255 bytes.
     */
    public static final String POI_AOI_SOURCE_ID = "POI/AOI Source ID";

    /**
     * RVT POI Local Set Tag 9 - POI/AOI Label.
     *
     * User Defined String, maximum 16 bytes.
     */
    public static final String POI_AOI_LABEL = "POI/AOI Label";

    /**
     * RVT POI Local Set Tag 10 - Operation ID.
     *
     * User Defined String, maximum 127 bytes.
     */
    public static final String OPERATION_ID = "Operation ID";

    /**
     * Create from value
     * @param name The display name for the string
     * @param value The string value
     */
    public RvtPoiTextString(String name, String value)
    {
        this.displayName = name;
        this.stringValue = value;
    }

    /**
     * Create from encoded bytes
     * @param name The display name for the string
     * @param bytes Encoded byte array
     */
    public RvtPoiTextString(String name, byte[] bytes)
    {
        this.displayName = name;
        this.stringValue = new String(bytes, StandardCharsets.UTF_8);
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

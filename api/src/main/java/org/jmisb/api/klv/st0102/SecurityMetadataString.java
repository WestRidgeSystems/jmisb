package org.jmisb.api.klv.st0102;

import java.nio.charset.StandardCharsets;

/**
 * Represents a string value in ST 0102
 */
public class SecurityMetadataString implements ISecurityMetadataValue
{
    public final static String CAVEATS = "Caveats";
    public final static String CLASSIFICATION_COMMENTS = "Classification Comments";
    public final static String CLASSIFICATION_REASON = "Classification Reason";
    public final static String CLASSIFIED_BY = "Classified By";
    public final static String CLASSIFYING_COUNTRY = "Classifying Country";
    public final static String DERIVED_FROM = "Derived From";
    public final static String MARKING_SYSTEM = "Marking System";
    public final static String OBJECT_COUNTRY_CODING_METHOD = "Object Country Coding Method";
    public final static String RELEASING_INSTRUCTIONS = "Releasing Instructions";
    public final static String SCI_SHI_INFO = "Security-SCI/SHI information";
    public final static String COUNTRY_CODING_METHOD = "Country Coding Method";

    private String stringValue;
    private String displayName;

    /**
     * Create from value
     *
     * @param displayName the display name for this particular string (see static values)
     * @param value The string value
     */
    public SecurityMetadataString(String displayName, String value)
    {
        this.displayName = displayName;
        this.stringValue = value;
    }

    /**
     * Create from encoded bytes
     *
     * @param displayName the display name for this particular string (see static values)
     * @param bytes Encoded byte array
     */
    public SecurityMetadataString(String displayName, byte[] bytes)
    {
        this.displayName = displayName;
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

    @Override
    public String getDisplayName()
    {
        return displayName;
    }
}

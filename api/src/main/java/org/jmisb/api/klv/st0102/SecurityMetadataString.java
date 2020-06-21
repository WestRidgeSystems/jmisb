package org.jmisb.api.klv.st0102;

import java.nio.charset.StandardCharsets;

/** Represents a string value in ST 0102 */
public class SecurityMetadataString implements ISecurityMetadataValue {
    public static final String CAVEATS = "Caveats";
    public static final String CLASSIFICATION_COMMENTS = "Classification Comments";
    public static final String CLASSIFICATION_REASON = "Classification Reason";
    public static final String CLASSIFIED_BY = "Classified By";
    public static final String CLASSIFYING_COUNTRY = "Classifying Country";
    public static final String DERIVED_FROM = "Derived From";
    public static final String MARKING_SYSTEM = "Marking System";
    public static final String OBJECT_COUNTRY_CODING_METHOD = "Object Country Coding Method";
    public static final String RELEASING_INSTRUCTIONS = "Releasing Instructions";
    public static final String SCI_SHI_INFO = "Security-SCI/SHI Information";
    public static final String COUNTRY_CODING_METHOD = "Country Coding Method";

    private String stringValue;
    private String displayName;

    /**
     * Create from value
     *
     * @param displayName the display name for this particular string (see static values)
     * @param value The string value
     */
    public SecurityMetadataString(String displayName, String value) {
        this.displayName = displayName;
        this.stringValue = value;
    }

    /**
     * Create from encoded bytes
     *
     * @param displayName the display name for this particular string (see static values)
     * @param bytes Encoded byte array
     */
    public SecurityMetadataString(String displayName, byte[] bytes) {
        this.displayName = displayName;
        this.stringValue = new String(bytes, StandardCharsets.US_ASCII);
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
        return stringValue.getBytes(StandardCharsets.US_ASCII);
    }

    @Override
    public String getDisplayableValue() {
        return stringValue;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}

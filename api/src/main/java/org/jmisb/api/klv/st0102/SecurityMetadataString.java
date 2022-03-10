package org.jmisb.api.klv.st0102;

import java.nio.charset.StandardCharsets;

/**
 * Represents a string value in ST 0102.
 *
 * <p>Strings are used across a range of security metadata items. This class represents the various
 * kinds of strings.
 */
public class SecurityMetadataString implements ISecurityMetadataValue {
    /** Caveats label. */
    public static final String CAVEATS = "Caveats";
    /** Classification Comments label. */
    public static final String CLASSIFICATION_COMMENTS = "Classification Comments";
    /** Classification Reason label. */
    public static final String CLASSIFICATION_REASON = "Classification Reason";
    /** Classified By label. */
    public static final String CLASSIFIED_BY = "Classified By";
    /** Classifying Country label. */
    public static final String CLASSIFYING_COUNTRY = "Classifying Country";
    /** Derived From label. */
    public static final String DERIVED_FROM = "Derived From";
    /** Marking System label. */
    public static final String MARKING_SYSTEM = "Marking System";
    /** Object Country Coding Method label. */
    public static final String OBJECT_COUNTRY_CODING_METHOD = "Object Country Coding Method";
    /** Releasing Instructions label. */
    public static final String RELEASING_INSTRUCTIONS = "Releasing Instructions";
    /** SCI / SHI Information Label. */
    public static final String SCI_SHI_INFO = "Security-SCI/SHI Information";
    /** Country Coding Method label. */
    public static final String COUNTRY_CODING_METHOD = "Country Coding Method";

    private final String stringValue;
    private final String displayName;

    /**
     * Create from value.
     *
     * @param displayName the display name for this particular string (see static values)
     * @param value The string value
     */
    public SecurityMetadataString(String displayName, String value) {
        this.displayName = displayName;
        this.stringValue = value;
    }

    /**
     * Create from encoded bytes.
     *
     * @param displayName the display name for this particular string (see static values)
     * @param bytes Encoded byte array
     */
    public SecurityMetadataString(String displayName, byte[] bytes) {
        this.displayName = displayName;
        this.stringValue = new String(bytes, StandardCharsets.US_ASCII);
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

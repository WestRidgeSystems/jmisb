package org.jmisb.viewer;

import org.jmisb.st0601.OpaqueValue;

/**
 * Entry in the metadata tree.
 *
 * <p>This is used to maintain state. Each entry has a unique identifier (not shown to the user), a
 * display name and a display value. The user gets to see the display name and the display value,
 * separated by a colon and a space.
 */
class MetadataEntry {
    public String displayName;
    public String tag;
    public String value;

    /**
     * Constructor.
     *
     * @param tag unique tag for this entry
     * @param displayName the display name (first part of the label text)
     * @param displayValue the display value (second part of the label text)
     */
    public MetadataEntry(String tag, String displayName, String displayValue) {
        this.displayName = displayName;
        this.tag = tag;
        this.value = displayValue;
    }

    @Override
    public String toString() {
        if (displayName.equals(OpaqueValue.DISPLAY_NAME)) {
            return tag + ": " + value;
        }
        return displayName + ": " + value;
    }

    /**
     * Unique tag for this entry.
     *
     * <p>Scope of unique is just within the parent node.
     *
     * @return unique identifier as a string
     */
    public String getTag() {
        return tag;
    }

    /**
     * The display value for this entry.
     *
     * <p>This is not the whole text label, but just the part after the colon.
     *
     * @param displayableValue display value as a string
     */
    void setValue(String displayableValue) {
        value = displayableValue;
    }

    /**
     * The display value for this entry.
     *
     * @return the display value as a string
     */
    String getValue() {
        return value;
    }
}

package org.jmisb.viewer;

import org.jmisb.api.klv.st0601.OpaqueValue;

class MetadataEntry {
    public String displayName;
    public String tag;
    public String value;

    public MetadataEntry(String tag, String displayName, String displayValue) {
        this.displayName = displayName;
        this.tag = tag;
        this.value = displayValue;
    }

    @Override
    public String toString() {
        if (displayName.equals(OpaqueValue.DISPLAYNAME)) {
            return tag + ": " + value;
        }
        return displayName + ": " + value;
    }
    
    public String getTag() {
        return tag;
    }

    void setValue(String displayableValue) {
        value = displayableValue;
    }

    String getValue() {
        return value;
    }
}
        
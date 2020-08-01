package org.jmisb.api.klv.st0806.userdefined;

import java.util.HashMap;
import java.util.Map;

/** ST 0806 User Defined local set keys - description and numbers. */
public enum RvtUserDefinedMetadataKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    NumericId(1),
    UserData(2);

    private int tag;

    private static final Map<Integer, RvtUserDefinedMetadataKey> tagTable = new HashMap<>();

    static {
        for (RvtUserDefinedMetadataKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    /**
     * Constructor.
     *
     * <p>Internal use only.
     *
     * @param tag the tag value to initialise the enumeration value.
     */
    RvtUserDefinedMetadataKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with this enumeration value.
     *
     * @return integer tag value for the metadata key
     */
    public int getTag() {
        return tag;
    }

    /**
     * Look up the metadata key by tag identifier.
     *
     * @param tag the integer tag value to look up
     * @return corresponding metadata key
     */
    public static RvtUserDefinedMetadataKey getKey(int tag) {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }
}

package org.jmisb.api.klv.st0806.userdefined;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/** ST 0806 User Defined local set keys - description and numbers. */
public enum RvtUserDefinedMetadataKey implements IKlvKey {
    /**
     * Unknown key.
     *
     * <p>This should not be created.
     */
    Undefined(0),
    /** Numeric identifier. */
    NumericId(1),
    /** User data. */
    UserData(2);

    private final int tag;

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

    @Override
    public int getIdentifier() {
        return tag;
    }

    /**
     * Look up the metadata key by tag identifier.
     *
     * @param tag the integer tag value to look up
     * @return corresponding metadata key
     */
    public static RvtUserDefinedMetadataKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}

package org.jmisb.api.klv.st1301;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/** ST 1301 tags - description and numbers. */
public enum MiisMetadataKey implements IKlvKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    /** ST 1301 Version. */
    Version(3),
    /** Core Identifier. */
    CoreIdentifier(4);

    private final int tag;

    private static final Map<Integer, MiisMetadataKey> tagTable = new HashMap<>();

    static {
        for (MiisMetadataKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    MiisMetadataKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with this enumeration value.
     *
     * @return integer tag value for the metadata key
     */
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
    public static MiisMetadataKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}

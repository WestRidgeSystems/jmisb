package org.jmisb.api.klv.st0808;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/** ST 0808 tags - description and numbers. */
public enum AncillaryTextMetadataKey implements IKlvKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    Originator(1),
    PrecisionTimeStamp(2),
    MessageBody(3),
    Source(4),
    MessageCreationTime(5);

    private int tag;

    private static final Map<Integer, AncillaryTextMetadataKey> tagTable = new HashMap<>();

    static {
        for (AncillaryTextMetadataKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    AncillaryTextMetadataKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with this enumeration value.
     *
     * @return integer tag value for the metadata key
     */
    public int getIdentifier() {
        return tag;
    }

    /**
     * Look up the metadata key by tag identifier.
     *
     * @param tag the integer tag value to look up
     * @return corresponding metadata key
     */
    public static AncillaryTextMetadataKey getKey(int tag) {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }
}

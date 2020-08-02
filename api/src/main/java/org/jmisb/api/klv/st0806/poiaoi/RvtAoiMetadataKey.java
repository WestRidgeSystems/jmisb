package org.jmisb.api.klv.st0806.poiaoi;

import java.util.HashMap;
import java.util.Map;

/** ST 0806 Area Of Interest tags - description and numbers. */
public enum RvtAoiMetadataKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    PoiAoiNumber(1),
    CornerLatitudePoint1(2),
    CornerLongitudePoint1(3),
    CornerLatitudePoint3(4),
    CornerLongitudePoint3(5),
    PoiAoiType(6),
    PoiAoiText(7),
    PoiAoiSourceId(8),
    PoiAoiLabel(9),
    OperationId(10);

    private int tag;

    private static final Map<Integer, RvtAoiMetadataKey> tagTable = new HashMap<>();

    static {
        for (RvtAoiMetadataKey key : values()) {
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
    RvtAoiMetadataKey(int tag) {
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
    public static RvtAoiMetadataKey getKey(int tag) {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }
}

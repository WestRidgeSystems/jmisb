package org.jmisb.api.klv.st0806.poiaoi;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/** ST 0806 Point Of Interest tags - description and numbers. */
public enum RvtPoiMetadataKey implements IKlvKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    PoiAoiNumber(1),
    PoiLatitude(2),
    PoiLongitude(3),
    PoiAltitude(4),
    PoiAoiType(5),
    PoiAoiText(6),
    PoiSourceIcon(7),
    PoiAoiSourceId(8),
    PoiAoiLabel(9),
    OperationId(10);

    private int tag;

    private static final Map<Integer, RvtPoiMetadataKey> tagTable = new HashMap<>();

    static {
        for (RvtPoiMetadataKey key : values()) {
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
    RvtPoiMetadataKey(int tag) {
        this.tag = tag;
    }

    /**
     * Look up the metadata key by tag identifier.
     *
     * @param tag the integer tag value to look up
     * @return corresponding metadata key
     */
    public static RvtPoiMetadataKey getKey(int tag) {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }

    @Override
    public int getIdentifier() {
        return tag;
    }
}

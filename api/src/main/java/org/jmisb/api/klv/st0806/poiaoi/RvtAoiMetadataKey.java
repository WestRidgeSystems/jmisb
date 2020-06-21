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

    RvtAoiMetadataKey(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public static RvtAoiMetadataKey getKey(int tag) {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }
}

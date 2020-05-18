package org.jmisb.api.klv.st0806.poiaoi;

import java.util.HashMap;
import java.util.Map;

/**
 * ST 0806 Point Of Interest tags - description and numbers.
 */
public enum RvtPoiMetadataKey
{
    /**
     * Unknown key. This should not be created.
     */
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

    static
    {
        for (RvtPoiMetadataKey key : values())
        {
            tagTable.put(key.tag, key);
        }
    }

    RvtPoiMetadataKey(int tag)
    {
        this.tag = tag;
    }

    public int getTag()
    {
        return tag;
    }

    public static RvtPoiMetadataKey getKey(int tag)
    {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }
}

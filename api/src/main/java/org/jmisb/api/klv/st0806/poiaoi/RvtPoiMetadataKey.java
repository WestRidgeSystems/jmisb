package org.jmisb.api.klv.st0806.poiaoi;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/** ST 0806 Point Of Interest tags - description and numbers. */
public enum RvtPoiMetadataKey implements IKlvKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    /** POI / AOI Number. */
    PoiAoiNumber(1),
    /** POI Latitude. */
    PoiLatitude(2),
    /** POI Longitude. */
    PoiLongitude(3),
    /** POI Altitude. */
    PoiAltitude(4),
    /** POI / AOI Type. */
    PoiAoiType(5),
    /** POI / AOI Text. */
    PoiAoiText(6),
    /** POI Source Icon. */
    PoiSourceIcon(7),
    /** POI / AOI Source Identifier. */
    PoiAoiSourceId(8),
    /** POI / AOI Label. */
    PoiAoiLabel(9),
    /** Operation Identifier. */
    OperationId(10);

    private final int tag;

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
        return tagTable.getOrDefault(tag, Undefined);
    }

    @Override
    public int getIdentifier() {
        return tag;
    }
}

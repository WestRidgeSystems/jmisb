package org.jmisb.st1602;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/**
 * ST 1602 tags - description and numbers.
 *
 * <p>This enumeration maps the Composite Imaging Local Set tag values to a name, and names to tag
 * values. It conceptually corresponds to the first two columns in ST 1602.1 Table 1.
 */
public enum CompositeImagingKey implements IKlvKey {
    /**
     * Unknown key.
     *
     * <p>This should not be created. It does not correspond to any known ST 1602 tag / key.
     */
    Undefined(0),

    /**
     * Precision Time Stamp.
     *
     * <p>ST 1602 Local Set Tag 1.
     */
    PrecisionTimeStamp(1),

    /**
     * Document Version.
     *
     * <p>ST 1602 Local Set Tag 2.
     */
    DocumentVersion(2),

    /**
     * Source Image Rows.
     *
     * <p>ST 1602 Local Set Tag 3.
     */
    SourceImageRows(3),

    /**
     * Source Image Columns.
     *
     * <p>ST 1602 Local Set Tag 4.
     */
    SourceImageColumns(4),

    /**
     * Source Image AOI Rows.
     *
     * <p>ST 1602 Local Set Tag 5.
     */
    SourceImageAOIRows(5),

    /**
     * Source Image AOI Columns.
     *
     * <p>ST 1602 Local Set Tag 6.
     */
    SourceImageAOIColumns(6),

    /**
     * Source Image AOI Position X.
     *
     * <p>ST 1602 Local Set Tag 7.
     */
    SourceImageAOIPositionX(7),

    /**
     * Source Image AOI Position Y.
     *
     * <p>ST 1602 Local Set Tag 8.
     */
    SourceImageAOIPositionY(8),

    /**
     * Sub-Image Rows.
     *
     * <p>ST 1602 Local Set Tag 9.
     */
    SubImageRows(9),

    /**
     * Sub-Image Columns.
     *
     * <p>ST 1602 Local Set Tag 10.
     */
    SubImageColumns(10),

    /**
     * Sub-Image Position X.
     *
     * <p>ST 1602 Local Set Tag 11.
     */
    SubImagePositionX(11),

    /**
     * Sub-Image Position Y.
     *
     * <p>ST 1602 Local Set Tag 12.
     */
    SubImagePositionY(12),

    /**
     * Active Sub-Image Rows.
     *
     * <p>ST 1602 Local Set Tag 13.
     */
    ActiveSubImageRows(13),

    /**
     * Active Sub-Image Columns.
     *
     * <p>ST 1602 Local Set Tag 14.
     */
    ActiveSubImageColumns(14),

    /**
     * Active Sub-Image Offset X.
     *
     * <p>ST 1602 Local Set Tag 15.
     */
    ActiveSubImageOffsetX(15),

    /**
     * Active Sub-Image Offset Y.
     *
     * <p>ST 1602 Local Set Tag 16.
     */
    ActiveSubImageOffsetY(16),

    /**
     * Transparency.
     *
     * <p>ST 1602 Local Set Tag 17.
     */
    Transparency(17),

    /**
     * Z-Order.
     *
     * <p>ST 1602 Local Set Tag 18.
     */
    ZOrder(18);

    private final int tag;

    private static final Map<Integer, CompositeImagingKey> tagTable = new HashMap<>();

    static {
        for (CompositeImagingKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    private CompositeImagingKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with this enumeration value.
     *
     * @return integer tag value for the local set identifier
     */
    @Override
    public int getIdentifier() {
        return tag;
    }

    /**
     * Look up the key by tag identifier.
     *
     * @param tag the integer tag value to look up
     * @return corresponding local set key
     */
    public static CompositeImagingKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}

package org.jmisb.api.klv.st0903.vfeature;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvTag;

/**
 * Metadata tag numbers for ST0903 VFeature local set.
 */
public enum VFeatureMetadataKey implements IKlvTag
{
    /**
     * Unknown key. This should not be created.
     */
    Undefined(0),
    /**
     * Uniform Resource Identifier (URI) which refers to an OGC Geography Markup
     * Language (GML) Observations and Measurements (O&amp;M) application schema.
     */
    schema(1),
    /**
     * OGC GML document structured according to the schema specified by Tag 1.
     * Intended to capture properties (values) observed for a feature of
     * interest.
     */
    schemaFeature(2);

    private int tag;

    private static final Map<Integer, VFeatureMetadataKey> tagTable = new HashMap<>();

    static
    {
        for (VFeatureMetadataKey key : values())
        {
            tagTable.put(key.tag, key);
        }
    }

    VFeatureMetadataKey(int tag)
    {
        this.tag = tag;
    }

    /**
     * Get the tag number associated with this VFeature tag.
     *
     * @return the tag associated with this enumerated value.
     */
    @Override
    public int getTagCode()
    {
        return tag;
    }

    /**
     * Look up a VFeature tag by enum value.
     *
     * @param tag the tag number.
     * @return the corresponding VFeature tag.
     */
    public static VFeatureMetadataKey getKey(int tag)
    {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }

}

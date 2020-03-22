package org.jmisb.api.klv.st0903.vobject;

import java.util.HashMap;
import java.util.Map;

/**
 * Metadata tag numbers for ST0903 VObject local set.
 */
public enum VObjectMetadataKey
{
    /**
     * Unknown key. This should not be created.
     */
    Undefined(0),
    /**
     * Uniform Resource Identifier (URI) which refers to a VObject ontology.
     */
    ontology(1),
    /**
     * The name of the target class or type, as defined in the VObject Ontology.
     */
    ontologyClass(2),
    /**
     * Identifier indicating which Ontology in the VMTI’s Ontology Series
     * represents this object.
     */
    ontologyId(3),
    /**
     * The amount of confidence in the classification of this object.
     */
    confidence(4);

    private int tag;

    private static final Map<Integer, VObjectMetadataKey> tagTable = new HashMap<>();

    static
    {
        for (VObjectMetadataKey key : values())
        {
            tagTable.put(key.tag, key);
        }
    }

    VObjectMetadataKey(int tag)
    {
        this.tag = tag;
    }

    /**
     * Get the tag number associated with this VObject tag.
     *
     * @return the tag associated with this enumerated value.
     */
    public int getTag()
    {
        return tag;
    }

    /**
     * Look up a VObject tag by enum value.
     *
     * @param tag the tag number.
     * @return the corresponding VObject tag.
     */
    public static VObjectMetadataKey getKey(int tag)
    {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }

}

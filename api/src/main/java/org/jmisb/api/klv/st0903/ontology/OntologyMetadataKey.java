package org.jmisb.api.klv.st0903.ontology;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvTag;

/**
 * Metadata tag numbers for ST0903 Ontology local set.
 */
public enum OntologyMetadataKey implements IKlvTag
{
    /**
     * Unknown key.
     * <p>
     * This should not be created.
     */
    Undefined(0),
    /**
     * Identifier for the ontology used.
     */
    id(1),
    /**
     * Defines the link when an OntologySeries has two related LS in the Series.
     */
    parentId(2),
    /**
     * Uniform Resource Identifier (URI) according to  the OWL Web Ontology Language.
     */
    ontology(3),
    /**
     * A value representing a target class or type, as defined by the Ontology.
     */
    ontologyClass(4);

    private int tag;

    private static final Map<Integer, OntologyMetadataKey> tagTable = new HashMap<>();

    static
    {
        for (OntologyMetadataKey key : values())
        {
            tagTable.put(key.tag, key);
        }
    }

    OntologyMetadataKey(int tag)
    {
        this.tag = tag;
    }

    /**
     * Get the tag number associated with this Ontology LS tag.
     *
     * @return the tag associated with this enumerated value.
     */
    public int getTagCode()
    {
        return tag;
    }

    /**
     * Look up an Ontology LS tag by enum value.
     *
     * @param tag the tag number.
     * @return the corresponding Ontology tag.
     */
    public static OntologyMetadataKey getKey(int tag)
    {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }

}

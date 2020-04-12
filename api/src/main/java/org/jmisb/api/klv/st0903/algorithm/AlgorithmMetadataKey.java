package org.jmisb.api.klv.st0903.algorithm;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/**
 * Metadata tag numbers for ST0903 Algorithm local set.
 */
public enum AlgorithmMetadataKey implements IKlvKey
{
    /**
     * Unknown key. This should not be created.
     */
    Undefined(0),
    /**
     * Identifier for the algorithm used.
     */
    id(1),
    /**
     * Name of algorithm.
     */
    name(2),
    /**
     * Version of algorithm.
     */
    version(3),
    /**
     * Type of algorithm.
     *
     * e.g., detector classifier
     */
    algorithmClass(4),
    /**
     * Number of frames algorithm operates over.
     */
    nFrames(5);

    private int tag;

    private static final Map<Integer, AlgorithmMetadataKey> tagTable = new HashMap<>();

    static
    {
        for (AlgorithmMetadataKey key : values())
        {
            tagTable.put(key.tag, key);
        }
    }

    AlgorithmMetadataKey(int tag)
    {
        this.tag = tag;
    }

    /**
     * Get the tag number associated with this Algorithm LS tag.
     *
     * @return the tag associated with this enumerated value.
     */
    @Override
    public int getTagCode()
    {
        return tag;
    }

    /**
     * Look up an Algorithm LS tag by enum value.
     *
     * @param tag the tag number.
     * @return the corresponding Algorithm tag.
     */
    public static AlgorithmMetadataKey getKey(int tag)
    {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }

}

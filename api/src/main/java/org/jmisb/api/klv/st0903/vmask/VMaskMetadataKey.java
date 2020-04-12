package org.jmisb.api.klv.st0903.vmask;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvTag;

/**
 * Metadata tag numbers for ST0903 VMask local set.
 */
public enum VMaskMetadataKey implements IKlvTag
{
    /**
     * Unknown key. This should not be created.
     */
    Undefined(0),
    /**
     * At least three unsigned integer numbers that specify the vertices of a
     * polygon representing the outline of a target.
     */
    polygon(1),
    /**
     * Describes the area of the frame occupied by a target using a run-length
     * encoded bit mask with 1 to indicate that a pixel subtends a part of the
     * target and 0 to indicate otherwise.
     */
    bitMaskSeries(2);

    private int tag;

    private static final Map<Integer, VMaskMetadataKey> tagTable = new HashMap<>();

    static
    {
        for (VMaskMetadataKey key : values())
        {
            tagTable.put(key.tag, key);
        }
    }

    VMaskMetadataKey(int tag)
    {
        this.tag = tag;
    }

    /**
     * Get the tag number associated with this VMask tag.
     *
     * @return the tag associated with this enumerated value.
     */
    @Override
    public int getTagCode()
    {
        return tag;
    }

    /**
     * Look up a VMask tag by enum value.
     *
     * @param tag the tag number.
     * @return the corresponding VMask tag.
     */
    public static VMaskMetadataKey getKey(int tag)
    {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }

}

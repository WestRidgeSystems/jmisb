package org.jmisb.api.klv.st0806.userdefined;

import java.util.HashMap;
import java.util.Map;

/**
 * ST 0806 User Defined local set keys - description and numbers.
 */
public enum RvtUserDefinedMetadataKey
{
    /**
     * Unknown key. This should not be created.
     */
    Undefined(0),
    NumericId(1),
    UserData(2);

    private int tag;

    private static final Map<Integer, RvtUserDefinedMetadataKey> tagTable = new HashMap<>();

    static
    {
        for (RvtUserDefinedMetadataKey key : values())
        {
            tagTable.put(key.tag, key);
        }
    }

    RvtUserDefinedMetadataKey(int tag)
    {
        this.tag = tag;
    }

    public int getTag()
    {
        return tag;
    }

    public static RvtUserDefinedMetadataKey getKey(int tag)
    {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }
}

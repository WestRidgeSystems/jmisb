// Generated file - changes will be lost on rebuild
package ${packageName};

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/**
 * ${name} metadata keys.
 *
 * See ${document} for more information on these values.
 */
public enum ${name}MetadataKey implements IKlvKey {
    Undefined(-1),
<#list entries as entry>
    ${entry.name}(${entry.number})<#sep>,</#sep><#if entry?is_last>;</#if>
</#list>

    private int tag;

    private static final Map<Integer, ${name}MetadataKey> tagTable = new HashMap<>();

    static {
        for (${name}MetadataKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    private ${name}MetadataKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the identifier value associated with this enumeration value.
     *
     * @return integer identifier value for the metadata key
     */
    public int getIdentifier() {
        return tag;
    }

    /**
     * Look up the metadata key by tag identifier.
     *
     * @param tag the integer tag value to look up
     * @return corresponding metadata key
     */
    public static ${name}MetadataKey getKey(int tag) {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }
}

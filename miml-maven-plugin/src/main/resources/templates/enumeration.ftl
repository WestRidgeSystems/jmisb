// Generated file - changes will be lost on rebuild
package ${packagename};

import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.st190x.IMimdMetadataValue;

/**
 * ${name} enumerated values.
 *
 * See ${document} for more information on these values.
 */
public enum ${name} implements IMimdMetadataValue {
    /**
     * Unknown value.
     *
     * This should not be intentionally created, and will not be serialised.
     */
     Unknown(-1, "Unknown"),
<#list entries as entry>
    /**
     * ${entry.description}.
     */
    ${entry.name}(${entry.number}, "${entry.description}")<#sep>,</#sep><#if entry?is_last>;</#if>
</#list>

    /**
     * Identifier for this enumeration value.
     *
     * The Enumeration Identifier is a unique (within the enumeration list) unsigned
     * integer to identify one of the enumeration items in the list, numerically.
     */
    private final int identifier;

    /**
     * Definition for this enumeration value.
     *
     * The Definition describes the meaning of the enumeration or references a document
     * section with further information.
     */
    private final String definition;

    private ${name}(int id, String description) {
        identifier = id;
        this.definition = description;
    }

    @Override
    public String getDisplayName() {
        return "${name}";
    }

    @Override
    public byte[] getBytes(){
        if (identifier == -1) {
            throw new IllegalArgumentException("${name}: Cannot serialise Unknown value");
        }
        return BerEncoder.encode(identifier);
    }

    @Override
    public String getDisplayableValue() {
        return definition;
    }
}

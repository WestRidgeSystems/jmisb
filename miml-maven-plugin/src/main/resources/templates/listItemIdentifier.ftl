// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.Objects;
import org.jmisb.api.klv.IKlvKey;

/** Pseudo-key item for series identifier. */
public class ${listItemType}Identifier implements IKlvKey, Comparable<${listItemType}Identifier> {

    private final Integer identifier;

    /**
     * Constructor.
     *
     * @param identifier the integer code for this ${listItemType} identifier.
     */
    public ${listItemType}Identifier(int identifier) {
        this.identifier = identifier;
    }

    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ${listItemType}Identifier other = (${listItemType}Identifier) obj;
        return Objects.equals(this.identifier, other.identifier);
    }

    @Override
    public int compareTo(${listItemType}Identifier o) {
        return identifier.compareTo(o.identifier);
    }

    // Note: this needs to match AlgorithmLS:getDisplayableValue()
    @Override
    public String toString() {
        return "${listItemType} " + identifier;
    }
}

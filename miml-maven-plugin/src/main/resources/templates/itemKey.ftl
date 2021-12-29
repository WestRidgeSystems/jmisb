// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.klv.IKlvKey;

/**
 * Pseudo-key item for series identifier.
 *
 * <p>This is intended to support enumeration of items in lists and arrays. You should not normally be
 * instantiating this class yourself.
 */
class ${namespacedName}ItemKey implements IKlvKey, Comparable<${namespacedName}ItemKey> {

    private final int identifier;

    /**
     * Constructor.
     *
     * @param identifier the integer code for this ${name} identifier.
     */
    public ${namespacedName}ItemKey(int identifier) {
        this.identifier = identifier;
    }

    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.identifier;
        return hash;
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
        final ${namespacedName}ItemKey other = (${namespacedName}ItemKey) obj;
        return this.identifier == other.identifier;
    }

    @Override
    public int compareTo(${namespacedName}ItemKey p) {
        return Integer.compare(identifier, p.identifier);
    }

    @Override
    public String toString() {
        return "Item " + identifier;
    }
}

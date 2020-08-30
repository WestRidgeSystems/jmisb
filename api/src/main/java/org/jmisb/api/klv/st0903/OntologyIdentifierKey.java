package org.jmisb.api.klv.st0903;

import java.util.Objects;
import org.jmisb.api.klv.IKlvKey;

/** Pseudo-key item for series identifier. */
public class OntologyIdentifierKey implements IKlvKey, Comparable<OntologyIdentifierKey> {

    private final Integer identifier;

    /**
     * Constructor.
     *
     * @param identifier the integer code for this Ontology identifier.
     */
    public OntologyIdentifierKey(int identifier) {
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
        final OntologyIdentifierKey other = (OntologyIdentifierKey) obj;
        return Objects.equals(this.identifier, other.identifier);
    }

    @Override
    public int compareTo(OntologyIdentifierKey o) {
        return identifier.compareTo(o.identifier);
    }

    // Note: this needs to match OntologyLS:getDisplayableValue()
    @Override
    public String toString() {
        return "Ontology " + identifier;
    }
}

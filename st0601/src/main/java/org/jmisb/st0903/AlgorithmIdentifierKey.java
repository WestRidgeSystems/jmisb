package org.jmisb.st0903;

import java.util.Objects;
import org.jmisb.api.klv.IKlvKey;

/** Pseudo-key item for series identifier. */
public class AlgorithmIdentifierKey implements IKlvKey, Comparable<AlgorithmIdentifierKey> {

    private final Integer identifier;

    /**
     * Constructor.
     *
     * @param identifier the integer code for this Algorithm identifier.
     */
    public AlgorithmIdentifierKey(int identifier) {
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
        final AlgorithmIdentifierKey other = (AlgorithmIdentifierKey) obj;
        return Objects.equals(this.identifier, other.identifier);
    }

    @Override
    public int compareTo(AlgorithmIdentifierKey o) {
        return identifier.compareTo(o.identifier);
    }

    // Note: this needs to match AlgorithmLS:getDisplayableValue()
    @Override
    public String toString() {
        return "Algorithm " + identifier;
    }
}

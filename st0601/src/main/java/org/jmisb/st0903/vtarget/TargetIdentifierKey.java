package org.jmisb.st0903.vtarget;

import java.util.Objects;
import org.jmisb.api.klv.IKlvKey;

/** Pseudo-key item for series identifier. */
public class TargetIdentifierKey implements IKlvKey, Comparable<TargetIdentifierKey> {

    private final Integer identifier;

    /**
     * Constructor.
     *
     * @param identifier the integer code for this Target identifier.
     */
    public TargetIdentifierKey(int identifier) {
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
        final TargetIdentifierKey other = (TargetIdentifierKey) obj;
        return Objects.equals(this.identifier, other.identifier);
    }

    @Override
    public int compareTo(TargetIdentifierKey o) {
        return identifier.compareTo(o.identifier);
    }

    // Note: this needs to match VTargetPack:getDisplayableValue()
    @Override
    public String toString() {
        return "Target " + identifier;
    }
}

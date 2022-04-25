package org.jmisb.st0601;

import java.util.Objects;
import org.jmisb.api.klv.IKlvKey;

/** Pseudo-key item for payload identifier. */
public class PayloadIdentifierKey implements IKlvKey, Comparable<PayloadIdentifierKey> {
    private final Integer identifier;

    /**
     * Constructor.
     *
     * @param identifier the integer code for this Payload identifier.
     */
    public PayloadIdentifierKey(int identifier) {
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
        final PayloadIdentifierKey other = (PayloadIdentifierKey) obj;
        return Objects.equals(this.identifier, other.identifier);
    }

    @Override
    public int compareTo(PayloadIdentifierKey o) {
        return identifier.compareTo(o.identifier);
    }

    @Override
    public String toString() {
        return "Payload " + identifier;
    }
}

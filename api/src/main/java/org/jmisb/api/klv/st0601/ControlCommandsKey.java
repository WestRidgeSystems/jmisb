package org.jmisb.api.klv.st0601;

import java.util.Objects;
import org.jmisb.api.klv.IKlvKey;

/** Pseudo-key value to track the various {@code ControlCommand} instances. */
public class ControlCommandsKey implements IKlvKey, Comparable<ControlCommandsKey> {

    private final Integer identifier;

    ControlCommandsKey(int id) {
        this.identifier = id;
    }

    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public int compareTo(ControlCommandsKey o) {
        return identifier.compareTo(o.identifier);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.identifier);
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
        final ControlCommandsKey other = (ControlCommandsKey) obj;
        return Objects.equals(this.identifier, other.identifier);
    }

    @Override
    public String toString() {
        return "Command " + identifier;
    }
}

package org.jmisb.api.klv.st1108.st1108_3.metric;

import java.util.Objects;
import org.jmisb.api.klv.IKlvKey;

/** Pseudo-key value to track the various {@code MetricLocalSet} instances. */
public class MetricLocalSetsKey implements IKlvKey, Comparable<MetricLocalSetsKey> {

    private final Integer identifier;

    MetricLocalSetsKey(int id) {
        this.identifier = id;
    }

    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public int compareTo(MetricLocalSetsKey o) {
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
        final MetricLocalSetsKey other = (MetricLocalSetsKey) obj;
        return Objects.equals(this.identifier, other.identifier);
    }

    @Override
    public String toString() {
        return "Metric " + identifier;
    }
}

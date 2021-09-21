package org.jmisb.api.klv;

import java.util.Set;

/** Interface for values that have nested child values. */
public interface INestedKlvValue {
    /**
     * Get the value of a given item.
     *
     * @param tag Tag or other identifier of the value to retrieve
     * @return The value, or null if no value was set
     */
    IKlvValue getField(IKlvKey tag);

    /**
     * Get the set of identifiers for populated values.
     *
     * @return The set of item identifiers for which values have been set
     */
    Set<? extends IKlvKey> getIdentifiers();
}

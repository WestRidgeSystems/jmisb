package org.jmisb.api.klv;

import java.util.Set;

/**
 * Interface for values that have nested child values.
 */
public interface INestedKlvValue
{
    /**
     * Get the value of a given tag
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    IKlvValue getField(IKlvTag tag);

    /**
     * Get the set of tags with populated values
     *
     * @return The set of tags for which values have been set
     */
    public Set<? extends IKlvTag> getTags();
}

package org.jmisb.api.klv;

/**
 * Interface for values that have (potentially) repeating nested child values.
 */
public interface IRepeatingNestedKlvValue
{
    /**
     * Get the number of repeating entries
     *
     * @return The number of entries
     */
    int getNumberOfEntries();

    /**
     * Get the nested metadata value by index.
     *
     * @param <T> class that extends INestedKlvValue.
     * @param i the index of the value.
     * @return The metadata for the specified index.
     */
    public <T extends INestedKlvValue> T getNestedValue(int i);
}

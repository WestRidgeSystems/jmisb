package org.jmisb.api.klv;

/** Generic KLV metadata value. */
public interface IKlvValue {
    /**
     * Get the human-readable name for the value.
     *
     * @return The name of the type
     */
    String getDisplayName();

    /**
     * Return a string of the displayable value.
     *
     * @return String representing the value
     */
    String getDisplayableValue();
}

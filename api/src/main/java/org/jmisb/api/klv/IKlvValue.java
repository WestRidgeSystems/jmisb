package org.jmisb.api.klv;

/** Generic KLV metadata value. */
public interface IKlvValue {
    /**
     * Get the human-readable name for the value.
     *
     * <p>
     *
     * @return The name of the type
     */
    String getDisplayName();

    /**
     * Return a string of the displayable value.
     *
     * <p>
     *
     * @return String representing the value
     */
    String getDisplayableValue();
}

package org.jmisb.api.klv.eg0104;

/** EG 0104 value. */
public interface IPredatorMetadataValue {
    /**
     * Return a string of the displayable value
     *
     * @return String representing the value
     */
    String getDisplayableValue();

    /**
     * Get the human-readable name for the value
     *
     * @return The name of the type
     */
    String getDisplayName();
}

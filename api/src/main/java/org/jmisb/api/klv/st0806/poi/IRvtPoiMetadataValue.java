package org.jmisb.api.klv.st0806.poi;

public interface IRvtPoiMetadataValue {
    /**
     * Get the encoded bytes
     * @return The encoded byte array
     */
    byte[] getBytes();

    /**
     * Return a string of the displayable value
     * @return String representing the value
     */
    String getDisplayableValue();

    /**
     * Get the human-readable name for the value
     * @return The name of the type
     */
    String getDisplayName();
}

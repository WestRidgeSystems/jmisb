package org.jmisb.api.klv.st0102;

/**
 * ST 0102 value
 */
public interface ISecurityMetadataValue
{
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
}

package org.jmisb.st1002;

import org.jmisb.api.klv.IKlvValue;

/**
 * ST 1002 metadata value.
 *
 * <p>All ST 1002 Range Image Local Set values implement this interface.
 */
public interface IRangeImageMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

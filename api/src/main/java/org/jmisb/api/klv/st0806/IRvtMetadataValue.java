package org.jmisb.api.klv.st0806;

import org.jmisb.api.klv.IKlvValue;

/**
 * ST 0806 metadata value.
 *
 * <p>All top level local set values implement this interface.
 */
public interface IRvtMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

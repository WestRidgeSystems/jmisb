package org.jmisb.st0806.userdefined;

import org.jmisb.api.klv.IKlvValue;

/**
 * ST 0806 RVT User Defined metadata value.
 *
 * <p>All RVT User Defined local set values implement this interface.
 */
public interface IRvtUserDefinedMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

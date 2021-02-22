package org.jmisb.api.klv.st0806;

import org.jmisb.api.klv.IKlvValue;

public interface IRvtMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

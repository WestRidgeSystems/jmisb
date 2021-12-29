package org.jmisb.api.klv.st1902;

import org.jmisb.api.klv.IKlvValue;

public interface IMimdMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

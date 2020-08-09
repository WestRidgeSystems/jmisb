package org.jmisb.api.klv.st0903;

import org.jmisb.api.klv.IKlvValue;

public interface IVmtiMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

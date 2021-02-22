package org.jmisb.api.klv.st0806.userdefined;

import org.jmisb.api.klv.IKlvValue;

public interface IRvtUserDefinedMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

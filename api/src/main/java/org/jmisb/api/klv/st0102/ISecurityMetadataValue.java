package org.jmisb.api.klv.st0102;

import org.jmisb.api.klv.IKlvValue;

/** ST 0102 value */
public interface ISecurityMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * <p>
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

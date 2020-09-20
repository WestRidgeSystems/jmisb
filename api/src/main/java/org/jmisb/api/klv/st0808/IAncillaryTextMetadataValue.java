package org.jmisb.api.klv.st0808;

import org.jmisb.api.klv.IKlvValue;

public interface IAncillaryTextMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

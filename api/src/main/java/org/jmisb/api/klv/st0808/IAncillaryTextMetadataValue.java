package org.jmisb.api.klv.st0808;

import org.jmisb.api.klv.IKlvValue;

/**
 * ST 0808 metadata value.
 *
 * <p>All ST 0808 Ancillary Text Local Set values implement this interface.
 */
public interface IAncillaryTextMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

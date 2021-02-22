package org.jmisb.api.klv.st0903.shared;

import org.jmisb.api.klv.IKlvValue;

public interface IVTrackMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

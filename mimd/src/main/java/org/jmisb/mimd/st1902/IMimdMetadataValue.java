package org.jmisb.mimd.st1902;

import org.jmisb.api.klv.IKlvValue;

/** ST 190x MIMD metadata value. */
public interface IMimdMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

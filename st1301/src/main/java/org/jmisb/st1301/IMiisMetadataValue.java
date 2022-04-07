package org.jmisb.st1301;

import org.jmisb.api.klv.IKlvValue;

/**
 * Motion Imagery Identification System (MIIS) Local Set metadata value.
 *
 * <p>All values for the MIIS local set implement this interface. It is unlikely you will need to
 * implement this interface.
 */
public interface IMiisMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

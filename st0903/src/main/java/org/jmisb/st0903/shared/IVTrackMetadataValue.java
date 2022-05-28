package org.jmisb.st0903.shared;

import org.jmisb.api.klv.IKlvValue;

/** ST 0903 VTrack metadata value. */
public interface IVTrackMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

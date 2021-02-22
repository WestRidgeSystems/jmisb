package org.jmisb.api.klv.st0806.poiaoi;

import org.jmisb.api.klv.IKlvValue;

public interface IRvtPoiAoiMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

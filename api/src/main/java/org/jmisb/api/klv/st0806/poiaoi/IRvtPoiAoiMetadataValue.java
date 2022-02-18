package org.jmisb.api.klv.st0806.poiaoi;

import org.jmisb.api.klv.IKlvValue;

/**
 * ST 0806 POI / AOI metadata value.
 *
 * <p>All POI and AOI local set values implement this interface.
 */
public interface IRvtPoiAoiMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

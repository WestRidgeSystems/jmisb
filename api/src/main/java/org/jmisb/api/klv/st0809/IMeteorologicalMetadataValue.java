package org.jmisb.api.klv.st0809;

import org.jmisb.api.klv.IKlvValue;

/**
 * Meteorological metadata value.
 *
 * <p>All local set values in ST 0809 implement this interface.
 */
public interface IMeteorologicalMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

package org.jmisb.st1206;

import org.jmisb.api.klv.IKlvValue;

/**
 * SAR Motion Imagery (SARMI) metadata value.
 *
 * <p>All values for the SARMI local set implement this interface. It is unlikely you will need to
 * implement this interface.
 */
public interface ISARMIMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

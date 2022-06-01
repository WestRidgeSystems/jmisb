package org.jmisb.st0903;

import org.jmisb.api.klv.IKlvValue;

/**
 * ST 0903 VMTI metadata value.
 *
 * <p>All top level local set values implement this interface.
 */
public interface IVmtiMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

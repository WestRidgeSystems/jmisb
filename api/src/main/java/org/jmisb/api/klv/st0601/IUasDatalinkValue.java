package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.IKlvValue;

/**
 * ST 0601 value
 */
public interface IUasDatalinkValue extends IKlvValue
{
    /**
     * Get the encoded bytes.
     * <p>
     * @return The encoded byte array
     */
    byte[] getBytes();
}

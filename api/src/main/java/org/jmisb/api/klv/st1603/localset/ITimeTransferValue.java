package org.jmisb.api.klv.st1603.localset;

import org.jmisb.api.klv.IKlvValue;

/**
 * ST 1603 Time Transfer Local Set value.
 *
 * <p>All Time Transfer Local Set values implement this interface. Users are unlikely to need to
 * implement this interface. Instead, use one of the implementations either directly, or via the
 * Local Set implementation.
 */
public interface ITimeTransferValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

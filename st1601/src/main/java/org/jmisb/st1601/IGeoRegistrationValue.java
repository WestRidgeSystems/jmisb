package org.jmisb.st1601;

import org.jmisb.api.klv.IKlvValue;

/**
 * ST 1601 Geo-Registration Local Set value.
 *
 * <p>All Geo-Registration Local Set values implement this interface. Users are unlikely to need to
 * implement this interface. Instead, use one of the implementations either directly, or via the
 * Local Set implementation.
 */
public interface IGeoRegistrationValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

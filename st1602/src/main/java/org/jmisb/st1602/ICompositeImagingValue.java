package org.jmisb.st1602;

import org.jmisb.api.klv.IKlvValue;

/**
 * ST 1602 Composite Imaging Local Set value.
 *
 * <p>All Composite Imaging Local Set values implement this interface. Users are unlikely to need to
 * implement this interface. Instead, use one of the implementations either directly, or via the
 * Local Set implementation.
 */
public interface ICompositeImagingValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

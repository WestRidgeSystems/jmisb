package org.jmisb.st1202;

import org.jmisb.api.klv.IKlvValue;

/**
 * ST 1202 Generalized Transformation Local Set value.
 *
 * <p>All Generalized Transformation Local Set values implement this interface. Users are unlikely
 * to need to implement this interface. Instead, use one of the implementations either directly, or
 * via the Local Set implementation.
 */
public interface IGeneralizedTransformationMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

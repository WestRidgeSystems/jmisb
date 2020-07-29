package org.jmisb.api.klv.st0102;

import org.jmisb.api.klv.IKlvValue;

/**
 * ST 0102 value.
 *
 * <p>Each metadata element within ST0102 consists of a key (e.g. tag number, or universal key)
 * represented by a {@link SecurityMetadataKey} instance, and a value represented by a instance
 * implementing this interface.
 */
public interface ISecurityMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

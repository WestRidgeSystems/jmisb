package org.jmisb.api.klv.st0602;

import org.jmisb.api.klv.IKlvValue;

/**
 * ST 0602 value.
 *
 * <p>Each metadata element within ST 0602 consists of a key (universal key) represented by a {@link
 * AnnotationMetadataKey} instance, and a value represented by a instance implementing this
 * interface.
 */
public interface IAnnotationMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

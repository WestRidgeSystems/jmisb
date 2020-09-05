package org.jmisb.api.klv;

/** Generic KLV metadata value that is serializable to a byte array. */
public interface ISerialisableKlvValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}

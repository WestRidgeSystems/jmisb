package org.jmisb.api.klv.st0601;

/**
 * Interface that indicates the KLV Value requires special treatment.
 *
 * <p>If a class implements this interface, it will be used to provide the required bytes when
 * framing (encoding) a {@code UasDatalinkMessage}.
 */
public interface ISpecialFraming {

    /**
     * Get the bytes that correspond to a full KLV encoding of this value.
     *
     * <p>This may include multiple entries (i.e. sequences of Tag-Length-Value encodings).
     *
     * @return byte array corresponding to the encoded KLV value.
     */
    public byte[] getEncodedValue();
}

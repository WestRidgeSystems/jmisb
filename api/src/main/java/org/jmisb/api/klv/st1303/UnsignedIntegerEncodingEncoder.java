package org.jmisb.api.klv.st1303;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;

/**
 * Multi-Dimensional Array Pack (MDAP) Unsigned Integer Encoding Encoder.
 *
 * <p>A multi-dimensional array of unsigned integers (UInt) encodes each Element as a BER-OID. The
 * multi-dimensional UInt array serializes into a single dimensional linear array. Each value of the
 * serialized array is BER-OID encoded and combined into a single block of BER-OID data.
 *
 * <p>Because each value is a variable length Element,extracting individual Elements from the
 * serialized array is not possible until decoding the whole array back into UInt values.
 */
public class UnsignedIntegerEncodingEncoder {

    /**
     * Constructor.
     *
     * <p>MDAP Unsigned Integer encoding does not require parameters.
     */
    public UnsignedIntegerEncodingEncoder() {}

    /**
     * Encode a two dimensional (unsigned) integer array to a Multi-Dimensional Array Pack using
     * Unsigned Integer Encoding.
     *
     * @param data the array of arrays of ({@code boolean}) values.
     * @return the encoded byte array including the MISB ST1303 header and array data.
     * @throws KlvParseException if the encoding fails, such as for invalid array dimensions.
     */
    public byte[] encode(long[][] data) throws KlvParseException {
        if ((data.length < 1) || (data[0].length < 1)) {
            throw new KlvParseException("MDAP encoding requires each dimension to be at least 1");
        }
        long minimumValue = Long.MAX_VALUE;
        for (long[] datum : data) {
            for (long l : datum) {
                if (l < minimumValue) {
                    minimumValue = l;
                }
            }
        }
        ArrayBuilder builder =
                new ArrayBuilder()
                        // Number of dimensions
                        .appendAsOID(2)
                        // dim_1
                        .appendAsOID(data.length)
                        // dim_2
                        .appendAsOID(data[0].length)
                        // E_bytes value
                        .appendAsOID(1)
                        // array processing algorithm (APA)
                        .appendAsOID(ArrayProcessingAlgorithm.UnsignedInteger.getCode())
                        // array processing algorithm support (APAS)
                        .appendAsOID((int) minimumValue);
        for (long[] datum : data) {
            for (long l : datum) {
                int relativeValue = (int) (l - minimumValue);
                builder.appendAsOID(relativeValue);
            }
        }
        return builder.toBytes();
    }

    /**
     * Encode an (unsigned) integer array to a Multi-Dimensional Array Pack using Unsigned Integer
     * Encoding.
     *
     * @param data the array of arrays of ({@code boolean}) values.
     * @return the encoded byte array including the MISB ST1303 header and array data.
     * @throws KlvParseException if the encoding fails, such as for invalid array dimensions.
     */
    public byte[] encode(long[] data) throws KlvParseException {
        if (data.length < 1) {
            throw new KlvParseException("MDAP encoding requires each dimension to be at least 1");
        }
        long minimumValue = Long.MAX_VALUE;
        for (long datum : data) {
            if (datum < minimumValue) {
                minimumValue = datum;
            }
        }
        ArrayBuilder builder =
                new ArrayBuilder()
                        // Number of dimensions
                        .appendAsOID(1)
                        // dim_1
                        .appendAsOID(data.length)
                        // E_bytes value
                        .appendAsOID(1)
                        // array processing algorithm (APA)
                        .appendAsOID(ArrayProcessingAlgorithm.UnsignedInteger.getCode())
                        // array processing algorithm support (APAS)
                        .appendAsOID((int) minimumValue);
        for (long datum : data) {
            int relativeValue = (int) (datum - minimumValue);
            builder.appendAsOID(relativeValue);
        }
        return builder.toBytes();
    }
}

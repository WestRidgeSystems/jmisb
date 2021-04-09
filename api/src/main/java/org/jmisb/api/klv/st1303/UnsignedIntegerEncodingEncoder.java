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
        for (int r = 0; r < data.length; r++) {
            for (int c = 0; c < data[r].length; c++) {
                if (data[r][c] < minimumValue) {
                    minimumValue = data[r][c];
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
        for (int r = 0; r < data.length; ++r) {
            for (int c = 0; c < data[r].length; ++c) {
                int relativeValue = (int) (data[r][c] - minimumValue);
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
        for (int i = 0; i < data.length; i++) {
            if (data[i] < minimumValue) {
                minimumValue = data[i];
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
        for (int i = 0; i < data.length; ++i) {
            int relativeValue = (int) (data[i] - minimumValue);
            builder.appendAsOID(relativeValue);
        }
        return builder.toBytes();
    }
}

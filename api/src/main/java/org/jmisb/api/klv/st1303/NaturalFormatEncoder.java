package org.jmisb.api.klv.st1303;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Multi-Dimensional Array Pack (MDAP) Natural Format Encoder.
 *
 * <p>This encodes the provided array as a sequence of primitive values.
 */
public class NaturalFormatEncoder {

    /**
     * Constructor.
     *
     * <p>MDAP Natural Format encoding does not require parameters.
     */
    public NaturalFormatEncoder() {}

    /**
     * Encode a one dimensional real array to a Multi-Dimensional Array Pack using Natural Format
     * Encoding.
     *
     * @param data the array of ({@code double}) values.
     * @return the encoded byte array including the MISB ST1303 header and array data.
     * @throws KlvParseException if the encoding fails, such as for invalid array dimensions.
     */
    public byte[] encode(double[] data) throws KlvParseException {
        if (data.length < 1) {
            throw new KlvParseException("MDAP encoding requires each dimension to be at least 1");
        }
        // Possibly this could be more efficient, but its hard to tell what accuracy we need.
        ArrayBuilder builder =
                new ArrayBuilder()
                        // Number of dimensions
                        .appendAsOID(1)
                        // dim_1
                        .appendAsOID(data.length)
                        // E_bytes value
                        .appendAsOID(Double.BYTES)
                        // array processing algorithm (APA)
                        // note: no array processing algorithm support (APAS)
                        .appendAsOID(ArrayProcessingAlgorithm.NaturalFormat.getCode());
        for (double datum : data) {
            builder.append(PrimitiveConverter.float64ToBytes(datum));
        }
        return builder.toBytes();
    }

    /**
     * Encode a one dimensional (signed) integer array to a Multi-Dimensional Array Pack using
     * Natural Format Encoding.
     *
     * @param data the array of ({@code long}) values.
     * @return the encoded byte array including the MISB ST1303 header and array data.
     * @throws KlvParseException if the encoding fails, such as for invalid array dimensions.
     */
    public byte[] encode(long[] data) throws KlvParseException {
        if (data.length < 1) {
            throw new KlvParseException("MDAP encoding requires each dimension to be at least 1");
        }
        ArrayBuilder builder =
                new ArrayBuilder()
                        // Number of dimensions
                        .appendAsOID(1)
                        // dim_1
                        .appendAsOID(data.length)
                        // E_bytes value
                        .appendAsOID(Long.BYTES)
                        // array processing algorithm (APA)
                        // note: no array processing algorithm support (APAS)
                        .appendAsOID(ArrayProcessingAlgorithm.NaturalFormat.getCode());
        for (long datum : data) {
            builder.append(PrimitiveConverter.int64ToBytes(datum));
        }
        return builder.toBytes();
    }

    /**
     * Encode a two dimensional (signed) integer array to a Multi-Dimensional Array Pack using
     * Natural Format Encoding.
     *
     * @param data the array of arrays of ({@code long}) values.
     * @return the encoded byte array including the MISB ST1303 header and array data.
     * @throws KlvParseException if the encoding fails, such as for invalid array dimensions.
     */
    public byte[] encode(long[][] data) throws KlvParseException {
        if ((data.length < 1) || (data[0].length < 1)) {
            throw new KlvParseException("MDAP encoding requires each dimension to be at least 1");
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
                        .appendAsOID(Long.BYTES)
                        // array processing algorithm (APA)
                        // note: no array processing algorithm support (APAS)
                        .appendAsOID(ArrayProcessingAlgorithm.NaturalFormat.getCode());
        for (long[] row : data) {
            for (long l : row) {
                builder.append(PrimitiveConverter.int64ToBytes(l));
            }
        }
        return builder.toBytes();
    }

    /**
     * Encode a one dimensional unsigned integer array to a Multi-Dimensional Array Pack using
     * Natural Format Encoding.
     *
     * @param data the arrays of ({@code long}) values.
     * @return the encoded byte array including the MISB ST1303 header and array data.
     * @throws KlvParseException if the encoding fails, such as for invalid array dimensions.
     */
    public byte[] encodeUnsigned(long[] data) throws KlvParseException {
        if (data.length < 1) {
            throw new KlvParseException("MDAP encoding requires each dimension to be at least 1");
        }
        int minRequiredLength = 0;
        for (long datum : data) {
            byte[] bytes = PrimitiveConverter.uintToVariableBytes(datum);
            minRequiredLength = Math.max(minRequiredLength, bytes.length);
        }
        ArrayBuilder builder =
                new ArrayBuilder()
                        // Number of dimensions
                        .appendAsOID(1)
                        // dim_1
                        .appendAsOID(data.length)
                        // E_bytes value
                        .appendAsOID(minRequiredLength)
                        // array processing algorithm (APA)
                        // note: no array processing algorithm support (APAS)
                        .appendAsOID(ArrayProcessingAlgorithm.NaturalFormat.getCode());
        for (long datum : data) {
            byte[] encodedValue = PrimitiveConverter.uintToVariableBytes(datum);
            int numPaddingBytes = minRequiredLength - encodedValue.length;
            for (int i = 0; i < numPaddingBytes; i++) {
                builder.appendByte((byte) 0x00);
            }
            builder.append(encodedValue);
        }
        return builder.toBytes();
    }

    /**
     * Encode a two dimensional unsigned integer array to a Multi-Dimensional Array Pack using
     * Natural Format Encoding.
     *
     * @param data the array of arrays of ({@code long}) values.
     * @return the encoded byte array including the MISB ST1303 header and array data.
     * @throws KlvParseException if the encoding fails, such as for invalid array dimensions.
     */
    public byte[] encodeUnsigned(long[][] data) throws KlvParseException {
        if ((data.length < 1) || (data[0].length < 1)) {
            throw new KlvParseException("MDAP encoding requires each dimension to be at least 1");
        }
        int minRequiredLength = 0;
        for (long[] datum : data) {
            for (long l : datum) {
                byte[] bytes = PrimitiveConverter.uintToVariableBytes(l);
                minRequiredLength = Math.max(minRequiredLength, bytes.length);
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
                        .appendAsOID(minRequiredLength)
                        // array processing algorithm (APA)
                        // note: no array processing algorithm support (APAS)
                        .appendAsOID(ArrayProcessingAlgorithm.NaturalFormat.getCode());
        for (long[] datum : data) {
            for (long l : datum) {
                byte[] encodedValue = PrimitiveConverter.uintToVariableBytes(l);
                int numPaddingBytes = minRequiredLength - encodedValue.length;
                for (int i = 0; i < numPaddingBytes; i++) {
                    builder.appendByte((byte) 0x00);
                }
                builder.append(encodedValue);
            }
        }
        return builder.toBytes();
    }

    /**
     * Encode a two dimensional Boolean array to a Multi-Dimensional Array Pack using Natural Format
     * Encoding.
     *
     * <p>This serialises each value into a byte array.
     *
     * @param data the array of arrays of ({@code boolean}) values.
     * @return the encoded byte array including the MISB ST1303 header and array data.
     * @throws KlvParseException if the encoding fails, such as for invalid array dimensions.
     */
    public byte[] encode(boolean[][] data) throws KlvParseException {
        if ((data.length < 1) || (data[0].length < 1)) {
            throw new KlvParseException("MDAP encoding requires each dimension to be at least 1");
        }
        ArrayBuilder builder =
                new ArrayBuilder()
                        // Number of dimensions
                        .appendAsOID(2)
                        // dim_1
                        .appendAsOID(data.length)
                        // dim_2
                        .appendAsOID(data[0].length)
                        // E_bytes value - single byte
                        .appendAsOID(1)
                        // array processing algorithm (APA)
                        // note no array processing algorithm support (APAS) values
                        .appendAsOID(ArrayProcessingAlgorithm.NaturalFormat.getCode());
        // Array Of Elements
        for (int r = 0; r < data.length; ++r) {
            for (int c = 0; c < data[r].length; ++c) {
                builder.appendByte(data[r][c] == true ? (byte) 0x01 : (byte) 0x00);
            }
        }
        return builder.toBytes();
    }
}

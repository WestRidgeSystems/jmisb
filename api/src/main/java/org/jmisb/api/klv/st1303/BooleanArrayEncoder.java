package org.jmisb.api.klv.st1303;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;

/**
 * Multi-Dimensional Array Pack (MDAP) Boolean Array Encoder.
 *
 * <p>A multi-dimensional array of Boolean values compacts into a series of bytes with each bit in
 * the bytes representing one Boolean value. With zero (0) equaling “false” and one (1) equaling
 * “true”, the multi-dimensional Boolean array is serialized into a single dimensional linear array.
 * The resulting byte representation groups each eight bits into a byte for the whole sequence of
 * Boolean values.
 */
public class BooleanArrayEncoder {

    /**
     * Constructor.
     *
     * <p>MDAP Boolean encoding does not require parameters.
     */
    public BooleanArrayEncoder() {}

    /**
     * Encode a two dimensional Boolean array to a Multi-Dimensional Array Pack using Binary Array
     * Encoding.
     *
     * <p>This packs each value into a bit-encoded 1D array, and serialises the array to a byte
     * array.
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
                        // E_bytes value, see ST1303 Appendix D.2
                        .appendAsOID(1)
                        // array processing algorithm (APA)
                        // note no array processing algorithm support (APAS) values
                        .appendAsOID(ArrayProcessingAlgorithm.BooleanArray.getCode());
        // Array Of Elements
        for (int r = 0; r < data.length; ++r) {
            for (int c = 0; c < data[r].length; ++c) {
                builder.appendAsBit(data[r][c]);
            }
        }
        return builder.toBytes();
    }
}

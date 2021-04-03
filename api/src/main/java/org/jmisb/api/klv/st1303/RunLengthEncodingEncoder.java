package org.jmisb.api.klv.st1303;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;

/**
 * Multi-Dimensional Array Pack (MDAP) Run Length Encoding Encoder.
 *
 * <p>Run-Length Encoding (RLE) an array provides a method for reducing the bandwidth for arrays
 * which have contiguous blocks of a constant value. Instead of including each value of an array in
 * the MDAP, an RLE array only lists starting coordinates and the lengths of a constant value.
 */
public class RunLengthEncodingEncoder {

    /**
     * Constructor.
     *
     * <p>MDAP Run Length encoding does not require parameters.
     */
    public RunLengthEncodingEncoder() {}

    /**
     * Encode a two dimensional Boolean array to a Multi-Dimensional Array Pack using Run Length
     * Encoding.
     *
     * <p>There is more than one valid result from MDAP Run Length Encoding.
     *
     * @param data the array of arrays of ({@code boolean}) values.
     * @return the encoded byte array including the MISB ST1303 header and array data.
     * @throws KlvParseException if the encoding fails, such as for invalid array dimensions.
     */
    public byte[] encode(boolean[][] data) throws KlvParseException {
        byte defaultValue = 0x00;
        if ((data.length < 1) || (data[0].length < 1)) {
            throw new KlvParseException("MDAP encoding requires each dimension to be at least 1");
        }
        boolean[][] encodedMap = new boolean[data.length][data[0].length];
        boolean firstValue = data[0][0];
        if (firstValue) {
            defaultValue = 0x01;
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
                        .appendAsOID(ArrayProcessingAlgorithm.RunLengthEncoding.getCode())
                        .append(new byte[] {defaultValue});
        for (int r = 0; r < data.length; ++r) {
            for (int c = 0; c < data[r].length; ++c) {
                if (data[r][c] == (defaultValue == 0x01)) {
                    encodedMap[r][c] = true;
                }
            }
        }
        for (int r = 0; r < data.length; ++r) {
            for (int c = 0; c < data[r].length; ++c) {
                if (encodedMap[r][c]) {
                    continue;
                }
                byte runValue = data[r][c] ? (byte) 0x01 : (byte) 0x00;
                int horizontalRunLength = 0;
                for (int i = 0; i < data[r].length - c; ++i) {
                    if (data[r][c] == data[r][c + i]) {
                        horizontalRunLength += 1;
                    } else {
                        break;
                    }
                }
                int verticalRunLength = 0;
                for (int i = 0; i < data.length - r; ++i) {
                    if (data[r][c] == data[r + i][c]) {
                        verticalRunLength += 1;
                    } else {
                        break;
                    }
                }
                if ((verticalRunLength > 1) && (horizontalRunLength > 1)) {
                    // See if we can make a rectangular patch out of this
                    boolean isRectangular = true;
                    for (int i = 0; i < verticalRunLength; i++) {
                        for (int j = 0; j < horizontalRunLength; j++) {
                            if (data[r][c] != data[r + i][c + j]) {
                                isRectangular = false;
                            }
                        }
                    }
                    if (!isRectangular) {
                        // Use whichever is the longest single pixel run
                        if (verticalRunLength > horizontalRunLength) {
                            horizontalRunLength = 1;
                        } else {
                            verticalRunLength = 1;
                        }
                    }
                }
                builder.appendAsOID(runValue)
                        .appendAsOID(r)
                        .appendAsOID(c)
                        .appendAsOID(verticalRunLength)
                        .appendAsOID(horizontalRunLength);
                // update encoded map with solved pixels
                for (int i = 0; i < verticalRunLength; i++) {
                    for (int j = 0; j < horizontalRunLength; j++) {
                        encodedMap[r + i][c + j] = true;
                    }
                }
            }
        }
        return builder.toBytes();
    }
}

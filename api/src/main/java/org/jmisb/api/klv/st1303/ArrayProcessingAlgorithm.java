package org.jmisb.api.klv.st1303;

import java.util.HashMap;
import java.util.Map;

/**
 * Array Processing Algorithm Identifiers.
 *
 * <p>These are the defined Array Processing Algorithm values from ST 1303.2 Table 3.
 *
 * <p>The result of {@link #getCode} is the APA value in the array pack.
 */
public enum ArrayProcessingAlgorithm {
    /**
     * Unused.
     *
     * <p>This does not represent a valid algorithm.
     */
    Unused(0x00),
    /**
     * The array is in "natural" format.
     *
     * <p>This is a column-minor (row-major for 2D) ordering using the usual primitive
     * representation for the data type.
     */
    NaturalFormat(0x01),
    /**
     * The array is floating point data encoded using IMAP format.
     *
     * <p>This is the same ordering as {@link #NaturalFormat} but with real (float/double) numbers
     * encoded using ST1201 IMAPA or IMAPB.
     */
    ST1201(0x02),
    /**
     * The array is booleans packed into bytes.
     *
     * <p>Each boolean value is represented by a bit.
     */
    BooleanArray(0x03),
    /**
     * The array is unsigned integer values, but with an offset value applied to reduce the number
     * of bytes required.
     */
    UnsignedInteger(0x04),
    /**
     * The array is run-length encoded.
     *
     * <p>Runs can be of arbitrary dimensions.
     */
    RunLengthEncoding(0x05);

    private final int code;

    private static final Map<Integer, ArrayProcessingAlgorithm> algorithmTable = new HashMap<>();

    static {
        for (ArrayProcessingAlgorithm algorithm : values()) {
            algorithmTable.put(algorithm.getCode(), algorithm);
        }
    }

    /**
     * Constructor.
     *
     * <p>Internal use only.
     *
     * @param code the code for the algorithm.
     */
    ArrayProcessingAlgorithm(int code) {
        this.code = code;
    }

    /**
     * Get the Array Processing Algorithm Value for this identifier.
     *
     * @return integer form.
     */
    public int getCode() {
        return code;
    }

    /**
     * Look up an Array Processing Algorithm by code value.
     *
     * @param code the APA value (per ST1303.2 Table 3).
     * @return the corresponding ArrayProcessingAlgorithm.
     */
    public static ArrayProcessingAlgorithm getValue(int code) {
        return algorithmTable.getOrDefault(code, Unused);
    }
}

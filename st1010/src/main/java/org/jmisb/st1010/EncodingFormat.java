package org.jmisb.st1010;

import org.jmisb.api.common.KlvParseException;

/**
 * Encoding format for Standard Deviation and Correlation Coefficient values.
 *
 * <p>ST 1010 supports the encoding of values in two formats (at least in Mode 2 parsing) - IEEE 754
 * floating point, and ST 1201 integer encoding. This enumeration allows identification of the
 * parsing format.
 *
 * <p>In Mode 1 parsing, the format for standard deviation are externally specified (in the invoking
 * specification document), but the correlation coefficients are always in ST 1201 format.
 */
public enum EncodingFormat {
    /**
     * Value is encoded in an IEEE 754 floating point format.
     *
     * <p>The supported formats are per MISB ST 0107.
     */
    IEEE(0),
    /**
     * Value is encoded in MISB ST 1201 format.
     *
     * <p>The encoding is IMAPB(-1.0, 1.0).
     */
    ST1201(1);

    private EncodingFormat(int value) {
        v = value;
    }

    private final int v;

    /**
     * Get the value associated with this encoding format.
     *
     * @return 1 if the format is ST1201, otherwise 0 to mean IEEE format.
     */
    public int getValue() {
        return v;
    }

    /**
     * Get the encoding from an integer value.
     *
     * @param v the value to look up (0 or 1)
     * @return the corresponding EncodingFormat for the value.
     * @throws KlvParseException if the value is out of range.
     */
    public static EncodingFormat getEncoding(int v) throws KlvParseException {
        if (v == 1) {
            return EncodingFormat.ST1201;
        } else if (v == 0) {
            return EncodingFormat.IEEE;
        } else {
            throw new KlvParseException("The only encoding format values are 0 or 1, got " + v);
        }
    }
}

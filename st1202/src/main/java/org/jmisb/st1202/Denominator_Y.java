package org.jmisb.st1202;

import org.jmisb.api.common.KlvParseException;

/** Denominator - y factor (ST 1202 Local Set Item 8). */
public class Denominator_Y extends AbstractGeneralizedTransformationMetadataValue {

    private static final String DISPLAY_NAME = "Denominator - y factor";

    /**
     * Create from value.
     *
     * @param value the value as a float.
     */
    public Denominator_Y(float value) {
        super(value, DISPLAY_NAME);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public Denominator_Y(byte[] bytes) throws KlvParseException {
        super(bytes, DISPLAY_NAME);
    }
}

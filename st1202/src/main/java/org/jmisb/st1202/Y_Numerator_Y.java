package org.jmisb.st1202;

import org.jmisb.api.common.KlvParseException;

/** y Equation Numerator - y factor (ST 1202 Local Set Item 5). */
public class Y_Numerator_Y extends AbstractGeneralizedTransformationMetadataValue {

    private static final String DISPLAY_NAME = "y Equation Numerator - y factor";

    /**
     * Create from value.
     *
     * @param value the value as a float.
     */
    public Y_Numerator_Y(float value) {
        super(value, DISPLAY_NAME);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public Y_Numerator_Y(byte[] bytes) throws KlvParseException {
        super(bytes, DISPLAY_NAME);
    }
}

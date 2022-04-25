package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;

/**
 * Temperature (ST 0809 Local Set Item 16).
 *
 * <p>Atmospheric temperature in Celsius.
 */
public class Temperature extends AbstractTemperature {

    /**
     * Create from value.
     *
     * @param temp temperature in degrees Celcius
     */
    public Temperature(float temp) {
        super(temp);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public Temperature(byte[] bytes) throws KlvParseException {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Temperature";
    }
}

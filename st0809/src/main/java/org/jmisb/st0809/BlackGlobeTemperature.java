package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;

/**
 * Black Globe Temperature (ST 0809 Local Set Item 7).
 *
 * <p>Temperature of a dry bulb inside a black copper sphere (usually 150 mm in diameter). Used to
 * calculate effects of solar radiation and wind on apparent temperature.
 */
public class BlackGlobeTemperature extends AbstractTemperature {

    /**
     * Create from value.
     *
     * @param temp temperature in degrees Celcius
     */
    public BlackGlobeTemperature(float temp) {
        super(temp);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public BlackGlobeTemperature(byte[] bytes) throws KlvParseException {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Black Globe Temperature";
    }
}

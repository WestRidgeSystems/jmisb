package org.jmisb.api.klv.st0809;

import org.jmisb.api.common.KlvParseException;

/**
 * Dry Bulb Temperature (ST 0809 Local Set Item 17).
 *
 * <p>The temperature in Celsius of the air as measured by the dry-bulb thermometer of a
 * psychrometer.
 */
public class DryBulbTemperature extends AbstractTemperature {

    /**
     * Create from value.
     *
     * @param temp temperature in degrees Celcius
     */
    public DryBulbTemperature(float temp) {
        super(temp);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public DryBulbTemperature(byte[] bytes) throws KlvParseException {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Dry Bulb Temperature";
    }
}

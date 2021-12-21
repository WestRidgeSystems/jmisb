package org.jmisb.api.klv.st0809;

import org.jmisb.api.common.KlvParseException;

/**
 * Wet Bulb Temperature Heat Index (ST 0809 Local Set Item 8).
 *
 * <p>A composite temperature used to estimate the effect of temperature, humidity, and solar
 * radiation on people. Consists of weighted sum of Web Bulb Temperature (0.7), Dry Bulb Temperature
 * (0.1), and Black Globe Temperature (0.2).
 */
public class WetBulbTemperatureHeatIndex extends AbstractTemperature {

    /**
     * Create from value.
     *
     * @param temp temperature in degrees Celcius
     */
    public WetBulbTemperatureHeatIndex(float temp) {
        super(temp);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public WetBulbTemperatureHeatIndex(byte[] bytes) throws KlvParseException {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Wet Bulb Temperature Heat Index";
    }
}

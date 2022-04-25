package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;

/**
 * Wet Bulb Temperature (ST 0809 Local Set Item 18).
 *
 * <p>The temperature in Celsius a parcel of air would have if cooled adiabatically to saturation at
 * constant pressure by evaporation of water into it with all latent heat supplied by the parcel.
 */
public class WetBulbTemperature extends AbstractTemperature {

    /**
     * Create from value.
     *
     * @param temp temperature in degrees Celcius
     */
    public WetBulbTemperature(float temp) {
        super(temp);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public WetBulbTemperature(byte[] bytes) throws KlvParseException {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Wet Bulb Temperature";
    }
}

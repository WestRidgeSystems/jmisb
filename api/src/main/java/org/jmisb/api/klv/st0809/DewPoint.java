package org.jmisb.api.klv.st0809;

import org.jmisb.api.common.KlvParseException;

/**
 * Dew Point (ST 0809 Local Set Item 20).
 *
 * <p>The temperature to which a given parcel of air must be cooled at constant pressure and
 * constant water-vapor content for saturation to occur. When this temperature is 0 oC, it is
 * sometimes called the Frost Point.
 *
 * <p>The temperature at which the saturation vapor pressure of the parcel is equal to the actual
 * vapor pressure of the contained water vapor.
 */
public class DewPoint extends AbstractTemperature {

    /**
     * Create from value.
     *
     * @param temp dew point in degrees Celcius
     */
    public DewPoint(float temp) {
        super(temp);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public DewPoint(byte[] bytes) throws KlvParseException {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Dew Point";
    }
}

package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Barometric Pressure (ST 0809 Local Set Item 6).
 *
 * <p>Atmospheric pressure as measured by a barometer using a column of liquid.
 */
public class BarometricPressure implements IMeteorologicalMetadataValue {

    private float value;
    /**
     * Create from value.
     *
     * @param pressure barometric pressure in Pascals
     */
    public BarometricPressure(float pressure) {
        this.value = pressure;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public BarometricPressure(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }

        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Barometric Pressure";
    }

    /**
     * Get the pressure value.
     *
     * @return barometric pressure in Pascals
     */
    public float getPressure() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.2f Pa", this.value);
    }
}

package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Ambient Light Color Temperature(ST 0809 Local Set Item 23).
 *
 * <p>The color temperature of the ambient scene in Kelvin.
 */
public class AmbientLightColorTemperature implements IMeteorologicalMetadataValue {

    private float value;
    /**
     * Create from value.
     *
     * @param colorTemperature The color temperature of the ambient scene in Kelvin.
     */
    public AmbientLightColorTemperature(float colorTemperature) {
        this.value = colorTemperature;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public AmbientLightColorTemperature(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }
        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Ambient Light Color Temperature";
    }

    /**
     * Get the color temperature.
     *
     * @return color temperature in Kelvin.
     */
    public float getColorTemperature() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.0f K", this.value);
    }
}

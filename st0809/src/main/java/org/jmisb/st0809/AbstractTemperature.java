package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

abstract class AbstractTemperature implements IMeteorologicalMetadataValue {

    private final float temperature;

    /**
     * Create from value.
     *
     * @param temp temperature in degrees Celcius
     */
    public AbstractTemperature(float temp) {
        this.temperature = temp;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public AbstractTemperature(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }

        this.temperature = PrimitiveConverter.toFloat32(bytes);
    }

    /**
     * Get the value as temperature.
     *
     * @return Temperature in degrees Celcius
     */
    public float getTemperature() {
        return this.temperature;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes(temperature);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.2f\u00B0C", this.temperature);
    }

    @Override
    public abstract String getDisplayName();
}

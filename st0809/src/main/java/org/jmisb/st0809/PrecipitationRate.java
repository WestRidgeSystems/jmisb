package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Precipitation Rate (ST 0809 Local Set Item 34).
 *
 * <p>Rate of precipitation in mm/hr.
 */
public class PrecipitationRate implements IMeteorologicalMetadataValue {

    private float value;
    /**
     * Create from value.
     *
     * @param rate precipitation rate in mm/hr
     */
    public PrecipitationRate(float rate) {
        this.value = rate;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public PrecipitationRate(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }
        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Precipitation Rate";
    }

    /**
     * Get the precipitation rate value.
     *
     * @return precipitation rate in mm/hr
     */
    public float getPrecipitationRate() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.2f mm/hr", this.value);
    }
}

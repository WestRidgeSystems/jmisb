package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Temperature Lapse Rate (ST 0809 Local Set Item 21).
 *
 * <p>The negative of the rate of change of temperature with respect to altitude (-dT/dz) in dry,
 * unperturbed air.
 *
 * <p>Note that ST 0809.2 says the units are °C. That is unlikely - it needs to be something like
 * °C/km or °C/1000ft to be dimensionally correct. There is an open query into the MISB to clarify
 * this.
 */
public class TemperatureLapseRate implements IMeteorologicalMetadataValue {

    private float value;
    /**
     * Create from value.
     *
     * @param rate lapse rate
     */
    public TemperatureLapseRate(float rate) {
        this.value = rate;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public TemperatureLapseRate(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }
        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Temperature Lapse Rate";
    }

    /**
     * Get the temperature lapse rate value.
     *
     * @return temperature lapse rate
     */
    public float getLapseRate() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        // TODO: update this after the appropriate units are defined.
        return String.format("%.2f °C", this.value);
    }
}

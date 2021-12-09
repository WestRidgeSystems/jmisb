package org.jmisb.api.klv.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Ozone Concentration (ST 0809 Local Set Item 15).
 *
 * <p>Atmospheric O<sub>3</sub> concentration in parts per million (ppm).
 */
public class OzoneConcentration implements IMeteorologicalMetadataValue {

    private float value;
    /**
     * Create from value.
     *
     * @param concentration concentration in ppm.
     */
    public OzoneConcentration(float concentration) {
        this.value = concentration;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public OzoneConcentration(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }
        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Ozone Concentration";
    }

    /**
     * Get the ozone concentration.
     *
     * @return concentration in ppm.
     */
    public float getConcentration() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.3f ppm", this.value);
    }
}

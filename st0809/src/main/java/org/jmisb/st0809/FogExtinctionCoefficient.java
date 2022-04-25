package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Fog Extinction Coefficient (ST 0809 Local Set Item 32).
 *
 * <p>The Fog Extinction Coefficient is the fraction of light lost to scattering and absorption per
 * unit distance due to fog. It has units of 1/m.
 */
public class FogExtinctionCoefficient implements IMeteorologicalMetadataValue {

    private float value;
    /**
     * Create from value.
     *
     * @param coefficient fog extinction coefficient in 1/m (i.e. m<sup>-1</sup>).
     */
    public FogExtinctionCoefficient(float coefficient) {
        this.value = coefficient;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public FogExtinctionCoefficient(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }
        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Fog Extinction Coefficient";
    }

    /**
     * Get the fog extinction coefficient value.
     *
     * @return fog extinction coefficient in 1/m (i.e. m<sup>-1</sup>).
     */
    public float getValue() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.3f 1/m", this.value);
    }
}

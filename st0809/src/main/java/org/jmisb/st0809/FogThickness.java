package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Fog Thickness (ST 0809 Local Set Item 30).
 *
 * <p>Fog thickness in meters.
 */
public class FogThickness implements IMeteorologicalMetadataValue {

    private float value;

    /**
     * Create from value.
     *
     * @param thickness thickness in meters.
     */
    public FogThickness(float thickness) {
        this.value = thickness;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public FogThickness(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }
        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Fog Thickness";
    }

    /**
     * Get the thickness value.
     *
     * @return thickness in meters.
     */
    public float getThickness() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.1f m", this.value);
    }
}

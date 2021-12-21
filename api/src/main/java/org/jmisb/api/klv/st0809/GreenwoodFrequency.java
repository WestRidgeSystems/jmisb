package org.jmisb.api.klv.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Greenwood Frequency (ST 0809 Local Set Item 37).
 *
 * <p>Measure of the temporal scale of atmospheric turbulence in Hz.
 */
public class GreenwoodFrequency implements IMeteorologicalMetadataValue {

    private float value;
    /**
     * Create from value.
     *
     * @param frequency frequency in Hz.
     */
    public GreenwoodFrequency(float frequency) {
        this.value = frequency;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public GreenwoodFrequency(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }
        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Greenwood Frequency";
    }

    /**
     * Get the Greenwood frequency.
     *
     * @return frequency in Hz.
     */
    public float getFrequency() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.1f Hz", this.value);
    }
}

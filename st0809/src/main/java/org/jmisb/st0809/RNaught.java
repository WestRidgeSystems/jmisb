package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * R Naught - r<sub>0</sub> (ST 0809 Local Set Item 35).
 *
 * <p>Fried’s seeing parameter or transverse coherence length (in cm) at 5000 Å (500 nm).
 */
public class RNaught implements IMeteorologicalMetadataValue {

    private float value;

    /**
     * Create from value.
     *
     * @param length length in centimeters.
     */
    public RNaught(float length) {
        this.value = length;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public RNaught(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }
        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "R Naught";
    }

    /**
     * Get the value.
     *
     * @return value in centimeters.
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
        return String.format("%.1f cm", this.value);
    }
}

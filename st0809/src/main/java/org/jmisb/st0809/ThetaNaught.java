package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Theta Naught - θ<sub>0</sub> (ST 0809 Local Set Item 36).
 *
 * <p>Isoplanatic angle; the measure of how quickly atmospheric turbulence changes relative to the
 * viewing angle as seen from a receiver. Given in micro-radians.
 */
public class ThetaNaught implements IMeteorologicalMetadataValue {

    private float value;

    /**
     * Create from value.
     *
     * @param angle angle in micro-radians
     */
    public ThetaNaught(float angle) {
        this.value = angle;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public ThetaNaught(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }
        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Theta Naught";
    }

    /**
     * Get the value.
     *
     * @return angle in micro-radians.
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
        return String.format("%.2f µr", this.value);
    }
}

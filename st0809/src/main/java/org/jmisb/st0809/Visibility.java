package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Visibility (ST 0809 Local Set Item 25).
 *
 * <p>Visibility range defined by Koschmieder formula in km.
 */
public class Visibility implements IMeteorologicalMetadataValue {

    private float value;

    /**
     * Create from value.
     *
     * @param visibility visibility in km.
     */
    public Visibility(float visibility) {
        this.value = visibility;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public Visibility(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }
        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Visibility";
    }

    /**
     * Get the visibility value.
     *
     * @return visibility in km.
     */
    public float getVisibility() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.3f km", this.value);
    }
}

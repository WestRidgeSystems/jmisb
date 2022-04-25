package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Rytov Parameter (ST 0809 Local Set Item 38).
 *
 * <p>Log-amplitude variance predicted by an approximate solution to Maxwell’s Equations for
 * propagation through a medium with a random Index of Refraction (unit less).
 */
public class RytovParameter implements IMeteorologicalMetadataValue {

    private float value;

    /**
     * Create from value.
     *
     * @param variance the Rytov Parameter value
     */
    public RytovParameter(float variance) {
        this.value = variance;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public RytovParameter(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }
        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Rytov Parameter";
    }

    /**
     * Get the value.
     *
     * @return parameter value
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
        return String.format("%.3f", this.value);
    }
}

package org.jmisb.api.klv.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

abstract class AbstractIllumination implements IMeteorologicalMetadataValue {

    private float value;

    /**
     * Create from value.
     *
     * @param illumination illumination value
     */
    protected AbstractIllumination(float illumination) {
        this.value = illumination;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    protected AbstractIllumination(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }

        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public abstract String getDisplayName();

    /**
     * Get the illumination value.
     *
     * @return illumination in Lux.
     */
    public float getIllumination() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.3f lx", this.value);
    }
}

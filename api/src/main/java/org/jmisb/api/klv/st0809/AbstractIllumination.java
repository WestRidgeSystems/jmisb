package org.jmisb.api.klv.st0809;

import org.jmisb.core.klv.PrimitiveConverter;

abstract class AbstractIllumination implements IMeteorologicalMetadataValue {

    protected float value;

    public AbstractIllumination() {}

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

package org.jmisb.api.klv.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

abstract class AbstractPercentage implements IMeteorologicalMetadataValue {

    protected static final float MIN_VALUE = 0.0f;
    protected static final float MAX_VALUE = 100.0f;
    protected final float value;

    AbstractPercentage(final float percentage) {
        if (percentage < MIN_VALUE || percentage > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [0,100]");
        }
        this.value = percentage;
    }

    AbstractPercentage(final byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }
        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public abstract String getDisplayName();

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.1f %%", this.value);
    }

    /**
     * Get the cloud cover percentage.
     *
     * @return the cloud cover percentage in the range [0,100]
     */
    public float getValue() {
        return this.value;
    }
}

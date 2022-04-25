package org.jmisb.eg0104;

import org.jmisb.core.klv.PrimitiveConverter;

/** Altitude value, generic value for EG 0104. */
public class AltitudeValue implements IPredatorMetadataValue {
    private final String label;
    private final float value;

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 4
     * @param label Human-readable label
     */
    public AltitudeValue(byte[] bytes, String label) {
        this.value = PrimitiveConverter.toFloat32(bytes);
        this.label = label;
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.1f m", value);
    }

    @Override
    public String getDisplayName() {
        return label;
    }

    /**
     * Get the elevation.
     *
     * @return elevation in meters.
     */
    public float getElevation() {
        return value;
    }
}

package org.jmisb.eg0104;

import org.jmisb.core.klv.PrimitiveConverter;

/** 32 bit floating point value, generic value for EG 0104. */
public class AngleValue implements IPredatorMetadataValue {
    private final String label;
    private final float degrees;

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 4
     * @param label Human-readable label
     */
    public AngleValue(byte[] bytes, String label) {
        this.degrees = PrimitiveConverter.toFloat32(bytes);
        this.label = label;
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.4f\u00B0", degrees);
    }

    @Override
    public String getDisplayName() {
        return label;
    }

    /**
     * Get the value of this angle in degrees.
     *
     * @return the value in degrees.
     */
    public double getDegrees() {
        return degrees;
    }
}

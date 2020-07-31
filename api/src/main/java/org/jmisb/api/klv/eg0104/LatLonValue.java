package org.jmisb.api.klv.eg0104;

import org.jmisb.core.klv.PrimitiveConverter;

/** Latitude or Longitude value for EG 0104. */
public class LatLonValue implements IPredatorMetadataValue {
    private final String label;
    private final double degrees;

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 8
     * @param label Human-readable label
     */
    public LatLonValue(byte[] bytes, String label) {
        this.degrees = PrimitiveConverter.toFloat64(bytes);
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
     * Get the value of this latitude or longitude in degrees.
     *
     * @return the value in degrees. North and East are positive.
     */
    public double getDegrees() {
        return degrees;
    }
}

package org.jmisb.api.klv.st1108.st1108_3.metric;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Metric Value.
 *
 * <p>From ST 1108.3:
 *
 * <blockquote>
 *
 * The Metric Value mandatory item is a floating-point numeric based on the resultant calculation.
 * The length field of the Metric Value KLV item dictates the length of the corresponding Metric
 * Value.
 *
 * </blockquote>
 */
public class MetricValue implements IMetricLocalSetValue {
    private final double value;

    /**
     * Create from value.
     *
     * @param metricValue the result of the metric algorithm or process.
     */
    public MetricValue(float metricValue) {
        this((double) metricValue);
    }

    /**
     * Create from value.
     *
     * @param metricValue the result of the metric algorithm or process.
     */
    public MetricValue(double metricValue) {
        value = metricValue;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 4 or 8 bytes, IEEE-754 format.
     */
    public MetricValue(byte[] bytes) {
        if ((bytes.length != Float.BYTES) && (bytes.length != Double.BYTES)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is four or eight byte floating point value");
        }
        value = PrimitiveConverter.toFloat64(bytes);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes((float) value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.2f", value);
    }

    @Override
    public final String getDisplayName() {
        return "Metric Value";
    }

    /**
     * Get the value of this metric.
     *
     * @return the metric value
     */
    public double getValue() {
        return value;
    }
}

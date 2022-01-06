package org.jmisb.api.klv.st1108.st1108_3.metric;

import org.jmisb.api.klv.st0107.Utf8String;

/**
 * Metric Parameters.
 *
 * <p>From ST 1108.3:
 *
 * <blockquote>
 *
 * <p>Metric Parameters optional item is a user-defined string for specifying any unique conditions
 * used in the computation. For example, within the period of calculation are all Frames used and
 * averaged, are several Frames skipped, etc. This information ensures repeatable calculations, or
 * the same process applied downstream for like-metric comparisons.
 *
 * </blockquote>
 */
public class MetricParameters implements IMetricLocalSetValue {
    private final Utf8String parameters;

    /**
     * Create from value.
     *
     * @param metricParameters the parameters for the metric calculation process.
     */
    public MetricParameters(String metricParameters) {
        parameters = new Utf8String(metricParameters);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of the metric parameters encoded in UTF-8.
     */
    public MetricParameters(byte[] bytes) {
        parameters = new Utf8String(bytes);
    }

    @Override
    public byte[] getBytes() {
        return parameters.getBytes();
    }

    @Override
    public String getDisplayableValue() {
        return getParameters();
    }

    @Override
    public final String getDisplayName() {
        return "Metric Parameters";
    }

    /**
     * Get the parameters of this metric.
     *
     * @return the metric parameters
     */
    public String getParameters() {
        return parameters.getValue();
    }
}

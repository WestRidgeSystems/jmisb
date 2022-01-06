package org.jmisb.api.klv.st1108.st1108_3.metric;

import org.jmisb.api.klv.st0107.Utf8String;
import org.jmisb.api.klv.st1108.*;

/**
 * Metric Version.
 *
 * <p>From ST 1108.3:
 *
 * <blockquote>
 *
 * The Metric Version mandatory item serves a dual role. Firstly, it indicates how the method used
 * to calculate the metric, and secondly, it provides configuration management information. When
 * generated by a human observer, the value is “human”. When generated by an algorithm, the value is
 * an alphanumeric configuration management identifier for the algorithm.
 *
 * </blockquote>
 */
public class MetricVersion implements IMetricLocalSetValue {
    private final Utf8String version;

    /**
     * Create from value.
     *
     * @param metricVersion the version of the metric algorithm, or "human".
     */
    public MetricVersion(String metricVersion) {
        version = new Utf8String(metricVersion);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of the metric version encoded in UTF-8.
     */
    public MetricVersion(byte[] bytes) {
        version = new Utf8String(bytes);
    }

    @Override
    public byte[] getBytes() {
        return version.getBytes();
    }

    @Override
    public String getDisplayableValue() {
        return getVersion();
    }

    @Override
    public final String getDisplayName() {
        return "Metric Version";
    }

    /**
     * Get the version of this metric.
     *
     * @return the metric version
     */
    public String getVersion() {
        return version.getValue();
    }
}
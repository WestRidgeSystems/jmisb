package org.jmisb.api.klv.st1108.st1108_3.metric;

import java.nio.charset.StandardCharsets;
import org.jmisb.api.klv.st1108.*;

/**
 * Metric Name.
 *
 * <p>From ST 1108.3:
 *
 * <blockquote>
 *
 * The Metric Name is the name of the metric calculated. Although its type is a string MISB defines
 * the following strings for consistency.
 *
 * <table border="1">
 * <tbody>
 * <tr><td>Metric Name</td><td>Meaning</td></tr>
 * <tr><td>GSD</td><td>Ground Sample Density</td></tr>
 * <tr><td>MSSIM</td><td>Multi-Scale Structural Similarity Index</td></tr>
 * <tr><td>PSNR</td><td>Peak Signal-to-Noise Ratio</td></tr>
 * <tr><td>RER</td><td>Relative Edge Response</td></tr>
 * <tr><td>SNR</td><td>Signal-to-Noise Ratio</td></tr>
 * <tr><td>SSIM</td><td>Structural Similarity Index</td></tr>
 * <tr><td>VNIIRS</td><td>Video-National Imagery Interpretability Rating Scale (see ST 0901)</td></tr>
 * </tbody>
 * </table>
 *
 * </blockquote>
 */
public class MetricName implements IMetricLocalSetValue {
    private final String name;

    /**
     * Create from value.
     *
     * @param metricName the name of the metric (from the MISB defined list, or user defined).
     */
    public MetricName(String metricName) {
        name = metricName;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of the metric name encoded in UTF-8.
     */
    public MetricName(byte[] bytes) {
        name = new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public byte[] getBytes() {
        return name.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getDisplayableValue() {
        return getName();
    }

    @Override
    public final String getDisplayName() {
        return "Metric Name";
    }

    /**
     * Get the name of this metric.
     *
     * @return the metric name
     */
    public String getName() {
        return name;
    }
}

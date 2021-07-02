package org.jmisb.api.klv.st1108.st1108_3.metric;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/**
 * ST 1108.3 Metric Local Set tags - description and numbers.
 *
 * <p>The Metric Local Set is a repeatable nested local set within the ST 1108.3 Interpretability
 * and Quality Local Set.
 */
public enum MetricLocalSetKey implements IKlvKey {
    /**
     * Unknown key.
     *
     * <p>This should not be created. It is used to provide a mapping for "any other value".
     */
    Undefined(0),
    /**
     * Metric Name.
     *
     * <p>The Metric Name is the name of the metric calculated.
     */
    MetricName(1),
    /**
     * Metric Version.
     *
     * <p>The Metric Version mandatory item serves a dual role. Firstly, it indicates how the method
     * used to calculate the metric, and secondly, it provides configuration management information.
     */
    MetricVersion(2),
    /**
     * Metric Implementer.
     *
     * <p>The Metric Implementer mandatory item identifies the organization responsible for how a
     * metric is calculated. This enables various versions of the same metric without having to
     * maintain an active registry.
     */
    MetricImplementer(3),
    /**
     * Optional Metric Parameters.
     *
     * <p>Metric Parameters optional item is a user-defined string for specifying any unique
     * conditions used in the computation.
     */
    MetricParameters(4),
    /**
     * Metric Time.
     *
     * <p>Metric Time mandatory item is the time of metric assessment, which with respect to the
     * Metric Time pack may be a future value.
     */
    MetricTime(5),
    /**
     * Metric Value.
     *
     * <p>The Metric Value mandatory item is a floating-point numeric based on the resultant
     * calculation.
     */
    MetricValue(6);

    private int tag;

    private static final Map<Integer, MetricLocalSetKey> tagTable = new HashMap<>();

    static {
        for (MetricLocalSetKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    MetricLocalSetKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with this enumeration value.
     *
     * @return integer tag value for the metadata key
     */
    @Override
    public int getIdentifier() {
        return tag;
    }

    /**
     * Look up the metadata key by tag identifier.
     *
     * @param tag the integer tag value to look up
     * @return corresponding metadata key, or Undefined if the key is not known / valid.
     */
    public static MetricLocalSetKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}

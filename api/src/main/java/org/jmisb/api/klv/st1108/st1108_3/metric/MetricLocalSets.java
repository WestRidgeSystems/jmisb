package org.jmisb.api.klv.st1108.st1108_3.metric;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;

/**
 * Metric Local Sets.
 *
 * <p>This class represents any number (zero or more) Metric Local Set instances that may be found
 * within an {@code InterpretabilityQualityLocalSet}.
 */
public class MetricLocalSets implements IInterpretabilityQualityMetadataValue, INestedKlvValue {

    public List<MetricLocalSet> metricLocalSets = new ArrayList<>();

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array containing an encoded MetricLocalSet
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public MetricLocalSets(final byte[] bytes) throws KlvParseException {
        this.addMetricFromBytes(bytes);
    }

    /**
     * Create from a single metric.
     *
     * @param metric the metric to add.
     */
    public MetricLocalSets(MetricLocalSet metric) {
        metricLocalSets.add(metric);
    }

    /**
     * Create from multiple metrics.
     *
     * @param metrics the metric to add.
     */
    public MetricLocalSets(List<MetricLocalSet> metrics) {
        metricLocalSets.addAll(metrics);
    }

    @Override
    public String getDisplayableValue() {
        return "[Metrics]";
    }

    @Override
    public final String getDisplayName() {
        return "Metrics";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        return metricLocalSets.get(tag.getIdentifier());
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        Set<MetricLocalSetsKey> identifiers = new TreeSet<>();
        for (int i = 0; i < metricLocalSets.size(); i++) {
            identifiers.add(new MetricLocalSetsKey(i));
        }
        return identifiers;
    }

    /**
     * Add a metric local set to this object from encoded bytes.
     *
     * @param bytes the byte array to parse
     * @throws KlvParseException if the parsing is not successful
     */
    public final void addMetricFromBytes(byte[] bytes) throws KlvParseException {
        MetricLocalSet metricLocalSet = MetricLocalSet.fromBytes(bytes);
        metricLocalSets.add(metricLocalSet);
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        metricLocalSets.forEach(
                metricLocalSet -> {
                    metricLocalSet.appendBytesToBuilder(arrayBuilder);
                });
    }
}

package org.jmisb.api.klv.st1108.st1108_3.metric;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.api.klv.st1108.st1108_3.IQMetadataKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetricLocalSet implements IInterpretabilityQualityMetadataValue, INestedKlvValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricLocalSet.class);

    private MetricLocalSet() {}

    /**
     * Construct a MetricLocalSet from a byte array.
     *
     * <p>This is intended for internal use (from the MetricLocalSets implementation) and is not
     * usually required to be instantiated directly from user code.
     *
     * @param bytes array of KLV encoded bytes to decode.
     * @return instantiated MetricLocalSet with the provided values.
     * @throws KlvParseException if the byte array could not be fully parsed.
     */
    public static MetricLocalSet fromBytes(byte[] bytes) throws KlvParseException {
        MetricLocalSet metricLocalSet = new MetricLocalSet();
        List<LdsField> fields = LdsParser.parseFields(bytes, 0, bytes.length);
        for (LdsField field : fields) {
            MetricLocalSetKey key = MetricLocalSetKey.getKey(field.getTag());
            switch (key) {
                case MetricName:
                    metricLocalSet.map.put(
                            MetricLocalSetKey.MetricName, new MetricName(field.getData()));
                    break;
                case MetricVersion:
                    metricLocalSet.map.put(
                            MetricLocalSetKey.MetricVersion, new MetricVersion(field.getData()));
                    break;
                case MetricImplementer:
                    metricLocalSet.map.put(
                            MetricLocalSetKey.MetricImplementer,
                            new MetricImplementer(field.getData()));
                    break;
                case MetricParameters:
                    metricLocalSet.map.put(
                            MetricLocalSetKey.MetricParameters,
                            new MetricParameters(field.getData()));
                    break;
                case MetricTime:
                    metricLocalSet.map.put(
                            MetricLocalSetKey.MetricTime, new MetricTime(field.getData()));
                    break;
                case MetricValue:
                    metricLocalSet.map.put(
                            MetricLocalSetKey.MetricValue, new MetricValue(field.getData()));
                    break;
                default:
                    LOGGER.warn(
                            "Unsupported/unknown ST 1108 Metric Local Set tag: {}", field.getTag());
            }
        }
        return metricLocalSet;
    }

    /**
     * Construct a MetricLocalSet from provided values.
     *
     * @param values the values to put into the local set.
     * @return instantiated MetricLocalSet with the provided values.
     */
    public static MetricLocalSet fromMap(Map<MetricLocalSetKey, IMetricLocalSetValue> values) {
        MetricLocalSet metricLocalSet = new MetricLocalSet();
        metricLocalSet.map.putAll(values);
        return metricLocalSet;
    }

    /** Map containing all data elements in the message. */
    private final SortedMap<MetricLocalSetKey, IMetricLocalSetValue> map = new TreeMap<>();

    @Override
    public IMetricLocalSetValue getField(IKlvKey tag) {
        MetricLocalSetKey identifier = (MetricLocalSetKey) tag;
        return map.get(identifier);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public String getDisplayName() {
        return "Metric";
    }

    @Override
    public String getDisplayableValue() {
        return "[Metric]";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(IQMetadataKey.MetricLocalSets.getIdentifier());
        byte[] valueBytes = getBytes();
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }

    byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (Entry<MetricLocalSetKey, IMetricLocalSetValue> entry : map.entrySet()) {
            arrayBuilder.appendAsOID(entry.getKey().getIdentifier());
            byte[] valueBytes = entry.getValue().getBytes();
            arrayBuilder.appendAsBerLength(valueBytes.length);
            arrayBuilder.append(valueBytes);
        }
        return arrayBuilder.toBytes();
    }
}

package org.jmisb.api.klv.st1108.st1108_3;

import static org.jmisb.api.klv.KlvConstants.InterpretabilityQualityLocalSetUl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.CrcCcitt;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.UniversalLabel;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.api.klv.st1108.st1108_3.metric.MetricLocalSets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interpretability and Quality Local Set.
 *
 * <p>The Interpretability and Quality Local Set is the parent local set for ST 1108. Note that the
 * Metric Local Set value within this Interpretability and Quality Local Set can repeat.
 */
public class IQLocalSet implements IMisbMessage {

    private static final int CRC16_LENGTH = 2;
    private static final Logger LOGGER = LoggerFactory.getLogger(IQLocalSet.class);

    /** Map containing all elements in the message. */
    private final SortedMap<IQMetadataKey, IInterpretabilityQualityMetadataValue> map =
            new TreeMap<>();

    private IQLocalSet() {};

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public IQLocalSet(Map<IQMetadataKey, IInterpretabilityQualityMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Build an Interpretability and Quality Metadata Local Set from extracted fields.
     *
     * @param fields the fields
     * @param bytes the bytes used
     * @return local set corresponding to the provided fields
     * @throws KlvParseException if parsing fails
     */
    public static IQLocalSet fromST1108_3Fields(List<LdsField> fields, final byte[] bytes)
            throws KlvParseException {
        IQLocalSet localSet = new IQLocalSet();
        for (LdsField field : fields) {
            localSet.processField(field, bytes);
        }
        return localSet;
    }

    private void processField(LdsField field, final byte[] bytes)
            throws KlvParseException, AssertionError {
        IQMetadataKey key = IQMetadataKey.getKey(field.getTag());
        switch (key) {
            case AssessmentPoint:
                map.put(key, AssessmentPoint.fromBytes(field.getData()));
                break;
            case MetricPeriodPack:
                map.put(key, new MetricPeriodPack(field.getData()));
                break;
            case WindowCornersPack:
                map.put(key, new WindowCornersPack(field.getData()));
                break;
            case MetricLocalSets:
                if (map.containsKey(IQMetadataKey.MetricLocalSets)) {
                    MetricLocalSets metricLocalSets =
                            (MetricLocalSets) map.get(IQMetadataKey.MetricLocalSets);
                    metricLocalSets.addMetricFromBytes(field.getData());
                } else {
                    map.put(key, new MetricLocalSets(field.getData()));
                }
                break;
            case CompressionType:
                map.put(key, CompressionType.fromBytes(field.getData()));
                break;
            case CompressionProfile:
                map.put(key, CompressionProfile.fromBytes(field.getData()));
                break;
            case CompressionLevel:
                map.put(key, new CompressionLevel(field.getData()));
                break;
            case CompressionRatio:
                map.put(key, new CompressionRatio(field.getData()));
                break;
            case StreamBitrate:
                map.put(key, new StreamBitrate(field.getData()));
                break;
            case DocumentVersion:
                map.put(key, new DocumentVersion(field.getData()));
                break;
            case CRC16CCITT:
                if (!CrcCcitt.verify(bytes, field.getData())) {
                    InvalidDataHandler handler = InvalidDataHandler.getInstance();
                    handler.handleInvalidChecksum(LOGGER, "Bad checksum");
                }
                break;
            default:
                LOGGER.info(
                        "Unknown Interpretability and Quality Metadata tag: {}", field.getTag());
        }
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        if (isNested) {
            throw new IllegalArgumentException(
                    "Interpretability and Quality Local Set cannot be nested");
        }
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (IQMetadataKey tag : map.keySet()) {
            if (tag.equals(IQMetadataKey.CRC16CCITT)) {
                // This will get added, at the end
                continue;
            }
            getField(tag).appendBytesToBuilder(arrayBuilder);
        }
        arrayBuilder.appendAsOID(IQMetadataKey.CRC16CCITT.getIdentifier());
        arrayBuilder.appendAsBerLength(CRC16_LENGTH);
        arrayBuilder.prependLengthPlus(2);
        arrayBuilder.prepend(getUniversalLabel());
        CrcCcitt crc = new CrcCcitt();
        crc.addData(arrayBuilder.toBytes());
        arrayBuilder.append(crc.getCrc());

        return arrayBuilder.toBytes();
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public IInterpretabilityQualityMetadataValue getField(IKlvKey key) {
        return map.get((IQMetadataKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return InterpretabilityQualityLocalSetUl;
    }

    @Override
    public String displayHeader() {
        return "ST 1108 Interpretability and Quality";
    }
}

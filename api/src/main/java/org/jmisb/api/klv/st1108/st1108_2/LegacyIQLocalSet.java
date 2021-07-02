package org.jmisb.api.klv.st1108.st1108_2;

import static org.jmisb.api.klv.KlvConstants.InterpretabilityQualityLocalSetUl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.UniversalLabel;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interpretability and Quality Local Set.
 *
 * <p>The Interpretability and Quality Local Set is the parent local set for ST 1108. Note that the
 * Metric Local Set value within this Interpretability and Quality Local Set can repeat.
 */
public class LegacyIQLocalSet implements IMisbMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(LegacyIQLocalSet.class);

    /** Map containing all elements in the message. */
    private final SortedMap<LegacyIQMetadataKey, IInterpretabilityQualityMetadataValue> map =
            new TreeMap<>();

    private LegacyIQLocalSet() {};

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public LegacyIQLocalSet(
            Map<LegacyIQMetadataKey, IInterpretabilityQualityMetadataValue> values) {
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
    public static LegacyIQLocalSet fromST1108_2Fields(List<LdsField> fields, final byte[] bytes)
            throws KlvParseException {
        LegacyIQLocalSet localSet = new LegacyIQLocalSet();
        for (LdsField field : fields) {
            localSet.processField(field, bytes);
        }
        return localSet;
    }

    private void processField(LdsField field, final byte[] bytes)
            throws KlvParseException, AssertionError {
        LegacyIQMetadataKey key = LegacyIQMetadataKey.getKey(field.getTag());
        switch (key) {
            case MostRecentFrameTime:
                map.put(
                        LegacyIQMetadataKey.MostRecentFrameTime,
                        new MostRecentFrameTime(field.getData()));
                break;
            case VideoInterpretability:
                map.put(
                        LegacyIQMetadataKey.VideoInterpretability,
                        new VideoInterpretability(field.getData()));
                break;
            case VideoQuality:
                map.put(LegacyIQMetadataKey.VideoQuality, new VideoQuality(field.getData()));
                break;
            case InterpretabilityQualityMethod:
                map.put(
                        LegacyIQMetadataKey.InterpretabilityQualityMethod,
                        new QualityMethod(field.getData()));
                break;
            case PSNRCoefficientIdentifier:
                map.put(
                        LegacyIQMetadataKey.PSNRCoefficientIdentifier,
                        new PSNRCoefficientIdentifier(field.getData()));
                break;
            case QualityCoefficientIdentifier:
                map.put(
                        LegacyIQMetadataKey.QualityCoefficientIdentifier,
                        new QualityCoefficientIdentifier(field.getData()));
                break;
            case RatingDuration:
                map.put(LegacyIQMetadataKey.RatingDuration, new RatingDuration(field.getData()));
                break;
            case MIQPakInsertionTime:
                map.put(
                        LegacyIQMetadataKey.MIQPakInsertionTime,
                        new MIQPakInsertionTime(field.getData()));
                break;
            case ChipLocationSizeBitDepth:
                map.put(
                        LegacyIQMetadataKey.ChipLocationSizeBitDepth,
                        new ChipLocationSizeBitDepth(field.getData()));
                break;
            case ChipYvaluesUncompressed:
                map.put(
                        LegacyIQMetadataKey.ChipYvaluesUncompressed,
                        new ChipValuesUncompressed(field.getData()));
                break;
            case ChipYvaluesPNG:
                map.put(LegacyIQMetadataKey.ChipYvaluesPNG, new ChipValuesPNG(field.getData()));
                break;
            case ChipEdgeIntensity:
                map.put(
                        LegacyIQMetadataKey.ChipEdgeIntensity,
                        new ChipEdgeIntensity(field.getData()));
                break;
            case ChipFrequencyRatio:
                map.put(
                        LegacyIQMetadataKey.ChipFrequencyRatio,
                        new ChipFrequencyRatio(field.getData()));
                break;
            case ChipPSNR:
                map.put(LegacyIQMetadataKey.ChipPSNR, new ChipPSNR(field.getData()));
                break;
            default:
                LOGGER.info(
                        "Unknown Legacy Interpretability and Quality Metadata tag: {}",
                        field.getTag());
        }
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        if (isNested) {
            throw new IllegalArgumentException(
                    "Interpretability and Quality Local Set cannot be nested");
        }
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (LegacyIQMetadataKey tag : map.keySet()) {
            getField(tag).appendBytesToBuilder(arrayBuilder);
        }
        arrayBuilder.prependLength();
        arrayBuilder.prepend(getUniversalLabel());

        return arrayBuilder.toBytes();
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public IInterpretabilityQualityMetadataValue getField(IKlvKey key) {
        return map.get((LegacyIQMetadataKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return InterpretabilityQualityLocalSetUl;
    }

    @Override
    public String displayHeader() {
        return "ST 1108 Legacy Interpretability and Quality";
    }
}

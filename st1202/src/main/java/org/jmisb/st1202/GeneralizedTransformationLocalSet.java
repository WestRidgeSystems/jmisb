package org.jmisb.st1202;

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
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.jmisb.st1010.SDCC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generalized Transformation Local Set.
 *
 * <p>This is the core ST 1202 Local Set.
 */
public class GeneralizedTransformationLocalSet implements IMisbMessage {

    /**
     * Universal label for Generalized Transformation Local Set.
     *
     * <p>See ST 1202.2 Table 2.
     */
    public static final UniversalLabel GeneralizedTransformationLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x05,
                        0x05, 0x00, 0x00, 0x00
                    });

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GeneralizedTransformationLocalSet.class);

    /**
     * Create a {@link IGeneralizedTransformationMetadataValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the parsing of the encoded bytes fails
     */
    static IGeneralizedTransformationMetadataValue createValue(
            GeneralizedTransformationParametersKey tag, byte[] bytes) throws KlvParseException {
        switch (tag) {
            case X_Numerator_x:
                return new X_Numerator_X(bytes);
            case X_Numerator_y:
                return new X_Numerator_Y(bytes);
            case X_Numerator_Constant:
                return new X_Numerator_Constant(bytes);
            case Y_Numerator_x:
                return new Y_Numerator_X(bytes);
            case Y_Numerator_y:
                return new Y_Numerator_Y(bytes);
            case Y_Numerator_Constant:
                return new Y_Numerator_Constant(bytes);
            case Denominator_x:
                return new Denominator_X(bytes);
            case Denominator_y:
                return new Denominator_Y(bytes);
            case SDCC:
                return new SDCC_FLP(bytes);
            case DocumentVersion:
                return new ST1202DocumentVersion(bytes);
            case TransformationEnumeration:
                return TransformationEnumeration.fromBytes(bytes);
            default:
                LOGGER.info("Unknown Generalized Transformation Metadata tag: {}", tag);
        }
        return null;
    }

    /** Map containing all elements in the message. */
    private final SortedMap<
                    GeneralizedTransformationParametersKey, IGeneralizedTransformationMetadataValue>
            map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public GeneralizedTransformationLocalSet(
            Map<GeneralizedTransformationParametersKey, IGeneralizedTransformationMetadataValue>
                    values) {
        map.putAll(values);
    }

    /**
     * Build a Generalized Transformation Local Set from encoded bytes.
     *
     * <p>This assumes the byte array is always nested (see requirement ST 1202.1-09).
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public GeneralizedTransformationLocalSet(final byte[] bytes) throws KlvParseException {
        List<LdsField> fields = LdsParser.parseFields(bytes, 0, bytes.length);
        for (LdsField field : fields) {
            GeneralizedTransformationParametersKey key =
                    GeneralizedTransformationParametersKey.getKey(field.getTag());
            switch (key) {
                case Undefined:
                    LOGGER.info("Unknown Generalized Transformation tag: {}", field.getTag());
                    break;
                default:
                    IGeneralizedTransformationMetadataValue value =
                            createValue(key, field.getData());
                    map.put(key, value);
            }
        }
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        if (!isNested) {
            throw new IllegalArgumentException(
                    "Generalized Tranformation Local Set must be nested");
        }
        ArrayBuilder builder = new ArrayBuilder();
        for (GeneralizedTransformationParametersKey tag : map.keySet()) {
            builder.appendAsOID(tag.getIdentifier());
            byte[] valueBytes = getField(tag).getBytes();
            builder.appendAsBerLength(valueBytes.length);
            builder.append(valueBytes);
        }
        return builder.toBytes();
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public IGeneralizedTransformationMetadataValue getField(IKlvKey key) {
        return map.get((GeneralizedTransformationParametersKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return GeneralizedTransformationLocalSetUl;
    }

    @Override
    public String displayHeader() {
        return "ST 1202 Generalized Transformation Local Set";
    }
}

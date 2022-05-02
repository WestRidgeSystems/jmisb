package org.jmisb.st1601;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Geo-Registration Local Set.
 *
 * <p>The Geo-Registration Local Set is a KLV Local Set whose items provide information specific to
 * a geo-registration algorithm, such as name and version, and additional geo-registration output,
 * such as correspondence points
 */
public class GeoRegistrationLocalSet implements IMisbMessage, IKlvValue, INestedKlvValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoRegistrationLocalSet.class);

    /** Universal label for Geo-Registration Local Set. */
    public static final UniversalLabel GeoRegistrationLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03,
                        0x01, 0x00, 0x00, 0x00
                    });

    /**
     * Create a {@link IGeoRegistrationValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the encoded bytes could not be parsed
     */
    static IGeoRegistrationValue createValue(GeoRegistrationKey tag, byte[] bytes)
            throws KlvParseException {
        switch (tag) {
            case DocumentVersion:
                return new ST1601DocumentVersion(bytes);
            case AlgorithmName:
                return new GeoRegistrationAlgorithmName(bytes);
            case AlgorithmVersion:
                return new GeoRegistrationAlgorithmVersion(bytes);
            case CorrespondencePointsRowColumn:
                return new CorrespondencePointsRowColumn(bytes);
            case CorrespondencePointsLatLon:
                return new CorrespondencePointsLatLon(bytes);
            case SecondImageName:
                return new SecondImageName(bytes);
            case AlgorithmConfigurationIdentifier:
                return new AlgorithmConfigurationIdentifier(bytes);
            case CorrespondencePointsElevation:
                return new CorrespondencePointsElevation(bytes);
            case CorrespondencePointsRowColumnSDCC:
                return new CorrespondencePointsRowColumnSDCC(bytes);
            case CorrespondencePointsLatLonElevSDCC:
                return new CorrespondencePointsLatLonElevSDCC(bytes);
            default:
                LOGGER.info("Unknown Geo-Registration tag: {}", tag);
        }
        return null;
    }

    /**
     * Build a Geo-Registration Local Set from encoded bytes.
     *
     * <p>The encoding is assumed to be nested (i.e. it does not have the Universal Label or length
     * parts). You can use the constructor taking a byte array for the non-nested case.
     *
     * @param bytes the bytes to build from
     * @param offset the index into the {@code bytes} array to start parsing from
     * @param len the number of bytes to parse (starting at {@code offset}.
     * @return local set corresponding to the provided bytes
     * @throws KlvParseException if parsing fails
     */
    public static GeoRegistrationLocalSet fromNestedBytes(final byte[] bytes, int offset, int len)
            throws KlvParseException {
        return new GeoRegistrationLocalSet(parseValues(bytes, offset, len));
    }

    private static SortedMap<GeoRegistrationKey, IGeoRegistrationValue> parseValues(
            final byte[] bytes, int offset, int len) throws KlvParseException {
        SortedMap<GeoRegistrationKey, IGeoRegistrationValue> values = new TreeMap<>();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, len);
        for (LdsField field : fields) {
            GeoRegistrationKey key = GeoRegistrationKey.getKey(field.getTag());
            IGeoRegistrationValue value = createValue(key, field.getData());
            if (value != null) {
                values.put(key, value);
            }
        }
        return values;
    }

    /** Map containing all elements in the message. */
    private final SortedMap<GeoRegistrationKey, IGeoRegistrationValue> map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public GeoRegistrationLocalSet(Map<GeoRegistrationKey, IGeoRegistrationValue> values) {
        map.putAll(values);
    }

    /**
     * Build a Geo-Registration Local Set from encoded bytes.
     *
     * <p>The encoding is assumed to be non-nested (i.e. it has the Universal Label and length
     * parts). You can use {@link fromNestedBytes} for the nested case.
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public GeoRegistrationLocalSet(final byte[] bytes) throws KlvParseException {
        int offset = UniversalLabel.LENGTH;
        BerField len = BerDecoder.decode(bytes, offset, false);
        offset += len.getLength();
        SortedMap<GeoRegistrationKey, IGeoRegistrationValue> values =
                parseValues(bytes, offset, len.getValue());
        map.putAll(values);
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        ArrayBuilder builder = new ArrayBuilder();
        for (GeoRegistrationKey tag : map.keySet()) {
            if (tag.equals(GeoRegistrationKey.Undefined)) {
                LOGGER.info("Skipping undefined Geo-Registration tag: {}", tag.getIdentifier());
                continue;
            }
            builder.appendAsOID(tag.getIdentifier());
            byte[] valueBytes = getField(tag).getBytes();
            builder.appendAsBerLength(valueBytes.length);
            builder.append(valueBytes);
        }
        if (!isNested) {
            builder.prependLength();
            builder.prepend(GeoRegistrationLocalSetUl);
        }
        return builder.toBytes();
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public IGeoRegistrationValue getField(IKlvKey key) {
        return map.get((GeoRegistrationKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return GeoRegistrationLocalSetUl;
    }

    @Override
    public String displayHeader() {
        return "ST 1601 Geo-Registration";
    }

    @Override
    public String getDisplayName() {
        return "Geo-Registration Local Set";
    }

    @Override
    public String getDisplayableValue() {
        return "Geo-Registration";
    }
}

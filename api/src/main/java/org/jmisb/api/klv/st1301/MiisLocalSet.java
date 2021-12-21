package org.jmisb.api.klv.st1301;

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
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Motion Imagery Identification System (MIIS) Local Set.
 *
 * <p>ST 1301 also refers to this as the Augmentation Local Set and Augmentation Identifiers Local
 * Set. However as of ST 1301.2, there are no augmentation identifiers approved.
 *
 * <p>The ST 1301 local set requires a core identifier (see ST 1204) and a document version. If a
 * document version is not provided, the current version will be inserted at serialisation time.
 */
public class MiisLocalSet implements IMisbMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiisLocalSet.class);

    /** Map containing all elements in the message. */
    private final SortedMap<MiisMetadataKey, IMiisMetadataValue> map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public MiisLocalSet(Map<MiisMetadataKey, IMiisMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Create the local set from encoded bytes.
     *
     * @param bytes the bytes to build from, starting from the Universal Label
     * @throws KlvParseException if parsing fails
     */
    public MiisLocalSet(byte[] bytes) throws KlvParseException {
        int offset = UniversalLabel.LENGTH;
        BerField len = BerDecoder.decode(bytes, offset, false);
        offset += len.getLength();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, len.getValue());
        for (LdsField field : fields) {
            MiisMetadataKey key = MiisMetadataKey.getKey(field.getTag());
            switch (key) {
                case Undefined:
                    LOGGER.info("Unknown MIIS Metadata tag: {}", field.getTag());
                    break;
                default:
                    IMiisMetadataValue value = createValue(key, field.getData());
                    map.put(key, value);
            }
        }
    }

    /**
     * Create a {@link IMiisMetadataValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the encoded bytes could not be decoded to an appropriate
     *     instance.
     */
    static IMiisMetadataValue createValue(MiisMetadataKey tag, byte[] bytes)
            throws KlvParseException {
        // There are a few unused and reserved fields. We treat those as unknown.
        switch (tag) {
            case Version:
                return new ST1301Version(bytes);
            case CoreIdentifier:
                return new ST1301CoreIdentifier(bytes);
            default:
                LOGGER.info("Unknown MIIS Metadata tag: {}", tag);
        }
        return null;
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return KlvConstants.MiisLocalSetUl;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        if (isNested) {
            throw new IllegalArgumentException("ST 1301 MIIS Local Set cannot be nested.");
        }
        // ST 1301.2-03
        if (!map.containsKey(MiisMetadataKey.CoreIdentifier)) {
            throw new IllegalArgumentException(
                    "Cannot frame MIIS Local Set without Core Identifier");
        }
        // ST 1301.2-02
        if (!map.containsKey(MiisMetadataKey.Version)) {
            map.put(
                    MiisMetadataKey.Version,
                    new ST1301Version(MiisMetadataConstants.ST_VERSION_NUMBER));
        }
        ArrayBuilder builder = new ArrayBuilder();
        for (MiisMetadataKey tag : map.keySet()) {
            builder.appendAsOID(tag.getIdentifier());
            byte[] valueBytes = getField(tag).getBytes();
            builder.appendAsBerLength(valueBytes.length);
            builder.append(valueBytes);
        }
        builder.prependLength();
        builder.prepend(KlvConstants.MiisLocalSetUl);
        return builder.toBytes();
    }

    @Override
    public String displayHeader() {
        return "ST 1301 MIIS Augmentation Identifiers";
    }

    @Override
    public IMiisMetadataValue getField(IKlvKey key) {
        return map.get((MiisMetadataKey) key);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }
}

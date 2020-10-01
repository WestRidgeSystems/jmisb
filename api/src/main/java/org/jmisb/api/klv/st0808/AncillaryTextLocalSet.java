package org.jmisb.api.klv.st0808;

import static org.jmisb.api.klv.KlvConstants.AncillaryTextLocalSetUl;

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
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AncillaryTextLocalSet implements IMisbMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(AncillaryTextLocalSet.class);

    /**
     * Create a {@link IAncillaryTextMetadataValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the byte array could not be parsed.
     */
    static IAncillaryTextMetadataValue createValue(AncillaryTextMetadataKey tag, byte[] bytes)
            throws KlvParseException {
        // This is fully implemented as of ST0808.2
        switch (tag) {
            case Originator:
                return new Originator(bytes);
            case PrecisionTimeStamp:
                return new PrecisionTimeStamp(bytes);
            case MessageBody:
                return new MessageBody(bytes);
            case Source:
                return new Source(bytes);
            case MessageCreationTime:
                return new MessageCreationTime(bytes);
            default:
                LOGGER.info("Unknown Ancillary Text Metadata tag: {}", tag);
        }
        return null;
    }

    /** Map containing all elements in the message. */
    private final SortedMap<AncillaryTextMetadataKey, IAncillaryTextMetadataValue> map =
            new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public AncillaryTextLocalSet(
            Map<AncillaryTextMetadataKey, IAncillaryTextMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Build an Ancillary Text Local Set from encoded bytes.
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public AncillaryTextLocalSet(final byte[] bytes) throws KlvParseException {
        int offset = UniversalLabel.LENGTH;
        BerField len = BerDecoder.decode(bytes, offset, false);
        offset += len.getLength();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, len.getValue());
        for (LdsField field : fields) {
            AncillaryTextMetadataKey key = AncillaryTextMetadataKey.getKey(field.getTag());
            switch (key) {
                case Undefined:
                    LOGGER.info("Unknown Ancillary Text Metadata tag: {}", field.getTag());
                    break;
                default:
                    IAncillaryTextMetadataValue value = createValue(key, field.getData());
                    map.put(key, value);
            }
        }
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        ArrayBuilder builder = new ArrayBuilder();
        for (AncillaryTextMetadataKey tag : map.keySet()) {
            builder.appendAsOID(tag.getIdentifier());
            byte[] valueBytes = getField(tag).getBytes();
            builder.appendAsBerLength(valueBytes.length);
            builder.append(valueBytes);
        }
        if (!isNested) {
            builder.prependLength();
            builder.prepend(AncillaryTextLocalSetUl);
        }
        return builder.toBytes();
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public IAncillaryTextMetadataValue getField(IKlvKey key) {
        return map.get((AncillaryTextMetadataKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return AncillaryTextLocalSetUl;
    }

    @Override
    public String displayHeader() {
        return "ST0808 Ancillary Text";
    }
}

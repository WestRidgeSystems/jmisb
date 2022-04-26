package org.jmisb.st1602;

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
 * <p>The Composite Imaging Local Set is a KLV Local Set construct whose items provide information
 * specific to an image, such as position, pixel density, transparency, and Z-order.
 */
public class CompositeImagingLocalSet implements IMisbMessage, IKlvValue, INestedKlvValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompositeImagingLocalSet.class);

    /** Universal label for Composite Imaging Local Set. */
    public static final UniversalLabel CompositeImagingLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03,
                        0x02, 0x00, 0x00, 0x00
                    });

    /**
     * Create a {@link ICompositeImagingValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the encoded bytes could not be parsed
     */
    static ICompositeImagingValue createValue(CompositeImagingKey tag, byte[] bytes)
            throws KlvParseException {
        switch (tag) {
            case PrecisionTimeStamp:
                return new PrecisionTimeStamp(bytes);
            case DocumentVersion:
                return new ST1602DocumentVersion(bytes);
            case SourceImageRows:
                return new SourceImageRows(bytes);
            case SourceImageColumns:
                return new SourceImageColumns(bytes);
            case SourceImageAOIRows:
                return new SourceImageAOIRows(bytes);
            case SourceImageAOIColumns:
                return new SourceImageAOIColumns(bytes);
            case SourceImageAOIPositionX:
                return new SourceImageAOIPositionX(bytes);
            case SourceImageAOIPositionY:
                return new SourceImageAOIPositionY(bytes);
            case SubImageRows:
                return new SubImageRows(bytes);
            case SubImageColumns:
                return new SubImageColumns(bytes);
            case SubImagePositionX:
                return new SubImagePositionX(bytes);
            case SubImagePositionY:
                return new SubImagePositionY(bytes);
            case ActiveSubImageRows:
                return new ActiveSubImageRows(bytes);
            case ActiveSubImageColumns:
                return new ActiveSubImageColumns(bytes);
            case ActiveSubImageOffsetX:
                return new ActiveSubImageOffsetX(bytes);
            case ActiveSubImageOffsetY:
                return new ActiveSubImageOffsetY(bytes);
            case Transparency:
                return new Transparency(bytes);
            case ZOrder:
                return new ZOrder(bytes);
            default:
                LOGGER.info("Unknown Composite Imaging tag: {}", tag);
        }
        return null;
    }

    /**
     * Build a Composite Imaging Local Set from encoded bytes.
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
    public static CompositeImagingLocalSet fromNestedBytes(final byte[] bytes, int offset, int len)
            throws KlvParseException {
        return new CompositeImagingLocalSet(parseValues(bytes, offset, len));
    }

    private static SortedMap<CompositeImagingKey, ICompositeImagingValue> parseValues(
            final byte[] bytes, int offset, int len) throws KlvParseException {
        SortedMap<CompositeImagingKey, ICompositeImagingValue> values = new TreeMap<>();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, len);
        for (LdsField field : fields) {
            CompositeImagingKey key = CompositeImagingKey.getKey(field.getTag());
            ICompositeImagingValue value = createValue(key, field.getData());
            if (value != null) {
                values.put(key, value);
            }
        }
        return values;
    }

    /** Map containing all elements in the message. */
    private final SortedMap<CompositeImagingKey, ICompositeImagingValue> map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public CompositeImagingLocalSet(Map<CompositeImagingKey, ICompositeImagingValue> values) {
        map.putAll(values);
    }

    /**
     * Build a Composite Imaging Local Set from encoded bytes.
     *
     * <p>The encoding is assumed to be non-nested (i.e. it has the Universal Label and length
     * parts). You can use {@link fromNestedBytes} for the nested case.
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public CompositeImagingLocalSet(final byte[] bytes) throws KlvParseException {
        int offset = UniversalLabel.LENGTH;
        BerField len = BerDecoder.decode(bytes, offset, false);
        offset += len.getLength();
        SortedMap<CompositeImagingKey, ICompositeImagingValue> values =
                parseValues(bytes, offset, len.getValue());
        map.putAll(values);
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        ArrayBuilder builder = new ArrayBuilder();
        for (CompositeImagingKey tag : map.keySet()) {
            if (tag.equals(CompositeImagingKey.Undefined)) {
                LOGGER.info("Skipping undefined Composite Imaging tag: {}", tag.getIdentifier());
                continue;
            }
            builder.appendAsOID(tag.getIdentifier());
            byte[] valueBytes = getField(tag).getBytes();
            builder.appendAsBerLength(valueBytes.length);
            builder.append(valueBytes);
        }
        if (!isNested) {
            builder.prependLength();
            builder.prepend(CompositeImagingLocalSetUl);
        }
        return builder.toBytes();
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public ICompositeImagingValue getField(IKlvKey key) {
        return map.get((CompositeImagingKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return CompositeImagingLocalSetUl;
    }

    @Override
    public String displayHeader() {
        return "ST 1602 Composite Imaging";
    }

    @Override
    public String getDisplayName() {
        return "Composite Imaging Local Set";
    }

    @Override
    public String getDisplayableValue() {
        return "Composite Imaging";
    }
}

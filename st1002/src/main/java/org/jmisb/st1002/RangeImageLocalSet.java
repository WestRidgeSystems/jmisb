package org.jmisb.st1002;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.CrcCcitt;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Range Image Local Set.
 *
 * <p>This is the core ST 1002 Local Set.
 */
public class RangeImageLocalSet implements IMisbMessage {

    private static final int CRC16_LENGTH = 2;

    /**
     * Universal label for Range Image Local Set.
     *
     * <p>See ST 1002.2 Table 2.
     */
    public static final UniversalLabel RangeImageLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03,
                        0x0C, 0x00, 0x00, 0x00
                    });

    private static final Logger LOGGER = LoggerFactory.getLogger(RangeImageLocalSet.class);

    /**
     * Create a {@link IRangeImageMetadataValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the parsing of the encoded bytes fails
     */
    static IRangeImageMetadataValue createValue(RangeImageMetadataKey tag, byte[] bytes)
            throws KlvParseException {
        switch (tag) {
            case PrecisionTimeStamp:
                return new ST1002PrecisionTimeStamp(bytes);
            case DocumentVersion:
                return new ST1002VersionNumber(bytes);
            case RangeImageEnumerations:
                return new RangeImageEnumerations(bytes);
            case SinglePointRangeMeasurement:
                return new SinglePointRangeMeasurement(bytes);
            case SinglePointRangeMeasurementUncertainty:
                return new SinglePointRangeMeasurementUncertainty(bytes);
            case SinglePointRangeMeasurementRowCoordinate:
                return new SinglePointRangeMeasurementRow(bytes);
            case SinglePointRangeMeasurementColumnCoordinate:
                return new SinglePointRangeMeasurementColumn(bytes);
            case NumberOfSectionsInX:
                return new NumberOfSectionsInX(bytes);
            case NumberOfSectionsInY:
                return new NumberOfSectionsInY(bytes);
            case GeneralizedTransformationLocalSet:
                return new GeneralizedTransformation(bytes);
            default:
                LOGGER.info("Unknown Range Image Metadata tag: {}", tag);
        }
        return null;
    }

    /** Map containing all elements in the message. */
    private final SortedMap<RangeImageMetadataKey, IRangeImageMetadataValue> map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public RangeImageLocalSet(Map<RangeImageMetadataKey, IRangeImageMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Build a Range Image Local Set from encoded bytes.
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
    public static RangeImageLocalSet fromNestedBytes(final byte[] bytes, int offset, int len)
            throws KlvParseException {
        return new RangeImageLocalSet(parseValues(bytes, offset, len));
    }

    /**
     * Build a Range Image Local Set from encoded bytes.
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public RangeImageLocalSet(final byte[] bytes) throws KlvParseException {
        int offset = UniversalLabel.LENGTH;
        BerField len = BerDecoder.decode(bytes, offset, false);
        offset += len.getLength();
        SortedMap<RangeImageMetadataKey, IRangeImageMetadataValue> values =
                parseValues(bytes, offset, len.getValue());
        map.putAll(values);
    }

    private static SortedMap<RangeImageMetadataKey, IRangeImageMetadataValue> parseValues(
            final byte[] bytes, int offset, int len) throws KlvParseException {
        SortedMap<RangeImageMetadataKey, IRangeImageMetadataValue> map = new TreeMap<>();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, len);
        for (LdsField field : fields) {
            RangeImageMetadataKey key = RangeImageMetadataKey.getKey(field.getTag());
            switch (key) {
                case Undefined:
                    LOGGER.info("Unknown Range Image Metadata tag: {}", field.getTag());
                    break;
                case CRC16CCITT:
                    if (!CrcCcitt.verify(bytes, field.getData())) {
                        InvalidDataHandler handler = InvalidDataHandler.getInstance();
                        handler.handleInvalidChecksum(LOGGER, "Bad checksum");
                    }
                    break;
                case SectionDataVLP:
                    SectionData sectionData = new SectionData(field.getData());
                    if (!map.containsKey(RangeImageMetadataKey.SectionDataVLP)) {
                        map.put(RangeImageMetadataKey.SectionDataVLP, new SectionDataList());
                    }
                    SectionDataList sectionDataList =
                            (SectionDataList) map.get(RangeImageMetadataKey.SectionDataVLP);
                    sectionDataList.add(sectionData);
                    break;
                default:
                    IRangeImageMetadataValue value = createValue(key, field.getData());
                    map.put(key, value);
            }
        }
        return map;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        ArrayBuilder builder = new ArrayBuilder();
        for (RangeImageMetadataKey tag : map.keySet()) {
            if (tag.equals(RangeImageMetadataKey.Undefined)) {
                // We can't serialise this
                continue;
            }
            if (tag.equals(RangeImageMetadataKey.CRC16CCITT)) {
                // handled at the end.
                continue;
            }
            if (tag.equals(RangeImageMetadataKey.SectionDataVLP)) {
                SectionDataList sectionDataList = (SectionDataList) getField(tag);
                List<SectionData> sectionDataPacks = sectionDataList.getPacks();
                for (SectionData sectionData : sectionDataPacks) {
                    try {
                        byte[] valueBytes = sectionData.getBytes();
                        builder.appendAsOID(tag.getIdentifier());
                        builder.appendAsBerLength(valueBytes.length);
                        builder.append(valueBytes);
                    } catch (KlvParseException ex) {
                        LOGGER.warn("Failed to serialize ST 1002 Section Data:" + ex.getMessage());
                    }
                }
            } else {
                builder.appendAsOID(tag.getIdentifier());
                byte[] valueBytes = getField(tag).getBytes();
                builder.appendAsBerLength(valueBytes.length);
                builder.append(valueBytes);
            }
        }
        builder.appendAsOID(RangeImageMetadataKey.CRC16CCITT.getIdentifier());
        builder.appendAsBerLength(CRC16_LENGTH);
        if (!isNested) {
            builder.prependLengthPlus(2);
            builder.prepend(RangeImageLocalSetUl);
        }
        CrcCcitt crc = new CrcCcitt();
        crc.addData(builder.toBytes());
        builder.append(crc.getCrc());
        return builder.toBytes();
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public IRangeImageMetadataValue getField(IKlvKey key) {
        return map.get((RangeImageMetadataKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return RangeImageLocalSetUl;
    }

    @Override
    public String displayHeader() {
        return "ST 1002 Range Image";
    }
}

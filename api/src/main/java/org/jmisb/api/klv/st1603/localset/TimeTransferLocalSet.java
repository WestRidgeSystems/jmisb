package org.jmisb.api.klv.st1603.localset;

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
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Time Transfer Local Set.
 *
 * <p>This local set allows a producer to describe its time source information, and some quality
 * metrics associated with that time source.
 */
public class TimeTransferLocalSet implements IMisbMessage, IKlvValue, INestedKlvValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTransferLocalSet.class);

    /**
     * Create a {@link ITimeTransferValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the encoded bytes could not be parsed
     */
    static ITimeTransferValue createValue(TimeTransferKey tag, byte[] bytes)
            throws KlvParseException {
        switch (tag) {
            case DocumentVersion:
                return new ST1603DocumentVersion(bytes);
            case UTCLeapSecondOffset:
                return new UTCLeapSecondOffset(bytes);
            case TimeTransferParameters:
                return new TimeTransferParameters(bytes);
            case SynchronizationPulseFrequency:
                return new SynchronizationPulseFrequency(bytes);
            case UnlockTime:
                return new UnlockTime(bytes);
            case LastSynchronizationDifference:
                return new LastSynchronizationDifference(bytes);
            case DriftRate:
                return new DriftRate(bytes);
            case SignalSourceDelay:
                return new SignalSourceDelay(bytes);
            case ReceptorClockUncertainty:
                return new ReceptorClockUncertainty(bytes);
            default:
                LOGGER.info("Unknown Time Transfer tag: {}", tag);
        }
        return null;
    }

    /**
     * Build a Time Transfer Local Set from encoded bytes.
     *
     * <p>The encoding is assumed to be nested (i.e. it does not have the Universal Label or length
     * parts).
     *
     * @param bytes the bytes to build from
     * @param offset the index into the {@code bytes} array to start parsing from
     * @param len the number of bytes to parse (starting at {@code offset}.
     * @return local set corresponding to the provided bytes
     * @throws KlvParseException if parsing fails
     */
    public static TimeTransferLocalSet fromNestedBytes(final byte[] bytes, int offset, int len)
            throws KlvParseException {
        return new TimeTransferLocalSet(parseValues(bytes, offset, len));
    }

    private static SortedMap<TimeTransferKey, ITimeTransferValue> parseValues(
            final byte[] bytes, int offset, int len) throws KlvParseException {
        SortedMap<TimeTransferKey, ITimeTransferValue> values = new TreeMap<>();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, len);
        for (LdsField field : fields) {
            TimeTransferKey key = TimeTransferKey.getKey(field.getTag());
            ITimeTransferValue value = createValue(key, field.getData());
            if (value != null) {
                values.put(key, value);
            }
        }
        return values;
    }

    /** Map containing all elements in the message. */
    private final SortedMap<TimeTransferKey, ITimeTransferValue> map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public TimeTransferLocalSet(Map<TimeTransferKey, ITimeTransferValue> values) {
        map.putAll(values);
    }

    /**
     * Build a Time Transfer Local Set from encoded bytes.
     *
     * <p>The encoding is assumed to be non-nested (i.e. it has the Universal Label and length
     * parts).
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     * @see {@link fromNestedBytes} for a way to handle a nested local set.
     */
    public TimeTransferLocalSet(final byte[] bytes) throws KlvParseException {
        int offset = UniversalLabel.LENGTH;
        BerField len = BerDecoder.decode(bytes, offset, false);
        offset += len.getLength();
        SortedMap<TimeTransferKey, ITimeTransferValue> values =
                parseValues(bytes, offset, len.getValue());
        map.putAll(values);
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        ArrayBuilder builder = new ArrayBuilder();
        for (TimeTransferKey tag : map.keySet()) {
            if (tag.equals(TimeTransferKey.Undefined)) {
                LOGGER.info("Skipping undefined Time Transfer tag: {}", tag.getIdentifier());
                continue;
            }
            builder.appendAsOID(tag.getIdentifier());
            byte[] valueBytes = getField(tag).getBytes();
            builder.appendAsBerLength(valueBytes.length);
            builder.append(valueBytes);
        }
        if (!isNested) {
            builder.prependLength();
            builder.prepend(KlvConstants.TimeTransferLocalSetUl);
        }
        return builder.toBytes();
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public ITimeTransferValue getField(IKlvKey key) {
        return map.get((TimeTransferKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return KlvConstants.TimeTransferLocalSetUl;
    }

    @Override
    public String displayHeader() {
        return "ST 1603 Time Transfer";
    }

    @Override
    public String getDisplayName() {
        return "Time Transfer Local Set";
    }

    @Override
    public String getDisplayableValue() {
        return "Time Transfer";
    }
}

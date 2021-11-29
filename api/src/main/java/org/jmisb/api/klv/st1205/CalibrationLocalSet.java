package org.jmisb.api.klv.st1205;

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

public class CalibrationLocalSet implements IMisbMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalibrationLocalSet.class);

    /**
     * Create a {@link ICalibrationMetadataValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the byte array could not be parsed.
     */
    static ICalibrationMetadataValue createValue(CalibrationPackMetadataKey tag, byte[] bytes)
            throws KlvParseException {
        switch (tag) {
            case TimeStampOfLastFrameInSequence:
                return new TimeStampOfLastFrameInSequence(bytes);
            case SequenceDuration:
                return new SequenceDuration(bytes);
            case TimeStampOfCalibrationPackCreation:
                return new TimeStampOfCalibrationPackCreation(bytes);
            case CalibrationSequenceIdentifier:
                return new CalibrationSequenceIdentifier(bytes);
            default:
                LOGGER.info("Unknown Calibration Pack Metadata tag: {}", tag);
        }
        return null;
    }

    /** Map containing all elements in the message. */
    private final SortedMap<CalibrationPackMetadataKey, ICalibrationMetadataValue> map =
            new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public CalibrationLocalSet(Map<CalibrationPackMetadataKey, ICalibrationMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Build a Calibration Pack Local Set from encoded bytes.
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public CalibrationLocalSet(final byte[] bytes) throws KlvParseException {
        // advance over UL and length.
        int offset = UniversalLabel.LENGTH;
        BerField len = BerDecoder.decode(bytes, offset, false);
        offset += len.getLength();
        int dataLength = len.getValue();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, dataLength);
        for (LdsField field : fields) {
            CalibrationPackMetadataKey key = CalibrationPackMetadataKey.getKey(field.getTag());
            switch (key) {
                case Undefined:
                    LOGGER.info("Unknown Calibration Pack Metadata tag: {}", field.getTag());
                    break;
                default:
                    ICalibrationMetadataValue value = createValue(key, field.getData());
                    map.put(key, value);
            }
        }
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        if (isNested) {
            throw new IllegalArgumentException("ST 1205 cannot be nested");
        }
        ArrayBuilder builder = new ArrayBuilder();
        for (CalibrationPackMetadataKey tag : map.keySet()) {
            builder.appendAsOID(tag.getIdentifier());
            byte[] valueBytes = getField(tag).getBytes();
            builder.appendAsBerLength(valueBytes.length);
            builder.append(valueBytes);
        }
        builder.prependLength();
        builder.prepend(KlvConstants.CalibrationPackUl);
        return builder.toBytes();
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public ICalibrationMetadataValue getField(IKlvKey key) {
        return map.get((CalibrationPackMetadataKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return KlvConstants.CalibrationPackUl;
    }

    @Override
    public String displayHeader() {
        return "ST 1205 Calibration Pack";
    }
}

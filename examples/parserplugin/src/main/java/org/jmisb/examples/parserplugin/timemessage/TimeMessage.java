package org.jmisb.examples.parserplugin.timemessage;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.UniversalLabel;

/**
 * Example Time Message.
 *
 * <p>This example handles a simple message, comprising a Universal Label, a length, and a single
 * field that is an 8 byte timestamp.
 */
public class TimeMessage implements IMisbMessage {

    /** Map containing all elements in the message. */
    private final Map<TimeMessageKey, ITimeMessageValue> map = new TreeMap<>();

    /**
     * Create the time message from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the time message.
     */
    public TimeMessage(Map<TimeMessageKey, ITimeMessageValue> values) {
        map.putAll(values);
    }

    /**
     * Create the time message from encoded bytes.
     *
     * @param bytes the encoded byte array, including the universal label.
     * @throws KlvParseException if parsing fails
     */
    public TimeMessage(byte[] bytes) throws KlvParseException {
        if (bytes.length != UniversalLabel.LENGTH + 1 + 8) {
            throw new KlvParseException("Incorrect number of bytes for an example Time Message");
        }
        byte[] timestampBytes = Arrays.copyOfRange(bytes, UniversalLabel.LENGTH + 1, bytes.length);
        PrecisionTimeStamp timeStamp = new PrecisionTimeStamp(timestampBytes);
        map.put(TimeMessageKey.PrecisionTimeStamp, timeStamp);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return TimeMessageConstants.TIME_STAMP_UL;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        ArrayBuilder builder = new ArrayBuilder();
        for (TimeMessageKey tag : map.keySet()) {
            byte[] valueBytes = getField(tag).getBytes();
            builder.append(valueBytes);
        }
        if (!isNested) {
            builder.prependLength();
            builder.prepend(TimeMessageConstants.TIME_STAMP_UL);
        }
        return builder.toBytes();
    }

    @Override
    public String displayHeader() {
        return "Time Message Example";
    }

    @Override
    public ITimeMessageValue getField(IKlvKey tag) {
        return map.get((TimeMessageKey) tag);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }
}

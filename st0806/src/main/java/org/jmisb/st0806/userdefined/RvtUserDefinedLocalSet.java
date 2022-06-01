package org.jmisb.st0806.userdefined;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.st0806.IRvtMetadataValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ST0806 Remove Video Terminal User Defined Local Set.
 *
 * <p>Any number of User Defined Local Sets (including none) can be embedded in a parent RvtLocalSet
 * instance.
 */
public class RvtUserDefinedLocalSet implements IRvtMetadataValue, INestedKlvValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(RvtUserDefinedLocalSet.class);

    /** Map containing all data elements in the message. */
    private final SortedMap<RvtUserDefinedMetadataKey, IRvtUserDefinedMetadataValue> map =
            new TreeMap<>();

    /**
     * Create the message from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set/
     */
    public RvtUserDefinedLocalSet(
            Map<RvtUserDefinedMetadataKey, IRvtUserDefinedMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Parse {@link LdsField}s from a byte array.
     *
     * @param bytes Byte array to parse
     * @param start Index of the first byte to parse
     * @param length Number of bytes to parse
     * @throws KlvParseException If a parsing error occurs
     */
    public RvtUserDefinedLocalSet(byte[] bytes, int start, int length) throws KlvParseException {
        List<LdsField> fields = LdsParser.parseFields(bytes, start, length);
        for (LdsField field : fields) {
            RvtUserDefinedMetadataKey key = RvtUserDefinedMetadataKey.getKey(field.getTag());
            if (key == RvtUserDefinedMetadataKey.Undefined) {
                LOGGER.info("Unknown RVT User Defined Metadata tag: {}", field.getTag());
            } else {
                IRvtUserDefinedMetadataValue value = createValue(key, field.getData());
                map.put(key, value);
            }
        }
    }

    /**
     * Create a {@link IRvtUserDefinedMetadataValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the bytes could not be parsed.
     */
    public static IRvtUserDefinedMetadataValue createValue(
            RvtUserDefinedMetadataKey tag, byte[] bytes) throws KlvParseException {
        switch (tag) {
            case NumericId:
                return new RvtNumericId(bytes);
            case UserData:
                return new RvtUserData(bytes);
            default:
                LOGGER.info("Unrecognized RVT User Defined Data tag: {}", tag);
        }
        return null;
    }

    /**
     * Get the set of tags with populated values.
     *
     * @return The set of tags for which values have been set
     */
    public Set<RvtUserDefinedMetadataKey> getTags() {
        return map.keySet();
    }

    /**
     * Get the value of a given tag.
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IRvtUserDefinedMetadataValue getField(RvtUserDefinedMetadataKey tag) {
        return map.get(tag);
    }

    /**
     * Get the byte array corresponding to the value for this Local Set.
     *
     * @return byte array with the encoded local set.
     */
    @Override
    public byte[] getBytes() {
        ArrayBuilder builder = new ArrayBuilder();
        for (RvtUserDefinedMetadataKey tag : getTags()) {
            builder.appendByte((byte) tag.getIdentifier());
            IRvtUserDefinedMetadataValue value = getField(tag);
            byte[] bytes = value.getBytes();
            builder.appendAsBerLength(bytes.length);
            builder.append(bytes);
        }
        return builder.toBytes();
    }

    @Override
    public String getDisplayableValue() {
        if (map.containsKey(RvtUserDefinedMetadataKey.NumericId)) {
            return getField(RvtUserDefinedMetadataKey.NumericId).getDisplayableValue();
        }
        return "[User Defined Local Set]";
    }

    @Override
    public String getDisplayName() {
        return "User Data";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        return this.getField((RvtUserDefinedMetadataKey) tag);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return this.getTags();
    }
}

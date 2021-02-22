package org.jmisb.api.klv.st0903.vmask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.core.klv.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** VMask Local Set. */
public class VMaskLS {
    private static final Logger LOGGER = LoggerFactory.getLogger(VMaskLS.class);

    /** Map containing all data elements in the message. */
    private final SortedMap<VMaskMetadataKey, IVmtiMetadataValue> map = new TreeMap<>();

    /**
     * Create the message from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public VMaskLS(Map<VMaskMetadataKey, IVmtiMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the VMask local set
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public VMaskLS(byte[] bytes) throws KlvParseException {
        int offset = 0;
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, bytes.length - offset);
        for (LdsField field : fields) {
            VMaskMetadataKey key = VMaskMetadataKey.getKey(field.getTag());
            if (key == VMaskMetadataKey.Undefined) {
                LOGGER.info("Unknown VMTI VMask Metadata tag: {}", field.getTag());
            } else {
                try {
                    IVmtiMetadataValue value = createValue(key, field.getData());
                    map.put(key, value);
                } catch (KlvParseException | IllegalArgumentException ex) {
                    InvalidDataHandler.getInstance()
                            .handleInvalidFieldEncoding(LOGGER, ex.getMessage());
                }
            }
        }
    }

    /**
     * Create a {@link IVmtiMetadataValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the bytes could not be parsed.
     */
    public static IVmtiMetadataValue createValue(VMaskMetadataKey tag, byte[] bytes)
            throws KlvParseException {
        switch (tag) {
            case polygon:
                return new PixelPolygon(bytes);
            case bitMaskSeries:
                return new BitMaskSeries(bytes);
            default:
                LOGGER.info("Unrecognized VMask tag: {}", tag);
        }
        return null;
    }

    /**
     * Get the set of tags with populated values.
     *
     * @return The set of tags for which values have been set
     */
    public Set<VMaskMetadataKey> getTags() {
        return map.keySet();
    }

    /**
     * Get the value of a given tag.
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IVmtiMetadataValue getField(VMaskMetadataKey tag) {
        return map.get(tag);
    }

    /**
     * Get the byte array corresponding to the value for this Local Set.
     *
     * @return byte array with the encoded local set.
     */
    public byte[] getBytes() {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (VMaskMetadataKey tag : getTags()) {
            chunks.add(new byte[] {(byte) tag.getTag()});
            len += 1;
            IVmtiMetadataValue value = getField(tag);
            byte[] bytes = value.getBytes();
            byte[] lengthBytes = BerEncoder.encode(bytes.length);
            chunks.add(lengthBytes);
            len += lengthBytes.length;
            chunks.add(bytes);
            len += bytes.length;
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }
}

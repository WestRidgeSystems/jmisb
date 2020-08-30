package org.jmisb.api.klv.st0903.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.AlgorithmId;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.core.klv.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Algorithm Local Set.
 *
 * <p>The Algorithm LS documents attributes of the algorithm used for detection and tracking of
 * targets.
 */
public class AlgorithmLS implements IKlvValue, INestedKlvValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmLS.class);

    /** Map containing all data elements in the message. */
    private final SortedMap<AlgorithmMetadataKey, IVmtiMetadataValue> map = new TreeMap<>();

    /**
     * Create the message from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set/
     */
    public AlgorithmLS(Map<AlgorithmMetadataKey, IVmtiMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Create the local set from encoded bytes.
     *
     * <p>This is effectively the parser logic for the Algorithm Local Set to extract the message
     * from part of a byte array.
     *
     * @param bytes Encoded bytes
     * @param offset the offset into the {@code bytes} array to start parsing
     * @param length the number of bytes to parse
     * @throws KlvParseException if the bytes could not be parsed.
     */
    public AlgorithmLS(byte[] bytes, int offset, int length) throws KlvParseException {
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, length);
        for (LdsField field : fields) {
            AlgorithmMetadataKey key = AlgorithmMetadataKey.getKey(field.getTag());
            if (key == AlgorithmMetadataKey.Undefined) {
                LOGGER.info("Unknown VMTI Algorithm Metadata tag: {}", field.getTag());
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
    public static IVmtiMetadataValue createValue(AlgorithmMetadataKey tag, byte[] bytes)
            throws KlvParseException {
        switch (tag) {
            case id:
                return new AlgorithmId(bytes);
            case name:
                return new VmtiTextString(VmtiTextString.ALGORITHM_NAME, bytes);
            case version:
                return new VmtiTextString(VmtiTextString.ALGORITHM_VERSION, bytes);
            case algorithmClass:
                return new VmtiTextString(VmtiTextString.ALGORITHM_CLASS, bytes);
            case nFrames:
                return new NumberOfFrames(bytes);
            default:
                LOGGER.info("Unrecognized Algorithm tag: {}", tag);
        }
        return null;
    }

    /**
     * Get the set of tags with populated values.
     *
     * @return The set of tags for which values have been set
     */
    public Set<AlgorithmMetadataKey> getTags() {
        return map.keySet();
    }

    /**
     * Get the value of a given tag.
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IVmtiMetadataValue getField(AlgorithmMetadataKey tag) {
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
        for (AlgorithmMetadataKey tag : getTags()) {
            chunks.add(new byte[] {(byte) tag.getTag()});
            len += 1;
            IVmtiMetadataValue value = getField(tag);
            byte[] bytes = value.getBytes();
            byte[] lengthBytes = BerEncoder.encode(bytes.length);
            chunks.add(lengthBytes);
            len += lengthBytes.length;
            ;
            chunks.add(bytes);
            len += bytes.length;
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        return getField((AlgorithmMetadataKey) tag);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return getTags();
    }

    @Override
    public String getDisplayName() {
        return "Algorithm Local Set";
    }

    @Override
    public String getDisplayableValue() {
        if (map.containsKey(AlgorithmMetadataKey.id)) {
            AlgorithmId id = (AlgorithmId) getField(AlgorithmMetadataKey.id);
            return "Algorithm " + id.getDisplayableValue();
        } else {
            return "[Algorithm]";
        }
    }
}

package org.jmisb.st0903.vfeature;

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
import org.jmisb.core.klv.ArrayUtils;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.VmtiTextString;
import org.jmisb.st0903.shared.VmtiUri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** VFeature Local Set. */
public class VFeatureLS {
    private static final Logger LOGGER = LoggerFactory.getLogger(VFeatureLS.class);

    /** Map containing all data elements in the message. */
    private final SortedMap<VFeatureMetadataKey, IVmtiMetadataValue> map = new TreeMap<>();

    /**
     * Create the message from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public VFeatureLS(Map<VFeatureMetadataKey, IVmtiMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Create the local set from encoded bytes.
     *
     * <p>This is effectively the parser logic for the VFeature Local Set to extract the message
     * from part of a byte array.
     *
     * @param bytes Encoded bytes
     * @throws KlvParseException if the bytes could not be parsed.
     */
    public VFeatureLS(byte[] bytes) throws KlvParseException {
        List<LdsField> fields = LdsParser.parseFields(bytes, 0, bytes.length);
        for (LdsField field : fields) {
            VFeatureMetadataKey key = VFeatureMetadataKey.getKey(field.getTag());
            if (key == VFeatureMetadataKey.Undefined) {
                LOGGER.info("Unknown VMTI VFeature Metadata tag: {}", field.getTag());
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
    public static IVmtiMetadataValue createValue(VFeatureMetadataKey tag, byte[] bytes)
            throws KlvParseException {
        switch (tag) {
            case schema:
                return new VmtiUri(VmtiUri.VFEATURE_SCHEMA, bytes);
            case schemaFeature:
                return new VmtiTextString(VmtiTextString.VFEATURE_SCHEMA_FEATURE, bytes);
            default:
                LOGGER.info("Unrecognized VFeature tag: {}", tag);
        }
        return null;
    }

    /**
     * Get the set of tags with populated values.
     *
     * @return The set of tags for which values have been set
     */
    public Set<VFeatureMetadataKey> getTags() {
        return map.keySet();
    }

    /**
     * Get the value of a given tag.
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IVmtiMetadataValue getField(VFeatureMetadataKey tag) {
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
        for (VFeatureMetadataKey tag : getTags()) {
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

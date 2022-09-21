package org.jmisb.st0903.vchip;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.VmtiTextString;
import org.jmisb.st0903.shared.VmtiUri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** VChip Local Set. */
public class VChipLS {
    private static final Logger LOGGER = LoggerFactory.getLogger(VChipLS.class);

    /** Map containing all data elements in the message. */
    private final SortedMap<VChipMetadataKey, IVmtiMetadataValue> map = new TreeMap<>();

    /**
     * Create the message from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public VChipLS(Map<VChipMetadataKey, IVmtiMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Create the local set from encoded bytes.
     *
     * <p>This is effectively the parser logic for the VChip Local Set to extract the message from
     * part of a byte array.
     *
     * @param bytes Encoded bytes
     * @param offset the offset into the {@code bytes} array to start parsing
     * @param length the number of bytes to parse
     * @throws KlvParseException if the bytes could not be parsed.
     */
    public VChipLS(byte[] bytes, int offset, int length) throws KlvParseException {
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, length);
        for (LdsField field : fields) {
            VChipMetadataKey key = VChipMetadataKey.getKey(field.getTag());
            if (key == VChipMetadataKey.Undefined) {
                LOGGER.info("Unknown VMTI VChip Metadata tag: {}", field.getTag());
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
    public static IVmtiMetadataValue createValue(VChipMetadataKey tag, byte[] bytes)
            throws KlvParseException {
        switch (tag) {
            case imageType:
                return new VmtiTextString(VmtiTextString.IMAGE_TYPE, bytes);
            case imageUri:
                return new VmtiUri(VmtiUri.IMAGE_URI, bytes);
            case embeddedImage:
                return new EmbeddedImage(bytes);
            default:
                LOGGER.info("Unrecognized VChip tag: {}", tag);
        }
        return null;
    }

    /**
     * Get the set of tags with populated values.
     *
     * @return The set of tags for which values have been set
     */
    public Set<VChipMetadataKey> getTags() {
        return map.keySet();
    }

    /**
     * Get the value of a given tag.
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IVmtiMetadataValue getField(VChipMetadataKey tag) {
        return map.get(tag);
    }

    /**
     * Get the byte array corresponding to the value for this Local Set.
     *
     * @return byte array with the encoded local set.
     */
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (VChipMetadataKey tag : getTags()) {
            arrayBuilder.appendByte((byte) tag.getTag());
            IVmtiMetadataValue value = getField(tag);
            byte[] bytes = value.getBytes();
            arrayBuilder.appendAsBerLength(bytes.length);
            arrayBuilder.append(bytes);
        }
        return arrayBuilder.toBytes();
    }
}
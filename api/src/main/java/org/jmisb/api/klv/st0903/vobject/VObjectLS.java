package org.jmisb.api.klv.st0903.vobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.api.klv.st0903.shared.VmtiUri;
import org.jmisb.core.klv.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VObject Local Set.
 */
public class VObjectLS
{
    private static final Logger LOGGER = LoggerFactory.getLogger(VObjectLS.class);

    /**
     * Map containing all data elements in the message
     */
    private final SortedMap<VObjectMetadataKey, IVmtiMetadataValue> map = new TreeMap<>();

    /**
     * Create the message from the given key/value pairs
     *
     * @param values Tag/value pairs to be included in the local set/
     */
    public VObjectLS(Map<VObjectMetadataKey, IVmtiMetadataValue> values)
    {
        map.putAll(values);
    }

    public VObjectLS(byte[] bytes, int offset, int length) throws KlvParseException
    {
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, length);
        for (LdsField field : fields) {
            VObjectMetadataKey key = VObjectMetadataKey.getKey(field.getTag());
            if (key == VObjectMetadataKey.Undefined) {
                LOGGER.info("Unknown VMTI VObject Metadata tag: {}", field.getTag());
            } else {
                IVmtiMetadataValue value = createValue(key, field.getData());
                map.put(key, value);
            }
        }
    }

    /**
     * Create a {@link IVmtiMetadataValue} instance from encoded bytes
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the bytes could not be parsed.
     */
    public static IVmtiMetadataValue createValue(VObjectMetadataKey tag, byte[] bytes) throws KlvParseException
    {
        switch (tag)
        {
            case ontology:
                return new VmtiUri(VmtiUri.ONTOLOGY, bytes);
            case ontologyClass:
                return new VmtiTextString(VmtiTextString.ONTOLOGY_CLASS, bytes);
            case ontologyId:
                return new OntologyId(bytes);
            case confidence:
                return new Confidence2(bytes);
            default:
                LOGGER.info("Unrecognized VObject tag: {}", tag);
        }
        return null;
    }

    /**
     * Get the set of tags with populated values
     *
     * @return The set of tags for which values have been set
     */
    public Set<VObjectMetadataKey> getTags()
    {
        return map.keySet();
    }

    /**
     * Get the value of a given tag
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IVmtiMetadataValue getField(VObjectMetadataKey tag)
    {
        return map.get(tag);
    }

    /**
     * Get the byte array corresponding to the value for this Local Set.
     * @return byte array with the encoded local set.
     */
    public byte[] getBytes()
    {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (VObjectMetadataKey tag: getTags())
        {
            chunks.add(new byte[]{(byte) tag.getTag()});
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

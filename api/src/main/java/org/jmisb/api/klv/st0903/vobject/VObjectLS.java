package org.jmisb.api.klv.st0903.vobject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.api.klv.st0903.shared.VmtiUri;

/**
 * VObject Local Set.
 */
public class VObjectLS {

    private static final Logger LOG = Logger.getLogger(VObjectLS.class.getName());

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

    // TODO consider refactoring to pass in the original array instead of a copy
    public VObjectLS(byte[] bytes) throws KlvParseException
    {
        int offset = 0;
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, bytes.length - offset);
        for (LdsField field : fields) {
            VObjectMetadataKey key = VObjectMetadataKey.getKey(field.getTag());
            if (key == VObjectMetadataKey.Undefined) {
                LOG.log(Level.INFO, "Unknown VMTI VObject Metadata tag: {0}", field.getTag());
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
                // TODO
                return null;
            case confidence:
                // TODO
                return null;
            default:
                System.out.println("Unrecognized VObject tag: " + tag);
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
     * @throws IOException if there is a problem during conversion.
     */
    public byte[] getBytes() throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (VObjectMetadataKey tag: getTags())
        {
            baos.write(new byte[]{(byte) tag.getTag()});
            IVmtiMetadataValue value = getField(tag);
            byte[] bytes = value.getBytes();
            baos.write(BerEncoder.encode(bytes.length));
            baos.write(bytes);
        }
        return baos.toByteArray();
    }
}

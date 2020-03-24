package org.jmisb.api.klv.st0903;

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
import org.jmisb.api.klv.st0903.vchip.VChipMetadataKey;

public class VmtiLocalSet {

    private static final Logger LOG = Logger.getLogger(VmtiLocalSet.class.getName());

    /**
     * Map containing all data elements in the message
     */
    private final SortedMap<VmtiMetadataKey, IVmtiMetadataValue> map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public VmtiLocalSet(Map<VmtiMetadataKey, IVmtiMetadataValue> values)
    {
        map.putAll(values);
    }

    /**
     * Build a VMTI Local Set from encoded bytes.
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public VmtiLocalSet(byte[] bytes) throws KlvParseException
    {
        int offset = 0;
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, bytes.length);
        for (LdsField field : fields)
        {
            VmtiMetadataKey key = VmtiMetadataKey.getKey(field.getTag());
            switch (key) {
                case Undefined:
                    LOG.log(Level.INFO, "Unknown VMTI Metadata tag: {0}", field.getTag());
                    break;
                case Checksum:
                    // TODO check the checksum
                    break;
                default:
                    IVmtiMetadataValue value = VmtiMetadataValueFactory.createValue(key, field.getData());
                    map.put(key, value);
                    break;
            }
        }        
    }

    /**
     * Get the byte array corresponding to the value for this Local Set.
     * @return byte array with the encoded local set.
     * @throws IOException if there is a problem during conversion.
     */
    public byte[] getBytes() throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (VmtiMetadataKey tag: getTags())
        {
            baos.write(new byte[]{(byte) tag.getTag()});
            IVmtiMetadataValue value = getField(tag);
            byte[] bytes = value.getBytes();
            baos.write(BerEncoder.encode(bytes.length));
            baos.write(bytes);
        }
        return baos.toByteArray();
    }

    /**
     * Get the set of tags with populated values
     *
     * @return The set of tags for which values have been set
     */
    public Set<VmtiMetadataKey> getTags()
    {
        return map.keySet();
    }

    /**
     * Get the value of a given tag
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IVmtiMetadataValue getField(VmtiMetadataKey tag)
    {
        return map.get(tag);
    }

}

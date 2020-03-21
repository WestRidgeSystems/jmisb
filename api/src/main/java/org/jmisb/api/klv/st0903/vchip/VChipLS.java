package org.jmisb.api.klv.st0903.vchip;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.api.klv.st0903.shared.VmtiUri;

/**
 * VChip Local Set.
 */
public class VChipLS {

    private static final Logger LOG = Logger.getLogger(VChipLS.class.getName());

    /**
     * Map containing all data elements in the message
     */
    private final SortedMap<VChipMetadataKey, IVmtiMetadataValue> map = new TreeMap<>();

    // TODO consider refactoring to pass in the original array instead of a copy
    public VChipLS(byte[] bytes) throws KlvParseException
    {
        int offset = 0;
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, bytes.length - offset);
        for (LdsField field : fields) {
            VChipMetadataKey key = VChipMetadataKey.getKey(field.getTag());
            if (key == VChipMetadataKey.Undefined) {
                LOG.log(Level.INFO, "Unknown VMTI VChip Metadata tag: {0}", field.getTag());
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
    public static IVmtiMetadataValue createValue(VChipMetadataKey tag, byte[] bytes) throws KlvParseException
    {
        // Keep the case statements in enum ordinal order so we can keep track of what is implemented.
        // Mark all unimplemented tags with TODO.
        switch (tag) {
            case imageType:
                return new VmtiTextString(VmtiTextString.IMAGE_TYPE, bytes);
            case imageUri:
                return new VmtiUri(VmtiUri.IMAGE_URI, bytes);
            case embeddedImage:
                return new EmbeddedImage(bytes);
            default:
                System.out.println("Unrecognized VChip tag: " + tag);
        }
        return null;
    }

}

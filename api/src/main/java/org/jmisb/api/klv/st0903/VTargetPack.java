package org.jmisb.api.klv.st0903;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.st0903.vtarget.TargetCentroidPixelNumber;

class VTargetPack {

    private static final Logger LOG = Logger.getLogger(VTargetPack.class.getName());

    private int targetId;

    // TODO consider refactoring to pass in the original array instead of a copy
    VTargetPack(byte[] bytes) throws KlvParseException
    {
        int offset = 0;
        BerField targetIdField = BerDecoder.decode(bytes, offset, true);
        offset += targetIdField.getLength();
        targetId = targetIdField.getValue();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, bytes.length - offset);
        for (LdsField field : fields) {
            VTargetMetadataKey key = VTargetMetadataKey.getKey(field.getTag());
            if (key == VTargetMetadataKey.Undefined) {
                LOG.log(Level.INFO, "Unknown VMTI VTarget Metadata tag: {0}", field.getTag());
            } else {
                IVmtiMetadataValue value = createValue(key, field.getData());
                setField(key, value);
            }
        }
    }

    private void setField(VTargetMetadataKey key, IVmtiMetadataValue value)
    {

    }

    /**
     * Create a {@link IVmtiMetadataValue} instance from encoded bytes
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     */
    public static IVmtiMetadataValue createValue(VTargetMetadataKey tag, byte[] bytes) throws KlvParseException
    {
        // Keep the case statements in enum ordinal order so we can keep track of what is implemented. Mark all
        // unimplemented tags with TODO.
        switch (tag) {
            // TODO: Checksum
            // TOOD: PTS
            case TargetCentroidPixelNumber:
                return new TargetCentroidPixelNumber(bytes);
            default:
                System.out.println("Unrecognized VTarget tag: " + tag);
        }
        return null;
    }

}

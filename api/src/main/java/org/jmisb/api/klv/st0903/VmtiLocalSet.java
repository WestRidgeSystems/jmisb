package org.jmisb.api.klv.st0903;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;

public class VmtiLocalSet {

    private static final Logger LOG = Logger.getLogger(VmtiLocalSet.class.getName());

    public VmtiLocalSet() {
        // TODO
    }

    /**
     * Build a VmtiLocalSet from encoded bytes.
     *
     * @param bytes the bytes to build from
     */
    public VmtiLocalSet(byte[] bytes) throws KlvParseException {
        int offset = 0;
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, bytes.length);
        for (LdsField field : fields)
        {
            VmtiMetadataKey key = VmtiMetadataKey.getKey(field.getTag());
            if (key == VmtiMetadataKey.Undefined) {
                LOG.log(Level.INFO, "Unknown VMTI Metadata tag: {0}", field.getTag());
            } else {
                IVmtiMetadataValue value = VmtiMetadataValueFactory.createValue(key, field.getData());
                setField(key, value);
            }
        }        
    }

    public byte[] getBytes() {
        // TODO
        return new byte[]{(byte) 0x00};
    }

    private void setField(VmtiMetadataKey key, IVmtiMetadataValue value) {
        if (value != null) {
            LOG.log(Level.INFO, "VMTI LS Element: {0} = {1}|{2}", new Object[]{key, value.getDisplayName(), value.getDisplayableValue()});
        
        }
    }

}

package org.jmisb.st0601;

import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.VmtiLocalSet;
import org.jmisb.st0903.VmtiMetadataKey;

/**
 * VMTI Local Set (ST 0601 Item 74).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Use the MISB ST 0903 Local Set within the MISB ST 0601 Item 74.
 *
 * <p>The length field is the size of all VMTI LS metadata items to be packaged within Item 74.
 *
 * <p>The VMTI Local Set allows users to include, or nest, VMTI LS (MISB ST 0903) metadata items
 * within MISB ST 0601.
 *
 * <p>This provides users who are required to use the VMTI LS a method to leverage the items within
 * MISB ST 0601 (like platform location, and sensor pointing angles, or frame center).
 *
 * </blockquote>
 */
public class NestedVmtiLocalSet implements IUasDatalinkValue, INestedKlvValue {
    private final VmtiLocalSet vmtiLocalSet;

    /**
     * Create from value.
     *
     * @param vmti the VMTI data
     */
    public NestedVmtiLocalSet(VmtiLocalSet vmti) {
        this.vmtiLocalSet = vmti;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array
     * @throws KlvParseException if the input is invalid
     */
    public NestedVmtiLocalSet(byte[] bytes) throws KlvParseException {
        this.vmtiLocalSet = new VmtiLocalSet(bytes);
    }

    @Override
    public byte[] getBytes() {
        return this.vmtiLocalSet.frameMessage(true);
    }

    @Override
    public String getDisplayableValue() {
        return "[VMTI]";
    }

    @Override
    public String getDisplayName() {
        return "VMTI";
    }

    /**
     * Get the VMTI data.
     *
     * @return the VMTI data
     */
    public VmtiLocalSet getVmti() {
        return this.vmtiLocalSet;
    }

    @Override
    public IVmtiMetadataValue getField(IKlvKey tag) {
        return this.vmtiLocalSet.getField(tag);
    }

    @Override
    public Set<VmtiMetadataKey> getIdentifiers() {
        return this.vmtiLocalSet.getIdentifiers();
    }
}

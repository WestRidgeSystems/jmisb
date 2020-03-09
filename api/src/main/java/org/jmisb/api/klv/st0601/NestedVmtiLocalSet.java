package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.VmtiLocalSet;

/**
 * VMTI Local Set (ST 0601 tag 74)
 * <p>
 * From ST:
 * <blockquote>
 * Use the MISB ST 0903 Local Set within the MISB ST 0601 Tag 74.
 * <p>
 * The length field is the size of all VMTI LS metadata items to be packaged
 * within Tag 74.
 * <p>
 * The VMTI Local Set allows users to include, or nest, VMTI LS (MISB ST 0903)
 * metadata items within MISB ST 0601.
 * <p>
 * This provides users who are required to use the VMTI LS a method to leverage
 * the items within MISB ST 0601 (like platform location, and sensor pointing
 * angles, or frame center).
 * </blockquote>
 */
public class NestedVmtiLocalSet implements IUasDatalinkValue {

    private VmtiLocalSet vmtiLocalSet;

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
        return this.vmtiLocalSet.getBytes();
    }

    @Override
    public String getDisplayableValue() {
        // TODO: something more meaningful?
        return this.vmtiLocalSet.toString();
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
}

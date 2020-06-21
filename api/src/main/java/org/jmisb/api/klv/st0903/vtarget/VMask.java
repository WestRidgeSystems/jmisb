package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.vmask.VMaskLS;

/**
 * VMask (ST0903 VTarget Pack Tag 101).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * The VMask LS defines the outline of a detected target. Downstream clients can redraw the outline
 * or “chip” the target from the Motion Imagery. Specifying the shape of the outline is by (1) three
 * or more points representing the vertices of a polygon within a Motion Imagery Frame, or (2) a bit
 * mask identifying the pixels within the Motion Imagery Frame subsumed by the target. There is no
 * restriction in specifying both a polygon and a bit mask simultaneously.
 *
 * </blockquote>
 */
public class VMask implements IVmtiMetadataValue {
    private final VMaskLS value;

    /**
     * Create from value.
     *
     * @param vmaskLocalSet the VMask Local Set.
     */
    public VMask(VMaskLS vmaskLocalSet) {
        value = vmaskLocalSet;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the VMask LS
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public VMask(byte[] bytes) throws KlvParseException {
        value = new VMaskLS(bytes);
    }

    @Override
    public byte[] getBytes() {
        return value.getBytes();
    }

    @Override
    public String getDisplayableValue() {
        return "[VMask]";
    }

    @Override
    public final String getDisplayName() {
        return "Target Pixel Mask";
    }

    /**
     * Get the VMaskLS.
     *
     * @return the vmask local set.
     */
    public VMaskLS getMask() {
        return value;
    }
}

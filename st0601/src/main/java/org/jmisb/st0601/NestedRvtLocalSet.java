package org.jmisb.st0601;

import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.st0806.IRvtMetadataValue;
import org.jmisb.st0806.RvtLocalSet;

/**
 * Remote Video Terminal Local Set (ST 0601 Item 73).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * <p>MISB ST 0806 RVT Local Set metadata items.
 *
 * <p>The RVT Local Set item allows users to include, or nest, RVT LS (MISB ST 0806) metadata items
 * within MISB ST 0601.
 *
 * <p>This provides users who are required to use the RVT LS metadata items (Points of Interest,
 * Areas of Interest, etc.) a method to leverage the data field contained within MISB ST 0601 (i.e.,
 * platform location, and sensor pointing angles).
 *
 * </blockquote>
 */
public class NestedRvtLocalSet implements IUasDatalinkValue, INestedKlvValue {
    private final RvtLocalSet rvtLocalSet;

    /**
     * Create from value.
     *
     * @param rvt the RVT data
     */
    public NestedRvtLocalSet(RvtLocalSet rvt) {
        rvtLocalSet = rvt;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array
     * @throws KlvParseException if the input is invalid
     */
    public NestedRvtLocalSet(byte[] bytes) throws KlvParseException {
        rvtLocalSet = new RvtLocalSet(bytes, true);
    }

    @Override
    public byte[] getBytes() {
        return rvtLocalSet.frameMessage(true);
    }

    @Override
    public String getDisplayableValue() {
        return "[RVT]";
    }

    @Override
    public String getDisplayName() {
        return "RVT";
    }

    /**
     * Get the RVT data.
     *
     * @return the RVT data
     */
    public RvtLocalSet getRVT() {
        return rvtLocalSet;
    }

    @Override
    public IRvtMetadataValue getField(IKlvKey tag) {
        return this.rvtLocalSet.getField(tag);
    }

    @Override
    public Set<IKlvKey> getIdentifiers() {
        return this.rvtLocalSet.getIdentifiers();
    }
}

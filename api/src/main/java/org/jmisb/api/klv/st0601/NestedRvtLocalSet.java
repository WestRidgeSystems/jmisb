package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0806.RvtLocalSet;

/**
 * Remote Video Terminal Local Set (ST 0601 tag 73).
 * <p>
 * From ST:
 * <blockquote>
 * MISB ST 0806 RVT Local Set metadata items.
 * <p>
 * The RVT Local Set item allows users to include, or nest, RVT LS (MISB ST
 * 0806) metadata items within MISB ST 0601.
 * <p>
 * This provides users who are required to use the RVT LS metadata items (Points
 * of Interest, Areas of Interest, etc.) a method to leverage the data field
 * contained within MISB ST 0601 (i.e., platform location, and sensor pointing
 * angles).
 * </blockquote>
 */
public class NestedRvtLocalSet implements IUasDatalinkValue
{
    private final RvtLocalSet rvtLocalSet;

    /**
     * Create from value.
     *
     * @param rvt the RVT data
     */
    public NestedRvtLocalSet(RvtLocalSet rvt)
    {
        rvtLocalSet = rvt;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array
     * @throws KlvParseException if the input is invalid
     */
    public NestedRvtLocalSet(byte[] bytes) throws KlvParseException
    {
        rvtLocalSet = new RvtLocalSet(bytes);
    }

    @Override
    public byte[] getBytes()
    {
        return rvtLocalSet.frameMessage(true);
    }

    @Override
    public String getDisplayableValue()
    {
        return "[RVT]";
    }

    @Override
    public String getDisplayName()
    {
        return "RVT";
    }

    /**
     * Get the RVT data.
     *
     * @return the RVT data
     */
    public RvtLocalSet getRVT()
    {
        return rvtLocalSet;
    }
}

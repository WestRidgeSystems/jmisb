package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.IKlvKey;

/**
 * Enumeration of the various flags used in GenericFlagData01.
 */
public enum FlagDataKey implements IKlvKey
{
    LaserRange(0),
    AutoTrack(1),
    IR_Polarity(2),
    IcingStatus(3),
    SlantRange(4),
    ImageInvalid(5);
    
    FlagDataKey(int key)
    {
        this.tag = key;
    }

    private final int tag;

    @Override
    public int getTagCode()
    {
        return tag;
    }
    
}

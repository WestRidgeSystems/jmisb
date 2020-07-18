package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.IKlvKey;

/**
 * Enumeration of the various flags used in GenericFlagData01.
 *
 * <p>Each of these corresponds to a single bit in the GenericFlagData01 data. The tag value matches
 * the bit (where 0 is the least significant bit).
 */
public enum FlagDataKey implements IKlvKey {
    LaserRange(0),
    AutoTrack(1),
    IR_Polarity(2),
    IcingStatus(3),
    SlantRange(4),
    ImageInvalid(5);

    FlagDataKey(int key) {
        this.tag = key;
    }

    private final int tag;

    @Override
    public int getTag() {
        return tag;
    }
}

package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.IKlvKey;

/**
 * Enumeration of the various flags used in GenericFlagData01.
 *
 * <p>Each of these corresponds to a single bit in the {@link GenericFlagData01} data. The tag value
 * matches the bit (where 0 is the least significant bit).
 */
public enum FlagDataKey implements IKlvKey {
    /** Laser Range. */
    LaserRange(0),
    /** Auto Track. */
    AutoTrack(1),
    /**
     * IR Polarity.
     *
     * <p>This conveys whether an IR scene is "white hot" or "black hot".
     */
    IR_Polarity(2),
    /** Icing Status. */
    IcingStatus(3),
    /** Slant Range. */
    SlantRange(4),
    /** Image invalid. */
    ImageInvalid(5);

    FlagDataKey(int key) {
        this.tag = key;
    }

    private final int tag;

    @Override
    public int getIdentifier() {
        return tag;
    }
}

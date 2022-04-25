package org.jmisb.st1603.nanopack;

import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.st0603.NanoPrecisionTimeStamp;
import org.jmisb.st1603.localset.TimeTransferLocalSet;

/**
 * ST 1603 Nano Time Transfer Pack pseudo-keys.
 *
 * <p>This enumeration exists to provide labels for the two values that are present in an ST 1603
 * Nano Time Transfer pack - the timestamp and the local set. This pack structure always has two
 * keys.
 *
 * <p>The keys are somewhat "pseudo" in that they are artifacts of the API, and not the
 * implementation.
 */
public enum NanoTimeTransferPackKey implements IKlvKey {
    /**
     * Nano Precision Time Stamp.
     *
     * <p>The corresponding value is a {@link NanoPrecisionTimeStamp}.
     */
    NanoPrecisionTimeStamp(1),
    /**
     * Time Transfer Local Set.
     *
     * <p>The corresponding value is a {@link TimeTransferLocalSet}.
     */
    TimeTransferLocalSetValue(2);

    private final int tag;

    private NanoTimeTransferPackKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with this enumeration value.
     *
     * @return integer tag value for the local set identifier
     */
    @Override
    public int getIdentifier() {
        return tag;
    }
}

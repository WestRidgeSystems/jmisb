package org.jmisb.examples.parserplugin.timemessage;

import org.jmisb.api.klv.IKlvKey;

/**
 * Metadata keys for Time Message example.
 *
 * <p>A real message would likely have more than one key.
 */
public enum TimeMessageKey implements IKlvKey {
    /** The precision time stamp key. */
    PrecisionTimeStamp(1);

    private final int key;

    private TimeMessageKey(int tag) {
        this.key = tag;
    }

    @Override
    public int getIdentifier() {
        return key;
    }
}

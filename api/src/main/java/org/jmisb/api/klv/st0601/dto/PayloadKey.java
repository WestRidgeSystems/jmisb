package org.jmisb.api.klv.st0601.dto;

import org.jmisb.api.klv.IKlvKey;

/**
 * Enumeration of the various fields used in {@link Payload}.
 *
 * <p>Each of these corresponds to part of the payload information.
 */
public enum PayloadKey implements IKlvKey {
    /**
     * Unknown payload element.
     *
     * <p>This should not be intentionally created.
     */
    unknown(0),
    /** Identifier part of Payload. */
    Identifier(1),
    /** Payload type part of Payload. */
    PayloadType(2),
    /** Payload name part of Payload. */
    PayloadName(3);

    private PayloadKey(int key) {
        this.tag = key;
    }

    private final int tag;

    @Override
    public int getIdentifier() {
        return tag;
    }
}

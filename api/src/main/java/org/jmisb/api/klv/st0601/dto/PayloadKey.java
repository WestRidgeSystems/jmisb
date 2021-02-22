package org.jmisb.api.klv.st0601.dto;

import org.jmisb.api.klv.IKlvKey;

/**
 * Enumeration of the various fields used in {@link Payload}.
 *
 * <p>Each of these corresponds to part of the payload information.
 */
public enum PayloadKey implements IKlvKey {
    unknown(0),
    Identifier(1),
    PayloadType(2),
    PayloadName(3);

    PayloadKey(int key) {
        this.tag = key;
    }

    private final int tag;

    @Override
    public int getIdentifier() {
        return tag;
    }
}

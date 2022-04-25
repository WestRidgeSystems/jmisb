package org.jmisb.st1603.localset;

import org.jmisb.api.klv.IKlvKey;

/**
 * Enumeration of the elements within a {@link TimeTransferParameters} instance.
 *
 * <p>Each of these corresponds to part of the pack structure.
 */
public enum TimeTransferParametersKey implements IKlvKey {
    /** Key for Reference Source. */
    ReferenceSource(1),
    /** Key for Correction Method. */
    CorrectionMethod(2),
    /** Key for Time Transfer Method. */
    TimeTransferMethod(3);

    private TimeTransferParametersKey(int ident) {
        this.ident = ident;
    }

    private final int ident;

    @Override
    public int getIdentifier() {
        return ident;
    }
}

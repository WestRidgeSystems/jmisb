package org.jmisb.st0903.vtrack;

import org.jmisb.st0903.shared.IVTrackMetadataValue;
import org.jmisb.st0903.shared.VmtiV3Value;

/**
 * Number of Track Points (ST 0903 VTrack Local Set Item 13).
 *
 * <p>The number of VTrackItem Packs contained in the VTrackItemSeries (Item 101).
 */
public class NumTrackPoints extends VmtiV3Value implements IVTrackMetadataValue {
    /**
     * Create from value.
     *
     * @param numTrackPoints the number of track points.
     */
    public NumTrackPoints(int numTrackPoints) {
        super(numTrackPoints);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes count, encoded as a variable length unsigned int (max 3 bytes)
     */
    public NumTrackPoints(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Num Track Points";
    }
}

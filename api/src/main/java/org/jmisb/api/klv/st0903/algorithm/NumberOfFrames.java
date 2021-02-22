package org.jmisb.api.klv.st0903.algorithm;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiV3Value;

/**
 * Number of Frames (Algorithm LS Tag 5).
 *
 * <p>The nFrames is the number of frames the algorithm processes when detecting or tracking the
 * object.
 */
public class NumberOfFrames extends VmtiV3Value implements IVmtiMetadataValue {
    /**
     * Create from value.
     *
     * @param nFrames the number of frames
     */
    public NumberOfFrames(int nFrames) {
        super(nFrames);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes number of frames, encoded as a variable length unsigned int (max 3 bytes)
     */
    public NumberOfFrames(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Number of Frames";
    }
}

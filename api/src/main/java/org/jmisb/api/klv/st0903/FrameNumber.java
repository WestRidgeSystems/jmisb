package org.jmisb.api.klv.st0903;

import org.jmisb.api.klv.st0903.shared.VmtiV3Value;

/**
 * VMTI Frame Number (ST 0903 VMTI LS Tag 7).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Corresponds to the Frame in which detections occur. Frame number can be used when a timestamp is
 * not available.
 *
 * </blockquote>
 */
public class FrameNumber extends VmtiV3Value implements IVmtiMetadataValue {
    /**
     * Create from value
     *
     * @param frameNumber the frame number the motion imagery frame.
     */
    public FrameNumber(int frameNumber) {
        super(frameNumber);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes width, encoded as a variable length unsigned int (max 3 bytes)
     */
    public FrameNumber(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Frame Number";
    }

    @Override
    public String getDisplayableValue() {
        return "" + value;
    }
}

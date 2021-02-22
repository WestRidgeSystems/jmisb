package org.jmisb.api.klv.st0903;

import org.jmisb.api.klv.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiV3Value;

/**
 * VMTI Frame Height (ST 0903 VMTI Local Set Item 9 and VTrackItem Pack Item 21).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Height of the Motion Imagery frame in pixels, which corresponds to the number of rows of pixels
 * in the image. Frame Height is never a required field. Assumes pixels appear in row-major order.
 * Do not use a value of zero.
 *
 * </blockquote>
 */
public class FrameHeight extends VmtiV3Value
        implements IVmtiMetadataValue, IVTrackItemMetadataValue {
    /**
     * Create from value.
     *
     * @param frameHeight the height of the motion imagery frame, in pixels.
     */
    public FrameHeight(int frameHeight) {
        super(frameHeight);
        if (frameHeight == 0) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [1,16777215]");
        }
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes height, encoded as a variable length unsigned int (max 3 bytes)
     */
    public FrameHeight(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Frame Height";
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%dpx", value);
    }
}

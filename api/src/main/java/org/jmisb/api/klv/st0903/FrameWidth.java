package org.jmisb.api.klv.st0903;

import org.jmisb.api.klv.st0903.shared.VmtiV3Value;

/**
 * VMTI Frame Width (ST 0903 VMTI LS Tag 8).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Width of the Motion Imagery frame in pixels, which corresponds to the number of pixels in a row
 * of the image. Assumes pixels appear in row-major order. Do not use a value of zero.
 *
 * <p>An efficient method to express the location of a target within a Motion Imagery frame is with
 * Target Centroid Pixel Number (VTarget Pack Tag 1). Computing Target Centroid Pixel Number from
 * pixel row and column coordinates requires knowledge of the Motion Imagery frame width.
 * Fortunately, the Motion Imagery from which VMTI information derives will always include
 * appropriate frame size information. Converting Target Centroid Pixel Number back to row/column
 * coordinates, however, presents an issue. Consider the case when VMTI information derived from
 * Motion Imagery is different than that disseminated; for example, two cameras in one turret (say,
 * narrow and wide angle) one used in the VMTI process and the other producing Motion Imagery for
 * downstream users. A second case is VMTI information transported independent of Motion Imagery. In
 * both instances, the Frame Width metadata specifies the frame width.
 *
 * </blockquote>
 */
public class FrameWidth extends VmtiV3Value implements IVmtiMetadataValue {
    /**
     * Create from value.
     *
     * @param frameWidth the width of the motion imagery frame, in pixels.
     */
    public FrameWidth(int frameWidth) {
        super(frameWidth);
        if (frameWidth == 0) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [1,16777215]");
        }
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes width, encoded as a variable length unsigned int (max 3 bytes)
     */
    public FrameWidth(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Frame Width";
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%dpx", value);
    }
}

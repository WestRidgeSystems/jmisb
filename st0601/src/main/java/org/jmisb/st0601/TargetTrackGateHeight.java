package org.jmisb.st0601;

/**
 * Target Track Gate Height (ST 0601 Item 44).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Tracking gate height (y value) of tracked target within field of view. Closely tied to source
 * Motion Imagery.
 *
 * <p>For Target Tracking Sensors which display a box or gate around the target location, the Target
 * Track Gate Height specifies the height in pixels for the displayed tracking gate.
 *
 * </blockquote>
 */
public class TargetTrackGateHeight extends TargetTrackGateSize {
    /**
     * Create from value.
     *
     * @param px The gate height in pixels, in range [0, 510]
     */
    public TargetTrackGateHeight(short px) {
        super(px);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 1
     */
    public TargetTrackGateHeight(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Target Track Gate Height";
    }
}

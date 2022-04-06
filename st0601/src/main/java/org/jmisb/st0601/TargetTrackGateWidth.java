package org.jmisb.st0601;

/**
 * Target Track Gate Width (ST 0601 Item 43).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Tracking gate width (x value) of tracked target within field of view. Closely tied to source
 * Motion Imagery.
 *
 * <p>For Target Tracking Sensors which display a box or gate around the target location, the Target
 * Track Gate Width specifies the width in pixels for the displayed tracking gate.
 *
 * </blockquote>
 */
public class TargetTrackGateWidth extends TargetTrackGateSize {
    /**
     * Create from value.
     *
     * @param px Size in pixels. Legal values are in [0, 510].
     */
    public TargetTrackGateWidth(short px) {
        super(px);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 1
     */
    public TargetTrackGateWidth(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Target Track Gate Width";
    }
}

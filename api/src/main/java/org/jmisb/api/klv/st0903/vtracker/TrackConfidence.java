package org.jmisb.api.klv.st0903.vtracker;

import org.jmisb.api.klv.st0903.shared.Confidence;

/**
 * Tracker Confidence Level (ST0903 VTracker Pack Tag 7)
 * <p>
 * From ST0903:
 * <blockquote>
 * Confidence level expressed as a percentage from 0 to 100. Value 0 indicates
 * no confidence; value 100 percent indicates absolute certainty. Confidence is
 * an estimation of the certainty or correctness that the track described by the
 * sequence of VMTI movement detections corresponds to the same object. For
 * example, detections derived from many unambiguous target reports, such as,
 * for a single vehicle on a road in a desert environment might signal high
 * confidence. Reports associated with several overlapping or nearby tracks in a
 * partially obscured environment, such as, for dismounts (people) in an urban
 * setting might signal low confidence.
 * <p>
 * Valid values: The set of all integers from 0 to 100 inclusive
 * </blockquote>
 */
public class TrackConfidence extends Confidence
{
    /**
     * Create from value.
     *
     * @param confidence the tracker confidence as a percentage (0 lowest, 100 highest)
     */
    public TrackConfidence(short confidence)
    {
        super(confidence);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public TrackConfidence(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public final String getDisplayName()
    {
        return "Track Confidence";
    }
}

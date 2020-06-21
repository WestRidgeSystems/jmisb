package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.shared.Confidence;

/**
 * Target Confidence Level (ST0903 VTarget Pack Tag 5)
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Confidence level, expressed as a percentage, based on criteria within the VMTI system. Target(s)
 * with the highest confidence may not have the highest priority value. Potential for use in limited
 * bandwidth scenarios to only send highest confidence targets. Multiple targets may have the same
 * confidence level. Range 0 to 100, where 100 percent is the highest confidence. Although a
 * confidence level of 0 percent indicates no confidence that a detection is a potential target. A
 * target detected with a high confidence may be a low priority target.
 *
 * </blockquote>
 */
public class TargetConfidenceLevel extends Confidence {
    /**
     * Create from value.
     *
     * @param confidence the target confidence as a percentage (0 lowest, 100 highest)
     */
    public TargetConfidenceLevel(short confidence) {
        super(confidence);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public TargetConfidenceLevel(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Target Confidence";
    }
}

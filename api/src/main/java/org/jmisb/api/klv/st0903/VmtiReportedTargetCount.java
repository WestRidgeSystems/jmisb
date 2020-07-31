package org.jmisb.api.klv.st0903;

import org.jmisb.api.klv.st0903.shared.VmtiV3Value;

/**
 * Number of Targets Reported (ST 0903 VMTI LS Tag 6).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Number of moving targets reported within a Frame. May be necessary (for bandwidth efficiency) to
 * report only a subset of detected targets. Number of Reported Targets = Total Number of Targets
 * (Tag 5) – Number of Culled Targets. The culling process is usually linked to priority value or
 * confidence level.
 *
 * </blockquote>
 */
public class VmtiReportedTargetCount extends VmtiV3Value implements IVmtiMetadataValue {
    /**
     * Create from value.
     *
     * @param targetCount the number of targets reported in the frame.
     */
    public VmtiReportedTargetCount(int targetCount) {
        super(targetCount);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes count, encoded as a variable length unsigned int (max 3 bytes)
     */
    public VmtiReportedTargetCount(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Reported Targets";
    }
}

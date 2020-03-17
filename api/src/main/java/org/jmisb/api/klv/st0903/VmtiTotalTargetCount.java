package org.jmisb.api.klv.st0903;

/**
 * Total Number of Targets Detected in the Frame (ST0903 VMTI LS Tag 5).
 * <p>
 * From ST0903:
 * <blockquote>
 * Total number of moving targets detected in the Frame. Particularly relevant
 * when the number of targets reported (VMTI LS Tag 6) is less than the total
 * number detected in the frame.
 * <p>
 * A value of 0 represents no targets detected.
 * </blockquote>
 */
public class VmtiTotalTargetCount extends VmtiV3Value implements IVmtiMetadataValue
{
    /**
     * Create from value
     *
     * @param targetCount the number of targets detected in the frame.
     */
    public VmtiTotalTargetCount(int targetCount)
    {
        super(targetCount);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes count, encoded as a variable length unsigned int (max 3
     * bytes)
     */
    public VmtiTotalTargetCount(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public String getDisplayName()
    {
        return "Targets In Frame";
    }

}

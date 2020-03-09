package org.jmisb.api.klv.st0903;

public class VmtiReportedTargetCount extends VmtiV3Value implements IVmtiMetadataValue
{
    /**
     * Create from value
     *
     * @param targetCount the number of targets reported in the frame.
     */
    public VmtiReportedTargetCount(int targetCount)
    {
        super(targetCount);
    }

    /**
     * Create from encoded bytes
     * @param bytes count, encoded as a variable length unsigned int (max 3 bytes)
     */
    public VmtiReportedTargetCount(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Reported Targets";
    }

}

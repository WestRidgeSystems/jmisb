package org.jmisb.api.klv.st0903;

import org.jmisb.api.common.KlvParseException;

class VmtiMetadataValueFactory {

    /**
     * Create a {@link IVmtiMetadataValue} instance from encoded bytes
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     */
    public static IVmtiMetadataValue createValue(VmtiMetadataKey tag, byte[] bytes) throws KlvParseException {
        // Keep the case statements in enum ordinal order so we can keep track of what is implemented. Mark all
        // unimplemented tags with TODO.
        switch (tag) {
            // TODO: Checksum
            // TOOD: PTS
            case SystemName:
                return new VmtiTextString(VmtiTextString.SYSTEM_NAME, bytes);
            case VersionNumber:
                return new ST0903Version(bytes);
            case TotalTargetsInFrame:
                return new VmtiTotalTargetCount(bytes);
            case NumberOfReportedTargets:
                return new VmtiReportedTargetCount(bytes);
            // TODO: frame number
            case FrameWidth:
                return new FrameWidth(bytes);
            case FrameHeight:
                return new FrameHeight(bytes);
            case SourceSensor:
                return new VmtiTextString(VmtiTextString.SOURCE_SENSOR, bytes);
            // TODO: Horizontal FOV
            // TODO: Vertical FOV
            // TODO: MIIS
            case VTargetSeries:
                return new VTargetSeries(bytes);
            default:
                System.out.println("Unrecognized tag: " + tag);
        }
        return null;
    }
}

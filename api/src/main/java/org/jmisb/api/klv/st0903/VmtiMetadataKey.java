package org.jmisb.api.klv.st0903;

import java.util.HashMap;
import java.util.Map;

/**
 * ST 0903 tags - description and numbers.
 * 
 * Note that ST0903.5 removes UL keys.
 */
public enum VmtiMetadataKey
{
    Undefined(0),
    Checksum(1),
    PrecisionTimeStamp(2),
    SystemName(3),
    VersionNumber(4),
    TotalTargetsInFrame(5),
    NumberOfReportedTargets(6),
    FrameNumber(7),
    FrameWidth(8),
    FrameHeight(9),
    SourceSensor(10),
    HorizontalFieldOfView(11),
    VerticalFieldOfView(12),
    MiisId(13),
    VTargetSeries(101),
    AlgorithmSeries(102),
    OntologySeries(103);

    private int tag;

    private static final Map<Integer, VmtiMetadataKey> tagTable = new HashMap<>();

    static
    {
        for (VmtiMetadataKey key : values())
        {
            tagTable.put(key.tag, key);
        }
    }

    VmtiMetadataKey(int tag)
    {
        this.tag = tag;
    }

    public int getTag()
    {
        return tag;
    }

    public static VmtiMetadataKey getKey(int tag)
    {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }
}

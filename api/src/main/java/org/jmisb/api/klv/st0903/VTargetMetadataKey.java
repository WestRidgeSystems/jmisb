package org.jmisb.api.klv.st0903;

import java.util.HashMap;
import java.util.Map;

public enum VTargetMetadataKey
{
    Undefined(0),
    TargetCentroidPixelNumber(1),
    TargetBBoxTopLeftPixelNumber(2),
    TargetBBoxBottomRightPixelNumber(3),
    TargetPriority(4),
    TargetConfidenceLevel(5),
    NewDetectionTargetHistory(6),
    PercentageOfTargetPixels(7),
    TargetColor(8),
    TargetIntensity(9),
    TargetLocationLatitudeOffset(10),
    TargetLocationLongitudeOffset(11),
    TargetHeight(12),
    BBoxTopLeftLatitudeOffset(13),
    BBoxTopLeftLongitudeOffset(14),
    BBoxBottomRightLatitudeOffset(15),
    BBoxBottomRightLongitudeOffset(16),
    TargetLocation(17),
    TargetBoundary(18),
    TargetCentroidPixelRow(19),
    TargetCentroidPixelColumn(20),
    FPAIndex(21),
    VMaskLocalSet(101),
    VObjectLocalSet(102),
    VFeatureLocalSet(103),
    VTrackerLocalSet(104),
    VChipLocalSet(105),
    VChipSeriesLocalSet(106);
    
    private int tag;

    private static final Map<Integer, VTargetMetadataKey> tagTable = new HashMap<>();

    static
    {
        for (VTargetMetadataKey key : values())
        {
            tagTable.put(key.tag, key);
        }
    }

    VTargetMetadataKey(int tag)
    {
        this.tag = tag;
    }

    public int getTag()
    {
        return tag;
    }

    public static VTargetMetadataKey getKey(int tag)
    {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }

}

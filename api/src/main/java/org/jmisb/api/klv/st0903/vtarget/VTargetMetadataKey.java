package org.jmisb.api.klv.st0903.vtarget;

import java.util.HashMap;
import java.util.Map;

public enum VTargetMetadataKey
{
    Undefined(0),
    /**
     * Defines the position of the target within the Motion Imagery frame in
     * pixels.
     */
    TargetCentroid(1),
    /**
     * Position in pixels of the top left corner of the target bounding box
     * within the Motion Imagery Frame.
     */
    BoundaryTopLeft(2),
    /**
     * Position in pixels of the bottom right corner of the target bounding box
     * within the Motion Imagery Frame.
     */
    BoundaryBottomRight(3),
    /**
     * Priority or validity of target based on criteria within the VMTI system.
     */
    TargetPriority(4),
    /**
     * Confidence level of target based on criteria within the VMTI system.
     */
    TargetConfidenceLevel(5),
    /**
     * Number of previous times the same target detected.
     */
    TargetHistory(6),
    /**
     * Percentage of pixels within the bounding box detected to be target pixels
     * rather than background pixels.
     */
    PercentageOfTargetPixels(7),
    /**
     * Dominant color of the target.
     */
    TargetColor(8),
    /**
     * Dominant Intensity of the target.
     */
    TargetIntensity(9),
    /**
     * Latitude offset for target from frame center latitude (used with MISB
     * ST 0601).
     */
    TargetLocationOffsetLat(10),
    /**
     * Longitude offset for target from frame center longitude (used with MISB
     * ST 0601).
     */
    TargetLocationOffsetLon(11),
    /**
     * Height of target in meters above WGS84 Ellipsoid.
     */
    TargetHAE(12),
    /**
     * Latitude offset for top left corner of target bounding box.
     */
    BoundaryTopLeftLatOffset(13),
    /**
     * Longitude offset for top left corner of target bounding box.
     */
    BoundaryTopLeftLonOffset(14),
    /**
     * Latitude offset for bottom right corner of target bounding box.
     */
    BoundaryBottomRightLatOffset(15),
    /**
     * Longitude offset for bottom right corner of target bounding box.
     */
    BoundaryBottomRightLonOffset(16),
    /**
     * Location of the target (latitude, longitude and height above WGS84
     * Ellipsoid), with sigma and rho values.
     */
    TargetLocation(17),
    /**
     * Boundary around the target.
     */
    TargetBoundarySeries(18),
    /**
     * Specifies the row in pixels of the target centroid within the Motion
     * Imagery Frame.
     */
    CentroidPixRow(19),
    /**
     * Specifies the column in pixels of the target centroid within the Motion
     * Imagery Frame.
     */
    CentroidPixColumn(20),
    /**
     * Specifies the column and the row of a sensor Focal Plane Array (FPA) in a
     * two-dimensional array of FPAs.
     */
    FPAIndex(21),
    /**
     * Identifier indicating which algorithm in Algorithm Series detected this
     * target.
     */
    AlgorithmId(22),
    /**
     * Local Set to include a mask for delineating the perimeter of the target.
     */
    VMask(101),
    /**
     * Local Set to specify the class or type of a target.
     */
    VObject(102),
    /**
     * Local Set to include features about the target.
     */
    VFeature(103),
    /**
     * LOcal Set to include track information about the target.
     */
    VTracker(104),
    /**
     * Local Set to include underlying pixel values for the target.
     */
    VChip(105),
    /**
     * Series of one or more VChip LS.
     */
    VChipSeries(106),
    /**
     * Series of one or more VObject LS.
     */
    VObjectSeries(107);
    
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

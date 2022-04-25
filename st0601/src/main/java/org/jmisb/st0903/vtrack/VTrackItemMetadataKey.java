package org.jmisb.st0903.vtrack;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/**
 * ST 0903 VTrackItem tags - description and numbers.
 *
 * <p>Note that ST0903.5 removes UL keys.
 */
public enum VTrackItemMetadataKey implements IKlvKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    /** Target Time Stamp. */
    TargetTimeStamp(1),
    /** Target Centroid Pixel Number. */
    TargetCentroidPixNum(2),
    /** Target Centroid Pixel Row Number. */
    TargetCentroidPixRow(3),
    /** Target Centroid Pixel Column Number. */
    TargetCentroidPixCol(4),
    /** Boundary Top Left Pixel Number. */
    BoundaryTopLeftPixNum(5),
    /** Boundary Bottom Right Pixel Number. */
    BoundaryBottomRightPixNum(6),
    /** Target Priority. */
    TargetPriority(7),
    /** Target Confidence Level. */
    TargetConfidenceLevel(8),
    /** Target History. */
    TargetHistory(9),
    /** Percentage Target Pixels. */
    PercentTargetPixels(10),
    /** Target Color. */
    TargetColor(11),
    /** Target Intensity. */
    TargetIntensity(12),
    /** Target Location. */
    TargetLocation(13),
    /** Target Boundary Series. */
    TargetBoundarySeries(14),
    /** Velocity. */
    Velocity(15),
    /** Acceleration. */
    Acceleration(16),
    /** FPA Index. */
    FpaIndex(17),
    /** Video Frame Number. */
    VideoFrameNumber(18),
    /** Motion Imagery Identification System Identifier. */
    MiisId(19),
    /** Frame Width. */
    FrameWidth(20),
    /** Frame Height. */
    FrameHeight(21),
    /** Sensor Horizontal Field of View. */
    SensorHorizontalFov(22),
    /** Sensor Vertical Field of View. */
    SensorVerticalFov(23),
    /** Motion Imagery URL. */
    MotionImageryUrl(24),
    /** VMask Local Set. */
    VMask(101),
    /** VObject Local Set. */
    VObject(102),
    /** VFeature Local Set. */
    VFeature(103),
    /** VChip Local Set. */
    VChip(105),
    /** VChip Series Local Set. */
    VChipSeries(106),
    /** VObject Series Local Set. */
    VObjectSeries(107);

    private final int tag;

    private static final Map<Integer, VTrackItemMetadataKey> tagTable = new HashMap<>();

    static {
        for (VTrackItemMetadataKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    /**
     * Constructor.
     *
     * <p>Internal use only.
     *
     * @param tag the tag value to initialise the enumeration value.
     */
    VTrackItemMetadataKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with the metadata key.
     *
     * <p>This is the value used in the Key part of the KLV encoding.
     *
     * @return metadata key tag value as an integer
     */
    @Override
    public int getIdentifier() {
        return tag;
    }

    /**
     * Look up a metadata key from its tag value.
     *
     * @param tag the tag value (Key part of KLV encoding).
     * @return the corresponding metadata key.
     */
    public static VTrackItemMetadataKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}

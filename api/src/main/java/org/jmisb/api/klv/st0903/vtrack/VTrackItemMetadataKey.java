package org.jmisb.api.klv.st0903.vtrack;

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
    TargetTimeStamp(1),
    TargetCentroidPixNum(2),
    TargetCentroidPixRow(3),
    TargetCentroidPixCol(4),
    BoundaryTopLeftPixNum(5),
    BoundaryBottomRightPixNum(6),
    TargetPriority(7),
    TargetConfidenceLevel(8),
    TargetHistory(9),
    PercentTargetPixels(10),
    TargetColor(11),
    TargetIntensity(12),
    TargetLocation(13),
    TargetBoundarySeries(14),
    Velocity(15),
    Acceleration(16),
    FpaIndex(17),
    VideoFrameNumber(18),
    MiisId(19),
    FrameWidth(20),
    FrameHeight(21),
    SensorHorizontalFov(22),
    SensorVerticalFov(23),
    MotionImageryUrl(24),
    VMask(101),
    VObject(102),
    VFeature(103),
    VChip(105),
    VChipSeries(106),
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

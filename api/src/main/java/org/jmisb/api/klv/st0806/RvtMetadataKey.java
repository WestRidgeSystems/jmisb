package org.jmisb.api.klv.st0806;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/** ST 0806 tags - description and numbers. */
public enum RvtMetadataKey implements IKlvKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    CRC32(1),
    UserDefinedTimeStampMicroseconds(2),
    PlatformTrueAirspeed(3),
    PlatformIndicatedAirspeed(4),
    TelemetryAccuracyIndicator(5),
    FragCircleRadius(6),
    FrameCode(7),
    UASLSVersionNumber(8),
    VideoDataRate(9),
    DigitalVideoFileFormat(10),
    UserDefinedLS(11),
    PointOfInterestLS(12),
    AreaOfInterestLS(13),
    MGRSZone(14),
    MGRSLatitudeBandAndGridSquare(15),
    MGRSEasting(16),
    MGRSNorthing(17),
    MGRSZoneSecondValue(18),
    MGRSLatitudeBandAndGridSquareSecondValue(19),
    MGRSEastingSecondValue(20),
    MGRSNorthingSecondValue(21);

    private final int tag;

    private static final Map<Integer, RvtMetadataKey> tagTable = new HashMap<>();

    static {
        for (RvtMetadataKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    RvtMetadataKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with this enumeration value.
     *
     * @return integer tag value for the metadata key
     */
    public int getIdentifier() {
        return tag;
    }

    /**
     * Look up the metadata key by tag identifier.
     *
     * @param tag the integer tag value to look up
     * @return corresponding metadata key
     */
    public static RvtMetadataKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}

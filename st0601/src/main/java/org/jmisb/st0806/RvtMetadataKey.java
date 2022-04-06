package org.jmisb.st0806;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/** ST 0806 tags - description and numbers. */
public enum RvtMetadataKey implements IKlvKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    /** Cyclic Redundancy Check value. */
    CRC32(1),
    /** User defined timestamp - microseconds. */
    UserDefinedTimeStampMicroseconds(2),
    /** Platform True Airspeed. */
    PlatformTrueAirspeed(3),
    /** Platform Indicated Airspeed. */
    PlatformIndicatedAirspeed(4),
    /** Telemetry Accuracy Indicator. */
    TelemetryAccuracyIndicator(5),
    /** Frag Circle Radius. */
    FragCircleRadius(6),
    /** Frame code. */
    FrameCode(7),
    /** UAS Local Set Version Number. */
    UASLSVersionNumber(8),
    /** Video Data Rate. */
    VideoDataRate(9),
    /** Digital Video File Format. */
    DigitalVideoFileFormat(10),
    /** User defined local set. */
    UserDefinedLS(11),
    /** Point of interest (POI) local set. */
    PointOfInterestLS(12),
    /** Area of interest (AOI) local set. */
    AreaOfInterestLS(13),
    /** MGRS Zone. */
    MGRSZone(14),
    /** MGRS Latitude Band and Grid Square. */
    MGRSLatitudeBandAndGridSquare(15),
    /** MGRS Easting. */
    MGRSEasting(16),
    /** MGRS Northing. */
    MGRSNorthing(17),
    /** MGRS Zone Second Value. */
    MGRSZoneSecondValue(18),
    /** MGRS Latitude Band and Grid Square Second Value. */
    MGRSLatitudeBandAndGridSquareSecondValue(19),
    /** MGRS Easting Second Value. */
    MGRSEastingSecondValue(20),
    /** MGRS Northing Second Value. */
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

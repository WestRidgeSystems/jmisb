package org.jmisb.api.klv.eg0104;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.UniversalLabel;

/** EG 0104 key. */
public enum PredatorMetadataKey implements IKlvKey {
    /**
     * Undefined.
     *
     * <p>This value is not valid and should not be intentionally created.
     */
    Undefined(
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                        0x00, 0x00, 0x00, 0x00
                    }),
            99),
    /** Frame Centre Latitude. */
    FrameCenterLatitude(PredatorMetadataConstants.FRAME_CENTRE_LATITUDE, 100),
    /** Frame Centre Longitude. */
    FrameCenterLongitude(PredatorMetadataConstants.FRAME_CENTRE_LONGITUDE, 101),
    /** Frame Centre Elevation. */
    FrameCenterElevation(PredatorMetadataConstants.FRAME_CENTRE_ELEVATION, 102),
    /** Image Coordinate System. */
    ImageCoordinateSystem(PredatorMetadataConstants.IMAGE_COORDINATE_SYSTEM, 103),
    /** Target Width. */
    TargetWidth(PredatorMetadataConstants.TARGET_WIDTH, 104),
    /** Start date-time in UTC. */
    StartDateTimeUtc(PredatorMetadataConstants.START_DATE_TIME_UTC, 105),
    /** Event start date-time in UTC. */
    EventStartDateTimeUtc(PredatorMetadataConstants.EVENT_START_DATE_TIME_UTC, 106),
    /** User defined time stamp. */
    UserDefinedTimeStamp(PredatorMetadataConstants.USER_DEFINED_TIME_STAMP, 107),
    /** Latitude of Corner Point 1. */
    CornerLatitudePoint1(PredatorMetadataConstants.CORNER_LATITUDE_POINT_1, 108),
    /** Latitude of Corner Point 2. */
    CornerLatitudePoint2(PredatorMetadataConstants.CORNER_LATITUDE_POINT_2, 109),
    /** Latitude of Corner Point 3. */
    CornerLatitudePoint3(PredatorMetadataConstants.CORNER_LATITUDE_POINT_3, 110),
    /** Latitude of Corner Point 4. */
    CornerLatitudePoint4(PredatorMetadataConstants.CORNER_LATITUDE_POINT_4, 111),
    /** Longitude of Corner Point 1. */
    CornerLongitudePoint1(PredatorMetadataConstants.CORNER_LONGITUDE_POINT_1, 112),
    /** Longitude of Corner Point 2. */
    CornerLongitudePoint2(PredatorMetadataConstants.CORNER_LONGITUDE_POINT_2, 113),
    /** Longitude of Corner Point 3. */
    CornerLongitudePoint3(PredatorMetadataConstants.CORNER_LONGITUDE_POINT_3, 114),
    /** Longitude of Corner Point 4. */
    CornerLongitudePoint4(PredatorMetadataConstants.CORNER_LONGITUDE_POINT_4, 115),
    /** Slant Range. */
    SlantRange(PredatorMetadataConstants.SLANT_RANGE, 116),
    /** Sensor Roll Angle. */
    SensorRollAngle(PredatorMetadataConstants.SENSOR_ROLL_ANGLE, 117),
    /** Angle to North. */
    AngleToNorth(PredatorMetadataConstants.ANGLE_TO_NORTH, 118),
    /** Obliquity Angle. */
    ObliquityAngle(PredatorMetadataConstants.OBLIQUITY_ANGLE, 119),
    /** Platform Roll Angle. */
    PlatformRollAngle(PredatorMetadataConstants.PLATFORM_ROLL_ANGLE, 120),
    /** Platform Pitch Angle. */
    PlatformPitchAngle(PredatorMetadataConstants.PLATFORM_PITCH_ANGLE, 121),
    /** Platform Heading Angle. */
    PlatformHeadingAngle(PredatorMetadataConstants.PLATFORM_HEADING_ANGLE, 122),
    /** Horizontal Field of View. */
    FieldOfViewHorizontal(PredatorMetadataConstants.FIELD_OF_VIEW_HORIZONTAL, 123),
    /** Vertical Field of View. */
    FieldOfViewVertical(PredatorMetadataConstants.FIELD_OF_VIEW_VERTICAL, 124),
    /** Device Altitude. */
    DeviceAltitude(PredatorMetadataConstants.DEVICE_ALTITUDE, 125),
    /** Device Latitude. */
    DeviceLatitude(PredatorMetadataConstants.DEVICE_LATITUDE, 126),
    /** Device Longitude. */
    DeviceLongitude(PredatorMetadataConstants.DEVICE_LONGITUDE, 127),
    /** Image Source Device. */
    ImageSourceDevice(PredatorMetadataConstants.IMAGE_SOURCE_DEVICE, 128),
    /** Episode Number. */
    EpisodeNumber(PredatorMetadataConstants.EPISODE_NUMBER, 129),
    /** Device Designation. */
    DeviceDesignation(PredatorMetadataConstants.DEVICE_DESIGATION, 130);

    private final UniversalLabel ul;
    /**
     * Tag-like field.
     *
     * <p>EG 0104 doesn't use a local-set like value, and there aren't tags as used in most other
     * metadata types. This serves as a surrogate to make the interface uniform.
     */
    private final int key;

    private static final Map<UniversalLabel, PredatorMetadataKey> ulTable = new HashMap<>();

    static {
        for (PredatorMetadataKey key : values()) {
            ulTable.put(key.ul, key);
        }
    }

    /**
     * Constructor.
     *
     * @param ul The key's universal label
     * @param key an integer ident for this key.
     */
    private PredatorMetadataKey(UniversalLabel ul, int key) {
        this.ul = ul;
        this.key = key;
    }

    /**
     * Get the universal label.
     *
     * @return The universal label for this key
     */
    public UniversalLabel getUl() {
        return ul;
    }

    /**
     * Get the key for a given universal label.
     *
     * @param ul Universal label
     * @return The PredatorMetadataKey
     */
    public static PredatorMetadataKey getKey(UniversalLabel ul) {
        return ulTable.getOrDefault(ul, Undefined);
    }

    @Override
    public int getIdentifier() {
        return key;
    }
}

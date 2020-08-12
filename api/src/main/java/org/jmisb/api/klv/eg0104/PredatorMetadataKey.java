package org.jmisb.api.klv.eg0104;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.UniversalLabel;

/** EG 0104 key. */
public enum PredatorMetadataKey implements IKlvKey {
    Undefined(
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                        0x00, 0x00, 0x00, 0x00
                    }),
            99),
    FrameCenterLatitude(PredatorMetadataConstants.FRAME_CENTRE_LATITUDE, 100),
    FrameCenterLongitude(PredatorMetadataConstants.FRAME_CENTRE_LONGITUDE, 101),
    FrameCenterElevation(PredatorMetadataConstants.FRAME_CENTRE_ELEVATION, 102),
    ImageCoordinateSystem(PredatorMetadataConstants.IMAGE_COORDINATE_SYSTEM, 103),
    TargetWidth(PredatorMetadataConstants.TARGET_WIDTH, 104),
    StartDateTimeUtc(PredatorMetadataConstants.START_DATE_TIME_UTC, 105),
    EventStartDateTimeUtc(PredatorMetadataConstants.EVENT_START_DATE_TIME_UTC, 106),
    UserDefinedTimeStamp(PredatorMetadataConstants.USER_DEFINED_TIME_STAMP, 107),
    CornerLatitudePoint1(PredatorMetadataConstants.CORNER_LATITUDE_POINT_1, 108),
    CornerLatitudePoint2(PredatorMetadataConstants.CORNER_LATITUDE_POINT_2, 109),
    CornerLatitudePoint3(PredatorMetadataConstants.CORNER_LATITUDE_POINT_3, 110),
    CornerLatitudePoint4(PredatorMetadataConstants.CORNER_LATITUDE_POINT_4, 111),
    CornerLongitudePoint1(PredatorMetadataConstants.CORNER_LONGITUDE_POINT_1, 112),
    CornerLongitudePoint2(PredatorMetadataConstants.CORNER_LONGITUDE_POINT_2, 113),
    CornerLongitudePoint3(PredatorMetadataConstants.CORNER_LONGITUDE_POINT_3, 114),
    CornerLongitudePoint4(PredatorMetadataConstants.CORNER_LONGITUDE_POINT_4, 115),
    SlantRange(PredatorMetadataConstants.SLANT_RANGE, 116),
    SensorRollAngle(PredatorMetadataConstants.SENSOR_ROLL_ANGLE, 117),
    AngleToNorth(PredatorMetadataConstants.ANGLE_TO_NORTH, 118),
    ObliquityAngle(PredatorMetadataConstants.OBLIQUITY_ANGLE, 119),
    PlatformRollAngle(PredatorMetadataConstants.PLATFORM_ROLL_ANGLE, 120),
    PlatformPitchAngle(PredatorMetadataConstants.PLATFORM_PITCH_ANGLE, 121),
    PlatformHeadingAngle(PredatorMetadataConstants.PLATFORM_HEADING_ANGLE, 122),
    FieldOfViewHorizontal(PredatorMetadataConstants.FIELD_OF_VIEW_HORIZONTAL, 123),
    FieldOfViewVertical(PredatorMetadataConstants.FIELD_OF_VIEW_VERTICAL, 124),
    DeviceAltitude(PredatorMetadataConstants.DEVICE_ALTITUDE, 125),
    DeviceLatitude(PredatorMetadataConstants.DEVICE_LATITUDE, 126),
    DeviceLongitude(PredatorMetadataConstants.DEVICE_LONGITUDE, 127),
    ImageSourceDevice(PredatorMetadataConstants.IMAGE_SOURCE_DEVICE, 128),
    EpisodeNumber(PredatorMetadataConstants.EPISODE_NUMBER, 129),
    DeviceDesignation(PredatorMetadataConstants.DEVICE_DESIGATION, 130);

    private UniversalLabel ul;
    /**
     * Tag-like field.
     *
     * <p>EG 0104 doesn't use a local-set like value, and there aren't tags as used in most other
     * metadata types. This serves as a surrogate to make the interface uniform.
     */
    private int key;

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
        return ulTable.containsKey(ul) ? ulTable.get(ul) : Undefined;
    }

    @Override
    public int getIdentifier() {
        return key;
    }
}

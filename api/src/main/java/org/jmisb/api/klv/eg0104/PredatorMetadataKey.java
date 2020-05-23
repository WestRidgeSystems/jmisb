package org.jmisb.api.klv.eg0104;

import java.util.Arrays;
import org.jmisb.api.klv.UniversalLabel;

import java.util.HashMap;
import java.util.Map;

/**
 * EG 0104 key
 */
public enum PredatorMetadataKey
{
    Undefined(new UniversalLabel(new byte[]{0x06, 0x0e, 0x2b, 0x34, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00})),
    FrameCenterLatitude(PredatorMetadataConstants.FRAME_CENTRE_LATITUDE),
    FrameCenterLongitude(PredatorMetadataConstants.FRAME_CENTRE_LONGITUDE),
    FrameCenterElevation(PredatorMetadataConstants.FRAME_CENTRE_ELEVATION),
    ImageCoordinateSystem(PredatorMetadataConstants.IMAGE_COORDINATE_SYSTEM),
    TargetWidth(PredatorMetadataConstants.TARGET_WIDTH),
    StartDateTimeUtc(PredatorMetadataConstants.START_DATE_TIME_UTC),
    EventStartDateTimeUtc(PredatorMetadataConstants.EVENT_START_DATE_TIME_UTC),
    UserDefinedTimeStamp(PredatorMetadataConstants.USER_DEFINED_TIME_STAMP),
    CornerLatitudePoint1(PredatorMetadataConstants.CORNER_LATITUDE_POINT_1),
    CornerLatitudePoint2(PredatorMetadataConstants.CORNER_LATITUDE_POINT_2),
    CornerLatitudePoint3(PredatorMetadataConstants.CORNER_LATITUDE_POINT_3),
    CornerLatitudePoint4(PredatorMetadataConstants.CORNER_LATITUDE_POINT_4),
    CornerLongitudePoint1(PredatorMetadataConstants.CORNER_LONGITUDE_POINT_1),
    CornerLongitudePoint2(PredatorMetadataConstants.CORNER_LONGITUDE_POINT_2),
    CornerLongitudePoint3(PredatorMetadataConstants.CORNER_LONGITUDE_POINT_3),
    CornerLongitudePoint4(PredatorMetadataConstants.CORNER_LONGITUDE_POINT_4),
    SlantRange(PredatorMetadataConstants.SLANT_RANGE),
    SensorRollAngle(PredatorMetadataConstants.SENSOR_ROLL_ANGLE),
    AngleToNorth(PredatorMetadataConstants.ANGLE_TO_NORTH),
    ObliquityAngle(PredatorMetadataConstants.OBLIQUITY_ANGLE),
    PlatformRollAngle(PredatorMetadataConstants.PLATFORM_ROLL_ANGLE),
    PlatformPitchAngle(PredatorMetadataConstants.PLATFORM_PITCH_ANGLE),
    PlatformHeadingAngle(PredatorMetadataConstants.PLATFORM_HEADING_ANGLE),
    FieldOfViewHorizontal(PredatorMetadataConstants.FIELD_OF_VIEW_HORIZONTAL),
    FieldOfViewVertical(PredatorMetadataConstants.FIELD_OF_VIEW_VERTICAL),
    DeviceAltitude(PredatorMetadataConstants.DEVICE_ALTITUDE),
    DeviceLatitude(PredatorMetadataConstants.DEVICE_LATITUDE),
    DeviceLongitude(PredatorMetadataConstants.DEVICE_LONGITUDE),
    ImageSourceDevice(PredatorMetadataConstants.IMAGE_SOURCE_DEVICE),
    EpisodeNumber(PredatorMetadataConstants.EPISODE_NUMBER),
    DeviceDesignation(PredatorMetadataConstants.DEVICE_DESIGATION);

    private UniversalLabel ul;

    private static final Map<UniversalLabel, PredatorMetadataKey> ulTable = new HashMap<>();

    static
    {
        for (PredatorMetadataKey key : values())
        {
            ulTable.put(key.ul, key);
        }
    }

    PredatorMetadataKey(UniversalLabel ul)
    {
        this.ul = ul;
    }

    public UniversalLabel getUl()
    {
        return ul;
    }

    public static PredatorMetadataKey getKey(UniversalLabel ul)
    {
        return ulTable.containsKey(ul) ? ulTable.get(ul) : Undefined;
    }
}

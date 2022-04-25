package org.jmisb.eg0104;

/** Dynamically create {@link IPredatorMetadataValue}s from {@link PredatorMetadataKey}s. */
public class PredatorUniversalSetFactory {
    private PredatorUniversalSetFactory() {}

    /**
     * Create a {@link IPredatorMetadataValue} instance from encoded bytes.
     *
     * @param key Key defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws IllegalArgumentException if input is invalid
     */
    public static IPredatorMetadataValue createValue(PredatorMetadataKey key, byte[] bytes) {
        switch (key) {
            case Undefined:
                return new OpaqueValue(bytes, "Undefined");
            case FrameCenterLatitude:
                return new LatLonValue(bytes, "Frame Center Latitude");
            case FrameCenterLongitude:
                return new LatLonValue(bytes, "Frame Center Longitude");
            case FrameCenterElevation:
                return new AltitudeValue(bytes, "Frame Center Elevation");
            case ImageCoordinateSystem:
                return new TextValue(bytes, "Image Coordinate System");
            case TargetWidth:
                return new TargetWidth(bytes);
            case StartDateTimeUtc:
                return new DateTimeUTC(bytes, "Start Date Time (UTC)");
            case EventStartDateTimeUtc:
                return new DateTimeUTC(bytes, "Event Start Date Time (UTC)");
            case UserDefinedTimeStamp:
                return new UserDefinedTimeStamp(bytes);
            case CornerLatitudePoint1:
                return new LatLonValue(bytes, "Corner Latitude Point 1");
            case CornerLatitudePoint2:
                return new LatLonValue(bytes, "Corner Latitude Point 2");
            case CornerLatitudePoint3:
                return new LatLonValue(bytes, "Corner Latitude Point 3");
            case CornerLatitudePoint4:
                return new LatLonValue(bytes, "Corner Latitude Point 4");
            case CornerLongitudePoint1:
                return new LatLonValue(bytes, "Corner Longitude Point 1");
            case CornerLongitudePoint2:
                return new LatLonValue(bytes, "Corner Longitude Point 2");
            case CornerLongitudePoint3:
                return new LatLonValue(bytes, "Corner Longitude Point 3");
            case CornerLongitudePoint4:
                return new LatLonValue(bytes, "Corner Longitude Point 4");
            case SlantRange:
                return new SlantRange(bytes);
            case SensorRollAngle:
                return new AngleValue(bytes, "Sensor Roll Angle");
            case AngleToNorth:
                return new AngleValue(bytes, "Angle to North");
            case ObliquityAngle:
                return new AngleValue(bytes, "Obliquity Angle");
            case PlatformRollAngle:
                return new AngleValue(bytes, "Platform Roll Angle");
            case PlatformPitchAngle:
                return new AngleValue(bytes, "Platform Pitch Angle");
            case PlatformHeadingAngle:
                return new AngleValue(bytes, "Platform Heading Angle");
            case FieldOfViewHorizontal:
                return new AngleValue(bytes, "Field of View - Horizontal");
            case FieldOfViewVertical:
                return new AngleValue(bytes, "Field of View - Vertical");
            case DeviceAltitude:
                return new AltitudeValue(bytes, "Device Altitude");
            case DeviceLatitude:
                return new LatLonValue(bytes, "Device Latitude");
            case DeviceLongitude:
                return new LatLonValue(bytes, "Device Longitude");
            case ImageSourceDevice:
                return new ImageSourceDevice(bytes);
            case EpisodeNumber:
                return new TextValue(bytes, "Episode Number");
            case DeviceDesignation:
                return new TextValue(bytes, "Device Designation");
            default:
                throw new IllegalArgumentException("Unrecognized Predator Metadata Key: " + key);
        }
    }
}

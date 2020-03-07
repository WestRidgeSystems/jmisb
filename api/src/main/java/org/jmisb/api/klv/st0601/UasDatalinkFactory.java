package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;

/**
 * Dynamically create {@link IUasDatalinkValue}s from {@link UasDatalinkTag}s.
 */
public class UasDatalinkFactory
{
    private UasDatalinkFactory() {}

    /**
     * Create a {@link IUasDatalinkValue} instance from encoded bytes
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     *
     * @throws IllegalArgumentException if input is invalid
     * @throws KlvParseException if a parsing error occurs
     */
    public static IUasDatalinkValue createValue(UasDatalinkTag tag, byte[] bytes) throws KlvParseException
    {
        // Keep the case statements in enum ordinal order so we can keep track of what is implemented. Mark all
        // unimplemented tags with TODO.
        switch (tag)
        {
            case Undefined:
                break;
            case PrecisionTimeStamp:
                return new PrecisionTimeStamp(bytes);
            case MissionId:
                return new UasDatalinkString(UasDatalinkString.MISSION_ID, bytes);
            case PlatformTailNumber:
                return new UasDatalinkString(UasDatalinkString.PLATFORM_TAIL_NUMBER, bytes);
            case PlatformHeadingAngle:
                return new PlatformHeadingAngle(bytes);
            case PlatformPitchAngle:
                return new PlatformPitchAngle(bytes);
            case PlatformRollAngle:
                return new PlatformRollAngle(bytes);
            case PlatformTrueAirspeed:
                return new PlatformTrueAirspeed(bytes);
            case PlatformIndicatedAirspeed:
                return new PlatformIndicatedAirspeed(bytes);
            case PlatformDesignation:
                return new UasDatalinkString(UasDatalinkString.PLATFORM_DESIGNATION, bytes);
            case ImageSourceSensor:
                return new UasDatalinkString(UasDatalinkString.IMAGE_SOURCE_SENSOR, bytes);
            case ImageCoordinateSystem:
                return new UasDatalinkString(UasDatalinkString.IMAGE_COORDINATE_SYSTEM, bytes);
            case SensorLatitude:
                return new SensorLatitude(bytes);
            case SensorLongitude:
                return new SensorLongitude(bytes);
            case SensorTrueAltitude:
                return new SensorTrueAltitude(bytes);
            case SensorHorizontalFov:
                return new HorizontalFov(bytes);
            case SensorVerticalFov:
                return new VerticalFov(bytes);
            case SensorRelativeAzimuthAngle:
                return new SensorRelativeAzimuth(bytes);
            case SensorRelativeElevationAngle:
                return new SensorRelativeElevation(bytes);
            case SensorRelativeRollAngle:
                return new SensorRelativeRoll(bytes);
            case SlantRange:
                return new SlantRange(bytes);
            case TargetWidth:
                return new TargetWidth(bytes);
            case FrameCenterLatitude:
                return new FrameCenterLatitude(bytes);
            case FrameCenterLongitude:
                return new FrameCenterLongitude(bytes);
            case FrameCenterElevation:
                return new FrameCenterElevation(bytes);
            case OffsetCornerLatitudePoint1:
                return new CornerOffset(bytes, CornerOffset.CORNER_LAT_1);
            case OffsetCornerLongitudePoint1:
                return new CornerOffset(bytes, CornerOffset.CORNER_LON_1);
            case OffsetCornerLatitudePoint2:
                return new CornerOffset(bytes, CornerOffset.CORNER_LAT_2);
            case OffsetCornerLongitudePoint2:
                return new CornerOffset(bytes, CornerOffset.CORNER_LON_2);
            case OffsetCornerLatitudePoint3:
                return new CornerOffset(bytes, CornerOffset.CORNER_LAT_3);
            case OffsetCornerLongitudePoint3:
                return new CornerOffset(bytes, CornerOffset.CORNER_LON_3);
            case OffsetCornerLatitudePoint4:
                return new CornerOffset(bytes, CornerOffset.CORNER_LAT_4);
            case OffsetCornerLongitudePoint4:
                return new CornerOffset(bytes, CornerOffset.CORNER_LON_4);
            case IcingDetected:
                return new IcingDetected(bytes);
            case WindDirection:
                return new WindDirectionAngle(bytes);
            case WindSpeed:
                return new WindSpeed(bytes);
            case StaticPressure:
                return new StaticPressure(bytes);
            case DensityAltitude:
                return new DensityAltitude(bytes);
            case OutsideAirTemp:
                return new OutsideAirTemperature(bytes);
            case TargetLocationLatitude:
                return new TargetLocationLatitude(bytes);
            case TargetLocationLongitude:
                return new TargetLocationLongitude(bytes);
            case TargetLocationElevation:
                return new TargetLocationElevation(bytes);
            case TargetTrackGateWidth:
                return new TargetTrackGateWidth(bytes);
            case TargetTrackGateHeight:
                return new TargetTrackGateHeight(bytes);
            case TargetErrorCe90:
                return new TargetErrorEstimateCe90(bytes);
            case TargetErrorLe90:
                return new TargetErrorEstimateLe90(bytes);
            case GenericFlagData01:
                // TODO
                return new OpaqueValue(bytes);
            case SecurityLocalMetadataSet:
                return new NestedSecurityMetadata(bytes);
            case DifferentialPressure:
                return new DifferentialPressure(bytes);
            case PlatformAngleOfAttack:
                return new PlatformAngleOfAttack(bytes);
            case PlatformVerticalSpeed:
                return new PlatformVerticalSpeed(bytes);
            case PlatformSideslipAngle:
                return new PlatformSideslipAngle(bytes);
            case AirfieldBarometricPressure:
                return new AirfieldBarometricPressure(bytes);
            case AirfieldElevation:
                return new AirfieldElevation(bytes);
            case RelativeHumidity:
                return new RelativeHumidity(bytes);
            case PlatformGroundSpeed:
                return new PlatformGroundSpeed(bytes);
            case GroundRange:
                return new GroundRange(bytes);
            case PlatformFuelRemaining:
                return new PlatformFuelRemaining(bytes);
            case PlatformCallSign:
                return new UasDatalinkString(UasDatalinkString.PLATFORM_CALL_SIGN, bytes);
            case WeaponLoad:
                // TODO
                return new OpaqueValue(bytes);
            case WeaponFired:
                // TODO
                return new OpaqueValue(bytes);
            case LaserPrfCode:
                return new LaserPrfCode(bytes);
            case SensorFovName:
                return new SensorFieldOfViewName(bytes);
            case PlatformMagneticHeading:
                return new PlatformMagneticHeading(bytes);
            case UasLdsVersionNumber:
                return new ST0601Version(bytes);
            case TargetLocationCovariance:
                // Deprecated
                return new OpaqueValue(bytes);
            case AlternatePlatformLatitude:
                return new AlternatePlatformLatitude(bytes);
            case AlternatePlatformLongitude:
                return new AlternatePlatformLongitude(bytes);
            case AlternatePlatformAltitude:
                return new AlternatePlatformAltitude(bytes);
            case AlternatePlatformName:
                return new UasDatalinkString(UasDatalinkString.ALTERNATE_PLATFORM_NAME, bytes);
            case AlternatePlatformHeading:
                return new AlternatePlatformHeading(bytes);
            case EventStartTimeUtc:
                // TODO
                return new OpaqueValue(bytes);
            case RvtLocalDataSet:
                // TODO Implement ST 0806
                return new OpaqueValue(bytes);
            case VmtiLocalDataSet:
                // TODO Implement ST 0903
                return new OpaqueValue(bytes);
            case SensorEllipsoidHeight:
                return new SensorEllipsoidHeight(bytes);
            case AlternatePlatformEllipsoidHeight:
                return new AlternatePlatformEllipsoidHeight(bytes);
            case OperationalMode:
                return new OperationalMode(bytes);
            case FrameCenterHae:
                return new FrameCenterHae(bytes);
            case SensorNorthVelocity:
                return new SensorNorthVelocity(bytes);
            case SensorEastVelocity:
                return new SensorEastVelocity(bytes);
            case ImageHorizonPixelPack:
                // TODO
                return new OpaqueValue(bytes);
            case CornerLatPt1:
                return new FullCornerLatitude(bytes, FullCornerLatitude.CORNER_LAT_1);
            case CornerLonPt1:
                return new FullCornerLongitude(bytes, FullCornerLongitude.CORNER_LON_1);
            case CornerLatPt2:
                return new FullCornerLatitude(bytes, FullCornerLatitude.CORNER_LAT_2);
            case CornerLonPt2:
                return new FullCornerLongitude(bytes, FullCornerLongitude.CORNER_LON_2);
            case CornerLatPt3:
                return new FullCornerLatitude(bytes, FullCornerLatitude.CORNER_LAT_3);
            case CornerLonPt3:
                return new FullCornerLongitude(bytes, FullCornerLongitude.CORNER_LON_3);
            case CornerLatPt4:
                return new FullCornerLatitude(bytes, FullCornerLatitude.CORNER_LAT_4);
            case CornerLonPt4:
                return new FullCornerLongitude(bytes, FullCornerLongitude.CORNER_LON_4);
            case PlatformPitchAngleFull:
                return new PlatformPitchAngleFull(bytes);
            case PlatformRollAngleFull:
                return new PlatformRollAngleFull(bytes);
            case PlatformAngleOfAttackFull:
                return new PlatformAngleOfAttackFull(bytes);
            case PlatformSideSlipAngle:
                return new PlatformSideslipAngleFull(bytes);
            case MiisCoreIdentifier:
                return new MiisCoreIdentifier(bytes);
            case SarMotionImageryMetadata:
                // TODO Implement ST 1206
                return new OpaqueValue(bytes);
            case TargetWidthExtended:
                return new TargetWidthExtended(bytes);
            case RangeImage:
                // TODO Implement ST 1002
                return new OpaqueValue(bytes);
            case Georegistration:
                // TODO Implement ST 1601
                return new OpaqueValue(bytes);
            case CompositeImaging:
                // TODO Implement ST 1602
                return new OpaqueValue(bytes);
            case Segment:
                // TODO Implement ST 1607
                return new OpaqueValue(bytes);
            case Amend:
                // TODO Implement ST 1607
                return new OpaqueValue(bytes);
            case SdccFlp:
                // TODO Implement ST 1010
                return new OpaqueValue(bytes);
            case DensityAltitudeExtended:
                return new DensityAltitudeExtended(bytes);
            case SensorEllipsoidHeightExtended:
                return new SensorEllipsoidHeightExtended(bytes);
            case AlternatePlatformEllipsoidHeightExtended:
                return new AlternatePlatformEllipsoidHeightExtended(bytes);
            case StreamDesignator:
                return new UasDatalinkString(UasDatalinkString.STREAM_DESIGNATOR, bytes);
            case OperationalBase:
                return new UasDatalinkString(UasDatalinkString.OPERATIONAL_BASE, bytes);
            case BroadcastSource:
                return new UasDatalinkString(UasDatalinkString.BROADCAST_SOURCE, bytes);
            case RangeToRecoveryLocation:
                return new RangeToRecoveryLocation(bytes);
            case TimeAirborne:
                return new TimeAirborne(bytes);
            case PropulsionUnitSpeed:
                return new PropulsionUnitSpeed(bytes);
            case PlatformCourseAngle:
                return new PlatformCourseAngle(bytes);
            case AltitudeAgl:
                return new AltitudeAGL(bytes);
            case RadarAltimeter:
                return new RadarAltimeter(bytes);
            case ControlCommand:
                // TODO
                return new OpaqueValue(bytes);
            case ControlCommandVerification:
                // TODO
                return new OpaqueValue(bytes);
            case SensorAzimuthRate:
                return new SensorAzimuthRate(bytes);
            case SensorElevationRate:
                return new SensorElevationRate(bytes);
            case SensorRollRate:
                return new SensorRollRate(bytes);
            case OnBoardMiStoragePercentFull:
                return new OnBoardMiStoragePercentFull(bytes);
            case ActiveWavelengthList:
                // TODO
                return new OpaqueValue(bytes);
            case CountryCodes:
                // TODO
                return new OpaqueValue(bytes);
            case NumberNavsatsInView:
                return new NavsatsInView(bytes);
            case PositioningMethodSource:
                return new PositioningMethodSource(bytes);
            case PlatformStatus:
                return new PlatformStatus(bytes);
            case SensorControlMode:
                return new SensorControlMode(bytes);
            case SensorFrameRatePack:
                // TODO
                return new OpaqueValue(bytes);
            case WavelengthsList:
                // TODO
                return new OpaqueValue(bytes);
            case TargetId:
                return new UasDatalinkString(UasDatalinkString.TARGET_ID, bytes);
            case AirbaseLocations:
                // TODO
                return new OpaqueValue(bytes);
            case TakeOffTime:
                // TODO
                return new OpaqueValue(bytes);
            case TransmissionFrequency:
                return new TransmissionFrequency(bytes);
            case OnBoardMiStorageCapacity:
                return new OnBoardMiStorageCapacity(bytes);
            case ZoomPercentage:
                return new ZoomPercentage(bytes);
            case CommunicationsMethod:
                return new UasDatalinkString(UasDatalinkString.COMMUNICATIONS_METHOD, bytes);
            case LeapSeconds:
                // TODO
                return new OpaqueValue(bytes);
            case CorrectionOffset:
                // TODO
                return new OpaqueValue(bytes);
            case PayloadList:
                // TODO
                return new OpaqueValue(bytes);
            case ActivePayloads:
                // TODO
                return new OpaqueValue(bytes);
            case WeaponsStores:
                // TODO
                return new OpaqueValue(bytes);
            case WaypointList:
                // TODO
                return new OpaqueValue(bytes);
        }

        throw new IllegalArgumentException("Unrecognized tag: " + tag);
    }
}

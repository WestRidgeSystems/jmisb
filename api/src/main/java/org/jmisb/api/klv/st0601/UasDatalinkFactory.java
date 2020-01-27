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
                return new CornerOffset(bytes);
            case OffsetCornerLongitudePoint1:
                return new CornerOffset(bytes);
            case OffsetCornerLatitudePoint2:
                return new CornerOffset(bytes);
            case OffsetCornerLongitudePoint2:
                return new CornerOffset(bytes);
            case OffsetCornerLatitudePoint3:
                return new CornerOffset(bytes);
            case OffsetCornerLongitudePoint3:
                return new CornerOffset(bytes);
            case OffsetCornerLatitudePoint4:
                return new CornerOffset(bytes);
            case OffsetCornerLongitudePoint4:
                return new CornerOffset(bytes);
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
                // TODO
                return new OpaqueValue(bytes);
            case TargetLocationLatitude:
                return new TargetLocationLatitude(bytes);
            case TargetLocationLongitude:
                return new TargetLocationLongitude(bytes);
            case TargetLocationElevation:
                return new TargetLocationElevation(bytes);
            case TargetTrackGateWidth:
                // TODO
                return new OpaqueValue(bytes);
            case TargetTrackGateHeight:
                // TODO
                return new OpaqueValue(bytes);
            case TargetErrorCe90:
                // TODO
                return new OpaqueValue(bytes);
            case TargetErrorLe90:
                // TODO
                return new OpaqueValue(bytes);
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
                // TODO
                return new OpaqueValue(bytes);
            case PlatformSideslipAngle:
                return new PlatformSideslipAngle(bytes);
            case AirfieldBarometricPressure:
                return new AirfieldBarometricPressure(bytes);
            case AirfieldElevation:
                // TODO
                return new OpaqueValue(bytes);
            case RelativeHumidity:
                // TODO
                return new OpaqueValue(bytes);
            case PlatformGroundSpeed:
                return new PlatformGroundSpeed(bytes);
            case GroundRange:
                // TODO
                return new OpaqueValue(bytes);
            case PlatformFuelRemaining:
                // TODO
                return new OpaqueValue(bytes);
            case PlatformCallSign:
                return new UasDatalinkString(UasDatalinkString.PLATFORM_CALL_SIGN, bytes);
            case WeaponLoad:
                // TODO
                return new OpaqueValue(bytes);
            case WeaponFired:
                // TODO
                return new OpaqueValue(bytes);
            case LaserPrfCode:
                // TODO
                return new OpaqueValue(bytes);
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
                // TODO
                return new OpaqueValue(bytes);
            case AlternatePlatformEllipsoidHeight:
                // TODO
                return new OpaqueValue(bytes);
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
                return new FullCornerLatitude(bytes);
            case CornerLonPt1:
                return new FullCornerLongitude(bytes);
            case CornerLatPt2:
                return new FullCornerLatitude(bytes);
            case CornerLonPt2:
                return new FullCornerLongitude(bytes);
            case CornerLatPt3:
                return new FullCornerLatitude(bytes);
            case CornerLonPt3:
                return new FullCornerLongitude(bytes);
            case CornerLatPt4:
                return new FullCornerLatitude(bytes);
            case CornerLonPt4:
                return new FullCornerLongitude(bytes);
            case PlatformPitchAngleFull:
                return new PlatformPitchAngleFull(bytes);
            case PlatformRollAngleFull:
                return new PlatformRollAngleFull(bytes);
            case PlatformAngleOfAttackFull:
                return new PlatformAngleOfAttackFull(bytes);
            case PlatformSideSlipAngle:
                return new PlatformSideslipAngleFull(bytes);
            case MiisCoreIdentifier:
                // TODO Implement ST 1204
                return new OpaqueValue(bytes);
            case SarMotionImageryMetadata:
                // TODO Implement ST 1206
                return new OpaqueValue(bytes);
            case TargetWidthExtended:
                // TODO IMAPB - does this mean ST1201?
                return new OpaqueValue(bytes);
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
                return new OpaqueValue(bytes);
            case SensorEllipsoidHeightExtended:
                return new OpaqueValue(bytes);
            case AlternatePlatformEllipsoidHeightExtended:
                return new OpaqueValue(bytes);
            case StreamDesignator:
                return new UasDatalinkString(UasDatalinkString.STREAM_DESIGNATOR, bytes);
            case OperationalBase:
                return new UasDatalinkString(UasDatalinkString.OPERATIONAL_BASE, bytes);
            case BroadcastSource:
                return new UasDatalinkString(UasDatalinkString.BROADCAST_SOURCE, bytes);
            case RangeToRecoveryLocation:
                // TODO
                return new OpaqueValue(bytes);
            case TimeAirborne:
                // TODO
                return new OpaqueValue(bytes);
            case PropulsionUnitSpeed:
                // TODO
                return new OpaqueValue(bytes);
            case PlatformCourseAngle:
                // TODO
                return new OpaqueValue(bytes);
            case AltitudeAgl:
                // TODO
                return new OpaqueValue(bytes);
            case RadarAltimeter:
                // TODO
                return new OpaqueValue(bytes);
            case ControlCommand:
                // TODO
                return new OpaqueValue(bytes);
            case ControlCommandVerification:
                // TODO
                return new OpaqueValue(bytes);
            case SensorAzimuthRate:
                // TODO
                return new OpaqueValue(bytes);
            case SensorElevationRate:
                // TODO
                return new OpaqueValue(bytes);
            case SensorRollRate:
                // TODO
                return new OpaqueValue(bytes);
            case OnBoardMiStoragePercentFull:
                // TODO
                return new OpaqueValue(bytes);
            case ActiveWavelengthList:
                // TODO
                return new OpaqueValue(bytes);
            case CountryCodes:
                // TODO
                return new OpaqueValue(bytes);
            case NumberNavsatsInView:
                // TODO
                return new OpaqueValue(bytes);
            case PositioningMethodSource:
                // TODO
                return new OpaqueValue(bytes);
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
                // TODO
                return new OpaqueValue(bytes);
            case OnBoardMiStorageCapacity:
                return new OnBoardMiStorageCapacity(bytes);
            case ZoomPercentage:
                // TODO
                return new OpaqueValue(bytes);
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

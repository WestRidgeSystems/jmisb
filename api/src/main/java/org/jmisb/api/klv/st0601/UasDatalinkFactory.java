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
            case PlatformTailNumber:
                return new UasDatalinkString(bytes);
            case PlatformHeadingAngle:
                return new PlatformHeadingAngle(bytes);
            case PlatformPitchAngle:
                return new PlatformPitchAngle(bytes);
            case PlatformRollAngle:
                return new PlatformRollAngle(bytes);
            case PlatformTrueAirspeed:
                // TODO
                return new OpaqueValue(bytes);
            case PlatformIndicatedAirspeed:
                // TODO
                return new OpaqueValue(bytes);
            case PlatformDesignation:
                return new UasDatalinkString(bytes);
            case ImageSourceSensor:
                return new UasDatalinkString(bytes);
            case ImageCoordinateSystem:
                return new UasDatalinkString(bytes);
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
            case OffsetCornerLongitudePoint1:
            case OffsetCornerLatitudePoint2:
            case OffsetCornerLongitudePoint2:
            case OffsetCornerLatitudePoint3:
            case OffsetCornerLongitudePoint3:
            case OffsetCornerLatitudePoint4:
            case OffsetCornerLongitudePoint4:
                return new CornerOffset(bytes);
            case IcingDetected:
                // TODO
                return new OpaqueValue(bytes);
            case WindDirection:
                // TODO
                return new OpaqueValue(bytes);
            case WindSpeed:
                // TODO
                return new OpaqueValue(bytes);
            case StaticPressure:
                // TODO
                return new OpaqueValue(bytes);
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
                // TODO
                return new OpaqueValue(bytes);
            case PlatformAngleOfAttack:
                // TODO
                return new OpaqueValue(bytes);
            case PlatformVerticalSpeed:
                // TODO
                return new OpaqueValue(bytes);
            case PlatformSideslipAngle:
                // TODO
                return new OpaqueValue(bytes);
            case AirfieldBarometricPressure:
                // TODO
                return new OpaqueValue(bytes);
            case AirfieldElevation:
                // TODO
                return new OpaqueValue(bytes);
            case RelativeHumidity:
                // TODO
                return new OpaqueValue(bytes);
            case PlatformGroundSpeed:
                // TODO
                return new OpaqueValue(bytes);
            case GroundRange:
                // TODO
                return new OpaqueValue(bytes);
            case PlatformFuelRemaining:
                // TODO
                return new OpaqueValue(bytes);
            case PlatformCallSign:
                return new UasDatalinkString(bytes);
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
                // TODO
                return new OpaqueValue(bytes);
            case PlatformMagneticHeading:
                // TODO
                return new OpaqueValue(bytes);
            case UasLdsVersionNumber:
                return new ST0601Version(bytes);
            case TargetLocationCovariance:
                // TODO ST0601.11 says "TBD"
                return new OpaqueValue(bytes);
            case AlternatePlatformLatitude:
                return new AlternatePlatformLatitude(bytes);
            case AlternatePlatformLongitude:
                return new AlternatePlatformLongitude(bytes);
            case AlternatePlatformAltitude:
                return new AlternatePlatformAltitude(bytes);
            case AlternatePlatformName:
                return new UasDatalinkString(bytes);
            case AlternatePlatformHeading:
                // TODO
                return new OpaqueValue(bytes);
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
                // TODO
                return new OpaqueValue(bytes);
            case FrameCenterHae:
                return new FrameCenterHae(bytes);
            case SensorNorthVelocity:
                // TODO
                return new OpaqueValue(bytes);
            case SensorEastVelocity:
                // TODO
                return new OpaqueValue(bytes);
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
                // TODO
                return new OpaqueValue(bytes);
            case PlatformRollAngleFull:
                // TODO
                return new OpaqueValue(bytes);
            case PlatformAngleOfAttackFull:
                // TODO
                return new OpaqueValue(bytes);
            case PlatformSideSlipAngle:
                // TODO
                return new OpaqueValue(bytes);
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
            case Segment:
                // TODO Implement ST 1607
                return new OpaqueValue(bytes);
            case Amend:
                // TODO Implement ST 1607
                return new OpaqueValue(bytes);
            case SdccFlp:
                // TODO Implement ST 1010
                return new OpaqueValue(bytes);
        }

        throw new IllegalArgumentException("Unrecognized tag: " + tag);
    }
}

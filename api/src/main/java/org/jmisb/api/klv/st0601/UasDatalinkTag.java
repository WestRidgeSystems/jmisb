package org.jmisb.api.klv.st0601;

import java.util.HashMap;
import java.util.Map;

/**
 * ST 0601 tag
 */
public enum UasDatalinkTag
{
    Undefined(0),
    Checksum(1),
    PrecisionTimeStamp(2),
    MissionId(3),
    PlatformTailNumber(4),
    PlatformHeadingAngle(5),
    PlatformPitchAngle(6),
    PlatformRollAngle(7),
    PlatformTrueAirspeed(8),
    PlatformIndicatedAirspeed(9),
    PlatformDesignation(10),
    ImageSourceSensor(11),
    ImageCoordinateSystem(12),
    SensorLatitude(13),
    SensorLongitude(14),
    SensorTrueAltitude(15),
    SensorHorizontalFov(16),
    SensorVerticalFov(17),
    SensorRelativeAzimuthAngle(18),
    SensorRelativeElevationAngle(19),
    SensorRelativeRollAngle(20),
    SlantRange(21),
    TargetWidth(22),
    FrameCenterLatitude(23),
    FrameCenterLongitude(24),
    FrameCenterElevation(25),
    OffsetCornerLatitudePoint1(26),
    OffsetCornerLongitudePoint1(27),
    OffsetCornerLatitudePoint2(28),
    OffsetCornerLongitudePoint2(29),
    OffsetCornerLatitudePoint3(30),
    OffsetCornerLongitudePoint3(31),
    OffsetCornerLatitudePoint4(32),
    OffsetCornerLongitudePoint4(33),
    IcingDetected(34),
    WindDirection(35),
    WindSpeed(36),
    StaticPressure(37),
    DensityAltitude(38),
    OutsideAirTemp(39),
    TargetLocationLatitude(40),
    TargetLocationLongitude(41),
    TargetLocationElevation(42),
    TargetTrackGateWidth(43),
    TargetTrackGateHeight(44),
    TargetErrorCe90(45),
    TargetErrorLe90(46),
    GenericFlagData01(47),
    SecurityLocalMetadataSet(48),
    DifferentialPressure(49),
    PlatformAngleOfAttack(50),
    PlatformVerticalSpeed(51),
    PlatformSideslipAngle(52),
    AirfieldBarometricPressure(53),
    AirfieldElevation(54),
    RelativeHumidity(55),
    PlatformGroundSpeed(56),
    GroundRange(57),
    PlatformFuelRemaining(58),
    PlatformCallSign(59),
    WeaponLoad(60),
    WeaponFired(61),
    LaserPrfCode(62),
    SensorFovName(63),
    PlatformMagneticHeading(64),
    UasLdsVersionNumber(65),
    TargetLocationCovariance(66),
    AlternatePlatformLatitude(67),
    AlternatePlatformLongitude(68),
    AlternatePlatformAltitude(69),
    AlternatePlatformName(70),
    AlternatePlatformHeading(71),
    EventStartTimeUtc(72),
    RvtLocalDataSet(73),
    VmtiLocalDataSet(74),
    SensorEllipsoidHeight(75),
    AlternatePlatformEllipsoidHeight(76),
    OperationalMode(77),
    FrameCenterHae(78),
    SensorNorthVelocity(79),
    SensorEastVelocity(80),
    ImageHorizonPixelPack(81),
    CornerLatPt1(82),
    CornerLonPt1(83),
    CornerLatPt2(84),
    CornerLonPt2(85),
    CornerLatPt3(86),
    CornerLonPt3(87),
    CornerLatPt4(88),
    CornerLonPt4(89),
    PlatformPitchAngleFull(90),
    PlatformRollAngleFull(91),
    PlatformAngleOfAttackFull(92),
    PlatformSideSlipAngle(93),
    MiisCoreIdentifier(94),
    SarMotionImageryMetadata(95),
    TargetWidthExtended(96),
    RangeImage(97),
    Georegistration(98),
    Segment(99),
    Amend(100),
    SdccFlp(101);

    private int code;

    private static final Map<Integer, UasDatalinkTag> lookupTable = new HashMap<>();

    static
    {
        for (UasDatalinkTag tag : values())
        {
            lookupTable.put(tag.code, tag);
        }
    }

    private UasDatalinkTag(int c)
    {
        code = c;
    }

    public int getCode()
    {
        return code;
    }

    public static UasDatalinkTag getTag(int tagCode)
    {
        return lookupTable.containsKey(tagCode) ? lookupTable.get(tagCode) : Undefined;
    }
}

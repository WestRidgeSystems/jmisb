package org.jmisb.api.klv.st0601;

import java.util.HashMap;
import java.util.Map;

/**
 * ST 0601 tag
 */
public enum UasDatalinkTag
{
    Undefined(0),
    /** Tag 1; Checksum used to detect errors within a UAS Datalink LS packet */
    Checksum(1),
    /** Tag 2; Timestamp for all metadata in this Local Set; used to coordinate with Motion Imagery; Value is a {@link PrecisionTimeStamp} */
    PrecisionTimeStamp(2),
    /** Tag 3; Descriptive mission identifier to distinguish event or sortie; Value is a {@link UasDatalinkString} */
    MissionId(3),
    /** Tag 4; Identifier of platform as posted; Value is a {@link UasDatalinkString} */
    PlatformTailNumber(4),
    /** Tag 5; Platform heading angle; Value is a {@link PlatformHeadingAngle} */
    PlatformHeadingAngle(5),
    /** Tag 6; Platform pitch angle; Value is a {@link PlatformPitchAngle} */
    PlatformPitchAngle(6),
    /** Tag 7; Platform roll angle; Value is a {@link PlatformRollAngle} */
    PlatformRollAngle(7),
    /** Tag 8; True airspeed (TAS) of platform; Value is a {@link PlatformTrueAirspeed} */
    PlatformTrueAirspeed(8),
    /** Tag 9; Indicated airspeed (IAS) of platform; Value is a {@link PlatformIndicatedAirspeed} */
    PlatformIndicatedAirspeed(9),
    /** Tag 10; Model name for the platform; Value is a {@link UasDatalinkString} */
    PlatformDesignation(10),
    /** Tag 11; Name of currently active sensor; Value is a {@link UasDatalinkString} */
    ImageSourceSensor(11),
    /** Tag 12; Name of the image coordinate system used; Value is a {@link UasDatalinkString} */
    ImageCoordinateSystem(12),
    /** Tag 13; Sensor latitude; Value is a {@link SensorLatitude} */
    SensorLatitude(13),
    /** Tag 14; Sensor longitude; Value is a {@link SensorLongitude} */
    SensorLongitude(14),
    /** Tag 15; Altitude of sensor as measured from Mean Sea Level (MSL); Value is a {@link SensorTrueAltitude} */
    SensorTrueAltitude(15),
    /** Tag 16; Horizontal field of view of selected imaging sensor; Value is a {@link HorizontalFov} */
    SensorHorizontalFov(16),
    /** Tag 17; Vertical field of view of selected imaging sensor; Value is a {@link VerticalFov} */
    SensorVerticalFov(17),
    /** Tag 18; Relative rotation angle of sensor to platform longitudinal axis; Value is a {@link SensorRelativeAzimuth} */
    SensorRelativeAzimuthAngle(18),
    /** Tag 19; Relative elevation angle of sensor to platform longitudinal-transverse plane; Value is a {@link SensorRelativeElevation} */
    SensorRelativeElevationAngle(19),
    /** Tag 20; Relative roll angle of sensor to aircraft platform; Value is a {@link SensorRelativeRoll} */
    SensorRelativeRollAngle(20),
    /** Tag 21; Slant range in meters; Value is a {@link SlantRange} */
    SlantRange(21),
    /** Tag 22; Target width within sensor field of view; Value is a {@link TargetWidth} */
    TargetWidth(22),
    /** Tag 23; Terrain latitude of frame center; Value is a {@link FrameCenterLatitude} */
    FrameCenterLatitude(23),
    /** Tag 24; Terrain longitude of frame center; Value is a {@link FrameCenterLongitude} */
    FrameCenterLongitude(24),
    /** Tag 25; Terrain elevation at frame center relative to Mean Sea Level (MSL); Value is a {@link FrameCenterElevation} */
    FrameCenterElevation(25),
    /** Tag 26; Frame latitude offset for upper left corner; Value is a {@link CornerOffset} */
    OffsetCornerLatitudePoint1(26),
    /** Tag 27; Frame longitude offset for upper left corner; Value is a {@link CornerOffset} */
    OffsetCornerLongitudePoint1(27),
    /** Tag 28; Frame latitude offset for upper right corner; Value is a {@link CornerOffset} */
    OffsetCornerLatitudePoint2(28),
    /** Tag 29; Frame longitude offset for upper right corner; Value is a {@link CornerOffset} */
    OffsetCornerLongitudePoint2(29),
    /** Tag 30; Frame latitude offset for lower right corner; Value is a {@link CornerOffset} */
    OffsetCornerLatitudePoint3(30),
    /** Tag 31; Frame longitude offset for lower right corner; Value is a {@link CornerOffset} */
    OffsetCornerLongitudePoint3(31),
    /** Tag 32; Frame latitude offset for lower left corner; Value is a {@link CornerOffset} */
    OffsetCornerLatitudePoint4(32),
    /** Tag 33; Frame longitude offset for lower left corner; Value is a {@link CornerOffset} */
    OffsetCornerLongitudePoint4(33),
    /** Tag 34; Flag for icing detected at aircraft location; Value is a {@link IcingDetected} */
    IcingDetected(34),
    /** Tag 35; Wind direction at aircraft location; Value is a {@link WindDirectionAngle} */
    WindDirection(35),
    /** Tag 36; Wind speed at aircraft location; Value is a {@link WindSpeed} */
    WindSpeed(36),
    /** Tag 37; Static pressure at aircraft location; Value is a {@link StaticPressure} */
    StaticPressure(37),
    /** Tag 38; Density altitude at aircraft location; Value is a {@link DensityAltitude} */
    DensityAltitude(38),
    /** Tag 39; Temperature outside of aircraft; Value is a {@link OutsideAirTemperature} */
    OutsideAirTemp(39),
    /** Tag 40; Calculated target latitude; Value is a {@link TargetLocationLatitude} */
    TargetLocationLatitude(40),
    /** Tag 41; Calculated target longitude; Value is a {@link TargetLocationLongitude} */
    TargetLocationLongitude(41),
    /** Tag 42; Calculated target elevation; Value is a {@link TargetLocationElevation} */
    TargetLocationElevation(42),
    /** Tag 43; Tracking gate width (x value) of tracked target within field of view; Value is a {@link TargetTrackGateSize} */
    TargetTrackGateWidth(43),
    /** Tag 44; Tracking gate height (y value) of tracked target within field of view; Value is a {@link TargetTrackGateSize} */
    TargetTrackGateHeight(44),
    /** Tag 45; Circular error 90 (CE90) is the estimated error distance in the horizontal direction; Value is a {@link TargetErrorEstimateCe90} */
    TargetErrorCe90(45),
    /** Tag 46; Lateral error 90 (LE90) is the estimated error distance in the vertical (or lateral) direction; Value is a {@link TargetErrorEstimateLe90} */
    TargetErrorLe90(46),
    /** Tag 47; Generic metadata flags; Value is a {@link OpaqueValue} */
    GenericFlagData01(47),
    /** Tag 48; MISB ST 0102 local let Security Metadata items; Value is a {@link NestedSecurityMetadata} */
    SecurityLocalMetadataSet(48),
    /** Tag 49; Differential pressure at aircraft location; Value is a {@link DifferentialPressure} */
    DifferentialPressure(49),
    /** Tag 50; Platform attack angle; Value is a {@link PlatformAngleOfAttack} */
    PlatformAngleOfAttack(50),
    /** Tag 51; Vertical speed of the aircraft relative to zenith; Value is a {@link PlatformVerticalSpeed} */
    PlatformVerticalSpeed(51),
    /** Tag 52; Angle between the platform longitudinal axis and relative wind; Value is a {@link PlatformSideslipAngle} */
    PlatformSideslipAngle(52),
    /** Tag 53; Local pressure at airfield of known height; Value is a {@link AirfieldBarometricPressure} */
    AirfieldBarometricPressure(53),
    /** Tag 54; Elevation of airfield corresponding to Airfield Barometric Pressure; Value is a {@link AirfieldElevation} */
    AirfieldElevation(54),
    /** Tag 55; Relative humidity at aircraft location; Value is a {@link RelativeHumidity} */
    RelativeHumidity(55),
    /** Tag 56; Speed projected to the ground of an airborne platform passing overhead; Value is a {@link PlatformGroundSpeed} */
    PlatformGroundSpeed(56),
    /** Tag 57; Horizontal distance from ground position of aircraft relative to nadir, and target of interest; Value is a {@link GroundRange} */
    GroundRange(57),
    /** Tag 58; Remaining fuel on airborne platform; Value is a {@link PlatformFuelRemaining} */
    PlatformFuelRemaining(58),
    /** Tag 59; Call sign of platform or operating unit; Value is a {@link UasDatalinkString} */
    PlatformCallSign(59),
    /** Tag 60; Current weapons stored on aircraft; Value is a {@link OpaqueValue} */
    WeaponLoad(60),
    /** Tag 61; Indication when a particular weapon is released; Value is a {@link OpaqueValue} */
    WeaponFired(61),
    /** Tag 62; A laser's Pulse Repetition Frequency (PRF) code used to mark a target; Value is a {@link LaserPrfCode} */
    LaserPrfCode(62),
    /** Tag 63; Sensor field of view names; Value is a {@link SensorFieldOfViewName} */
    SensorFovName(63),
    /** Tag 64; Aircraft magnetic heading angle; Value is a {@link PlatformMagneticHeading} */
    PlatformMagneticHeading(64),
    /** Tag 65; Version number of the UAS Datalink LS document used to generate KLV metadata; Value is a {@link ST0601Version} */
    UasLdsVersionNumber(65),
    /** Tag 66; Covariance Matrix of the error associated with a targeted location (DEPRECATED) Value is a {@link OpaqueValue} */
    TargetLocationCovariance(66),
    /** Tag 67; Alternate platform latitude; Value is an {@link AlternatePlatformLatitude} */
    AlternatePlatformLatitude(67),
    /** Tag 68; Alternate platform longitude; Value is an {@link AlternatePlatformLongitude} */
    AlternatePlatformLongitude(68),
    /** Tag 69; Altitude of alternate platform as measured from Mean Sea Level (MSL); Value is an {@link AlternatePlatformAltitude} */
    AlternatePlatformAltitude(69),
    /** Tag 70; Name of alternate platform connected to UAS; Value is a {@link UasDatalinkString} */
    AlternatePlatformName(70),
    /** Tag 71; Heading angle of alternate platform connected to UAS; Value is an {@link AlternatePlatformHeading} */
    AlternatePlatformHeading(71),
    /** Tag 72; Start time of scene, project, event, mission, editing event, license, publication, etc; Value is a {@link OpaqueValue} */
    EventStartTimeUtc(72),
    /** Tag 73; MISB ST 0806 RVT Local Set metadata items; Value is a {@link OpaqueValue} */
    RvtLocalDataSet(73),
    /** Tag 74; MISB ST 0903 VMTI Local Set metadata items; Value is a {@link OpaqueValue} */
    VmtiLocalDataSet(74),
    /** Tag 75; Sensor ellipsoid height as measured from the reference WGS84 ellipsoid; Value is a {@link SensorEllipsoidHeight} */
    SensorEllipsoidHeight(75),
    /** Tag 76; Alternate platform ellipsoid height as measured from the reference WGS84 Ellipsoid; Value is a {@link AlternatePlatformEllipsoidHeight} */
    AlternatePlatformEllipsoidHeight(76),
    /** Tag 77; Indicates the mode of operations of the event portrayed in Motion Imagery; Value is a {@link OperationalMode} */
    OperationalMode(77),
    /** Tag 78; Frame center ellipsoid height as measured from the reference WGS84 ellipsoid; Value is a {@link FrameCenterHae} */
    FrameCenterHae(78),
    /** Tag 79; Northing velocity of the sensor or platform; Value is a {@link SensorNorthVelocity} */
    SensorNorthVelocity(79),
    /** Tag 80; Easting velocity of the sensor or platform; Value is a {@link SensorEastVelocity} */
    SensorEastVelocity(80),
    /** Tag 81; Location of earth-sky horizon in the Imagery; Value is a {@link OpaqueValue} */
    ImageHorizonPixelPack(81),
    /** Tag 82; Frame latitude for upper left corner; Value is a {@link FullCornerLatitude} */
    CornerLatPt1(82),
    /** Tag 83; Frame latitude for upper left corner; Value is a {@link FullCornerLongitude} */
    CornerLonPt1(83),
    /** Tag 84; Frame latitude for upper right corner; Value is a {@link FullCornerLatitude} */
    CornerLatPt2(84),
    /** Tag 85; Frame longitude for upper right corner; Value is a {@link FullCornerLongitude} */
    CornerLonPt2(85),
    /** Tag 86; Frame latitude for lower right corner; Value is a {@link FullCornerLatitude} */
    CornerLatPt3(86),
    /** Tag 87; Frame longitude for lower right corner; Value is a {@link FullCornerLongitude} */
    CornerLonPt3(87),
    /** Tag 88; Frame latitude for lower left corner; Value is a {@link FullCornerLatitude} */
    CornerLatPt4(88),
    /** Tag 89; Frame longitude for lower left corner; Value is a {@link FullCornerLongitude} */
    CornerLonPt4(89),
    /** Tag 90; Aircraft pitch angle; Value is a {@link PlatformPitchAngleFull} */
    PlatformPitchAngleFull(90),
    /** Tag 91; Platform roll angle; Value is a {@link PlatformRollAngleFull} */
    PlatformRollAngleFull(91),
    /** Tag 92; Platform attack angle; Value is a {@link PlatformAngleOfAttackFull} */
    PlatformAngleOfAttackFull(92),
    /** Tag 93; Angle between the platform longitudinal axis and relative wind; Value is a {@link PlatformSideslipAngleFull} */
    PlatformSideSlipAngle(93),
    /** Tag 94; MISB ST 1204 MIIS Core Identifier binary value; Value is a {@link MiisCoreIdentifier} */
    MiisCoreIdentifier(94),
    /** Tag 95; MISB ST 1206 SAR Motion Imagery Metadata Local Set metadata items; Value is a {@link OpaqueValue} */
    SarMotionImageryMetadata(95),
    /** Tag 96; Target width within sensor field of view; Value is a {@link TargetWidthExtended} */
    TargetWidthExtended(96),
    /** Tag 97; MISB ST 1002 Range Imaging Local Set metadata items; Value is a {@link OpaqueValue} */
    RangeImage(97),
    /** Tag 98; MISB ST 1601 Geo-Registration Local Set metadata items; Value is a {@link OpaqueValue} */
    Georegistration(98),
    /** Tag 99; MISB ST 1602 Composite Imaging Local Set metadata items; Value is a {@link OpaqueValue} */
    CompositeImaging(99),
    /** Tag 100; MISB ST 1607 Segment Local Set metadata items, used to enable metadata sharing; Value is a {@link OpaqueValue} */
    Segment(100),
    /** Tag 101; MISB ST 1607 Amend Local Set metadata items, used to provide metadata corrections; Value is a {@link OpaqueValue} */
    Amend(101),
    /** Tag 102; MISB ST 1010 Floating Length Pack (FLP) metadata item, providing Standard Deviation and Cross Correlation (SDCC) metadata; Value is a {@link OpaqueValue} */
    SdccFlp(102),
    /** Tag 103; Density altitude above MSL at aircraft location; Value is a {@link DensityAltitudeExtended} */
    DensityAltitudeExtended(103),
    /** Tag 104; Sensor ellipsoid height extended as measured from the reference WGS84 ellipsoid; Value is a {@link SensorEllipsoidHeightExtended} */
    SensorEllipsoidHeightExtended(104),
    /** Tag 105; Alternate platform ellipsoid height extended as measured from the reference WGS84 ellipsoid; Value is a {@link AlternatePlatformEllipsoidHeightExtended} */
    AlternatePlatformEllipsoidHeightExtended(105),
    /** Tag 106; A second designation given to a sortie; Value is a {@link UasDatalinkString} */
    StreamDesignator(106),
    /** Tag 107; Name of the operational base hosting the platform; Value is a {@link UasDatalinkString} */
    OperationalBase(107),
    /** Tag 108; Name of the source, where the Motion Imagery is first broadcast; Value is a {@link UasDatalinkString} */
    BroadcastSource(108),
    /** Tag 109; Distance from current position to airframe recovery position; Value is a {@link RangeToRecoveryLocation} */
    RangeToRecoveryLocation(109),
    /** Tag 110; Number of seconds aircraft has been airborne; Value is a {@link TimeAirborne} */
    TimeAirborne(110),
    /** Tag 111; The speed the engine (or electric motor) is rotating at; Value is a {@link PropulsionUnitSpeed} */
    PropulsionUnitSpeed(111),
    /** Tag 112; Direction the aircraft is moving relative to True North; Value is a {@link PlatformCourseAngle} */
    PlatformCourseAngle(112),
    /** Tag 113; Above Ground Level (AGL) height above the ground/water; Value is a {@link AltitudeAGL} */
    AltitudeAgl(113),
    /** Tag 114; Height above the ground/water as reported by a RADAR altimeter; Value is a {@link RadarAltimeter} */
    RadarAltimeter(114),
    /** Tag 115; Record of command from GCS to Aircraft; Value is a {@link OpaqueValue} */
    ControlCommand(115),
    /** Tag 116; Acknowledgement of one or more control commands were received by the platform; Value is a {@link OpaqueValue} */
    ControlCommandVerification(116),
    /** Tag 117; The rate the sensors azimuth angle is changing; Value is a {@link SensorAzimuthRate} */
    SensorAzimuthRate(117),
    /** Tag 118; The rate the sensors elevation angle is changing; Value is a {@link SensorElevationRate} */
    SensorElevationRate(118),
    /** Tag 119; The rate the sensors roll angle is changing; Value is a {@link SensorRollRate} */
    SensorRollRate(119),
    /** Tag 120; Amount of on-board Motion Imagery storage used as a percentage of the total storage; Value is a {@link OnBoardMiStoragePercentFull} */
    OnBoardMiStoragePercentFull(120),
    /** Tag 121; List of wavelengths in Motion Imagery; Value is a {@link OpaqueValue} */
    ActiveWavelengthList(121),
    /** Tag 122; Country codes which are associated with the platform and its operation; Value is a {@link OpaqueValue} */
    CountryCodes(122),
    /** Tag 123; Count of navigation satellites in view of platform; Value is a {@link NavsatsInView} */
    NumberNavsatsInView(123),
    /** Tag 124; Source of the navigation positioning information. (e.g., NAVSAT-GPS, NAVSAT-Galileo, INS); Value is a {@link PositioningMethodSource} */
    PositioningMethodSource(124),
    /** Tag 125; Enumeration of operational modes of the platform (e.g., in-route, RTB); Value is a {@link PlatformStatus} */
    PlatformStatus(125),
    /** Tag 126; Enumerated value for the current sensor control operational status; Value is a {@link SensorControlMode} */
    SensorControlMode(126),
    /** Tag 127; Values used to compute the frame rate of the Motion Imagery at the sensor; Value is a {@link OpaqueValue} */
    SensorFrameRatePack(127),
    /** Tag 128; List of wavelength bands provided by sensor(s); Value is a {@link OpaqueValue} */
    WavelengthsList(128),
    /** Tag 129; Alpha-numeric identification of a target; Value is a {@link UasDatalinkString} */
    TargetId(129),
    /** Tag 130; Geographic location of the take-off site and recovery site; Value is a {@link OpaqueValue} */
    AirbaseLocations(130),
    /** Tag 131; Time when aircraft became airborne; Value is a {@link OpaqueValue} */
    TakeOffTime(131),
    /** Tag 132; Radio frequency used to transmit the Motion Imagery; Value is a {@link TransmissionFrequency} */
    TransmissionFrequency(132),
    /** Tag 133; The total capacity of on-board Motion Imagery storage; Value is a {@link OnBoardMiStorageCapacity} */
    OnBoardMiStorageCapacity(133),
    /** Tag 134; For a variable zoom system, the percentage of zoom; Value is a {@link ZoomPercentage} */
    ZoomPercentage(134),
    /** Tag 135; Type of communications used with platform; Value is a {@link UasDatalinkString} */
    CommunicationsMethod(135),
    /** Tag 136; Number of leap seconds to adjust Precision Time Stamp (Tag 2) to UTC; Value is a {@link OpaqueValue} */
    LeapSeconds(136),
    /** Tag 137; Post-flight time adjustment to correct Precision Time Stamp (Tag 2) as needed; Value is a {@link OpaqueValue} */
    CorrectionOffset(137),
    /** Tag 138; List of payloads available on the Platform; Value is a {@link OpaqueValue} */
    PayloadList(138),
    /** Tag 139; List of currently active payloads from the payload list (Tag 138); Value is a {@link OpaqueValue} */
    ActivePayloads(139),
    /** Tag 140; List of weapon stores and status; Value is a {@link OpaqueValue} */
    WeaponsStores(140),
    /** Tag 141; List of waypoints and their status; Value is a {@link OpaqueValue} */
    WaypointList(141);

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

package org.jmisb.examples.mimdgenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1204.CoreIdentifier;
import org.jmisb.api.klv.st1902.MimdId;
import org.jmisb.api.klv.st1902.MimdIdReference;
import org.jmisb.api.klv.st1903.MIMD;
import org.jmisb.api.klv.st1903.MIMD_Platforms;
import org.jmisb.api.klv.st1903.MIMD_SecurityOptions;
import org.jmisb.api.klv.st1903.MIMD_Timers;
import org.jmisb.api.klv.st1903.MIMD_Version;
import org.jmisb.api.klv.st1903.Security;
import org.jmisb.api.klv.st1903.Security_Classification;
import org.jmisb.api.klv.st1903.Security_ClassifyingMethod;
import org.jmisb.api.klv.st1903.TimeTransferMethod;
import org.jmisb.api.klv.st1903.Timer;
import org.jmisb.api.klv.st1903.Timer_NanoPrecisionTimestamp;
import org.jmisb.api.klv.st1903.Timer_UtcLeapSeconds;
import org.jmisb.api.klv.st1905.Platform;
import org.jmisb.api.klv.st1905.PlatformType;
import org.jmisb.api.klv.st1905.Platform_Identity;
import org.jmisb.api.klv.st1905.Platform_Name;
import org.jmisb.api.klv.st1905.Platform_Payloads;
import org.jmisb.api.klv.st1905.Platform_Stages;
import org.jmisb.api.klv.st1906.AbsEnu;
import org.jmisb.api.klv.st1906.AbsEnu_RotAboutEast;
import org.jmisb.api.klv.st1906.AbsEnu_RotAboutNorth;
import org.jmisb.api.klv.st1906.AbsEnu_RotAboutUp;
import org.jmisb.api.klv.st1906.AbsGeodetic;
import org.jmisb.api.klv.st1906.AbsGeodetic_Hae;
import org.jmisb.api.klv.st1906.AbsGeodetic_Lat;
import org.jmisb.api.klv.st1906.AbsGeodetic_Lon;
import org.jmisb.api.klv.st1906.Orientation;
import org.jmisb.api.klv.st1906.Position;
import org.jmisb.api.klv.st1906.Position_Country;
import org.jmisb.api.klv.st1906.Stage;
import org.jmisb.api.klv.st1907.Correspondence;
import org.jmisb.api.klv.st1907.CorrespondenceGroup;
import org.jmisb.api.klv.st1907.CorrespondenceGroupType;
import org.jmisb.api.klv.st1907.CorrespondenceGroup_Rectangle;
import org.jmisb.api.klv.st1907.Correspondence_Col;
import org.jmisb.api.klv.st1907.Correspondence_Row;
import org.jmisb.api.klv.st1907.DeviceError;
import org.jmisb.api.klv.st1907.DeviceStatus;
import org.jmisb.api.klv.st1907.DeviceWarning;
import org.jmisb.api.klv.st1907.GISensorType;
import org.jmisb.api.klv.st1907.GeoIntelligenceSensor;
import org.jmisb.api.klv.st1907.GeoIntelligenceSensor_CorrespondenceGroups;
import org.jmisb.api.klv.st1907.GeoIntelligenceSensor_Correspondences;
import org.jmisb.api.klv.st1907.GeoIntelligenceSensor_NCols;
import org.jmisb.api.klv.st1907.GeoIntelligenceSensor_NRows;
import org.jmisb.api.klv.st1907.GeoIntelligenceSensor_Name;
import org.jmisb.api.klv.st1907.LaserSensor;
import org.jmisb.api.klv.st1907.LaserSensor_Wavelength;
import org.jmisb.api.klv.st1907.MeasureMethod;
import org.jmisb.api.klv.st1907.Payload;
import org.jmisb.api.klv.st1907.Payload_GeoIntelligenceSensors;
import org.jmisb.api.klv.st1907.Range;
import org.jmisb.api.klv.st1907.Range_RangeDistance;
import org.jmisb.api.klv.st1907.SafetyState;
import org.jmisb.api.klv.st1908.FieldOfView;
import org.jmisb.api.klv.st1908.FieldOfView_Horizontal;
import org.jmisb.api.klv.st1908.FieldOfView_Vertical;
import org.jmisb.api.klv.st1908.ImagerSystem;
import org.jmisb.api.klv.st1908.ImagerSystem_Name;
import org.jmisb.api.klv.st1908.MIIS;
import org.jmisb.api.klv.st1908.MinorCoreId;
import org.jmisb.api.klv.st1908.MinorCoreId_Uuid;
import org.jmisb.api.video.CodecIdentifier;
import org.jmisb.api.video.IVideoFileOutput;
import org.jmisb.api.video.KlvFormat;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.api.video.VideoFileOutput;
import org.jmisb.api.video.VideoFrame;
import org.jmisb.api.video.VideoOutputOptions;
import org.jmisb.core.klv.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Generator {

    private final int width = 1280;
    private final int height = 960;
    private final int bitRate = 500_000;
    private final int gopSize = 30;
    private final double frameRate = 20.0;
    private final double frameDuration = 1.0 / frameRate;
    private final int duration = 90;
    private final String filename = "mimd.mpeg";

    private static final Logger LOG = LoggerFactory.getLogger(Generator.class);
    private final UUID coreIdentifierUUID;

    public Generator() {
        coreIdentifierUUID = UUID.randomUUID();
    }

    public void generate() throws KlvParseException {

        CoreIdentifier coreIdentifier = new CoreIdentifier();
        coreIdentifier.setMinorUUID(UUID.randomUUID());
        coreIdentifier.setVersion(1);

        VideoOutputOptions options =
                new VideoOutputOptions(
                        width,
                        height,
                        bitRate,
                        frameRate,
                        gopSize,
                        KlvFormat.Asynchronous,
                        CodecIdentifier.H264);
        try (IVideoFileOutput output = new VideoFileOutput(options)) {
            output.open(filename);

            // Write some frames
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            try {
                image = ImageIO.read(new File("test1280.jpg"));
            } catch (IOException e) {
                // TODO: log
            }

            final long numFrames = duration * Math.round(frameRate);
            long baseTime =
                    1000
                            * 1000
                            * System.currentTimeMillis(); // nanoseconds, or close enough for this.
            double pts = 0.0;
            for (long i = 0; i < numFrames; ++i) {
                MIMD message = new MIMD();
                message.setVersion(new MIMD_Version(1));
                message.setTimers(getTimers(baseTime + (long) (pts * 1.0e9)));
                message.setSecurityOptions(getSecurityOptions());
                message.setSecurity(new MimdIdReference(0, 1, "Security", "Security"));
                message.setCompositeProductSecurity(
                        new MimdIdReference(0, 1, "CompositeProductSecurity", "Security"));
                message.setCompositeMotionImagerySecurity(
                        new MimdIdReference(0, 1, "CompositeMotionImagerySecurity", "Security"));
                message.setCompositeMetadataSecurity(
                        new MimdIdReference(0, 1, "CompositeMetadataSecurity", "Security"));
                message.setPlatforms(this.getPlatforms());

                String path = String.format("MIMD_%04.02f.dat", pts);
                try (FileOutputStream stream = new FileOutputStream(path)) {
                    stream.write(message.getBytes());
                }
                output.addVideoFrame(new VideoFrame(image, pts));
                output.addMetadataFrame(new MetadataFrame(message, pts));
                pts += frameDuration;
            }

        } catch (IOException e) {
            LOG.error("Failed to write file", e);
        }
    }

    private MIMD_Timers getTimers(long nanos) throws KlvParseException {
        List<Timer> timerList = new ArrayList<>();
        timerList.add(this.getTimer(nanos));
        MIMD_Timers timers = new MIMD_Timers(timerList);
        return timers;
    }

    private Timer getTimer(long nanos) throws KlvParseException {
        Timer timer = new Timer();
        timer.setNanoPrecisionTimestamp(new Timer_NanoPrecisionTimestamp(nanos));
        timer.setUtcLeapSeconds(new Timer_UtcLeapSeconds(37));
        timer.setTimeTransferMethod(TimeTransferMethod.NTP_V3_3);
        return timer;
    }

    private MIMD_SecurityOptions getSecurityOptions() throws KlvParseException {
        List<Security> securityList = new ArrayList<>();
        securityList.add(this.getSecurityUnclas());
        securityList.add(this.getSecurityFOUO());
        MIMD_SecurityOptions securityOptions = new MIMD_SecurityOptions(securityList);
        return securityOptions;
    }

    private Security getSecurityUnclas() throws KlvParseException {
        Security security = new Security();
        security.setMimdId(new MimdId(0, 1));
        security.setClassifyingMethod(new Security_ClassifyingMethod("US-1"));
        security.setClassification(
                new Security_Classification("UNCLASSIFIED//REL TO USA, AUS, CAN, GBR"));

        return security;
    }

    private Security getSecurityFOUO() throws KlvParseException {
        Security security = new Security();
        security.setMimdId(new MimdId(1, 1));
        security.setClassifyingMethod(new Security_ClassifyingMethod("US-1"));
        security.setClassification(
                new Security_Classification(
                        "UNCLASSIFIED//FOR OFFICIAL USE ONLY//REL TO USA, AUS, CAN, GBR"));

        return security;
    }

    private MIMD_Platforms getPlatforms() throws KlvParseException {
        List<Platform> platformList = new ArrayList<>();
        platformList.add(this.getPlatform());
        MIMD_Platforms platforms = new MIMD_Platforms(platformList);
        return platforms;
    }

    private Platform getPlatform() throws KlvParseException {
        Platform platform = new Platform();
        platform.setName(new Platform_Name("Test System"));
        platform.setIdentity(new Platform_Identity("jMISB Test 1"));
        platform.setType(PlatformType.Pole);
        platform.setStages(getStages());
        platform.setPayloads(getPayloads());
        return platform;
    }

    private Platform_Stages getStages() throws KlvParseException {
        List<Stage> stageList = new ArrayList<>();
        stageList.add(this.getStage());
        Platform_Stages stages = new Platform_Stages(stageList);
        return stages;
    }

    private Stage getStage() throws KlvParseException {
        Stage stage = new Stage();
        stage.setPosition(getPosition());
        stage.setOrientation(this.getOrientation());
        return stage;
    }

    private Position getPosition() throws KlvParseException {
        Position position = new Position();
        position.setAbsGeodetic(getGeodeticPosition());
        position.setCountry(new Position_Country("ge:ISO1:3:VII-13:AUS"));
        return position;
    }

    private Orientation getOrientation() throws KlvParseException {
        Orientation orientation = new Orientation();
        orientation.setAbsEnu(this.getAbsEnu());
        return orientation;
    }

    private AbsEnu getAbsEnu() throws KlvParseException {
        AbsEnu absEnu = new AbsEnu();
        absEnu.setRotAboutEast(new AbsEnu_RotAboutEast(0.0));
        absEnu.setRotAboutNorth(new AbsEnu_RotAboutNorth(0.0));
        absEnu.setRotAboutUp(new AbsEnu_RotAboutUp(45.0 * Math.PI / 180.0));
        return absEnu;
    }

    private AbsGeodetic getGeodeticPosition() throws KlvParseException {
        AbsGeodetic geodeticPosition = new AbsGeodetic();
        geodeticPosition.setLat(new AbsGeodetic_Lat(-35.35349 * Math.PI / 180.0));
        geodeticPosition.setLon(new AbsGeodetic_Lon(149.08932 * Math.PI / 180.0));
        geodeticPosition.setHae(new AbsGeodetic_Hae(642.1));
        return geodeticPosition;
    }

    private Platform_Payloads getPayloads() throws KlvParseException {
        List<Payload> payloadList = new ArrayList<>();
        payloadList.add(this.getPayload());
        Platform_Payloads payloads = new Platform_Payloads(payloadList);
        return payloads;
    }

    private Payload getPayload() throws KlvParseException {
        Payload payload = new Payload();
        payload.setGeoIntelligenceSensors(getGeoIntelligenceSensors());
        return payload;
    }

    private Payload_GeoIntelligenceSensors getGeoIntelligenceSensors() throws KlvParseException {
        List<GeoIntelligenceSensor> sensorList = new ArrayList<>();
        sensorList.add(getGeoIntelligenceSensor());
        sensorList.add(getGeoIntelligenceLaserSensor());
        Payload_GeoIntelligenceSensors sensors = new Payload_GeoIntelligenceSensors(sensorList);
        return sensors;
    }

    private GeoIntelligenceSensor getGeoIntelligenceLaserSensor() throws KlvParseException {
        GeoIntelligenceSensor sensor = new GeoIntelligenceSensor();
        sensor.setType(GISensorType.LIDAR);
        sensor.setNCols(new GeoIntelligenceSensor_NCols(1));
        sensor.setNRows(new GeoIntelligenceSensor_NRows(1));
        sensor.setStatus(DeviceStatus.Nominal);
        sensor.setLaserSensor(getLaserSensor());
        sensor.setError(DeviceError.Nominal);
        sensor.setWarning(DeviceWarning.Nominal);
        sensor.setCorrespondences(getLaserSensorCorrespondences());
        return sensor;
    }

    private LaserSensor getLaserSensor() throws KlvParseException {
        LaserSensor laserSensor = new LaserSensor();
        laserSensor.setSafetyState(SafetyState.Firing);
        laserSensor.setWavelength(new LaserSensor_Wavelength(1.0640));
        return laserSensor;
    }

    private GeoIntelligenceSensor_Correspondences getLaserSensorCorrespondences()
            throws KlvParseException {
        List<Correspondence> laserSensorCorrespondences = new ArrayList<>();
        Correspondence laserSensorCorrespondence =
                createImageToLocationCorrespondence(0.5, 0.5, -35.35340, 149.08940);
        laserSensorCorrespondence.setMimdId(new MimdId(1, 2));
        laserSensorCorrespondence
                .getPosition()
                .setCountry(new Position_Country("ge:ISO1:3:VII-13:AUS"));
        Range rangeToTarget = new Range();
        rangeToTarget.setRangeDistance(new Range_RangeDistance(37.55));
        rangeToTarget.setMeasureMethod(MeasureMethod.Measured);
        laserSensorCorrespondence.setRange(rangeToTarget);
        laserSensorCorrespondence.setCrossRef(
                new MimdIdReference(1, 3, "crossRef", "Correspondence"));
        laserSensorCorrespondences.add(laserSensorCorrespondence);
        return new GeoIntelligenceSensor_Correspondences(laserSensorCorrespondences);
    }

    private GeoIntelligenceSensor getGeoIntelligenceSensor() throws KlvParseException {
        GeoIntelligenceSensor sensor = new GeoIntelligenceSensor();
        sensor.setType(GISensorType.EO);
        sensor.setNCols(new GeoIntelligenceSensor_NCols(1280));
        sensor.setNRows(new GeoIntelligenceSensor_NRows(960));
        sensor.setName(new GeoIntelligenceSensor_Name("Some camera"));
        sensor.setWarning(DeviceWarning.Nominal);
        sensor.setError(DeviceError.Nominal);
        sensor.setImagerSystem(getImagerSystem());
        List<CorrespondenceGroup> correspondenceGroups = new ArrayList<>();
        CorrespondenceGroup footprint = getFootprint();
        correspondenceGroups.add(footprint);
        sensor.setCorrespondenceGroups(
                new GeoIntelligenceSensor_CorrespondenceGroups(correspondenceGroups));
        List<Correspondence> otherCorrespondences = new ArrayList<>();
        Correspondence lrfLocationCorrespondence = buildBaseCorrespondence(302.5, 730.5);
        lrfLocationCorrespondence.setMimdId(new MimdId(1, 3));
        lrfLocationCorrespondence.setCrossRef(
                new MimdIdReference(1, 2, "crossRef", "Correspondence"));
        otherCorrespondences.add(lrfLocationCorrespondence);
        sensor.setCorrespondences(new GeoIntelligenceSensor_Correspondences(otherCorrespondences));
        return sensor;
    }

    private CorrespondenceGroup getFootprint() throws KlvParseException {
        CorrespondenceGroup footprint = new CorrespondenceGroup();
        Correspondence centroid =
                createImageToLocationCorrespondence(640.0, 480.0, -35.35300, 149.08920);
        footprint.setCentroid(centroid);
        List<Correspondence> cornerCoordinates = new ArrayList<>();
        Correspondence corner1 =
                createImageToLocationCorrespondence(0.0, 0.0, -35.35290, 149.08887);
        cornerCoordinates.add(corner1);
        Correspondence corner2 =
                createImageToLocationCorrespondence(1280.0, 0.0, -35.35279, 149.08936);
        cornerCoordinates.add(corner2);
        Correspondence corner3 =
                createImageToLocationCorrespondence(1280.0, 960.0, -35.35313, 149.08950);
        cornerCoordinates.add(corner3);
        Correspondence corner4 =
                createImageToLocationCorrespondence(0.0, 960.0, -35.35325, 149.08900);
        cornerCoordinates.add(corner4);
        footprint.setRectangle(new CorrespondenceGroup_Rectangle(cornerCoordinates));
        footprint.setType(CorrespondenceGroupType.Footprint);
        return footprint;
    }

    private Correspondence createImageToLocationCorrespondence(
            double col, double row, double lat, double lon) throws KlvParseException {
        Correspondence correspondence = buildBaseCorrespondence(col, row);
        Position position = buildPositionLatLon(lat, lon);
        correspondence.setPosition(position);
        return correspondence;
    }

    private Position buildPositionLatLon(double lat, double lon) throws KlvParseException {
        Position position = new Position();
        AbsGeodetic geodeticPosition = new AbsGeodetic();
        geodeticPosition.setLat(new AbsGeodetic_Lat(degToRadians(lat)));
        geodeticPosition.setLon(new AbsGeodetic_Lon(degToRadians(lon)));
        position.setAbsGeodetic(geodeticPosition);
        return position;
    }

    private Correspondence buildBaseCorrespondence(double col, double row)
            throws KlvParseException {
        Correspondence correspondence = new Correspondence();
        correspondence.setCol(new Correspondence_Col(col));
        correspondence.setRow(new Correspondence_Row(row));
        return correspondence;
    }

    private ImagerSystem getImagerSystem() throws KlvParseException {
        ImagerSystem imagerSystem = new ImagerSystem();
        imagerSystem.setName(new ImagerSystem_Name("EO Fixed"));
        imagerSystem.setFieldOfView(getFieldOfView());
        imagerSystem.setMiis(getMiis());
        return imagerSystem;
    }

    private FieldOfView getFieldOfView() throws KlvParseException {
        FieldOfView fieldOfView = new FieldOfView();
        fieldOfView.setHorizontal(new FieldOfView_Horizontal(degToRadians(144.571298)));
        fieldOfView.setVertical(new FieldOfView_Vertical(degToRadians(152.643626)));
        return fieldOfView;
    }

    private MIIS getMiis() throws KlvParseException {
        MIIS miis = new MIIS();
        miis.setMinorCoreId(getMinorCoreId());
        return miis;
    }

    private MinorCoreId getMinorCoreId() throws KlvParseException {
        byte[] coreIdentifierAsBytes = UuidUtils.uuidToArray(coreIdentifierUUID);
        long[] data = new long[coreIdentifierAsBytes.length];
        for (int i = 0; i < coreIdentifierAsBytes.length; ++i) {
            data[i] = (coreIdentifierAsBytes[i] & 0xFF);
        }
        MinorCoreId_Uuid uuid = new MinorCoreId_Uuid(data);
        MinorCoreId minorCoreId = new MinorCoreId();
        minorCoreId.setUuid(uuid);
        return minorCoreId;
    }

    private double degToRadians(double degrees) {
        return degrees * Math.PI / 180.0;
    }
}

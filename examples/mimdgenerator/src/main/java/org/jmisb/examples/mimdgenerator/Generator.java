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
import org.jmisb.api.klv.st1907.GeoIntelligenceSensor;
import org.jmisb.api.klv.st1907.Payload;
import org.jmisb.api.klv.st1907.Payload_GeoIntelligenceSensors;
import org.jmisb.api.klv.st1908.FieldOfView;
import org.jmisb.api.klv.st1908.FieldOfView_Horizontal;
import org.jmisb.api.klv.st1908.FieldOfView_Vertical;
import org.jmisb.api.klv.st1908.ImagerSystem;
import org.jmisb.api.klv.st1908.ImagerSystem_Name;
import org.jmisb.api.klv.st1908.MIIS;
import org.jmisb.api.klv.st1908.MinorCoreId;
import org.jmisb.api.klv.st1908.MinorCoreId_Uuid;
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
                        width, height, bitRate, frameRate, gopSize, KlvFormat.Asynchronous);
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
        Payload_GeoIntelligenceSensors sensors = new Payload_GeoIntelligenceSensors(sensorList);
        return sensors;
    }

    private GeoIntelligenceSensor getGeoIntelligenceSensor() throws KlvParseException {
        GeoIntelligenceSensor sensor = new GeoIntelligenceSensor();
        sensor.setImagerSystem(getImagerSystem());
        return sensor;
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
        fieldOfView.setHorizontal(new FieldOfView_Horizontal(144.571298 * Math.PI / 180.0));
        fieldOfView.setVertical(new FieldOfView_Vertical(152.643626 * Math.PI / 180.0));
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
}

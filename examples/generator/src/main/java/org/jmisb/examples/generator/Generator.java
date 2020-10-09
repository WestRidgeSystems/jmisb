package org.jmisb.examples.generator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.jmisb.api.klv.st0102.Classification;
import org.jmisb.api.klv.st0102.CountryCodingMethod;
import org.jmisb.api.klv.st0102.ISecurityMetadataValue;
import org.jmisb.api.klv.st0102.ObjectCountryCodeString;
import org.jmisb.api.klv.st0102.ST0102Version;
import org.jmisb.api.klv.st0102.SecurityMetadataKey;
import org.jmisb.api.klv.st0102.SecurityMetadataString;
import org.jmisb.api.klv.st0102.localset.CcMethod;
import org.jmisb.api.klv.st0102.localset.ClassificationLocal;
import org.jmisb.api.klv.st0102.localset.OcMethod;
import org.jmisb.api.klv.st0102.localset.SecurityMetadataLocalSet;
import org.jmisb.api.klv.st0601.CountryCodes;
import org.jmisb.api.klv.st0601.FrameCenterElevation;
import org.jmisb.api.klv.st0601.FrameCenterLatitude;
import org.jmisb.api.klv.st0601.FrameCenterLongitude;
import org.jmisb.api.klv.st0601.HorizontalFov;
import org.jmisb.api.klv.st0601.IUasDatalinkValue;
import org.jmisb.api.klv.st0601.MiisCoreIdentifier;
import org.jmisb.api.klv.st0601.NestedSecurityMetadata;
import org.jmisb.api.klv.st0601.NestedVmtiLocalSet;
import org.jmisb.api.klv.st0601.PayloadList;
import org.jmisb.api.klv.st0601.PlatformHeadingAngle;
import org.jmisb.api.klv.st0601.PlatformPitchAngle;
import org.jmisb.api.klv.st0601.PlatformRollAngle;
import org.jmisb.api.klv.st0601.PrecisionTimeStamp;
import org.jmisb.api.klv.st0601.ST0601Version;
import org.jmisb.api.klv.st0601.SensorLatitude;
import org.jmisb.api.klv.st0601.SensorLongitude;
import org.jmisb.api.klv.st0601.SensorRelativeAzimuth;
import org.jmisb.api.klv.st0601.SensorRelativeElevation;
import org.jmisb.api.klv.st0601.SensorRelativeRoll;
import org.jmisb.api.klv.st0601.SensorTrueAltitude;
import org.jmisb.api.klv.st0601.SlantRange;
import org.jmisb.api.klv.st0601.TargetWidth;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0601.UasDatalinkString;
import org.jmisb.api.klv.st0601.UasDatalinkTag;
import org.jmisb.api.klv.st0601.VerticalFov;
import org.jmisb.api.klv.st0601.dto.Payload;
import org.jmisb.api.klv.st0903.AlgorithmSeries;
import org.jmisb.api.klv.st0903.FrameHeight;
import org.jmisb.api.klv.st0903.FrameWidth;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.OntologySeries;
import org.jmisb.api.klv.st0903.ST0903Version;
import org.jmisb.api.klv.st0903.VTargetSeries;
import org.jmisb.api.klv.st0903.VmtiLocalSet;
import org.jmisb.api.klv.st0903.VmtiMetadataKey;
import org.jmisb.api.klv.st0903.VmtiReportedTargetCount;
import org.jmisb.api.klv.st0903.VmtiTotalTargetCount;
import org.jmisb.api.klv.st0903.algorithm.AlgorithmLS;
import org.jmisb.api.klv.st0903.algorithm.AlgorithmMetadataKey;
import org.jmisb.api.klv.st0903.ontology.OntologyLS;
import org.jmisb.api.klv.st0903.ontology.OntologyMetadataKey;
import org.jmisb.api.klv.st0903.shared.AlgorithmId;
import org.jmisb.api.klv.st0903.shared.LocationPack;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.api.klv.st0903.shared.VmtiUri;
import org.jmisb.api.klv.st0903.vobject.OntologyId;
import org.jmisb.api.klv.st0903.vtarget.BoundaryBottomRight;
import org.jmisb.api.klv.st0903.vtarget.BoundaryTopLeft;
import org.jmisb.api.klv.st0903.vtarget.CentroidPixelColumn;
import org.jmisb.api.klv.st0903.vtarget.CentroidPixelRow;
import org.jmisb.api.klv.st0903.vtarget.FpaIndex;
import org.jmisb.api.klv.st0903.vtarget.FpaIndexPack;
import org.jmisb.api.klv.st0903.vtarget.TargetLocation;
import org.jmisb.api.klv.st0903.vtarget.VTargetMetadataKey;
import org.jmisb.api.klv.st0903.vtarget.VTargetPack;
import org.jmisb.api.klv.st0903.vtarget.VTracker;
import org.jmisb.api.klv.st0903.vtracker.Acceleration;
import org.jmisb.api.klv.st0903.vtracker.AccelerationPack;
import org.jmisb.api.klv.st0903.vtracker.DetectionStatus;
import org.jmisb.api.klv.st0903.vtracker.VTrackerLS;
import org.jmisb.api.klv.st0903.vtracker.VTrackerMetadataKey;
import org.jmisb.api.klv.st0903.vtracker.Velocity;
import org.jmisb.api.klv.st0903.vtracker.VelocityPack;
import org.jmisb.api.klv.st1204.CoreIdentifier;
import org.jmisb.api.video.IVideoFileOutput;
import org.jmisb.api.video.KlvFormat;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.api.video.VideoFileOutput;
import org.jmisb.api.video.VideoFrame;
import org.jmisb.api.video.VideoOutputOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Generator {

    private final int width = 640;
    private final int height = 480;
    private final int bitRate = 500_000;
    private final int gopSize = 30;
    private final double frameRate = 15.0;
    private final double frameDuration = 1.0 / frameRate;
    private final int duration = 90;
    private final double sensorLatitude = -35.35355;
    private final double sensorLongitude = 149.08939;
    private final double sensorAltitude = 1258.3;
    private final double slantRange = 2000.0;
    private final String missionId = "IntTesting";
    private KlvFormat klvFormat = KlvFormat.Asynchronous;
    private byte version0601 = 16; // Last version supported by CMITT 1.3.0 is 9
    private byte version0102 = 12;
    private byte version0903 = 5;
    private String filename = "generator_output.mpeg";

    private static final Logger LOG = LoggerFactory.getLogger(Generator.class);

    public Generator() {}

    public void setKlvFormat(KlvFormat klvFormat) {
        this.klvFormat = klvFormat;
    }

    public void setVersion0601(byte version0601) {
        this.version0601 = version0601;
    }

    public void setVersion0102(byte version0102) {
        this.version0102 = version0102;
    }

    public void setVersion0903(byte version0903) {
        this.version0903 = version0903;
    }

    public void setOutputFile(String filename) {
        this.filename = filename;
    }

    public void generate() {

        showConfiguration();
        CoreIdentifier coreIdentifier = new CoreIdentifier();
        coreIdentifier.setMinorUUID(UUID.randomUUID());
        coreIdentifier.setVersion(1);

        // TODO: rework to make this a command line option
        try (IVideoFileOutput output =
                new VideoFileOutput(
                        new VideoOutputOptions(
                                width, height, bitRate, frameRate, gopSize, klvFormat))) {
            output.open(filename);

            // Write some frames
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            try {
                image = ImageIO.read(new File("test.jpg"));
            } catch (IOException e) {
                // TODO: log
            }

            final long numFrames = duration * Math.round(frameRate);
            final long switchingValue = numFrames / 6;
            double pts = 1000.0 * System.currentTimeMillis(); // Close enough for this.
            for (long i = 0; i < numFrames; ++i) {
                SortedMap<UasDatalinkTag, IUasDatalinkValue> values = new TreeMap<>();

                values.put(UasDatalinkTag.PrecisionTimeStamp, new PrecisionTimeStamp((long) pts));
                values.put(UasDatalinkTag.UasLdsVersionNumber, new ST0601Version(version0601));

                values.put(
                        UasDatalinkTag.MiisCoreIdentifier, new MiisCoreIdentifier(coreIdentifier));
                values.put(
                        UasDatalinkTag.MissionId,
                        new UasDatalinkString(UasDatalinkString.MISSION_ID, missionId));
                values.put(
                        UasDatalinkTag.PlatformDesignation,
                        new UasDatalinkString(UasDatalinkString.PLATFORM_DESIGNATION, "WideScope"));
                values.put(
                        UasDatalinkTag.ImageSourceSensor,
                        new UasDatalinkString(UasDatalinkString.IMAGE_SOURCE_SENSOR, "DTV"));
                values.put(
                        UasDatalinkTag.ImageCoordinateSystem,
                        new UasDatalinkString(
                                UasDatalinkString.IMAGE_COORDINATE_SYSTEM, "Geodetic WGS84"));

                values.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(sensorLatitude));
                values.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(sensorLongitude));
                values.put(
                        UasDatalinkTag.SensorTrueAltitude, new SensorTrueAltitude(sensorAltitude));

                values.put(UasDatalinkTag.PlatformHeadingAngle, new PlatformHeadingAngle(10.0));
                values.put(
                        UasDatalinkTag.PlatformPitchAngle,
                        new PlatformPitchAngle(1.0 * (i / switchingValue)));
                values.put(
                        UasDatalinkTag.PlatformRollAngle,
                        new PlatformRollAngle(-1.0 * (i / switchingValue)));

                values.put(
                        UasDatalinkTag.SensorRelativeAzimuthAngle,
                        new SensorRelativeAzimuth(330.0));
                values.put(
                        UasDatalinkTag.SensorRelativeElevationAngle,
                        new SensorRelativeElevation(-70.0));
                values.put(UasDatalinkTag.SensorRelativeRollAngle, new SensorRelativeRoll(0.0));

                values.put(UasDatalinkTag.SensorHorizontalFov, new HorizontalFov(7.0));
                values.put(UasDatalinkTag.SensorVerticalFov, new VerticalFov(5.0));

                values.put(UasDatalinkTag.SlantRange, new SlantRange(slantRange));
                values.put(UasDatalinkTag.TargetWidth, new TargetWidth(100.0));

                values.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(-35.35305));
                values.put(
                        UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(149.08939));
                values.put(UasDatalinkTag.FrameCenterElevation, new FrameCenterElevation(12.0));

                values.put(
                        UasDatalinkTag.SecurityLocalMetadataSet,
                        new NestedSecurityMetadata(getSecurityLs(i / switchingValue)));
                if (this.version0601 > 12) {
                    if ((i / switchingValue) % 2 == 0) {
                        values.put(
                                UasDatalinkTag.CountryCodes,
                                new CountryCodes(
                                        CountryCodingMethod.GENC_THREE_LETTER, "CAN", "", "FRA"));
                    } else {
                        values.put(
                                UasDatalinkTag.CountryCodes,
                                new CountryCodes(
                                        CountryCodingMethod.ISO3166_THREE_LETTER,
                                        "AUS",
                                        "CAN",
                                        "NZL"));
                    }
                }
                values.put(
                        UasDatalinkTag.VmtiLocalDataSet, new NestedVmtiLocalSet(getVmtiLocalSet()));
                if (this.version0601 > 12) {
                    values.put(UasDatalinkTag.PayloadList, getPayloadList());
                }
                UasDatalinkMessage message = new UasDatalinkMessage(values);

                output.addVideoFrame(new VideoFrame(image, pts * 1.0e-6));
                output.addMetadataFrame(new MetadataFrame(message, pts));
                pts += frameDuration * 1.0e6;
            }

        } catch (IOException e) {
            LOG.error("Failed to write file", e);
        }
    }

    private SecurityMetadataLocalSet getSecurityLs(long i) {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));

        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.ISO3166_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));

        if (i % 2 == 0) {
            values.put(
                    SecurityMetadataKey.OcCodingMethod,
                    new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
            values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("US"));
        } else {
            values.put(
                    SecurityMetadataKey.OcCodingMethod,
                    new OcMethod(CountryCodingMethod.ISO3166_TWO_LETTER));
            values.put(
                    SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU;NZ"));
        }

        values.put(SecurityMetadataKey.Version, new ST0102Version(version0102));

        return new SecurityMetadataLocalSet(values);
    }

    private VmtiLocalSet getVmtiLocalSet() {
        VTargetSeries vmtiTargets = getVmtiTargets();
        Map<VmtiMetadataKey, IVmtiMetadataValue> values = new TreeMap<>();
        values.put(VmtiMetadataKey.VersionNumber, new ST0903Version(this.version0903));
        values.put(
                VmtiMetadataKey.NumberOfReportedTargets,
                new VmtiReportedTargetCount(vmtiTargets.getVTargets().size()));
        values.put(
                VmtiMetadataKey.TotalTargetsInFrame,
                new VmtiTotalTargetCount(vmtiTargets.getVTargets().size()));
        values.put(
                VmtiMetadataKey.SystemName,
                new VmtiTextString(VmtiTextString.SYSTEM_NAME, "Jericho Test 1"));
        values.put(
                VmtiMetadataKey.SourceSensor,
                new VmtiTextString(VmtiTextString.SOURCE_SENSOR, "WideScope"));
        values.put(VmtiMetadataKey.FrameWidth, new FrameWidth(1024));
        values.put(VmtiMetadataKey.FrameHeight, new FrameHeight(768));
        if (this.version0903 > 4) {
            values.put(VmtiMetadataKey.AlgorithmSeries, new AlgorithmSeries(getAlgorithms()));
            values.put(VmtiMetadataKey.OntologySeries, new OntologySeries(getOntologies()));
        }
        values.put(VmtiMetadataKey.VTargetSeries, vmtiTargets);
        return new VmtiLocalSet(values);
    }

    private VTargetSeries getVmtiTargets() {
        List<VTargetPack> values = new ArrayList<>();
        Map<VTargetMetadataKey, IVmtiMetadataValue> targetData = getVmtiTarget1();
        VTargetPack targetPack = new VTargetPack(1, targetData);
        values.add(targetPack);
        return new VTargetSeries(values);
    }

    private Map<VTargetMetadataKey, IVmtiMetadataValue> getVmtiTarget1() {
        Map<VTargetMetadataKey, IVmtiMetadataValue> targetData = new TreeMap<>();
        targetData.put(VTargetMetadataKey.CentroidPixColumn, new CentroidPixelColumn(400));
        targetData.put(VTargetMetadataKey.CentroidPixRow, new CentroidPixelRow(300));
        targetData.put(
                VTargetMetadataKey.FPAIndex, new FpaIndex(new FpaIndexPack((short) 1, (short) 1)));
        targetData.put(VTargetMetadataKey.BoundaryTopLeft, new BoundaryTopLeft(280L * 1024 + 360));
        targetData.put(
                VTargetMetadataKey.BoundaryBottomRight, new BoundaryBottomRight(320L * 1024 + 440));
        if (this.version0903 > 4) {
            targetData.put(VTargetMetadataKey.AlgorithmId, new AlgorithmId(1));
        }
        targetData.put(
                VTargetMetadataKey.TargetLocation,
                new TargetLocation(new LocationPack(-34.2, 143.2, 651.0, 8.0, 12.0, 14.0)));
        targetData.put(VTargetMetadataKey.VTracker, getVTrackerForTarget1());
        return targetData;
    }

    private List<AlgorithmLS> getAlgorithms() {
        List<AlgorithmLS> algorithms = new ArrayList<>();
        Map<AlgorithmMetadataKey, IVmtiMetadataValue> algorithmDetails0 = new TreeMap<>();
        algorithmDetails0.put(AlgorithmMetadataKey.id, new AlgorithmId(1));
        algorithmDetails0.put(
                AlgorithmMetadataKey.name,
                new VmtiTextString(VmtiTextString.ALGORITHM_NAME, "DetectNet2"));
        algorithmDetails0.put(
                AlgorithmMetadataKey.algorithmClass,
                new VmtiTextString(VmtiTextString.ALGORITHM_CLASS, "Detector"));
        AlgorithmLS algorithm0 = new AlgorithmLS(algorithmDetails0);
        algorithms.add(algorithm0);
        Map<AlgorithmMetadataKey, IVmtiMetadataValue> algorithmDetails1 = new TreeMap<>();
        algorithmDetails1.put(AlgorithmMetadataKey.id, new AlgorithmId(2));
        algorithmDetails1.put(
                AlgorithmMetadataKey.name,
                new VmtiTextString(
                        VmtiTextString.ALGORITHM_NAME, "Discriminative Correlation Filter"));
        algorithmDetails1.put(
                AlgorithmMetadataKey.algorithmClass,
                new VmtiTextString(VmtiTextString.ALGORITHM_CLASS, "Tracker"));
        AlgorithmLS algorithm1 = new AlgorithmLS(algorithmDetails1);
        algorithms.add(algorithm1);
        return algorithms;
    }

    private List<OntologyLS> getOntologies() {
        List<OntologyLS> ontologies = new ArrayList<>();
        Map<OntologyMetadataKey, IVmtiMetadataValue> ontologyDetails0 = new TreeMap<>();
        ontologyDetails0.put(OntologyMetadataKey.id, new OntologyId(1));
        IVmtiMetadataValue ontology =
                new VmtiUri(
                        VmtiUri.ONTOLOGY,
                        "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl");
        ontologyDetails0.put(OntologyMetadataKey.ontology, ontology);
        IVmtiMetadataValue ontologyClass =
                new VmtiTextString(VmtiTextString.ONTOLOGY_CLASS, "Mushroom");
        ontologyDetails0.put(OntologyMetadataKey.ontologyClass, ontologyClass);
        OntologyLS ontology0 = new OntologyLS(ontologyDetails0);
        ontologies.add(ontology0);
        return ontologies;
    }

    private VTracker getVTrackerForTarget1() {
        Map<VTrackerMetadataKey, IVmtiMetadataValue> map = new TreeMap<>();
        map.put(VTrackerMetadataKey.detectionStatus, new DetectionStatus((byte) 0x01));
        if (this.version0903 > 4) {
            map.put(VTrackerMetadataKey.algorithmId, new AlgorithmId(2));
        }
        Velocity velocity =
                new Velocity(new VelocityPack(-10.2, 8.3, 0.2, 0.01, 0.02, 0.03, 0.12, 0.06, 0.09));
        map.put(VTrackerMetadataKey.velocity, velocity);
        Acceleration acceleration = new Acceleration(new AccelerationPack(0.1, 0.2, 0.0));
        map.put(VTrackerMetadataKey.acceleration, acceleration);
        VTrackerLS trackerLS = new VTrackerLS(map);
        return new VTracker(trackerLS);
    }

    private PayloadList getPayloadList() {
        List<Payload> payloads = new ArrayList<>();
        Payload payload6 = new Payload(6, 0, "Box Brownie");
        payloads.add(payload6);
        Payload payload8 = new Payload(8, 4, "HF SAR");
        payloads.add(payload8);
        PayloadList payloadList = new PayloadList(payloads);
        return payloadList;
    }

    private void showConfiguration() {
        System.out.println("Generating with configuration:");
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Generator{"
                + "width="
                + width
                + ", height="
                + height
                + ", bitRate="
                + bitRate
                + ", gopSize="
                + gopSize
                + ", frameRate="
                + frameRate
                + ", frameDuration="
                + frameDuration
                + ", duration="
                + duration
                + ",\nsensorLatitude="
                + sensorLatitude
                + ", sensorLongitude="
                + sensorLongitude
                + ", sensorAltitude="
                + sensorAltitude
                + ", slantRange="
                + slantRange
                + ", missionId="
                + missionId
                + ",\nklvFormat="
                + klvFormat
                + ", version0601="
                + version0601
                + ", version0102="
                + version0102
                + ", version0903="
                + version0903
                + ",\nfilename="
                + filename
                + '}';
    }
}

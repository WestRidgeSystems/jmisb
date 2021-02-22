package org.jmisb.api.video;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.st0102.*;
import org.jmisb.api.klv.st0102.localset.CcMethod;
import org.jmisb.api.klv.st0102.localset.ClassificationLocal;
import org.jmisb.api.klv.st0102.localset.SecurityMetadataLocalSet;
import org.jmisb.api.klv.st0601.*;
import org.jmisb.core.video.TimingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/** Integration test for {@link VideoFileInput} and {@link VideoFileOutput} */
@Test(groups = {"integration-tests"})
public class VideoFileInputOutputIT {
    private static final Logger logger = LoggerFactory.getLogger(VideoFileInputOutputIT.class);
    private final double sensorLatitude = 42.4036;
    private final double sensorLongitude = -71.1284;
    private final double sensorAltitude = 1258.3;
    private final double slantRange = 2000.0;
    private final String missionId = "Unit Testing";
    private final byte version0601 = 11;
    private final byte version0102 = 12;

    /** Video stream only test */
    @Test
    public void testSimple() {
        final String filename = "testSimple.ts";
        final int width = 640;
        final int height = 480;
        final int bitRate = 1_500_000;
        final double frameRate = 30.0;
        final int gopSize = 30;
        final boolean hasKlv = false;
        final double frameDuration = 1.0 / frameRate;
        final int numFrames = 300;

        try (IVideoFileOutput output =
                new VideoFileOutput(
                        new VideoOutputOptions(
                                width,
                                height,
                                bitRate,
                                frameRate,
                                gopSize,
                                KlvFormat.Asynchronous))) {
            output.open(filename);

            // Write some frames
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            try {
                image = ImageIO.read(new File("test.jpg"));
            } catch (IOException e) {
                Assert.fail("Could not read test image");
            }

            double pts = 0.0;
            for (int i = 0; i < numFrames; ++i) {
                output.addVideoFrame(new VideoFrame(image, pts));
                pts += frameDuration;
            }

            // Note: output.close() is automatically called by using try-with-resources

        } catch (IOException e) {
            logger.error("Failed to write file", e);
            Assert.fail("Failed to write file");
        }

        // Just test that the file was created and has the correct duration / number of frames
        checkFileDuration(filename, frameRate, numFrames);
    }

    @Test
    public void testWithData() {
        final double frameRate = 15.0;
        final int numFrames = 120;
        final String filename = "testWithData.ts";

        createFile(filename, frameRate, numFrames);
        checkFileDuration(filename, frameRate, numFrames);

        // Check metadata contents
        try (IVideoFileInput input = new VideoFileInput()) {
            input.open(filename);
            List<PesInfo> pesList = input.getPesInfo();
            Assert.assertEquals(pesList.size(), 2);

            input.addMetadataListener(new MetadataListener());

            // Play and read at least 1s of data
            input.play();
            Assert.assertTrue(input.isPlaying());
            TimingUtils.shortWait(1000);

        } catch (IOException e) {
            logger.error("Failed to read file", e);
            Assert.fail("Failed to read file");
        }

        Assert.assertTrue(true);
    }

    @Test
    private void testRipKlv() {
        // Disable video decoding, verify all metadata can be pulled out
        final double frameRate = 15.0;
        final int numFrames = 120;
        final String filename = "testRipKlv.ts";

        createFile(filename, frameRate, numFrames);

        try (IVideoFileInput input =
                new VideoFileInput(new VideoFileInputOptions(false, true, false, true))) {
            input.open(filename);

            MetadataCounter counter = new MetadataCounter();
            input.addMetadataListener(counter);
            input.addFrameListener(new DisallowedListener());

            // Read file as fast as possible
            input.setPlaybackSpeed(Double.MAX_VALUE);
            input.play();
            TimingUtils.shortWait(1000);

            // The main test is that the video listener never gets called, but also make sure
            // the metadata listener receives a reasonable number of frames
            int count = counter.getCount();
            Assert.assertTrue(count >= frameRate);

        } catch (IOException e) {
            logger.error("Failed to read file", e);
            Assert.fail("Failed to read file");
        }
    }

    private void createFile(String filename, double frameRate, int numFrames) {
        final int width = 640;
        final int height = 480;
        final int bitRate = 500_000;
        final int gopSize = 30;
        final boolean hasKlv = true;
        final double frameDuration = 1.0 / frameRate;

        try (IVideoFileOutput output =
                new VideoFileOutput(
                        new VideoOutputOptions(
                                width,
                                height,
                                bitRate,
                                frameRate,
                                gopSize,
                                KlvFormat.Synchronous))) {
            output.open(filename);

            // Write some frames
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            try {
                image = ImageIO.read(new File("test.jpg"));
            } catch (IOException e) {
                Assert.fail("Could not read test image");
            }

            double pts = 0.0;
            for (int i = 0; i < numFrames; ++i) {
                SortedMap<UasDatalinkTag, IUasDatalinkValue> values = new TreeMap<>();

                values.put(
                        UasDatalinkTag.PrecisionTimeStamp,
                        new PrecisionTimeStamp(LocalDateTime.now()));

                values.put(
                        UasDatalinkTag.MissionId,
                        new UasDatalinkString(UasDatalinkString.MISSION_ID, missionId));
                values.put(
                        UasDatalinkTag.PlatformDesignation,
                        new UasDatalinkString(
                                UasDatalinkString.PLATFORM_DESIGNATION, "Thunderbolt"));
                values.put(
                        UasDatalinkTag.ImageSourceSensor,
                        new UasDatalinkString(UasDatalinkString.IMAGE_SOURCE_SENSOR, "DTV"));
                values.put(
                        UasDatalinkTag.ImageCoordinateSystem,
                        new UasDatalinkString(
                                UasDatalinkString.IMAGE_SOURCE_SENSOR, "Geodetic WGS84"));

                values.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(sensorLatitude));
                values.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(sensorLongitude));
                values.put(
                        UasDatalinkTag.SensorTrueAltitude, new SensorTrueAltitude(sensorAltitude));

                values.put(UasDatalinkTag.PlatformHeadingAngle, new PlatformHeadingAngle(10.0));
                values.put(UasDatalinkTag.PlatformPitchAngle, new PlatformPitchAngle(1.0));
                values.put(UasDatalinkTag.PlatformRollAngle, new PlatformRollAngle(-1.0));

                values.put(
                        UasDatalinkTag.SensorRelativeAzimuthAngle,
                        new SensorRelativeAzimuth(330.0));
                values.put(
                        UasDatalinkTag.SensorRelativeElevationAngle,
                        new SensorRelativeElevation(-70.0));
                values.put(UasDatalinkTag.SensorRelativeRollAngle, new SensorRelativeRoll(0.0));

                values.put(UasDatalinkTag.SensorHorizontalFov, new HorizontalFov(8.0));
                values.put(UasDatalinkTag.SensorVerticalFov, new VerticalFov(5.0));

                values.put(UasDatalinkTag.SlantRange, new SlantRange(slantRange));
                values.put(UasDatalinkTag.TargetWidth, new TargetWidth(100.0));

                values.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(42.1032));
                values.put(UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(-71.2382));
                values.put(UasDatalinkTag.FrameCenterElevation, new FrameCenterElevation(12.0));

                values.put(
                        UasDatalinkTag.SecurityLocalMetadataSet,
                        new NestedSecurityMetadata(getSecurityLs()));
                values.put(UasDatalinkTag.UasLdsVersionNumber, new ST0601Version(version0601));

                UasDatalinkMessage message = new UasDatalinkMessage(values);

                output.addVideoFrame(new VideoFrame(image, pts));
                output.addMetadataFrame(new MetadataFrame(message, pts));
                pts += frameDuration;
            }

        } catch (IOException e) {
            logger.error("Failed to write file", e);
            Assert.fail("Failed to write file");
        }
    }

    private void checkFileDuration(String filename, double frameRate, int numFrames) {
        try (IVideoFileInput input = new VideoFileInput()) {
            input.open(filename);
            Assert.assertTrue(input.isOpen());
            Assert.assertEquals(input.getDuration(), numFrames / frameRate);
            Assert.assertEquals(input.getNumFrames(), numFrames);

        } catch (IOException e) {
            logger.error("Failed to read file", e);
            Assert.fail("Failed to read file");
        }
    }

    private SecurityMetadataLocalSet getSecurityLs() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));

        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//US"));

        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("US;CA"));

        values.put(SecurityMetadataKey.Version, new ST0102Version(version0102));

        return new SecurityMetadataLocalSet(values);
    }

    private static class DisallowedListener implements IVideoListener {
        @Override
        public void onVideoReceived(VideoFrame image) {
            Assert.fail("Received an unexpected video frame");
        }
    }

    private static class MetadataCounter implements IMetadataListener {
        private int count = 0;

        @Override
        public void onMetadataReceived(MetadataFrame metadataFrame) {
            count++;
        }

        int getCount() {
            return count;
        }
    }

    private class MetadataListener implements IMetadataListener {
        @Override
        public void onMetadataReceived(MetadataFrame metadataFrame) {
            IMisbMessage misbMessage = metadataFrame.getMisbMessage();
            if (misbMessage instanceof UasDatalinkMessage) {
                UasDatalinkMessage datalinkMessage = (UasDatalinkMessage) misbMessage;

                UasDatalinkString decMissionId =
                        (UasDatalinkString) datalinkMessage.getField(UasDatalinkTag.MissionId);
                Assert.assertEquals(decMissionId.getDisplayableValue(), missionId);

                UasDatalinkLatitude decSensorLatitude =
                        (UasDatalinkLatitude)
                                datalinkMessage.getField((UasDatalinkTag.SensorLatitude));
                Assert.assertEquals(
                        decSensorLatitude.getDegrees(), sensorLatitude, UasDatalinkLatitude.DELTA);

                UasDatalinkLongitude decSensorLongitude =
                        (UasDatalinkLongitude)
                                datalinkMessage.getField((UasDatalinkTag.SensorLongitude));
                Assert.assertEquals(
                        decSensorLongitude.getDegrees(),
                        sensorLongitude,
                        UasDatalinkLongitude.DELTA);

                UasDatalinkAltitude decSensorAltitude =
                        (UasDatalinkAltitude)
                                datalinkMessage.getField((UasDatalinkTag.SensorTrueAltitude));
                Assert.assertEquals(
                        decSensorAltitude.getMeters(), sensorAltitude, UasDatalinkAltitude.DELTA);

                SlantRange decSlantRange =
                        (SlantRange) datalinkMessage.getField(UasDatalinkTag.SlantRange);
                Assert.assertEquals(decSlantRange.getMeters(), slantRange, SlantRange.DELTA);

                ST0601Version decVersion =
                        (ST0601Version)
                                datalinkMessage.getField(UasDatalinkTag.UasLdsVersionNumber);
                Assert.assertEquals(decVersion.getVersion(), version0601);

                IUasDatalinkValue value =
                        datalinkMessage.getField(UasDatalinkTag.SecurityLocalMetadataSet);
                if (value instanceof NestedSecurityMetadata) {
                    NestedSecurityMetadata nestedData = (NestedSecurityMetadata) value;
                    SecurityMetadataLocalSet localSet = nestedData.getLocalSet();

                    ClassificationLocal decClassification =
                            (ClassificationLocal)
                                    localSet.getField(SecurityMetadataKey.SecurityClassification);
                    Assert.assertEquals(
                            decClassification.getClassification(), Classification.UNCLASSIFIED);

                    ST0102Version dec0102Version =
                            (ST0102Version) localSet.getField(SecurityMetadataKey.Version);
                    Assert.assertEquals(dec0102Version.getVersion(), version0102);
                } else {
                    Assert.fail("Found an invalid Security Metadata message");
                }
            } else {
                Assert.fail("Found an invalid UAS Datalink message");
            }
        }
    }
}

package org.jmisb.api.video;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import org.jmisb.api.klv.st0601.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/** Integration test for {@link VideoStreamInput} and {@link VideoStreamOutput} */
@Test(groups = {"integration-tests"})
public class VideoStreamInputOutputIT {
    private static Logger logger = LoggerFactory.getLogger(VideoStreamInputOutputIT.class);

    private BufferedImage image;
    private UasDatalinkMessage message;

    private final int width = 640;
    private final int height = 480;
    private final int bitRate = 1_500_000;
    private final double frameRate = 10.0;
    private final int gopSize = 30;

    @BeforeClass
    public void setUp() {
        // Sample image
        image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        try {
            image = ImageIO.read(new File("test.jpg"));
        } catch (IOException e) {
            Assert.fail("Could not read test image");
        }

        // Sample metadata
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = new TreeMap<>();
        values.put(UasDatalinkTag.PrecisionTimeStamp, new PrecisionTimeStamp(LocalDateTime.now()));
        values.put(
                UasDatalinkTag.MissionId,
                new UasDatalinkString(UasDatalinkString.MISSION_ID, "Unit Testing"));
        values.put(
                UasDatalinkTag.PlatformDesignation,
                new UasDatalinkString(UasDatalinkString.PLATFORM_DESIGNATION, "Thunderbolt"));
        values.put(
                UasDatalinkTag.ImageSourceSensor,
                new UasDatalinkString(UasDatalinkString.IMAGE_SOURCE_SENSOR, "DTV"));
        values.put(
                UasDatalinkTag.ImageCoordinateSystem,
                new UasDatalinkString(UasDatalinkString.IMAGE_COORDINATE_SYSTEM, "Geodetic WGS84"));
        values.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(42.4036));
        values.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(-71.1284));
        values.put(UasDatalinkTag.SensorTrueAltitude, new SensorTrueAltitude(1258.3));
        values.put(UasDatalinkTag.UasLdsVersionNumber, new ST0601Version((byte) 11));

        message = new UasDatalinkMessage(values);
    }

    @Test
    public void testVideoOnly() {
        final String url = "udp://225.1.1.1:31200";
        final double frameDuration = 1.0 / frameRate;

        try (IVideoStreamOutput output =
                new VideoStreamOutput(
                        new VideoOutputOptions(
                                width, height, bitRate, frameRate, gopSize, KlvFormat.NoKlv))) {
            output.open(url);
            Assert.assertTrue(output.isOpen());

            // Write some video frames
            double pts = 0.0;
            for (int i = 0; i < 80; ++i) {
                output.queueVideoFrame(new VideoFrame(image, pts));
                pts += frameDuration;
                Assert.assertTrue(output.isOpen());
            }

            // Note: output.close() is automatically called by using try-with-resources

        } catch (IOException e) {
            logger.error("Failed to write stream", e);
            Assert.fail("Failed to write stream");
        }
    }

    @Test
    public void testVideoAndMetadata() {
        final String url = "udp://225.1.1.1:31200";
        final double frameDuration = 1.0 / frameRate;

        try (IVideoStreamOutput output =
                new VideoStreamOutput(
                        new VideoOutputOptions(
                                width,
                                height,
                                bitRate,
                                frameRate,
                                gopSize,
                                KlvFormat.Asynchronous))) {
            output.open(url);

            // Write some video and metadata frames
            double pts = 0.0;
            for (int i = 0; i < 80; ++i) {
                output.queueVideoFrame(new VideoFrame(image, pts));
                output.queueMetadataFrame(new MetadataFrame(message, pts));
                pts += frameDuration;
            }
        } catch (IOException e) {
            logger.error("Failed to write stream", e);
            Assert.fail("Failed to write stream");
        }
    }

    @Test
    public void testClose() {
        final String url = "udp://225.1.1.1:31200";
        IVideoStreamOutput output;

        try {
            output =
                    new VideoStreamOutput(
                            new VideoOutputOptions(
                                    width,
                                    height,
                                    bitRate,
                                    frameRate,
                                    gopSize,
                                    KlvFormat.Asynchronous));

            output.open(url);
            Assert.assertTrue(output.isOpen());

            output.close();
            Assert.assertFalse(output.isOpen());
        } catch (IOException ex) {
            Assert.fail("IOException opening stream");
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidProtocol() {
        try (IVideoStreamOutput output =
                new VideoStreamOutput(
                        new VideoOutputOptions(
                                width,
                                height,
                                bitRate,
                                frameRate,
                                gopSize,
                                KlvFormat.Synchronous))) {
            output.open("http://127.0.0.1:30800/test.sdp");
        } catch (IOException ex) {
            Assert.fail("Caught IOException");
        }
    }

    @Test(expectedExceptions = IOException.class)
    public void testInvalidAddress() throws IOException {
        try (IVideoStreamOutput output =
                new VideoStreamOutput(
                        new VideoOutputOptions(
                                width,
                                height,
                                bitRate,
                                frameRate,
                                gopSize,
                                KlvFormat.Asynchronous))) {
            output.open("udp://256.0.0.0:30800");
        }
    }
}

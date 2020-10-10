package org.jmisb.api.video;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.st0601.IUasDatalinkValue;
import org.jmisb.api.klv.st0601.PrecisionTimeStamp;
import org.jmisb.api.klv.st0601.ST0601Version;
import org.jmisb.api.klv.st0601.SensorLatitude;
import org.jmisb.api.klv.st0601.SensorLongitude;
import org.jmisb.api.klv.st0601.SensorTrueAltitude;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0601.UasDatalinkString;
import org.jmisb.api.klv.st0601.UasDatalinkTag;

/** Interactive test for VideoStreamOutput to stand up a UDP stream with a test pattern */
public class StreamerUtil {
    private static final int width = 640;
    private static final int height = 480;
    private static final double frameRate = 10.0;
    private static final int bitRate = 1_500_000;
    private static final int gopSize = 30;

    public static void main(String[] args) {
        // Sample image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        try {
            image = ImageIO.read(new File("api/test.jpg"));
        } catch (IOException e) {
            System.out.println("Could not read sample image");
            System.exit(1);
        }

        final String url = "udp://225.1.1.1:30120";
        final double frameDuration = 1.0 / frameRate;

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
        values.put(
                UasDatalinkTag.TargetId,
                new UasDatalinkString(UasDatalinkString.TARGET_ID, "tango"));

        IMisbMessage message;

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

            double pts = 0.0;
            long numBuffered;
            OutputStatistics stats;
            for (int i = 0; i < 1000000; ++i) {
                stats = output.getStatistics();
                numBuffered = stats.getNumVideoFramesQueued() - stats.getNumVideoFramesSent();

                // Slow down so we don't buffer everything
                if (numBuffered > gopSize) {
                    Thread.sleep(Math.round(frameDuration * 1000));
                    System.out.println(
                            "buf: "
                                    + numBuffered
                                    + "  sent: "
                                    + stats.getNumVideoFramesSent()
                                    + "  enc:  "
                                    + stats.getNumVideoFramesEncoded());
                }
                message = new UasDatalinkMessage(values);
                values.put(
                        UasDatalinkTag.PrecisionTimeStamp,
                        new PrecisionTimeStamp(LocalDateTime.now()));
                pts += frameDuration;

                output.queueVideoFrame(new VideoFrame(image, pts));
                output.queueMetadataFrame(new MetadataFrame(message, pts));
            }

            stats = output.getStatistics();
            numBuffered = stats.getNumVideoFramesQueued() - stats.getNumVideoFramesSent();
            while (numBuffered > 0) {
                stats = output.getStatistics();
                numBuffered = stats.getNumVideoFramesQueued() - stats.getNumVideoFramesSent();
                System.out.println(
                        "buf: "
                                + numBuffered
                                + "  sent: "
                                + stats.getNumVideoFramesSent()
                                + "  enc:  "
                                + stats.getNumVideoFramesEncoded());
                Thread.sleep(Math.round(frameDuration * 1000));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

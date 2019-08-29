package org.jmisb.api.video;

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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Interactive test for VideoStreamOutput to stand up a UDP stream with a test pattern
 */
public class StreamerUtil
{
    private static final int width = 640;
    private static final int height = 480;
    private static final double frameRate = 10.0;
    private static final int bitRate = 1_500_000;
    private static final int gopSize = 30;

    public static void main(String[] args)
    {
        // Sample image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        try
        {
            image = ImageIO.read(new File("api/test.jpg"));
        } catch (IOException e)
        {
            System.out.println("Could not read sample image");
            System.exit(1);
        }

        final String url = "udp://225.1.1.1:30120";
        final double frameDuration = 1.0 / frameRate;

        // Sample metadata
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = new TreeMap<>();
        values.put(UasDatalinkTag.PrecisionTimeStamp, new PrecisionTimeStamp(LocalDateTime.now()));
        values.put(UasDatalinkTag.MissionId, new UasDatalinkString("Unit Testing"));
        values.put(UasDatalinkTag.PlatformDesignation, new UasDatalinkString("Thunderbolt"));
        values.put(UasDatalinkTag.ImageSourceSensor, new UasDatalinkString("DTV"));
        values.put(UasDatalinkTag.ImageCoordinateSystem, new UasDatalinkString("Geodetic WGS84"));
        values.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(42.4036));
        values.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(-71.1284));
        values.put(UasDatalinkTag.SensorTrueAltitude, new SensorTrueAltitude(1258.3));
        values.put(UasDatalinkTag.UasLdsVersionNumber, new ST0601Version((byte)11));

        IMisbMessage message = new UasDatalinkMessage(values);

        try (IVideoStreamOutput output = VideoSystem.createOutputStream(
            new VideoOutputOptions(width, height, bitRate, frameRate, gopSize, true)))
        {
            output.open(url);

            double pts = 0.0;
            long numBuffered;
            OutputStatistics stats;
            for (int i = 0; i < 1000000; ++i)
            {
                stats = output.getStatistics();
                numBuffered = stats.getNumVideoFramesQueued() - stats.getNumVideoFramesSent();

                // Slow down so we don't buffer everything
                if (numBuffered > gopSize)
                {
                    Thread.sleep(Math.round(frameDuration * 1000));
                    System.out.println("buf: " + numBuffered + "  sent: " +
                        stats.getNumVideoFramesSent() + "  enc:  " +
                        stats.getNumVideoFramesEncoded());
                }
                output.queueVideoFrame(new VideoFrame(image, pts));
                output.queueMetadataFrame(new MetadataFrame(message, pts));
                pts += frameDuration;
            }

            stats = output.getStatistics();
            numBuffered = stats.getNumVideoFramesQueued() - stats.getNumVideoFramesSent();
            while (numBuffered > 0)
            {
                stats = output.getStatistics();
                numBuffered = stats.getNumVideoFramesQueued() - stats.getNumVideoFramesSent();
                System.out.println("buf: " + numBuffered + "  sent: " +
                    stats.getNumVideoFramesSent() + "  enc:  " +
                    stats.getNumVideoFramesEncoded());
                Thread.sleep(Math.round(frameDuration * 1000));
            }
        } catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

package org.jmisb.api.video;

import org.jmisb.api.klv.st0102.*;
import org.jmisb.api.klv.st0102.localset.CcMethod;
import org.jmisb.api.klv.st0102.localset.ClassificationLocal;
import org.jmisb.api.klv.st0102.localset.SecurityMetadataLocalSet;
import org.jmisb.api.klv.st0601.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;

public class VideoFileOutputTest
{
    private static Logger logger = LoggerFactory.getLogger(VideoFileOutputTest.class);

    /**
     * Video stream only test
     */
    @Test
    public void testSimple()
    {
        final String filename = "testSimple.ts";
        final int width = 640;
        final int height = 480;
        final int bitRate = 1_500_000;
        final double frameRate = 30.0;
        final int gopSize = 30;
        final double frameDuration = 1.0 / frameRate;

        try (IVideoFileOutput output = VideoSystem.createOutputFile(
                new VideoOutputOptions(width, height, bitRate, frameRate, gopSize)))
        {
            output.open(filename);

            // Write some frames
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            try {
                image = ImageIO.read(new File("test.jpg"));
            } catch (IOException e) {
                Assert.fail("Could not read test image");
            }

            double pts = 0.0;
            for (int i = 0; i < 300; ++i)
            {
                output.addVideoFrame(new VideoFrame(image, pts));
                pts += frameDuration;
            }

            // Note: output.close() is automatically called by using try-with-resources

        } catch (IOException e)
        {
            logger.error("Failed to write file" , e);
            Assert.fail("Failed to write file");
        }

        try (IVideoFileInput input = VideoSystem.createInputFile())
        {
            input.open(filename);
            Assert.assertTrue(input.isOpen());
            Assert.assertEquals(input.getDuration(), 10.0);
            Assert.assertEquals(input.getNumFrames(), 300);

        } catch (IOException e)
        {
            logger.error("Failed to read file", e);
            Assert.fail("Failed to read file");
        }
    }

    @Test
    public void testWithData()
    {
        final String filename = "testWithData.ts";
        final int width = 640;
        final int height = 480;
        final int bitRate = 500_000;
        final double frameRate = 15.0;
        final int gopSize = 30;
        final double frameDuration = 1.0 / frameRate;

        try (IVideoFileOutput output = VideoSystem.createOutputFile(
                new VideoOutputOptions(width, height, bitRate, frameRate, gopSize)))
        {
            output.open(filename);

            // Write some frames
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            try {
                image = ImageIO.read(new File("test.jpg"));
            } catch (IOException e) {
                Assert.fail("Could not read test image");
            }

            double pts = 0.0;
            for (int i = 0; i < 120; ++i)
            {
                SortedMap<UasDatalinkTag, IUasDatalinkValue> values = new TreeMap<>();

                values.put(UasDatalinkTag.PrecisionTimeStamp, new PrecisionTimeStamp(LocalDateTime.now()));

                values.put(UasDatalinkTag.MissionId, new UasDatalinkString("Unit Testing"));
                values.put(UasDatalinkTag.PlatformDesignation, new UasDatalinkString("Thunderbolt"));
                values.put(UasDatalinkTag.ImageSourceSensor, new UasDatalinkString("DTV"));
                values.put(UasDatalinkTag.ImageCoordinateSystem, new UasDatalinkString("Geodetic WGS84"));

                values.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(42.4036));
                values.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(-71.1284));
                values.put(UasDatalinkTag.SensorTrueAltitude, new SensorTrueAltitude(1258.3));

                values.put(UasDatalinkTag.PlatformHeadingAngle, new PlatformHeadingAngle(10.0));
                values.put(UasDatalinkTag.PlatformPitchAngle, new PlatformPitchAngle(1.0));
                values.put(UasDatalinkTag.PlatformRollAngle, new PlatformRollAngle(-1.0));

                values.put(UasDatalinkTag.SensorRelativeAzimuthAngle, new SensorRelativeAzimuth(330.0));
                values.put(UasDatalinkTag.SensorRelativeElevationAngle, new SensorRelativeElevation(-70.0));
                values.put(UasDatalinkTag.SensorRelativeRollAngle, new SensorRelativeRoll(0.0));

                values.put(UasDatalinkTag.SensorHorizontalFov, new HorizontalFov(8.0));
                values.put(UasDatalinkTag.SensorVerticalFov, new VerticalFov(5.0));

                values.put(UasDatalinkTag.SlantRange, new SlantRange(2000.0));
                values.put(UasDatalinkTag.TargetWidth, new TargetWidth(100.0));

                values.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(42.1032));
                values.put(UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(-71.2382));
                values.put(UasDatalinkTag.FrameCenterElevation, new FrameCenterElevation(12.0));

                values.put(UasDatalinkTag.SecurityLocalMetadataSet, new NestedSecurityMetadata(getSecurityLs()));
                values.put(UasDatalinkTag.UasLdsVersionNumber, new ST0601Version((byte)11));

                UasDatalinkMessage message = new UasDatalinkMessage(values);

                output.addVideoFrame(new VideoFrame(image, pts));
                output.addMetadataFrame(new MetadataFrame(message, pts));
                pts += frameDuration;
            }

        } catch (IOException e)
        {
            logger.error("Failed to write file" , e);
            Assert.fail("Failed to write file");
        }

        try (IVideoFileInput input = VideoSystem.createInputFile())
        {
            input.open(filename);
            Assert.assertTrue(input.isOpen());
            Assert.assertEquals(input.getDuration(), 8.0);
            Assert.assertEquals(input.getNumFrames(), 120);

        } catch (IOException e)
        {
            logger.error("Failed to read file", e);
            Assert.fail("Failed to read file");
        }
    }

    private SecurityMetadataLocalSet getSecurityLs()
    {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(SecurityMetadataKey.SecurityClassification, new ClassificationLocal(Classification.UNCLASSIFIED));

        values.put(SecurityMetadataKey.CcCodingMethod, new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ClassifyingCountry, new SecurityMetadataString("//US"));

        values.put(SecurityMetadataKey.OcCodingMethod, new CcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new SecurityMetadataString("US;CA"));

        values.put(SecurityMetadataKey.Version, new ST0102Version(12));

        return new SecurityMetadataLocalSet(values);
    }
}
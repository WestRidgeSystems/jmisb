package org.jmisb.api.video;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_BGR24;
import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_YUV420P;
import static org.bytedeco.ffmpeg.global.avutil.av_frame_alloc;
import static org.bytedeco.ffmpeg.global.avutil.av_image_fill_arrays;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.PointerPointer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FrameConverterTest extends LoggerChecks {

    public FrameConverterTest() {
        super(FrameConverter.class);
    }

    @Test
    public void basicConstruct() {
        FrameConverter frameConverter = new FrameConverter();
        Assert.assertNotNull(frameConverter);
    }

    @Test
    public void convertCheck() {
        BufferedImage sourceImage = new BufferedImage(1024, 960, TYPE_3BYTE_BGR);
        Graphics2D graphics = sourceImage.createGraphics();
        graphics.setPaint(Color.CYAN);
        graphics.fillRect(0, 0, sourceImage.getWidth(), sourceImage.getHeight());
        DataBuffer dataBuffer = sourceImage.getRaster().getDataBuffer();
        BytePointer pixelData = new BytePointer(((DataBufferByte) dataBuffer).getData());
        AVFrame avFrame = av_frame_alloc();
        avFrame.format(AV_PIX_FMT_BGR24);
        avFrame.width(sourceImage.getWidth());
        avFrame.height(sourceImage.getHeight());
        av_image_fill_arrays(
                new PointerPointer(avFrame),
                avFrame.linesize(),
                pixelData,
                AV_PIX_FMT_BGR24,
                sourceImage.getWidth(),
                sourceImage.getHeight(),
                1);
        FrameConverter frameConverter = new FrameConverter();
        BufferedImage resultImage = frameConverter.convert(avFrame);
        Assert.assertEquals(sourceImage.getWidth(), resultImage.getWidth());
        Assert.assertEquals(sourceImage.getHeight(), resultImage.getHeight());
        for (int y = 0; y < sourceImage.getHeight(); y++) {
            for (int x = 0; x < sourceImage.getWidth(); x++) {
                Assert.assertEquals(sourceImage.getRGB(x, y), resultImage.getRGB(x, y));
            }
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void convertNullFrame() {
        FrameConverter frameConverter = new FrameConverter();
        frameConverter.convert(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void convertWrongFrameFormat() {
        FrameConverter frameConverter = new FrameConverter();
        AVFrame avFrame = new AVFrame();
        avFrame.format(AV_PIX_FMT_YUV420P);
        frameConverter.convert(avFrame);
    }
}

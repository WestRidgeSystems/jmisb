package org.jmisb.api.video;

import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_BGR24;

import java.awt.image.*;
import java.nio.ByteBuffer;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Convert video frames between AVFrame and BufferedImage. */
public class FrameConverter {
    private static final Logger logger = LoggerFactory.getLogger(FrameConverter.class);

    private BufferedImage bufferedImage;

    /**
     * Convert an AVFrame to a BufferedImage.
     *
     * @param frame The AVFrame; must be 3-byte BGR format
     * @return The BufferedImage
     */
    public BufferedImage convert(AVFrame frame) {
        if (frame == null) {
            throw new IllegalArgumentException("Input frame cannot be null");
        }

        if (frame.format() != AV_PIX_FMT_BGR24) {
            throw new IllegalArgumentException("Input format must be BGR24");
        }

        // Allocate bufferedImage if needed
        if (bufferedImage == null
                || bufferedImage.getWidth() != frame.width()
                || bufferedImage.getHeight() != frame.height()) {
            logger.debug("Allocating buffer of size " + frame.width() + "x" + frame.height());
            bufferedImage =
                    new BufferedImage(frame.width(), frame.height(), BufferedImage.TYPE_3BYTE_BGR);
        }

        // Output buffer
        SampleModel sm = bufferedImage.getSampleModel();
        Raster r = bufferedImage.getRaster();
        DataBuffer out = r.getDataBuffer();

        int x = -r.getSampleModelTranslateX();
        int y = -r.getSampleModelTranslateY();
        int step = ((ComponentSampleModel) sm).getScanlineStride();
        int channels = ((ComponentSampleModel) sm).getPixelStride();

        int start = y * step + x * channels;

        byte[] a = ((DataBufferByte) out).getData();

        ByteBuffer src =
                frame.data(0).capacity((long) frame.height() * (long) frame.linesize(0)).asBuffer();
        copy(src, frame.linesize(0), ByteBuffer.wrap(a, start, a.length - start), step);

        return bufferedImage;
    }

    private static void copy(ByteBuffer srcBuf, int srcStep, ByteBuffer dstBuf, int dstStep) {
        assert srcBuf != dstBuf;
        int srcLine = srcBuf.position();
        int dstLine = dstBuf.position();
        final int w = Math.min(srcStep, dstStep);

        while (srcLine < srcBuf.capacity() && dstLine < dstBuf.capacity()) {
            srcBuf.position(srcLine);
            dstBuf.position(dstLine);

            ByteBuffer srcRow = srcBuf.slice();
            ByteBuffer dstRow = dstBuf.slice();

            srcRow.limit(w);
            dstRow.limit(w);

            dstRow.put(srcRow);

            srcLine += srcStep;
            dstLine += dstStep;
        }
    }
}

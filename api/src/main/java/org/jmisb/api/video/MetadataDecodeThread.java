package org.jmisb.api.video;

import static org.bytedeco.ffmpeg.global.avcodec.av_packet_clone;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_alloc_context3;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_free_context;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_parameters_to_context;
import static org.bytedeco.ffmpeg.global.avutil.av_q2d;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvParser;
import org.jmisb.core.klv.ArrayUtils;
import org.jmisb.core.video.FfmpegUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Metadata decoding thread
 *
 * <p>This thread decodes KLV metadata and sends {@link IMisbMessage}s up to the {@link VideoInput}.
 */
class MetadataDecodeThread extends ProcessingThread {
    private static Logger logger = LoggerFactory.getLogger(MetadataDecodeThread.class);
    private static final int INPUT_QUEUE_SIZE = 100;
    private final VideoInput inputStream;
    private final AVStream dataStream;
    private BlockingQueue<AVPacket> packetQueue = new LinkedBlockingDeque<>(INPUT_QUEUE_SIZE);

    /**
     * Constructor
     *
     * @param inputStream The {@link VideoInput}
     * @param dataStream The metadata stream
     */
    MetadataDecodeThread(VideoInput inputStream, AVStream dataStream) {
        this.inputStream = inputStream;
        this.dataStream = dataStream;
        start();
    }

    /**
     * Enqueue an incoming packet for decoding
     *
     * @param packet The packet to queue
     * @return True if the packet was queued, false if the queue is currently full
     */
    public boolean enqueue(AVPacket packet) {
        try {
            return packetQueue.offer(av_packet_clone(packet), 10, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignored) {
            return false;
        }
    }

    public void clear() {
        // Clear out our input queue
        packetQueue.clear();
    }

    @Override
    public void run() {
        Thread.currentThread().setName("MetadataDecodeThread - " + inputStream.getUrl());

        AVCodecContext codecContext = avcodec_alloc_context3(null);
        int ret;
        if ((ret = avcodec_parameters_to_context(codecContext, dataStream.codecpar())) < 0) {
            logger.error(
                    "Couldn't create AVCodecContext for stream "
                            + dataStream.index()
                            + " codec: "
                            + dataStream.codecpar().codec_tag()
                            + " error "
                            + FfmpegUtils.formatError(ret));
            avcodec_free_context(codecContext);
            return;
        }

        while (!isShutdown()) {
            // If paused, sleep until play() or shutdown() is called
            if (pauseOrResume()) {
                // Either we were interrupted, or shutdown() was called
                break;
            }

            try {
                AVPacket packet = packetQueue.poll(10, TimeUnit.MILLISECONDS);
                if (packet != null) {
                    double pts = packet.pts() * av_q2d(dataStream.time_base());
                    // logger.debug("Data PTS = " + pts);

                    byte[] data = new byte[packet.size()];
                    packet.data().get(data);

                    try {
                        List<IMisbMessage> messages = KlvParser.parseBytes(data);
                        for (IMisbMessage message : messages) {
                            boolean queued = false;
                            while (!queued && !isShutdown() && !isPauseRequested()) {
                                queued =
                                        inputStream.queueMetadataFrame(
                                                new MetadataFrame(message, pts), 20);
                            }
                            if (isShutdown() || isPauseRequested()) break;
                        }
                    } catch (KlvParseException exception) {
                        logger.error("KLV parse exception", exception);
                        if (logger.isDebugEnabled()) {
                            logger.debug(ArrayUtils.toHexString(data));
                        }
                    }
                }
            } catch (InterruptedException ignored) {
            }
        }

        if (logger.isDebugEnabled()) logger.debug("Data stream decoder exiting");

        avcodec_free_context(codecContext);
    }

    public void notifyEOF() {
        if (inputStream instanceof IVideoFileInput) {
            IVideoFileInput fileInputStream = (IVideoFileInput) inputStream;
            fileInputStream.notifyEOF();
        }
    }
}

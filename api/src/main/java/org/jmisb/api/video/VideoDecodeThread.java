package org.jmisb.api.video;

import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.swscale.SwsContext;
import org.bytedeco.javacpp.*;
import org.jmisb.core.video.FfmpegUtils;
import org.jmisb.core.video.FrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.bytedeco.ffmpeg.global.avcodec.av_packet_clone;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_alloc_context3;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_find_decoder;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_flush_buffers;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_free_context;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_open2;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_parameters_to_context;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_receive_frame;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_send_packet;
import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_BGR24;
import static org.bytedeco.ffmpeg.global.avutil.av_dict_free;
import static org.bytedeco.ffmpeg.global.avutil.av_frame_alloc;
import static org.bytedeco.ffmpeg.global.avutil.av_frame_free;
import static org.bytedeco.ffmpeg.global.avutil.av_image_fill_arrays;
import static org.bytedeco.ffmpeg.global.avutil.av_image_get_buffer_size;
import static org.bytedeco.ffmpeg.global.avutil.av_malloc;
import static org.bytedeco.ffmpeg.global.avutil.av_q2d;
import static org.bytedeco.ffmpeg.global.swscale.SWS_FAST_BILINEAR;
import static org.bytedeco.ffmpeg.global.swscale.sws_getContext;
import static org.bytedeco.ffmpeg.global.swscale.sws_scale;

/**
 * Video decoding thread
 * <p>
 * This thread buffers and decodes video data, and sends uncompressed images in BGR24 format back up to the
 * {@link VideoInput}.
 */
class VideoDecodeThread extends ProcessingThread
{
    private static Logger logger = LoggerFactory.getLogger(VideoDecodeThread.class);
    private final static int INPUT_QUEUE_SIZE = 100;
    private final VideoInput inputStream;
    private final AVStream videoStream;
    private AVCodecContext codecContext;
    private BlockingQueue<AVPacket> packetQueue = new LinkedBlockingDeque<>(INPUT_QUEUE_SIZE);

    /**
     * Image buffer in native stream format
     */
    private AVFrame nativeFrame;

    /**
     * Image buffer in BGR24 format
     */
    private AVFrame bgrFrame;

    private final FrameConverter frameConverter = new FrameConverter();

    VideoDecodeThread(VideoInput inputStream, AVStream videoStream)
    {
        this.inputStream = inputStream;
        this.videoStream = videoStream;
        start();
    }

    /**
     * Enqueue an incoming packet for decoding
     *
     * @param packet The packet to queue
     * @return True if the packet was queued, false if the queue is currently full
     */
    public boolean enqueue(AVPacket packet)
    {
        try
        {
            return packetQueue.offer(av_packet_clone(packet), 10, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignored)
        {
            return false;
        }
    }

    /**
     * Clear the queue of packets to be decoded and flush codec buffers
     */
    public void clear()
    {
        packetQueue.clear();
        avcodec_flush_buffers(codecContext);
    }

    @Override
    public void run()
    {
        Thread.currentThread().setName("VideoDecodeThread - " + inputStream.getUrl());

        AVCodec codec = avcodec_find_decoder(videoStream.codecpar().codec_id());
        if (codec == null)
        {
            logger.error(
                    "avcodec_find_decoder() error: Unsupported video format or codec not found: "
                            + videoStream.codecpar().codec_id() + ".");
            return;
        }

        // Open video codec
        AVDictionary opts = new AVDictionary(null);
        codecContext = avcodec_alloc_context3(codec);
        if (codecContext == null)
        {
            logger.error("Could not allocate codec context");
            return;
        }
        if (avcodec_parameters_to_context(codecContext, videoStream.codecpar()) < 0)
        {
            logger.error("Couldn't create AVCodecContext for video stream");
            avcodec_free_context(codecContext);
            return;
        }

        int ret;
        if ((ret = avcodec_open2(codecContext, codec, opts)) < 0)
        {
            logger.error("avcodec_open2() error " + ret + ": Could not open video codec.");
            return;
        }
        av_dict_free(opts);

        // Hack to correct wrong frame rates that seem to be generated by some codecs
        if (codecContext.time_base().num() > 1000 && codecContext.time_base().den() == 1)
        {
            codecContext.time_base().den(1000);
        }

        // Allocate image buffers
        allocateImages(videoStream.codecpar().width(), videoStream.codecpar().height());

        // Allocate SwsContext used for color conversion/scaling
        SwsContext swsContext;
        swsContext = sws_getContext(
                videoStream.codecpar().width(),
                videoStream.codecpar().height(),
                codecContext.pix_fmt(),
                bgrFrame.width(),
                bgrFrame.height(),
                bgrFrame.format(),
                SWS_FAST_BILINEAR,
                null,
                null,
                (DoublePointer) null);

        AVFrame avFrame = av_frame_alloc();

        while (!isShutdown())
        {
            // If paused, sleep until play() or shutdown() is called
            if (pauseOrResume())
            {
                // Either we were interrupted, or shutdown() was called
                break;
            }

            try
            {
                AVPacket packet = packetQueue.poll(10, TimeUnit.MILLISECONDS);
                if (packet != null)
                {
                    // Send the packet to the decoder
                    if ((ret = avcodec_send_packet(codecContext, packet)) < 0)
                    {
                        logger.error("avcodec_send_packet error " + FfmpegUtils.formatError(ret));
                    }

                    // Check for decoded frames
                    ret = avcodec_receive_frame(codecContext, avFrame);
                    if (ret >= 0)
                    {
                        //long pts = av_frame_get_best_effort_timestamp(avFrame);
                        double pts = packet.pts() * av_q2d(videoStream.time_base());
//                        logger.debug("Video PTS = " + pts);

                        // Convert image from native pixel format to BGR24
                        sws_scale(swsContext,
                                new PointerPointer(avFrame), avFrame.linesize(), 0, codecContext.height(),
                                bgrFrame.data(), bgrFrame.linesize());

                        // FrameConverter will internally cache the BufferedImage and re-use it for each call
                        BufferedImage image = frameConverter.convert(bgrFrame);

                        // TODO: on a seek, the final frame before the seek often gets hung up here
                        boolean queued = false;
                        while (!queued && !isShutdown() && !isPauseRequested())
                        {
                            queued = inputStream.queueVideoFrame(new VideoFrame(image, pts), 20);
                        }
                    }
                    else if (ret != -11 && ret != -35) // -11 = EAGAIN, -35 = EDEADLK
                    {
                        // -11 is expected and just means the decoder is waiting for more packets
                        // -35 seems to be warning us about some concurrency issue unique to MacOS
                        logger.error("avcodec_receive_frame error " + FfmpegUtils.formatError(ret));
                    }
                }
            }
            catch (InterruptedException ignored)
            {
            }
        }

        if (logger.isDebugEnabled())
            logger.debug("Video decoder exiting");

        // Clean up resources
        avcodec_free_context(codecContext);
        av_frame_free(avFrame);

        deallocateImages();
    }

    private void allocateImages(int width, int height)
    {
        deallocateImages();

        // Allocate video frame and an AVFrame structure for the BGR image
        if ((nativeFrame = av_frame_alloc()) == null)
        {
            logger.error("av_frame_alloc() error: Could not allocate native frame");
            // TODO: handle
        }
        if ((bgrFrame = av_frame_alloc()) == null)
        {
            logger.error("av_frame_alloc() error: Could not allocate BGR frame");
            // TODO: handle
        }
        else
        {
            bgrFrame.format(AV_PIX_FMT_BGR24);
            bgrFrame.width(width);
            bgrFrame.height(height);

            // Determine required buffer size and allocate buffer
            int size = av_image_get_buffer_size(AV_PIX_FMT_BGR24, width, height, 32);
            BytePointer bgrFrameBufferPointer = new BytePointer(av_malloc(size)).capacity(size);

            // Populate fields of bgrFrame
            PointerPointer pp = new PointerPointer(bgrFrame);
            IntPointer lineSize = new IntPointer(bgrFrame.linesize());
            av_image_fill_arrays(pp, lineSize, bgrFrameBufferPointer, bgrFrame.format(), bgrFrame.width(), bgrFrame.height(), 32);
        }
    }

    private void deallocateImages()
    {
        if (bgrFrame != null)
        {
            av_frame_free(bgrFrame);
            bgrFrame = null;
        }

        // Free the native format nativeFrame frame
        if (nativeFrame != null)
        {
            av_frame_free(nativeFrame);
            nativeFrame = null;
        }
    }

    public void notifyEOF()
    {
        if (inputStream instanceof IVideoFileInput)
        {
            IVideoFileInput fileInputStream = (IVideoFileInput)inputStream;
            fileInputStream.notifyEOF();
        }
    }
}

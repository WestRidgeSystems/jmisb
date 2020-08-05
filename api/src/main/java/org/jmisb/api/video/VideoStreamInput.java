package org.jmisb.api.video;

import static org.bytedeco.ffmpeg.global.avcodec.avcodec_find_decoder;
import static org.bytedeco.ffmpeg.global.avformat.av_dump_format;
import static org.bytedeco.ffmpeg.global.avformat.avformat_alloc_context;
import static org.bytedeco.ffmpeg.global.avformat.avformat_find_stream_info;
import static org.bytedeco.ffmpeg.global.avformat.avformat_open_input;
import static org.bytedeco.ffmpeg.global.avutil.av_dict_free;
import static org.bytedeco.ffmpeg.global.avutil.av_dict_set;

import java.io.IOException;
import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.javacpp.*;
import org.jmisb.core.video.FfmpegUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Manages an incoming video stream. */
public class VideoStreamInput extends VideoInput implements IVideoStreamInput {
    private static final Logger logger = LoggerFactory.getLogger(VideoStreamInput.class);
    private final VideoStreamInputOptions options;
    private StreamDemuxer demuxer;
    private boolean open = false;

    /** Construct with default options. */
    public VideoStreamInput() {
        this(new VideoStreamInputOptions());
    }

    /**
     * Construct with options.
     *
     * @param options Options for video input
     */
    public VideoStreamInput(VideoStreamInputOptions options) {
        this.options = options;
    }

    @Override
    public void open(String url) throws IOException {
        this.url = url;
        logger.debug("Opening " + url + "...");
        formatContext = avformat_alloc_context();

        // Open the input stream
        AVDictionary openOpts = new AVDictionary(null);
        String timeoutVal = "" + options.getOpenTimeout() * 1000;
        if (url.startsWith("rtsp://")) {
            av_dict_set(openOpts, "stimeout", timeoutVal, 0);
        } else {
            av_dict_set(openOpts, "timeout", timeoutVal, 0);
        }
        int ret = avformat_open_input(formatContext, url, null, openOpts);
        av_dict_free(openOpts);
        if (ret < 0) {
            freeContext();
            throw new IOException("Could not open input " + url);
        }

        // Retrieve stream information- this scans the stream and tries to pull out stuff like
        // codec, frame rate, etc.

        // Analysis can take a while, particularly for streams with long keyframe intervals; clients
        // should adjust analyze duration appropriately
        //
        formatContext.max_analyze_duration(options.getMaxAnalyzeDuration() * 1000);

        if ((ret = avformat_find_stream_info(formatContext, (PointerPointer) null)) < 0) {
            // If you are getting these, try increasing the timeout above
            freeContext();
            throw new IOException(
                    "avformat_find_stream_info() error "
                            + ret
                            + ": Could not find stream information.");
        }

        // Dump information about the format onto standard error
        av_dump_format(formatContext, 0, url, 0);

        // Find the video, audio, and data streams, if present
        AVStream videoStream = FfmpegUtils.getVideoStream(formatContext);

        // Require a valid video stream
        if (videoStream == null) {
            freeContext();
            throw new IOException("Did not find a video stream within URL: " + url);
        }

        // Find the decoder for the video stream to ensure we can decode it
        AVCodec codec = avcodec_find_decoder(videoStream.codecpar().codec_id());
        if (codec == null) {
            freeContext();
            throw new IOException(
                    "avcodec_find_decoder() error: Unsupported video format or codec not found: "
                            + videoStream.codecpar().codec_id()
                            + ".");
        }

        // Create the demuxer and start demuxing
        demuxer = new StreamDemuxer(this, formatContext, options);
        demuxer.start();

        // Start notifier threads
        startNotifiers(false);

        open = true;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void close() {
        logger.debug("Closing " + url);

        if (isOpen()) {
            stopStreamDemuxer();
            stopNotifiers();
            freeContext();
            open = false;
        }
    }

    @Override
    public VideoStreamInputOptions getOptions() {
        return options;
    }

    @Override
    protected void delayVideo(double pts) {
        // No-op
    }

    @Override
    protected void delayMetadata(double pts) {
        // No-op
    }

    /** Stop the demuxer thread. */
    private void stopStreamDemuxer() {
        demuxer.shutdown();
        try {
            demuxer.join();
        } catch (InterruptedException e) {
            logger.warn("Interrupted while joining demuxer thread", e);
        }
    }
}

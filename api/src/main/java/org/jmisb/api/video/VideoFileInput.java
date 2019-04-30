package org.jmisb.api.video;

import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.javacpp.PointerPointer;
import org.jmisb.core.video.FfmpegUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.bytedeco.ffmpeg.global.avcodec.avcodec_find_decoder;
import static org.bytedeco.ffmpeg.global.avformat.AVSEEK_FLAG_BACKWARD;
import static org.bytedeco.ffmpeg.global.avformat.av_read_frame;
import static org.bytedeco.ffmpeg.global.avformat.avformat_alloc_context;
import static org.bytedeco.ffmpeg.global.avformat.avformat_find_stream_info;
import static org.bytedeco.ffmpeg.global.avformat.avformat_flush;
import static org.bytedeco.ffmpeg.global.avformat.avformat_open_input;
import static org.bytedeco.ffmpeg.global.avformat.avformat_seek_file;
import static org.bytedeco.ffmpeg.global.avutil.AVERROR_EOF;
import static org.jmisb.core.video.TimingUtils.shortWait;

/**
 * Read video/metadata from a file
 */
public class VideoFileInput extends VideoInput implements IVideoFileInput
{
    private static Logger logger = LoggerFactory.getLogger(VideoFileInput.class);
    private final VideoFileInputOptions options;
    private FileDemuxer demuxer;
    private boolean open = false;
    private boolean playing = false;
    private double position = 0.0;
    private double duration = 0.0;
    private int numFrames = 0;

    private double rateMultiplier = 1.0;

    // TODO: put these in a utility class
    private long prevVideoTime;
    private double prevVideoPts;
    private long videoDelay;

    /**
     * Clients must use {@link VideoSystem#createInputFile()} to construct new instances
     */
    VideoFileInput(VideoFileInputOptions options)
    {
        this.options = options;
    }

    @Override
    public void open(String url) throws IOException
    {
        this.url = url;
        logger.debug("Opening " + url + "...");

        formatContext = avformat_alloc_context();

        // Open the file
        int ret = avformat_open_input(formatContext, url, null, null);
        if (ret < 0)
        {
            freeContext();
            throw new IOException("Could not open input " + url);
        }

        // Retrieve stream information- this scans the stream and tries to pull out stuff like codec,
        // frame rate, etc.
        if ((ret = avformat_find_stream_info(formatContext, (PointerPointer) null)) < 0)
        {
            freeContext();
            throw new IOException(
                    "avformat_find_stream_info() error " + ret + ": Could not find stream information.");
        }

        // Dump information about the format onto standard error
        // av_dump_format(formatContext, 0, url, 0);

        // Find the video, audio, and data streams, if present
        AVStream videoStream = FfmpegUtils.getVideoStream(formatContext);

        duration = FfmpegUtils.getDuration(formatContext);

        // Require a valid video stream
        if (videoStream == null)
        {
            freeContext();
            throw new IOException("Did not find a video stream within URL: " + url);
        }

        // Find the decoder for the video stream to ensure we can decode it
        AVCodec codec = avcodec_find_decoder(videoStream.codecpar().codec_id());
        if (codec == null)
        {
            freeContext();
            throw new IOException(
                    "avcodec_find_decoder() error: Unsupported video format or codec not found: "
                            + videoStream.codecpar().codec_id() + ".");
        }

        // Analyze frames
        numFrames = countFrames(videoStream);

        // Create the demuxer and start thread
        demuxer = new FileDemuxer(this, formatContext);
        demuxer.start();

        // Start notifier threads
        playing = !options.isInitiallyPaused();
        startNotifiers(options.isInitiallyPaused());

        open = true;
    }

    private int countFrames(AVStream videoStream)
    {
        // Loop through all frames, counting only video frames
        int numFrames = 0;
        AVPacket packet = new AVPacket();
        while (true)
        {
            int ret;
            if ((ret = av_read_frame(formatContext, packet)) < 0)
            {
                if (ret != AVERROR_EOF)
                {
                    logger.error("Error reading frame before EOF");
                }
                break;
            }
            if (packet.stream_index() == videoStream.index())
            {
                numFrames++;
            }
        }

        // Seek back to start of file
        int ret;
        if ((ret = avformat_seek_file(formatContext, -1, Long.MIN_VALUE, 0, Long.MAX_VALUE,
                AVSEEK_FLAG_BACKWARD)) < 0)
        {
            logger.error("Error seeking to start of file");
        }
        avformat_flush(formatContext);

        return numFrames;
    }

    @Override
    public boolean isOpen()
    {
        return open;
    }

    @Override
    public void close()
    {
        logger.debug("Closing " + url);

        if (isOpen())
        {
            stopFileDemuxer();
            stopNotifiers();
            freeContext();
            open = false;
        }
    }

    @Override
    public void play()
    {
        if (logger.isDebugEnabled())
            logger.debug("Playing " + url);

        // Cause videoDelay to be reset back to default
        prevVideoTime = 0;

        videoNotifier.resumeOutput();
        metadataNotifier.resumeOutput();

        playing = true;
    }

    @Override
    public boolean isPlaying()
    {
        return playing;
    }

    @Override
    public void setPlaybackSpeed(double multiplier)
    {
        if (multiplier <= 0)
        {
            throw new IllegalArgumentException("Multiplier must be greater than zero");
        }
        this.rateMultiplier = multiplier;
    }

    @Override
    public double getPlaybackSpeed()
    {
        return this.rateMultiplier;
    }

    @Override
    public void pause()
    {
        if (logger.isDebugEnabled())
            logger.debug("Pausing " + url);

        videoNotifier.pauseOutput();
        metadataNotifier.pauseOutput();

        playing = false;
    }

    @Override
    public double getDuration()
    {
        return duration;
    }

    @Override
    public double getPosition()
    {
        return position;
    }

    @Override
    public void seek(double pos)
    {
        if (pos < 0 || pos > getDuration())
        {
            throw new IllegalArgumentException("Invalid position");
        }

        if (logger.isDebugEnabled())
            logger.debug("Seeking to " + pos + "s");

        // Seek the demuxer, this will also cause packet queues to be cleared
        demuxer.seek(pos);

        // Stop notifiers & clear decodedFrameQueue & decodedMetadataQueue
        stopNotifiers();

        // Resume notifiers
        startNotifiers(true);

        // Get one frame even if we are paused
        sendOneFrame();

        // Reset for computing delay
        prevVideoTime = 0;
    }

    @Override
    public int getNumFrames()
    {
        return numFrames;
    }

    @Override
    protected void delayVideo(double pts)
    {
        // TODO: deal with discontinuities, e.g., from seeking
        long time = System.currentTimeMillis();

        long frameDuration = Math.round(1000.0 / (demuxer.getVideoFrameRate() * rateMultiplier));

        if (prevVideoTime != 0)
        {
            long elapsedTimeMs = time - prevVideoTime;

            long diff = frameDuration - elapsedTimeMs;
            videoDelay += diff / 2;

            videoDelay = Math.min(frameDuration, videoDelay);
            videoDelay = Math.max(0, videoDelay);
        } else
        {
            videoDelay = frameDuration;
        }

//        logger.debug("delay: " + videoDelay);
        if (videoDelay > 0)
            shortWait(videoDelay);

        prevVideoPts = pts;
        prevVideoTime = time;

        // TODO: good?
        position = prevVideoPts;

        //logger.debug("Setting position = " + prevVideoPts);
    }

    @Override
    protected void delayMetadata(double pts) throws InterruptedException
    {
        // Just sync to the video based on PTS
        while (pts > prevVideoPts)
        {
            Thread.sleep(10);
        }
    }

    /**
     * Stop the demuxer thread
     */
    private void stopFileDemuxer()
    {
        demuxer.shutdown();
        try
        {
            demuxer.join();
        } catch (InterruptedException e)
        {
            logger.warn("Interrupted while joining demuxer thread", e);
        }
    }
}

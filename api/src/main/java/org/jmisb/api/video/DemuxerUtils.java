package org.jmisb.api.video;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avformat;
import org.bytedeco.javacpp.avutil;
import org.jmisb.core.video.FfmpegUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.bytedeco.javacpp.avformat.*;
import static org.bytedeco.javacpp.avutil.AVERROR_EOF;

/**
 * Utility methods for demuxing
 */
public class DemuxerUtils
{
    private static Logger logger = LoggerFactory.getLogger(DemuxerUtils.class);

    private DemuxerUtils() {}

    /**
     * Create a VideoDecodeThread
     *
     * @param videoStreamIndex Index of the video stream
     * @param avFormatContext The format context
     * @param inputStream The input stream
     * @return A new VideoDecodeThread, or null if one could not be created
     */
    static VideoDecodeThread createVideoDecodeThread(int videoStreamIndex, avformat.AVFormatContext avFormatContext, VideoInput inputStream)
    {
        if (videoStreamIndex >= 0)
        {
            avformat.AVStream videoStream = FfmpegUtils.getVideoStream(avFormatContext);
            VideoDecodeThread videoDecodeThread = new VideoDecodeThread(inputStream, videoStream);

            if (logger.isDebugEnabled())
            {
                avutil.AVRational frameRate = av_guess_frame_rate(avFormatContext, videoStream, null);
                logger.debug("Detected frame rate = " + frameRate.num() + " / " + frameRate.den());
            }

            return videoDecodeThread;
        }

        return null;
    }

    /**
     * Read the next packet
     *
     * @param avFormatContext The format context
     * @param packet The packet
     * @return True if a packet was read, false otherwise
     */
    static boolean readPacket(avformat.AVFormatContext avFormatContext, avcodec.AVPacket packet)
    {
        int ret;
        if ((ret = av_read_frame(avFormatContext, packet)) < 0)
        {
            if (ret == AVERROR_EOF)
            {
                logger.debug("EOF packet received: " + FfmpegUtils.formatError(ret));
                return false;
            } else if (ret != -11)
            {
                logger.error("av_read_frame returned an error: " + FfmpegUtils.formatError(ret));
                return false;
            } else
            {
                // AVERROR(EAGAIN) == -11, just need to wait a moment and try again
                return false;
            }
        }
        return true;
    }

    /**
     * Seek to the specified position
     *
     * @param avFormatContext The format context
     * @param seekPosition The position
     */
    static void seek(avformat.AVFormatContext avFormatContext, double seekPosition)
    {
        long microseconds = Math.round(seekPosition * 1000000);
        if (logger.isDebugEnabled())
        {
            logger.debug("Seeking to " + microseconds + "us");
        }

        int ret;
        if ((ret = avformat_seek_file(avFormatContext, -1, Long.MIN_VALUE, microseconds, Long.MAX_VALUE,
                AVSEEK_FLAG_BACKWARD)) < 0)
        {
            logger.error("Error seeking to " + seekPosition);
        }
        avformat_flush(avFormatContext);
    }
}

package org.jmisb.api.video;

import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.jmisb.core.video.FfmpegUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.bytedeco.ffmpeg.global.avformat.AVSEEK_FLAG_BACKWARD;
import static org.bytedeco.ffmpeg.global.avformat.av_guess_frame_rate;
import static org.bytedeco.ffmpeg.global.avformat.av_read_frame;
import static org.bytedeco.ffmpeg.global.avformat.avformat_flush;
import static org.bytedeco.ffmpeg.global.avformat.avformat_seek_file;
import static org.bytedeco.ffmpeg.global.avutil.AVERROR_EOF;

/**
 * Utility methods for demuxing
 */
class DemuxerUtils
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
    static VideoDecodeThread createVideoDecodeThread(int videoStreamIndex, AVFormatContext avFormatContext, VideoInput inputStream)
    {
        if (videoStreamIndex >= 0)
        {
            AVStream videoStream = FfmpegUtils.getVideoStream(avFormatContext);
            VideoDecodeThread videoDecodeThread = new VideoDecodeThread(inputStream, videoStream);

            if (logger.isDebugEnabled())
            {
                AVRational frameRate = av_guess_frame_rate(avFormatContext, videoStream, null);
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
    static boolean readPacket(AVFormatContext avFormatContext, AVPacket packet)
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
    static void seek(AVFormatContext avFormatContext, double seekPosition)
    {
        long microseconds = Math.round(seekPosition * 1000000);
        if (logger.isDebugEnabled())
        {
            logger.debug("Seeking to " + microseconds + "us");
        }

        if (avformat_seek_file(avFormatContext, -1, Long.MIN_VALUE, microseconds, Long.MAX_VALUE,
                AVSEEK_FLAG_BACKWARD) < 0)
        {
            logger.error("Error seeking to " + seekPosition);
        }
        avformat_flush(avFormatContext);
    }
}

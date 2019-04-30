package org.jmisb.core.video;

import org.bytedeco.ffmpeg.avcodec.AVCodecDescriptor;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.avutil.AVRational;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.bytedeco.ffmpeg.global.avcodec.avcodec_descriptor_get;
import static org.bytedeco.ffmpeg.global.avformat.av_guess_frame_rate;
import static org.bytedeco.ffmpeg.global.avutil.AVMEDIA_TYPE_DATA;
import static org.bytedeco.ffmpeg.global.avutil.AVMEDIA_TYPE_VIDEO;
import static org.bytedeco.ffmpeg.global.avutil.AV_TIME_BASE;
import static org.bytedeco.ffmpeg.global.avutil.av_q2d;
import static org.bytedeco.ffmpeg.global.avutil.av_strerror;

/**
 * Utility methods for common FFmpeg operations
 */
public class FfmpegUtils
{
    private FfmpegUtils() {}

    /**
     * Get the string for a given FFmpeg error code
     *
     * @param error The error code
     * @return The error description
     */
    public static String formatError(int error)
    {
        byte[] bytes = new byte[256];
        if (av_strerror(error, bytes, 256) < 0)
        {
            return "(" + error + ") Unknown to strerror";
        }
        return "(" + error + ") " + new String(bytes);
    }

    public static AVStream getVideoStream(AVFormatContext context)
    {
        return getStreamOfType(context, AVMEDIA_TYPE_VIDEO);
    }

    public static AVStream getDataStream(AVFormatContext context)
    {
        return getStreamOfType(context, AVMEDIA_TYPE_DATA);
    }

    private static AVStream getStreamOfType(AVFormatContext context, int streamType)
    {
        int index = getStreamIndex(context, streamType);
        return (index < 0) ? null : context.streams(index);
    }

    public static int getVideoStreamIndex(AVFormatContext context)
    {
        return getStreamIndex(context, AVMEDIA_TYPE_VIDEO);
    }

    public static int getDataStreamIndex(AVFormatContext context)
    {
        return getStreamIndex(context, AVMEDIA_TYPE_DATA);
    }

    /**
     * Convert 32-bit integer codec tag to a four CC string
     *
     * @param i The codec tag
     * @return 4-character string with the four CC
     */
    public static String tagToFourCc(int i)
    {
        return new String(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array());
    }

    /**
     * Convert four CC string to an integer tag
     *
     * @param str 4-character string with the four CC
     * @return Integer codec tag
     */
    public static int fourCcToTag(String str)
    {
        if (str.length() != 4)
        {
            throw new IllegalArgumentException("Four CC must be 4 characters");
        }

        return ByteBuffer.wrap(str.getBytes()).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
    }

    /**
     * Get the file duration
     *
     * @param context The format context
     * @return The duration, in seconds
     */
    public static double getDuration(AVFormatContext context)
    {
        return (double)(context.duration() / AV_TIME_BASE);
    }

    /**
     * Get the frame rate
     *
     * @param context The format context
     * @return The frame rate, in frames per second
     */
    public static double getFrameRate(AVFormatContext context)
    {
        AVRational rational = av_guess_frame_rate(context, getVideoStream(context), null);
        return av_q2d(rational);
    }

    public static int getNumStreams(AVFormatContext context)
    {
        return context.nb_streams();
    }

    public static int getStreamType(AVFormatContext context, int index)
    {
        return context.streams(index).codecpar().codec_type();
    }

    public static String getCodecName(AVFormatContext context, int index)
    {
        AVCodecDescriptor descriptor = avcodec_descriptor_get(context.streams(index).codecpar().codec_id());

        if (descriptor != null)
            return descriptor.name().getString();

        return "Unknown";
    }

    private static int getStreamIndex(AVFormatContext context, int streamType)
    {
        int numStreams = context.nb_streams();
        for (int i = 0; i < numStreams; i++)
        {
            AVStream st = context.streams(i);

            if (st.codecpar().codec_type() == streamType)
            {
                // Require data stream to have fourcc = KLVA
                if (streamType == AVMEDIA_TYPE_DATA)
                {
                    String fourcc = tagToFourCc(st.codecpar().codec_tag());
                    if (fourcc.equals("KLVA"))
                    {
                        return i;
                    }
                }
                else
                {
                    return i;
                }
            }
        }

        return -1;
    }
}

package org.jmisb.api.video;

import static org.bytedeco.ffmpeg.global.avcodec.avcodec_descriptor_get;
import static org.bytedeco.ffmpeg.global.avformat.av_guess_frame_rate;
import static org.bytedeco.ffmpeg.global.avutil.AVMEDIA_TYPE_DATA;
import static org.bytedeco.ffmpeg.global.avutil.AVMEDIA_TYPE_VIDEO;
import static org.bytedeco.ffmpeg.global.avutil.AV_TIME_BASE;
import static org.bytedeco.ffmpeg.global.avutil.av_q2d;
import static org.bytedeco.ffmpeg.global.avutil.av_strerror;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.ffmpeg.avcodec.AVCodecDescriptor;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.avutil.AVRational;

/** Utility methods for common FFmpeg operations. */
public class FfmpegUtils {
    private FfmpegUtils() {}

    /**
     * Get the string for a given FFmpeg error code.
     *
     * @param error The error code
     * @return The error description
     */
    public static String formatError(int error) {
        byte[] bytes = new byte[256];
        if (av_strerror(error, bytes, 256) < 0) {
            return "(" + error + ") Unknown to strerror";
        }
        return "(" + error + ") " + new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Get the video stream for the given context.
     *
     * @param context The format context
     * @return The video stream
     */
    public static AVStream getVideoStream(AVFormatContext context) {
        return getStreamOfType(context, AVMEDIA_TYPE_VIDEO);
    }

    /**
     * Get the data stream for the given context.
     *
     * @param context The format context
     * @return The data stream
     */
    public static AVStream getDataStream(AVFormatContext context) {
        return getStreamOfType(context, AVMEDIA_TYPE_DATA);
    }

    /**
     * Get the stream with given index.
     *
     * @param context The format context
     * @param index Index of the stream
     * @return The stream
     */
    public static AVStream getStreamByIndex(AVFormatContext context, int index) {
        return (index < 0) ? null : context.streams(index);
    }

    /**
     * Get the stream of given stream type.
     *
     * @param context The format context
     * @param streamType The stream type
     * @return The stream
     */
    private static AVStream getStreamOfType(AVFormatContext context, int streamType) {
        int index = getStreamIndex(context, streamType);
        return (index < 0) ? null : context.streams(index);
    }

    /**
     * Get the video stream index.
     *
     * @param context The format context
     * @return Index of the video stream
     */
    public static int getVideoStreamIndex(AVFormatContext context) {
        return getStreamIndex(context, AVMEDIA_TYPE_VIDEO);
    }

    /**
     * Get the data stream index.
     *
     * @param context The format context
     * @return Index of the data stream
     */
    public static int getDataStreamIndex(AVFormatContext context) {
        return getStreamIndex(context, AVMEDIA_TYPE_DATA);
    }

    /**
     * Get indices of all data streams.
     *
     * @param context The format context
     * @return List of stream indices
     */
    public static List<Integer> getDataStreamIndices(AVFormatContext context) {
        List<Integer> dataStreamIndices = new ArrayList<>();
        int numStreams = context.nb_streams();
        for (int i = 0; i < numStreams; i++) {
            AVStream st = context.streams(i);
            if (st.codecpar().codec_type() == AVMEDIA_TYPE_DATA) {
                String fourcc = tagToFourCc(st.codecpar().codec_tag());
                if (fourcc.equals("KLVA")) {
                    dataStreamIndices.add(i);
                }
            }
        }
        return dataStreamIndices;
    }

    /**
     * Convert 32-bit integer codec tag to a four CC string.
     *
     * @param i The codec tag
     * @return 4-character string with the four CC
     */
    public static String tagToFourCc(int i) {
        return new String(
                ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array(),
                StandardCharsets.US_ASCII);
    }

    /**
     * Convert four CC string to an integer tag.
     *
     * @param str 4-character string with the four CC
     * @return Integer codec tag
     */
    public static int fourCcToTag(String str) {
        if (str.length() != 4) {
            throw new IllegalArgumentException("Four CC must be 4 characters");
        }

        return ByteBuffer.wrap(str.getBytes(StandardCharsets.US_ASCII))
                .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt();
    }

    /**
     * Get the file duration.
     *
     * @param context The format context
     * @return The duration, in seconds
     */
    public static double getDuration(AVFormatContext context) {
        return (double) context.duration() / AV_TIME_BASE;
    }

    /**
     * Get the frame rate.
     *
     * @param context The format context
     * @return The frame rate, in frames per second
     */
    public static double getFrameRate(AVFormatContext context) {
        AVRational rational = av_guess_frame_rate(context, getVideoStream(context), null);
        return av_q2d(rational);
    }

    /**
     * Get the number of streams.
     *
     * @param context The format context
     * @return Number of streams
     */
    public static int getNumStreams(AVFormatContext context) {
        return context.nb_streams();
    }

    /**
     * Get the type of the specified stream.
     *
     * @param context The format context
     * @param index Index of the stream
     * @return The stream type
     */
    public static int getStreamType(AVFormatContext context, int index) {
        return context.streams(index).codecpar().codec_type();
    }

    /**
     * Get the codec of the specified stream.
     *
     * @param context The format context
     * @param index Index of the stream
     * @return The codec name
     */
    public static String getCodecName(AVFormatContext context, int index) {
        AVCodecDescriptor descriptor =
                avcodec_descriptor_get(context.streams(index).codecpar().codec_id());

        if (descriptor != null) return descriptor.name().getString();

        return "Unknown";
    }

    /**
     * Get the first instance of the given stream type.
     *
     * @param context The format context
     * @param streamType The stream type
     * @return The index of the first stream of the specified type
     */
    private static int getStreamIndex(AVFormatContext context, int streamType) {
        int numStreams = context.nb_streams();
        for (int i = 0; i < numStreams; i++) {
            AVStream st = context.streams(i);

            if (st.codecpar().codec_type() == streamType) {
                // Require data stream to have fourcc = KLVA
                if (streamType == AVMEDIA_TYPE_DATA) {
                    String fourcc = tagToFourCc(st.codecpar().codec_tag());
                    if (fourcc.equals("KLVA")) {
                        return i;
                    }
                } else {
                    return i;
                }
            }
        }

        return -1;
    }
}

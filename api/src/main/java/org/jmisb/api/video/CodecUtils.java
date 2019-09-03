package org.jmisb.api.video;

import org.bytedeco.ffmpeg.avcodec.AVCodecContext;

/**
 * Utility methods for encoding/decoding
 */
public class CodecUtils
{
    /**
     * Get codec parameters as a string
     * @param context The AVCodecContext
     * @return String containing codec parameters
     */
    public static String getCodecInfo(AVCodecContext context)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("codec_type = ").append(context.codec_type()).append("\n")
            .append("codec_id = ").append(context.codec_id()).append("\n")
            .append("has_b_frames = ").append(context.has_b_frames()).append("\n")
            .append("max_b_frames = ").append(context.max_b_frames()).append("\n")
            .append("gop_size = ").append(context.gop_size()).append("\n")
            .append("time_base.num = ").append(context.time_base().num()).append("\n")
            .append("time_base.den = ").append(context.time_base().den()).append("\n");
        return sb.toString();
    }
}

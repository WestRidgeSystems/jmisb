package org.jmisb.api.video;

import org.bytedeco.javacpp.*;
import org.jmisb.core.video.FfmpegUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.IOException;

import static org.bytedeco.javacpp.avcodec.*;
import static org.bytedeco.javacpp.avformat.av_guess_format;
import static org.bytedeco.javacpp.avutil.*;
import static org.bytedeco.javacpp.swscale.SWS_FAST_BILINEAR;

/**
 * Abstract base class for video output
 */
public abstract class VideoOutput
{
    protected VideoOutputOptions options;

    // Format
    avformat.AVFormatContext formatContext;

    // Video codec
    avcodec.AVCodecContext videoCodecContext;
    private avcodec.AVCodec videoCodec;

    // Metadata codec
    avcodec.AVCodecContext metadataCodecContext;
    avcodec.AVCodecParameters klvCodecParams;

    // Streams
    private static final int VIDEO_STREAM_INDEX = 0;
    private static final int METADATA_STREAM_INDEX = 1;

    private avformat.AVStream videoStream;
    private avformat.AVStream metadataStream;

    private swscale.SwsContext swsContext;

    int framesWritten = 0;

    /**
     * Constructor
     *
     * @param options The output options
     */
    VideoOutput(VideoOutputOptions options)
    {
        this.options = options;
    }

    /**
     * Initialize the video and metadata codecs
     *
     * @throws IOException if an error occurs
     */
    void initCodecs() throws IOException
    {
        // Find h.264 video encoder
        if ((videoCodec = avcodec.avcodec_find_encoder(AV_CODEC_ID_H264)) == null)
        {
            throw new IOException("Could not find H.264 codec");
        }

        // Allocate the video codec context
        if ((videoCodecContext = avcodec.avcodec_alloc_context3(videoCodec)) == null)
        {
            throw new IOException("avcodec_alloc_context3() error: Could not allocate video encoding context.");
        }

        // Allocate the metadata codec context
        if ((metadataCodecContext = avcodec.avcodec_alloc_context3(null)) == null)
        {
            throw new IOException("avcodec_alloc_context3() error: Could not allocate video encoding context.");
        }
        klvCodecParams = avcodec_parameters_alloc();
        klvCodecParams.codec_tag(FfmpegUtils.fourCcToTag("klva"));
        klvCodecParams.codec_type(AVMEDIA_TYPE_DATA);
        klvCodecParams.codec_id(AV_CODEC_ID_SMPTE_KLV);

        int ret;
        if ((ret = avcodec.avcodec_parameters_to_context(metadataCodecContext, klvCodecParams)) < 0)
        {
            throw new IOException("Could not allocate metadata codec context: " + FfmpegUtils.formatError(ret));
        }

        // TODO: investigate H.264 profiles

        // Set bit rate
        videoCodecContext.bit_rate(options.getBitRate());

        // Set frame rate
        avutil.AVRational frameRate = avutil.av_d2q(options.getFrameRate(), 1001000);
        avutil.AVRational timeBase = avutil.av_inv_q(frameRate);
        videoCodecContext.time_base(timeBase);

        // Encoded picture format
        videoCodecContext.pix_fmt(AV_PIX_FMT_YUV420P);

        // No B-frames
        videoCodecContext.has_b_frames(0);
        videoCodecContext.max_b_frames(0);

        // I-frame interval
        videoCodecContext.gop_size(options.getGopSize());

        // Set dimensions
        videoCodecContext.width(options.getWidth());
        videoCodecContext.height(options.getHeight());
    }

    /**
     * Allocate the format context
     *
     * @throws IOException if the format context could not be allocated
     */
    void initFormat() throws IOException
    {
        // Set the output format - MPEGTS
        avformat.AVOutputFormat outputFormat = av_guess_format("mpegts", null, null);

        // Get the format context
        formatContext = new avformat.AVFormatContext(null);
        if (avformat.avformat_alloc_output_context2(formatContext, outputFormat,
                (String)null, null) < 0)
        {
            throw new IOException("Could not allocate format context");
        }
    }

    /**
     * Open the video codec
     *
     * @throws IOException if the codec could not be opened
     */
    void openVideoCodec() throws IOException
    {
        // Open the codec
        int ret;
        avutil.AVDictionary opts = new avutil.AVDictionary(null);
        if ((ret = avcodec_open2(videoCodecContext, videoCodec, opts)) < 0)
        {
            av_dict_free(opts);
            throw new IOException("Error opening video codec: " + FfmpegUtils.formatError(ret));
        }
        av_dict_free(opts);
    }

    /**
     * Create the video stream
     *
     * @throws IOException if the video stream could not be created
     */
    void createVideoStream() throws IOException
    {
        avutil.AVRational frameRate = avutil.av_d2q(options.getFrameRate(), 1001000);

        // Create the video stream
        videoStream = avformat.avformat_new_stream(formatContext, videoCodec);
        videoStream.index(VIDEO_STREAM_INDEX);
        videoStream.time_base(avutil.av_inv_q(frameRate));

        // Copy the video stream parameters to the muxer
        int ret;
        if ((ret = avcodec_parameters_from_context(videoStream.codecpar(), videoCodecContext)) < 0)
        {
            throw new IOException("Could not copy the video stream parameters: " + FfmpegUtils.formatError(ret));
        }
    }

    /**
     * Create the metadata stream
     */
    void createMetadataStream()
    {
        metadataStream = avformat.avformat_new_stream(formatContext, metadataCodecContext.codec());
        metadataStream.index(METADATA_STREAM_INDEX);
        metadataStream.codecpar(klvCodecParams);
        metadataStream.time_base(videoStream.time_base());
    }

    /**
     * Convert a BufferedImage to an AVFrame, transforming to the pixel format required by the codec
     *
     * @param image The input image
     * @return The output image
     * @throws IOException If the frame could not be written
     */
    private avutil.AVFrame convert(BufferedImage image) throws IOException
    {
        // TODO: move to FrameConverter if possible
        // TODO: handle other input formats
        int srcFormat = AV_PIX_FMT_BGR24;
        int srcWidth = image.getWidth();
        int srcHeight = image.getHeight();

        int dstFormat = videoCodecContext.pix_fmt();
        int dstWidth = videoCodecContext.width();
        int dstHeight = videoCodecContext.height();

        swsContext = swscale.sws_getCachedContext(swsContext,
                srcWidth, srcHeight, srcFormat,
                dstWidth, dstHeight, dstFormat,
                SWS_FAST_BILINEAR, null, null, (DoublePointer) null);

        if (swsContext == null)
        {
            throw new IOException("Cannot initialize conversion context");
        }

        // Wrap src image in AVFrame
        // TODO: should only need to allocate once, when output is first opened or first frame is written
        // TODO: must be deallocated using av_frame_free
        AVFrame avFrameSrc = avutil.av_frame_alloc();

        DataBuffer dataBuffer = image.getRaster().getDataBuffer();
        if (dataBuffer instanceof DataBufferByte)
        {
            BytePointer pixelData = new BytePointer(((DataBufferByte) dataBuffer).getData());
            av_image_fill_arrays(new PointerPointer(avFrameSrc), avFrameSrc.linesize(), pixelData, srcFormat, srcWidth, srcHeight, 1);
        }
        else
        {
            throw new IllegalArgumentException("Input must be an 8-bit image");
        }

        // Allocate the AVFrame
        // TODO: should only need to allocate once, when output is first opened or first frame is written
        // TODO: must be deallocated using av_frame_free
        AVFrame avFrameDst = avutil.av_frame_alloc();

        // Fill in the AVFrame structure
        av_image_alloc(avFrameDst.data(), avFrameDst.linesize(), options.getWidth(), options.getHeight(),
                videoCodecContext.pix_fmt(), 1);
        avFrameDst.format(videoCodecContext.pix_fmt());
        avFrameDst.width(options.getWidth());
        avFrameDst.height(options.getHeight());

        swscale.sws_scale(swsContext, new PointerPointer(avFrameSrc), avFrameSrc.linesize(),
                0, image.getHeight(), new PointerPointer(avFrameDst), avFrameDst.linesize());

        return avFrameDst;
    }

    /**
     * Convert a MetadataFrame to an AVPacket
     *
     * @param frame the MetadataFrame
     * @return The AVPacket
     */
    AVPacket convert(MetadataFrame frame)
    {
        // Convert frame to an AVPacket
        AVPacket packet = av_packet_alloc();
        packet.stream_index(METADATA_STREAM_INDEX);

        // Convert PTS in seconds to PTS in "time base" units
        long pts = Math.round(frame.getPts() / av_q2d(metadataStream.time_base()));
        packet.pts(pts);

        // TODO: how to set dts?
        long dts = pts;
        packet.dts(dts);

        // Set packet data
        byte[] bytes = frame.getMisbMessage().frameMessage(false);
        BytePointer bytePointer = new BytePointer(bytes);
        packet.data(bytePointer);
        packet.size(bytes.length);

        return packet;
    }

    /**
     * Send a video frame to the encoder
     *
     * @param frame The video frame
     * @throws IOException if an error occurs
     */
    void encodeFrame(VideoFrame frame) throws IOException
    {
        AVFrame avFrame = convert(frame.getImage());

        // Convert PTS in seconds to PTS in "time base" units
        long pts = Math.round(frame.getPts() / av_q2d(videoStream.time_base()));
        avFrame.pts(pts);
        avFrame.pkt_dts(pts); // TODO: correct?

        // Send the frame to the encoder
        int ret;
        ret = avcodec_send_frame(videoCodecContext, avFrame);
        if (ret != 0 && ret != AVERROR_EAGAIN())
        {
            throw new IOException("Error sending frame to encoder: " + FfmpegUtils.formatError(ret));
        }
    }
}

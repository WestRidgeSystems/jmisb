package org.jmisb.api.video;

import static org.bytedeco.ffmpeg.avcodec.AVCodecContext.FF_PROFILE_KLVA_ASYNC;
import static org.bytedeco.ffmpeg.avcodec.AVCodecContext.FF_PROFILE_KLVA_SYNC;
import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_H264;
import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_H265;
import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_SMPTE_KLV;
import static org.bytedeco.ffmpeg.global.avcodec.av_packet_alloc;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_alloc_context3;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_find_encoder;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_find_encoder_by_name;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_free_context;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_open2;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_parameters_alloc;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_parameters_from_context;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_parameters_to_context;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_send_frame;
import static org.bytedeco.ffmpeg.global.avformat.av_guess_format;
import static org.bytedeco.ffmpeg.global.avformat.avformat_alloc_output_context2;
import static org.bytedeco.ffmpeg.global.avformat.avformat_free_context;
import static org.bytedeco.ffmpeg.global.avformat.avformat_new_stream;
import static org.bytedeco.ffmpeg.global.avformat.avio_close;
import static org.bytedeco.ffmpeg.global.avutil.AVMEDIA_TYPE_DATA;
import static org.bytedeco.ffmpeg.global.avutil.AVMEDIA_TYPE_VIDEO;
import static org.bytedeco.ffmpeg.global.avutil.AV_FRAME_DATA_SEI_UNREGISTERED;
import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_BGR24;
import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_YUV420P;
import static org.bytedeco.ffmpeg.global.avutil.av_d2q;
import static org.bytedeco.ffmpeg.global.avutil.av_frame_alloc;
import static org.bytedeco.ffmpeg.global.avutil.av_frame_free;
import static org.bytedeco.ffmpeg.global.avutil.av_frame_new_side_data;
import static org.bytedeco.ffmpeg.global.avutil.av_image_alloc;
import static org.bytedeco.ffmpeg.global.avutil.av_image_fill_arrays;
import static org.bytedeco.ffmpeg.global.avutil.av_inv_q;
import static org.bytedeco.ffmpeg.global.avutil.av_q2d;
import static org.bytedeco.ffmpeg.global.swscale.SWS_FAST_BILINEAR;
import static org.bytedeco.ffmpeg.global.swscale.sws_getCachedContext;
import static org.bytedeco.ffmpeg.global.swscale.sws_scale;
import static org.bytedeco.ffmpeg.presets.avutil.AVERROR_EAGAIN;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.List;
import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.avcodec.AVCodecParameters;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVOutputFormat;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.avutil.AVFrameSideData;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.swscale.SwsContext;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.PointerPointer;
import org.jmisb.api.klv.st2101.ST2101Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Abstract base class for video output. */
public abstract class VideoOutput extends VideoIO {

    private static final Logger logger = LoggerFactory.getLogger(VideoOutput.class);

    private static final int METADATA_AU_HEADER_LEN = 5;

    /**
     * Configuration options for the video output.
     *
     * <p>This is holds the configuration settings that change the output (e.g. rates, data
     * included, and frame size).
     */
    protected VideoOutputOptions options;

    // Format
    AVFormatContext formatContext;

    // Video codec
    AVCodecContext videoCodecContext;
    private AVCodec videoCodec;

    // Metadata codec
    AVCodecContext metadataCodecContext;
    AVCodecParameters klvCodecParams;

    // Streams
    private static final int VIDEO_STREAM_INDEX = 0;
    private static final int METADATA_STREAM_INDEX = 1;

    private AVStream videoStream;
    private AVStream metadataStream;

    private SwsContext swsContext;
    private AVFrame avFrameSrc;
    private AVFrame avFrameDst;

    private BufferedImage tempImageBuffer;

    int framesWritten = 0;

    /**
     * Constructor.
     *
     * @param options The output options
     */
    public VideoOutput(VideoOutputOptions options) {
        this.options = options;
    }

    /**
     * Initialize the video and metadata codecs.
     *
     * @throws IOException if an error occurs
     */
    void initCodecs() throws IOException {

        switch (options.getCodec()) {
            case H264:
                init264VideoCodec();
                break;
            case H265:
                init265VideoCodec();
                break;
            default:
                throw new IllegalArgumentException("Codec must be H.264 or H.265");
        }

        // Allocate the metadata codec context
        if (options.hasKlvStream()) {
            if ((metadataCodecContext = avcodec_alloc_context3(null)) == null) {
                throw new IOException(
                        "avcodec_alloc_context3() error: Could not allocate video encoding context.");
            }
            klvCodecParams = avcodec_parameters_alloc();
            if (options.getMultiplexingMethod().equals(KlvFormat.Synchronous)) {
                klvCodecParams.profile(FF_PROFILE_KLVA_SYNC);
            } else {
                klvCodecParams.profile(FF_PROFILE_KLVA_ASYNC);
            }
            klvCodecParams.codec_tag(FfmpegUtils.fourCcToTag("klva"));
            klvCodecParams.codec_type(AVMEDIA_TYPE_DATA);
            klvCodecParams.codec_id(AV_CODEC_ID_SMPTE_KLV);

            int ret;
            if ((ret = avcodec_parameters_to_context(metadataCodecContext, klvCodecParams)) < 0) {
                throw new IOException(
                        "Could not allocate metadata codec context: "
                                + FfmpegUtils.formatError(ret));
            }
        }
    }
    /**
     * Initialise a H.264 video encoder.
     *
     * @throws IOException if no valid encoder could be initialised.
     */
    private void init264VideoCodec() throws IOException {
        boolean codecOpened =
                tryCodecs(
                        CodecConfigurations.getInstance().getCodecs(CodecIdentifier.H264),
                        AV_CODEC_ID_H264);
        if (videoCodec == null || videoCodecContext == null || !codecOpened) {
            throw new IOException("Could not initialize H.264 encoder");
        }
        logger.debug("video encoder = " + videoCodec.long_name().getString());
    }

    /**
     * Initialise a H.265 video encoder.
     *
     * @throws IOException if no valid encoder could be initialised.
     */
    private void init265VideoCodec() throws IOException {
        // Attempt to open hardware-accelerated codecs first; fall back on libx265
        boolean codecOpened =
                tryCodecs(
                        CodecConfigurations.getInstance().getCodecs(CodecIdentifier.H265),
                        AV_CODEC_ID_H265);
        if (videoCodec == null || videoCodecContext == null || !codecOpened) {
            throw new IOException("Could not initialize H.265 encoder");
        }
        logger.info("video encoder = " + videoCodec.long_name().getString());
    }

    /**
     * Try to initialise the video codecs in priority order.
     *
     * @param codecsToTry the codecs to try (name, description pairs), from highest priority to
     *     lowest.
     * @param fallbackCodecId a fallback codec ID, in case none of the codecs worked out.
     * @return true on successful initialization, otherwise false.
     */
    private boolean tryCodecs(List<CodecConfiguration> codecsToTry, int fallbackCodecId) {
        boolean codecOpened = false;
        for (CodecConfiguration codec : codecsToTry) {
            codecOpened = tryCodec(codec);
            if (codecOpened) {
                break;
            }
        }
        if (!codecOpened) {
            codecOpened = findCodec(fallbackCodecId);
        }
        return codecOpened;
    }

    private boolean tryCodec(CodecConfiguration codecConfiguration) {
        logger.info("Trying " + codecConfiguration.getLabel() + " encoder...");
        videoCodec = avcodec_find_encoder_by_name(codecConfiguration.getName());
        videoCodecContext = avcodec_alloc_context3(videoCodec);
        if ((videoCodec == null) || (videoCodecContext == null)) {
            return false;
        }
        return openVideoCodec(codecConfiguration);
    }

    private boolean findCodec(int codecId) {
        logger.info("Searching for any valid encoder...");
        videoCodec = avcodec_find_encoder(codecId);
        videoCodecContext = avcodec_alloc_context3(videoCodec);
        if ((videoCodec == null) || (videoCodecContext == null)) {
            return false;
        }
        return openVideoCodec(null);
    }

    /**
     * Allocate the format context.
     *
     * @throws IOException if the format context could not be allocated
     */
    void initFormat() throws IOException {
        // Set the output format - MPEGTS
        AVOutputFormat outputFormat = av_guess_format("mpegts", null, null);

        // Get the format context
        formatContext = new AVFormatContext(null);
        if (avformat_alloc_output_context2(formatContext, outputFormat, (String) null, null) < 0) {
            throw new IOException("Could not allocate format context");
        }
    }

    /**
     * Open the video codec.
     *
     * @param codecConfiguration special codec options, or null for defaults.
     * @return false if the codec could not be opened (e.g., unsupported by the OS/hardware)
     */
    private boolean openVideoCodec(CodecConfiguration codecConfiguration) {
        // codec context options must be set before opening codec
        videoCodecContext.codec_type(AVMEDIA_TYPE_VIDEO);
        videoCodecContext.codec_id(videoCodec.id());

        // Set dimensions
        videoCodecContext.width(options.getWidth());
        videoCodecContext.height(options.getHeight());

        // Set bit rate
        videoCodecContext.bit_rate(options.getBitRate());

        // Set frame rate
        AVRational frameRate = av_d2q(options.getFrameRate(), 1001000);
        AVRational timeBase = av_inv_q(frameRate);
        videoCodecContext.time_base(timeBase);

        // Encoded picture format
        videoCodecContext.pix_fmt(AV_PIX_FMT_YUV420P);

        // I-frame interval
        videoCodecContext.gop_size(options.getGopSize());

        // Disable B frames
        videoCodecContext.has_b_frames(0);
        videoCodecContext.max_b_frames(0);
        // Open the codec
        AVDictionary codecOptions;
        if (codecConfiguration != null) {
            codecOptions = codecConfiguration.getOptions();
        } else {
            codecOptions = new AVDictionary();
        }
        int ret = avcodec_open2(videoCodecContext, videoCodec, codecOptions);
        logger.debug("Video encoder options: " + CodecUtils.getCodecInfo(videoCodecContext));

        return ret >= 0;
    }

    /**
     * Create the video AVStream.
     *
     * @throws IOException if the AVStream could not be created
     */
    void createVideoStream() throws IOException {
        AVRational frameRate = av_d2q(options.getFrameRate(), 1001000);

        // Create the video stream
        videoStream = avformat_new_stream(formatContext, videoCodec);
        videoStream.index(VIDEO_STREAM_INDEX);
        videoStream.time_base(av_inv_q(frameRate));

        // Copy the video stream parameters to the muxer
        int ret;
        if ((ret = avcodec_parameters_from_context(videoStream.codecpar(), videoCodecContext))
                < 0) {
            throw new IOException(
                    "Could not copy the video stream parameters: " + FfmpegUtils.formatError(ret));
        }

        // Allocate reusable frame
        avFrameSrc = av_frame_alloc();
    }

    /** Create the metadata stream. */
    void createMetadataStream() {
        metadataStream = avformat_new_stream(formatContext, metadataCodecContext.codec());
        metadataStream.index(METADATA_STREAM_INDEX);
        metadataStream.codecpar(klvCodecParams);
        metadataStream.time_base(videoStream.time_base());
    }

    /** Release any resources (to be called by any subclasses when stream/file is closed). */
    void cleanup() {
        if (formatContext != null && formatContext.pb() != null) {
            avio_close(formatContext.pb());
        }

        if (videoCodecContext != null) {
            avcodec_free_context(videoCodecContext);
            videoCodecContext = null;
        }

        if (metadataCodecContext != null) {
            avcodec_free_context(metadataCodecContext);
            metadataCodecContext = null;
        }

        if (klvCodecParams != null) {
            // Apparently already freed elsewhere
            // avcodec_parameters_free(klvCodecParams);
            klvCodecParams = null;
        }

        if (formatContext != null) {
            avformat_free_context(formatContext);
            formatContext = null;
        }

        if (avFrameSrc != null) {
            av_frame_free(avFrameSrc);
            avFrameSrc = null;
        }

        if (avFrameDst != null) {
            av_frame_free(avFrameDst);
            avFrameDst = null;
        }
    }

    /**
     * Copy a VideoFrame to avFrameDst, transforming to the pixel format required by the codec.
     *
     * @param frame The input video frame
     * @throws IOException If the frame could not be written
     */
    private void convert(VideoFrame frame) throws IOException {
        BufferedImage image = frame.getImage();
        // If needed, convert to TYPE_3BYTE_BGR format (TODO: is there a more efficient way?)
        BufferedImage inputImage = image;
        if (image.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            // Lazily create tempImageBuffer
            if (tempImageBuffer == null
                    || tempImageBuffer.getWidth() != options.getWidth()
                    || tempImageBuffer.getHeight() != options.getHeight()) {
                logger.debug("Converting BufferedImages of type " + image.getType());
                tempImageBuffer =
                        new BufferedImage(
                                image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            }
            Graphics2D g2d = tempImageBuffer.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();
            inputImage = tempImageBuffer;
        }

        int srcFormat = AV_PIX_FMT_BGR24;
        int srcWidth = inputImage.getWidth();
        int srcHeight = inputImage.getHeight();

        int dstFormat = videoCodecContext.pix_fmt();
        int dstWidth = videoCodecContext.width();
        int dstHeight = videoCodecContext.height();

        swsContext =
                sws_getCachedContext(
                        swsContext,
                        srcWidth,
                        srcHeight,
                        srcFormat,
                        dstWidth,
                        dstHeight,
                        dstFormat,
                        SWS_FAST_BILINEAR,
                        null,
                        null,
                        (DoublePointer) null);

        if (swsContext == null) {
            throw new IOException("Cannot initialize conversion context");
        }

        // Wrap src image in AVFrame
        DataBuffer dataBuffer = inputImage.getRaster().getDataBuffer();
        if (!(dataBuffer instanceof DataBufferByte)) {
            throw new IllegalArgumentException("Input must be an 8-bit image");
        }

        // Set up pointers and line sizes for avFrameSrc to point to inputImage's data
        BytePointer pixelData = new BytePointer(((DataBufferByte) dataBuffer).getData());
        av_image_fill_arrays(
                new PointerPointer(avFrameSrc),
                avFrameSrc.linesize(),
                pixelData,
                srcFormat,
                srcWidth,
                srcHeight,
                1);

        // Lazily create avFrameDst and allocate its buffer
        if (avFrameDst == null) {
            avFrameDst = av_frame_alloc();
            av_image_alloc(
                    avFrameDst.data(),
                    avFrameDst.linesize(),
                    options.getWidth(),
                    options.getHeight(),
                    videoCodecContext.pix_fmt(),
                    1);
            avFrameDst.format(videoCodecContext.pix_fmt());
            avFrameDst.width(options.getWidth());
            avFrameDst.height(options.getHeight());
        }

        // Copy avFrameSrc -> avFrameDst
        sws_scale(
                swsContext,
                new PointerPointer(avFrameSrc),
                avFrameSrc.linesize(),
                0,
                inputImage.getHeight(),
                new PointerPointer(avFrameDst),
                avFrameDst.linesize());

        if (frame.getMiisCoreId() != null) {
            byte[] sideData = ST2101Converter.coreIdToSideDataBytes(frame.getMiisCoreId());
            AVFrameSideData st2101_sei =
                    av_frame_new_side_data(
                            avFrameDst, AV_FRAME_DATA_SEI_UNREGISTERED, sideData.length);
            st2101_sei.data().put(sideData, 0, sideData.length);
        }
    }

    /**
     * Convert a MetadataFrame to an AVPacket.
     *
     * @param frame the MetadataFrame
     * @return The AVPacket
     */
    AVPacket convert(MetadataFrame frame) {
        // Convert frame to an AVPacket
        AVPacket packet = av_packet_alloc();
        packet.stream_index(METADATA_STREAM_INDEX);

        // Convert PTS in seconds to PTS in "time base" units
        long pts = Math.round(frame.getPts() / av_q2d(metadataStream.time_base()));
        packet.pts(pts);

        // TODO: how to set dts?
        packet.dts(pts);

        // Set packet data
        byte[] bytes = frame.getMisbMessage().frameMessage(false);
        if (options.getMultiplexingMethod().equals(KlvFormat.Synchronous)) {
            byte[] metadata_au_cell = new byte[bytes.length + METADATA_AU_HEADER_LEN];
            metadata_au_cell[0] = 0x00; /* metadata_service_id */
            metadata_au_cell[1] = 0x01; /* TODO: fix sequence number */
            metadata_au_cell[2] = (byte) 0b11011111;
            metadata_au_cell[3] = (byte) (bytes.length >>> 8);
            metadata_au_cell[4] = (byte) bytes.length;
            System.arraycopy(bytes, 0, metadata_au_cell, METADATA_AU_HEADER_LEN, bytes.length);
            BytePointer bytePointer = new BytePointer(metadata_au_cell);
            packet.data(bytePointer);
            packet.size(metadata_au_cell.length);
        } else {
            BytePointer bytePointer = new BytePointer(bytes);
            packet.data(bytePointer);
            packet.size(bytes.length);
        }

        return packet;
    }

    /**
     * Encode a video frame.
     *
     * @param frame The video frame
     * @throws IOException if an error occurs
     */
    void encodeFrame(VideoFrame frame) throws IOException {
        convert(frame);

        // Convert PTS in seconds to PTS in "time base" units
        long pts = Math.round(frame.getPts() / av_q2d(videoStream.time_base()));
        avFrameDst.pts(pts);
        avFrameDst.pkt_dts(pts); // TODO: correct?

        // Encode the frame
        int ret;
        ret = avcodec_send_frame(videoCodecContext, avFrameDst);
        if (ret != 0 && ret != AVERROR_EAGAIN()) {
            throw new IOException("Error encoding video frame: " + FfmpegUtils.formatError(ret));
        }
    }
}

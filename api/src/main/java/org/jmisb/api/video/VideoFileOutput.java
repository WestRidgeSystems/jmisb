package org.jmisb.api.video;

import org.bytedeco.javacpp.*;
import org.jmisb.core.video.FfmpegUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.javacpp.avcodec.*;
import static org.bytedeco.javacpp.avformat.*;
import static org.bytedeco.javacpp.avutil.*;
import static org.bytedeco.javacpp.swscale.SWS_FAST_BILINEAR;

/**
 * Write video/metadata to a file
 */
public class VideoFileOutput implements IVideoFileOutput
{
    private static Logger logger = LoggerFactory.getLogger(VideoFileOutput.class);
    private String filename;
    private VideoOutputOptions options;

    // Format
    private avformat.AVFormatContext formatContext;
    private avformat.AVOutputFormat outputFormat;

    // Video codec
    private avcodec.AVCodecContext videoCodecContext;
    private avcodec.AVCodec videoCodec;

    // Metadata codec
    private avcodec.AVCodecContext metadataCodecContext;
    private avcodec.AVCodecParameters klvCodecParams;

    // Streams
    private static final int VIDEO_STREAM_INDEX = 0;
    private static final int METADATA_STREAM_INDEX = 1;

    private avformat.AVStream videoStream;
    private avformat.AVStream metadataStream;

    private swscale.SwsContext swsContext;

    private int framesWritten = 0;

    /**
     * Constructor
     * <p>
     * Clients must use {@link VideoSystem#createOutputFile(VideoOutputOptions)} to construct new instances
     */
    VideoFileOutput(VideoOutputOptions options)
    {
        this.options = options;
    }

    @Override
    public void open(String filename) throws IOException
    {
        this.filename = filename;

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

        // Set the output format - MPEGTS
        this.outputFormat = av_guess_format("mpegts", filename, null);

        // Get the format context
        formatContext = new avformat.AVFormatContext(null);
        if (avformat.avformat_alloc_output_context2(formatContext, outputFormat,
                "mpegts", filename) < 0)
        {
            throw new IOException("Could not allocate format context");
        }

        // Open the codec
        avutil.AVDictionary opts = new avutil.AVDictionary(null);
        if ((ret = avcodec_open2(videoCodecContext, videoCodec, opts)) < 0)
        {
            av_dict_free(opts);
            throw new IOException("Error opening video codec: " + FfmpegUtils.formatError(ret));
        }
        av_dict_free(opts);

        // Create the video stream
        videoStream = avformat.avformat_new_stream(formatContext, videoCodec);
        videoStream.index(VIDEO_STREAM_INDEX);
        videoStream.time_base(avutil.av_inv_q(frameRate));

        // Copy the video stream parameters to the muxer
        if ((ret = avcodec_parameters_from_context(videoStream.codecpar(), videoCodecContext)) < 0)
        {
            throw new IOException("Could not copy the video stream parameters: " + FfmpegUtils.formatError(ret));
        }

        // Create the metadata stream
        metadataStream = avformat.avformat_new_stream(formatContext, metadataCodecContext.codec());
        metadataStream.index(METADATA_STREAM_INDEX);
        metadataStream.codecpar(klvCodecParams);
        metadataStream.time_base(videoStream.time_base());

        // Open the file
        avformat.AVIOContext ioContext = new avformat.AVIOContext(null);
        if ((ret = avio_open2(ioContext, filename, AVIO_FLAG_WRITE, null, null)) < 0)
        {
            throw new IOException("Error opening file: " + FfmpegUtils.formatError(ret));
        }
        formatContext.pb(ioContext);

        opts = new avutil.AVDictionary(null);
        avformat_write_header(formatContext, opts);
        av_dict_free(opts);

//        av_dump_format(formatContext, 0, filename, 1);
    }

    @Override
    public boolean isOpen()
    {
        return formatContext != null;
    }

    @Override
    public void close() throws IOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Closing " + filename);
        }

        if (!isOpen())
        {
            return;
        }

        flush();

        if (formatContext != null && formatContext.pb() != null)
        {
            avio_close(formatContext.pb());
        }

        if (videoCodecContext != null)
        {
            avcodec_free_context(videoCodecContext);
            videoCodecContext = null;
        }

        if (metadataCodecContext != null)
        {
            avcodec_free_context(metadataCodecContext);
            metadataCodecContext = null;
        }

        if (klvCodecParams != null)
        {
            // Apparently already freed elsewhere
            // avcodec_parameters_free(klvCodecParams);
            klvCodecParams = null;
        }

        if (formatContext != null)
        {
            avformat_free_context(formatContext);
            formatContext = null;
        }
    }

    /**
     * Write all available data to the file before closing
     */
    private void flush() throws IOException
    {
        // Send null to the encoder, signalling EOF and entering "draining mode"
        avcodec_send_frame(videoCodecContext, null);

        // Write out all available packets
        writeAvailablePackets(true);

        // Write trailer
        // TODO: may not be necessary
        av_write_trailer(formatContext);

        if (logger.isDebugEnabled())
        {
            logger.debug("# frames written: " + framesWritten);
        }
    }

    @Override
    public void addVideoFrame(VideoFrame frame) throws IOException
    {
        if (frame.getImage().getWidth() != options.getWidth() ||
                frame.getImage().getHeight() != options.getHeight())
        {
            throw new IllegalArgumentException("Invalid image dimensions");
        }

        // Luca's note on using the new ffmpeg API:
        //
        // – You feed data using the avcodec_send_* functions until you get a AVERROR(EAGAIN), that signals that the
        //   internal input buffer is full.
        //
        // – You get the data back using the matching avcodec_receive_* function until you get a AVERROR(EAGAIN),
        //   signalling that the internal output buffer is empty.
        //
        // – Once you are done feeding data you have to pass a NULL to signal the end of stream.
        //
        // – You can keep calling the avcodec_receive_* function until you get AVERROR_EOF.
        //
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

        // Write out any available packets
        writeAvailablePackets(false);
    }

    /**
     * Takes all available packets out of the encoder's internal buffer, then writes them to the file
     *
     * @param eof If true, expect EOF and throw exception if not found
     * @throws IOException if expected EOF packet is not found
     */
    private void writeAvailablePackets(boolean eof) throws IOException
    {
        List<AVPacket> packets = new ArrayList<>();

        // Drain all packets from the encoder
        int ret2 = 0;
        while (ret2 != AVERROR_EOF && ret2 != AVERROR_EAGAIN())
        {
            AVPacket packet = av_packet_alloc();
            ret2 = avcodec_receive_packet(videoCodecContext, packet);
            if (ret2 == 0)
            {
                packets.add(packet);
            }
            else if (ret2 == AVERROR_EOF)
            {
                logger.debug("EOF reached");
            }
        }

        if (eof && ret2 != AVERROR_EOF)
        {
            throw new IOException("Expected EOF packet not found");
        }

        // Write all new packets to the file
        for (AVPacket packet : packets)
        {
            int ret3;
            if ((ret3 = av_write_frame(formatContext, packet)) < 0)
            {
                logger.error("Error writing video packet: " + FfmpegUtils.formatError(ret3));
            }
            framesWritten++;
            av_packet_free(packet);
        }
    }

    @Override
    public void addMetadataFrame(MetadataFrame frame) throws IOException
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

        // Write the packet to the file
        int ret;
        if ((ret = av_write_frame(formatContext, packet)) < 0)
        {
            throw new IOException("Error writing metadata packet: " + FfmpegUtils.formatError(ret));
        }

        // Free packet
        av_packet_free(packet);
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
        // TODO: should only need to allocate once, when file is first opened or first frame is written
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
        // TODO: should only need to allocate once, when file is first opened or first frame is written
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
}

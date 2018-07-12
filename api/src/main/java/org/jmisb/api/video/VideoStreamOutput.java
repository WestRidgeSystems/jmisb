package org.jmisb.api.video;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avformat;
import org.bytedeco.javacpp.avutil;
import org.jmisb.core.video.FfmpegUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.bytedeco.javacpp.avcodec.av_packet_alloc;
import static org.bytedeco.javacpp.avcodec.av_packet_free;
import static org.bytedeco.javacpp.avcodec.avcodec_receive_packet;
import static org.bytedeco.javacpp.avformat.*;
import static org.bytedeco.javacpp.avutil.AVERROR_EOF;
import static org.bytedeco.javacpp.avutil.av_dict_free;
import static org.bytedeco.javacpp.presets.avutil.AVERROR_EAGAIN;

/**
 * Manages an outgoing video stream
 */
public class VideoStreamOutput extends VideoOutput implements IVideoStreamOutput
{
    private static Logger logger = LoggerFactory.getLogger(VideoStreamOutput.class);
    private String url;

    private Runnable videoEncoder;
    private Future<?> encoderFuture;
    private BlockingQueue<VideoFrame> videoFrames = new LinkedBlockingDeque<>();
    private ExecutorService encoderExecSvc;

    private Runnable packetSender;
    private ScheduledFuture<?> senderFuture;
    private BlockingQueue<avcodec.AVPacket> klvPackets = new LinkedBlockingDeque<>();
    private ScheduledExecutorService senderExecSvc;

    /**
     * Constructor
     * <p>
     * Clients must use {@link VideoSystem#createOutputStream(VideoOutputOptions)} to construct new instances
     */
    VideoStreamOutput(VideoOutputOptions options)
    {
        super(options);
    }

    @Override
    public void open(String url) throws IOException
    {
        logger.debug("Opening " + url);

        String protocol = avio_find_protocol_name(url);
        if (protocol == null || !protocol.equals("udp"))
        {
            throw new IllegalArgumentException("Invalid protocol: " + url + "; currently only UDP is supported");
        }

        // Attempt to open the url
        this.url = url;
        int ret;
        avformat.AVIOContext ioContext = new avformat.AVIOContext(null);

        if ((ret = avio_open2(ioContext, url, AVIO_FLAG_WRITE, null, null)) < 0)
        {
            String message = "Error opening stream: " + FfmpegUtils.formatError(ret);
            logger.error(message);
            throw new IOException(message);
        }

        initCodecs();
        initFormat();
        openVideoCodec();
        createVideoStream();

        // TODO: make optional
        createMetadataStream();

        formatContext.pb(ioContext);

        avutil.AVDictionary opts = new avutil.AVDictionary(null);
        avformat_write_header(formatContext, opts);
        av_dict_free(opts);

        createVideoEncoder();
        createPacketSender();

        encoderExecSvc = Executors.newSingleThreadExecutor();
        encoderFuture = encoderExecSvc.submit(videoEncoder);

        senderExecSvc = Executors.newSingleThreadScheduledExecutor();
        senderFuture = senderExecSvc.scheduleAtFixedRate(packetSender, 0,
                Math.round(1000.0/options.getFrameRate()), TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean isOpen()
    {
        return (senderFuture != null) && (!senderFuture.isCancelled());
    }

    @Override
    public void close()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Closing " + url);
        }

        if (!isOpen())
        {
            logger.warn("Video output stream " + url + " is already closed; ignoring close() call");
            return;
        }

        if (encoderFuture != null)
        {
            encoderFuture.cancel(true);
            videoFrames.clear();
        }

        if (senderFuture != null)
        {
            senderFuture.cancel(true);
            klvPackets.clear();
        }

        shutdownExecSvc(encoderExecSvc);
        encoderExecSvc = null;

        shutdownExecSvc(senderExecSvc);
        senderExecSvc = null;

        // Clean up in super
        cleanup();
    }

    @Override
    public void queueVideoFrame(VideoFrame videoFrame) throws IOException
    {
        videoFrames.offer(videoFrame);
    }

    @Override
    public void queueMetadataFrame(MetadataFrame metadataFrame) throws IOException
    {
        avcodec.AVPacket packet = convert(metadataFrame);
        klvPackets.offer(packet);
    }

    /**
     * Create the video encoder runnable to be run in the background
     */
    private void createVideoEncoder()
    {
        videoEncoder = () -> {

            boolean cancelled = false;
            while (!cancelled)
            {
                try
                {
                    // Block waiting for a frame to become available
                    VideoFrame frame = videoFrames.take();
                    encodeFrame(frame);

                } catch (IOException e)
                {
                    // TODO: notify client of error
                    logger.error("IOException while encoding frame", e);
                    cancelled = true;
                } catch (InterruptedException e)
                {
                    logger.error("InterruptedException while encoding frame", e);
                    cancelled = true;
                }
            }
        };
    }

    /**
     * Create the packet sender runnable, to be called once every 1/frame_rate seconds
     */
    private void createPacketSender()
    {
        avcodec.AVPacket packet = av_packet_alloc();
        packetSender = () -> {

            int ret2 = 0;
            while (ret2 != AVERROR_EOF && ret2 != AVERROR_EAGAIN())
            {
                ret2 = avcodec_receive_packet(videoCodecContext, packet);
                if (ret2 == 0)
                {
                    int ret3;
                    if ((ret3 = av_write_frame(formatContext, packet)) < 0)
                    {
                        logger.error("Error writing video packet: " + FfmpegUtils.formatError(ret3));
                    }
                } else if (ret2 == AVERROR_EOF)
                {
                    logger.debug("EOF reached");
                }
            }
        };
    }

    private void shutdownExecSvc(ExecutorService service)
    {
        if (service != null)
        {
            logger.debug("Shutting down exec service");
            service.shutdown();
            try
            {
                service.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e)
            {
                logger.error("Interrupted while awaiting executor service termination", e);
            }
        }
    }
}

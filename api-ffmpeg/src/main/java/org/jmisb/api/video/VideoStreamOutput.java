package org.jmisb.api.video;

import static org.bytedeco.ffmpeg.global.avcodec.av_packet_alloc;
import static org.bytedeco.ffmpeg.global.avcodec.av_packet_clone;
import static org.bytedeco.ffmpeg.global.avcodec.av_packet_free;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_receive_packet;
import static org.bytedeco.ffmpeg.global.avformat.AVIO_FLAG_WRITE;
import static org.bytedeco.ffmpeg.global.avformat.av_write_frame;
import static org.bytedeco.ffmpeg.global.avformat.avformat_write_header;
import static org.bytedeco.ffmpeg.global.avformat.avio_find_protocol_name;
import static org.bytedeco.ffmpeg.global.avformat.avio_open;
import static org.bytedeco.ffmpeg.global.avutil.AVERROR_EOF;
import static org.bytedeco.ffmpeg.global.avutil.av_dict_free;
import static org.bytedeco.ffmpeg.presets.avutil.AVERROR_EAGAIN;

import java.io.IOException;
import java.util.concurrent.*;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVIOContext;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Manages an outgoing video stream. */
public class VideoStreamOutput extends VideoOutput implements IVideoStreamOutput {
    private static Logger logger = LoggerFactory.getLogger(VideoStreamOutput.class);
    private String url;

    private Runnable videoEncoder;
    private Future<?> encoderFuture;
    private BlockingQueue<VideoFrame> videoFrames = new LinkedBlockingDeque<>();
    private ExecutorService encoderExecSvc;

    private Runnable packetSender;
    private Future<?> senderFuture;
    private BlockingQueue<AVPacket> videoPackets = new LinkedBlockingDeque<>();
    private BlockingQueue<AVPacket> klvPackets = new LinkedBlockingDeque<>();
    private ExecutorService senderExecSvc;

    private OutputStatistics outputStatistics = new OutputStatistics();

    /**
     * Constructor.
     *
     * @param options Options for video output
     */
    public VideoStreamOutput(VideoOutputOptions options) {
        super(options);
    }

    @Override
    public void open(String url) throws IOException {
        logger.debug("Opening " + url);

        outputStatistics.reset();

        String protocol = avio_find_protocol_name(url);
        if (protocol == null || !protocol.equals("udp")) {
            throw new IllegalArgumentException(
                    "Invalid protocol: " + url + "; currently only UDP is supported");
        }

        // Attempt to open the url
        this.url = url;
        int ret;
        AVIOContext ioContext = new AVIOContext(null);

        if ((ret = avio_open(ioContext, url, AVIO_FLAG_WRITE)) < 0) {
            String message = "Error opening stream: " + FfmpegUtils.formatError(ret);
            logger.error(message);
            throw new IOException(message);
        }

        initCodecs();
        initFormat();
        createVideoStream();

        if (options.hasKlvStream()) {
            createMetadataStream();
        }

        formatContext.pb(ioContext);

        AVDictionary muxerOptions = new AVDictionary(null);
        // TODO: Set muxer private options disabling SDT and PAT?
        // av_dict_set(muxerOptions, "sdt_period", "1000000", 0);
        // av_dict_set(muxerOptions, "pat_period", "1000000", 0);
        avformat_write_header(formatContext, muxerOptions);
        av_dict_free(muxerOptions);

        createVideoEncoder();
        createPacketSender();

        encoderExecSvc = Executors.newSingleThreadExecutor();
        encoderFuture = encoderExecSvc.submit(videoEncoder);

        senderExecSvc = Executors.newSingleThreadExecutor();
        senderFuture = senderExecSvc.submit(packetSender);
    }

    @Override
    public boolean isOpen() {
        return (senderFuture != null) && (!senderFuture.isCancelled());
    }

    @Override
    public void close() {
        if (logger.isDebugEnabled()) {
            logger.debug("Closing " + url);
        }

        if (!isOpen()) {
            logger.warn("Video output stream " + url + " is already closed; ignoring close() call");
            return;
        }

        if (encoderFuture != null) {
            encoderFuture.cancel(true);
            videoFrames.clear();
        }

        if (senderFuture != null) {
            senderFuture.cancel(true);
            videoPackets.clear();
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
    public void queueVideoFrame(VideoFrame videoFrame) {
        boolean wasAdded = videoFrames.offer(videoFrame);
        if (!wasAdded) {
            logger.info("Video frame could not be queued, possible lag");
            return;
        }
        outputStatistics.videoFrameQueued();
    }

    @Override
    public void queueMetadataFrame(MetadataFrame metadataFrame) throws IOException {
        if (!options.hasKlvStream()) {
            throw new IOException("Attempted to write metadata without a KLV stream");
        }

        AVPacket packet = convert(metadataFrame);
        boolean wasAdded = klvPackets.offer(av_packet_clone(packet));
        if (!wasAdded) {
            logger.info("Metadata Frame could not be queued, possible lag");
            return;
        }
        outputStatistics.metadataFrameQueued();
    }

    @Override
    public OutputStatistics getStatistics() {
        return outputStatistics;
    }

    /** Create the video encoder runnable to be run in the background. */
    private void createVideoEncoder() {
        videoEncoder =
                () -> {
                    boolean cancelled = false;
                    while (!cancelled) {
                        try {
                            // Block waiting for a frame from the client
                            VideoFrame frame = videoFrames.take();
                            encodeFrame(frame);
                            outputStatistics.videoFrameEncoded();

                            // TODO: not sure we should be allocating here; avcodec_receive_packet
                            // below says packet will be allocated by the encoder
                            AVPacket packet = av_packet_alloc();

                            int ret = avcodec_receive_packet(videoCodecContext, packet);
                            if (ret == 0) {
                                boolean wasQueued = videoPackets.offer(av_packet_clone(packet));
                                if (!wasQueued) {
                                    logger.info(
                                            "Packet could not be queued, possible lag in stream");
                                    return;
                                }
                            } else if (ret == AVERROR_EOF) {
                                logger.info("Received EOF from encoder");
                                cancelled = true;
                            } else if (ret != AVERROR_EAGAIN()) {
                                throw new IOException(
                                        "Video encoder error: " + FfmpegUtils.formatError(ret));
                            }
                        } catch (IOException e) {
                            // TODO: notify client of error
                            logger.error("IOException while encoding frame", e);
                            cancelled = true;
                        } catch (InterruptedException e) {
                            // Normal way of shutting down; don't log as an error
                            cancelled = true;
                        }
                    }
                };
    }

    /** Create the packet sender runnable, to be called once every 1/frame_rate seconds. */
    private void createPacketSender() {
        packetSender =
                () -> {
                    boolean cancelled = false;
                    long lastVideoPts = 0;
                    while (!cancelled) {
                        // Block waiting for a video packet
                        AVPacket packet;
                        try {
                            packet = videoPackets.take();
                            int ret;
                            if ((ret = av_write_frame(formatContext, packet)) < 0) {
                                logger.error(
                                        "Error writing video packet: "
                                                + FfmpegUtils.formatError(ret));
                            } else {
                                lastVideoPts = packet.pts();
                                outputStatistics.videoFrameSent();
                            }
                            av_packet_free(packet);
                        } catch (InterruptedException e) {
                            cancelled = true;
                        }

                        // Send all KLV packets whose PTS <= the last video packet's PTS
                        while (klvPackets.peek() != null
                                && klvPackets.peek().pts() <= lastVideoPts) {
                            try {
                                AVPacket pkt = klvPackets.take();
                                int ret;
                                if ((ret = av_write_frame(formatContext, pkt)) < 0) {
                                    logger.error(
                                            "Error writing metadata packet: "
                                                    + FfmpegUtils.formatError(ret));
                                } else {
                                    outputStatistics.metadataFrameSent();
                                }
                                av_packet_free(pkt);
                            } catch (InterruptedException e) {
                                cancelled = true;
                                break;
                            }
                        }
                    }
                    logger.debug("Packet sender thread exiting");
                };
    }

    private void shutdownExecSvc(ExecutorService service) {
        if (service != null) {
            logger.debug("Shutting down exec service");
            service.shutdown();
            try {
                service.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error("Interrupted while awaiting executor service termination", e);
            }
        }
    }
}

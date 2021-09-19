package org.jmisb.api.video;

import static org.bytedeco.ffmpeg.global.avcodec.av_packet_unref;
import static org.jmisb.core.video.TimingUtils.shortWait;

import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Demux video/metadata contained in a file. */
class FileDemuxer extends Demuxer {

    private static Logger logger = LoggerFactory.getLogger(FileDemuxer.class);
    private final VideoInput inputStream;

    private double videoFrameRate;

    private boolean seekRequested = false;
    private double seekPosition;

    FileDemuxer(
            VideoInput inputStream,
            AVFormatContext avFormatContext,
            VideoFileInputOptions options) {
        super(avFormatContext, options);
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Demuxer - " + inputStream.getUrl());
        logger.debug("Starting file demuxer for " + inputStream.getUrl());

        videoFrameRate = FfmpegUtils.getFrameRate(avFormatContext);

        createDecodeThreads(inputStream);

        AVPacket packet = new AVPacket();
        while (!isShutdown()) {
            // If paused, sleep until play() or shutdown() is called
            // TODO: There does not seem to be a good reason to pause this demuxer thread; consider
            // changing its superclass
            if (pauseOrResume()) {
                // Either we were interrupted, or shutdown() was called
                break;
            }

            // Check if we were asked to seek
            if (seekRequested) {
                // Pause the decoder threads
                if (videoDecodeThread != null) {
                    videoDecodeThread.pause();
                }
                for (MetadataDecodeThread metadataDecodeThread : metadataDecodeThreads.values()) {
                    if (metadataDecodeThread != null) {
                        metadataDecodeThread.pause();
                    }
                }

                // Perform the seek
                DemuxerUtils.seek(avFormatContext, seekPosition);

                // Reset the decoders
                if (videoDecodeThread != null) {
                    videoDecodeThread.clear();
                }
                for (MetadataDecodeThread metadataDecodeThread : metadataDecodeThreads.values()) {
                    if (metadataDecodeThread != null) {
                        metadataDecodeThread.clear();
                    }
                }
                // Resume decoding
                if (videoDecodeThread != null) {
                    videoDecodeThread.play();
                }
                for (MetadataDecodeThread metadataDecodeThread : metadataDecodeThreads.values()) {
                    if (metadataDecodeThread != null) {
                        metadataDecodeThread.play();
                    }
                }
                seekRequested = false;
            }

            // Read a packet from the stream
            DemuxReturnValue ret = DemuxerUtils.readPacket(avFormatContext, packet);
            if (ret == DemuxReturnValue.EOF) {
                if (videoDecodeThread != null) {
                    videoDecodeThread.notifyEOF();
                }
                for (MetadataDecodeThread metadataDecodeThread : metadataDecodeThreads.values()) {
                    if (metadataDecodeThread != null) {
                        metadataDecodeThread.notifyEOF();
                    }
                }
            }
            if (ret != DemuxReturnValue.SUCCESS) {
                shortWait(10);
                continue;
            }

            // double pts = packet.pts() *
            // av_q2d(avFormatContext.streams(packet.stream_index()).time_base());
            // Pass packet to the appropriate decoder
            boolean queued = false;
            while (shouldDecode(packet) && !queued && !isShutdown() && !seekRequested) {
                if (packet.stream_index() == videoStreamIndex) {
                    queued = videoDecodeThread.enqueue(packet);
                } else if (dataStreamIndices.contains(packet.stream_index())) {
                    // logger.debug("Data PTS: " + pts);
                    queued = metadataDecodeThreads.get(packet.stream_index()).enqueue(packet);
                }
            }

            // Release the packet's buffer
            av_packet_unref(packet);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Demuxer exiting");
        }

        // Clean up resources
        shutdownThreads();
    }

    @Override
    protected void pause() {
        if (videoDecodeThread != null) {
            videoDecodeThread.pause();
        }
        for (MetadataDecodeThread metadataDecodeThread : metadataDecodeThreads.values()) {
            if (metadataDecodeThread != null) {
                metadataDecodeThread.pause();
            }
        }
        super.pause();
    }

    @Override
    protected void play() {
        super.play();
        if (videoDecodeThread != null) {
            videoDecodeThread.play();
        }
        for (MetadataDecodeThread metadataDecodeThread : metadataDecodeThreads.values()) {
            if (metadataDecodeThread != null) {
                metadataDecodeThread.play();
            }
        }
    }

    void seek(double position) {
        // Notify our thread that a seek has been requested
        seekRequested = true;
        seekPosition = position;

        // Block until seek has been performed
        // TODO: this seems potentially dangerous
        while (seekRequested) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }
    }

    /**
     * Get the video frame rate.
     *
     * @return The video frame rate, in frames/second
     */
    double getVideoFrameRate() {
        return videoFrameRate;
    }
}

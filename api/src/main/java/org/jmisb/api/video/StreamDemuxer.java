package org.jmisb.api.video;

import static org.bytedeco.ffmpeg.global.avcodec.av_packet_unref;
import static org.jmisb.core.video.TimingUtils.shortWait;

import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Demux video/metadata contained in a network stream */
class StreamDemuxer extends Demuxer {
    private static Logger logger = LoggerFactory.getLogger(StreamDemuxer.class);
    private final VideoInput inputStream;

    StreamDemuxer(
            VideoInput inputStream,
            AVFormatContext avFormatContext,
            VideoStreamInputOptions options) {
        super(avFormatContext, options);
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Demuxer - " + inputStream.getUrl());
        logger.debug("Starting stream demuxer for " + inputStream.getUrl());

        createDecodeThreads(inputStream);

        AVPacket packet = new AVPacket();
        while (!isShutdown()) {
            // Read a packet from the stream
            if (DemuxerUtils.readPacket(avFormatContext, packet) != DemuxReturnValue.SUCCESS) {
                shortWait(10);
                continue;
            }

            // Pass packet to the appropriate decoder
            boolean queued = false;
            while (shouldDecode(packet) && !queued && !isShutdown()) {
                if (packet.stream_index() == videoStreamIndex) {
                    queued = videoDecodeThread.enqueue(packet);
                } else if (packet.stream_index() == dataStreamIndex) {
                    queued = metadataDecodeThread.enqueue(packet);
                }
            }

            // Release the packet's buffer
            av_packet_unref(packet);
        }

        // Clean up resources
        shutdownThreads();
    }
}

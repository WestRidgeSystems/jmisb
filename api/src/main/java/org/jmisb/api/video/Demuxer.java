package org.jmisb.api.video;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.jmisb.core.video.FfmpegUtils;

/** Abstract base class for Demuxers. */
abstract class Demuxer extends ProcessingThread {

    final AVFormatContext avFormatContext;
    VideoDecodeThread videoDecodeThread;
    Map<Integer, MetadataDecodeThread> metadataDecodeThreads = new HashMap<>(3);
    int videoStreamIndex;
    List<Integer> dataStreamIndices;
    private final VideoInputOptions options;

    Demuxer(AVFormatContext avFormatContext, VideoInputOptions options) {
        this.avFormatContext = avFormatContext;
        this.options = options;
    }

    void createDecodeThreads(VideoInput videoInput) {
        videoStreamIndex = FfmpegUtils.getVideoStreamIndex(avFormatContext);
        dataStreamIndices = FfmpegUtils.getDataStreamIndices(avFormatContext);

        if (options.isDecodeVideo()) {
            videoDecodeThread =
                    DemuxerUtils.createVideoDecodeThread(
                            videoStreamIndex, avFormatContext, videoInput);
        }

        if (options.isDecodeMetadata()) {
            for (int streamIndex : dataStreamIndices) {
                AVStream stream = FfmpegUtils.getStreamByIndex(avFormatContext, streamIndex);
                MetadataDecodeThread metadataDecodeThread =
                        new MetadataDecodeThread(videoInput, stream);
                metadataDecodeThreads.put(streamIndex, metadataDecodeThread);
            }
        }
    }

    boolean shouldDecode(AVPacket packet) {
        boolean shouldDecode = false;
        if (packet.stream_index() == videoStreamIndex && options.isDecodeVideo()) {
            shouldDecode = true;
        } else if (dataStreamIndices.contains(packet.stream_index())
                && options.isDecodeMetadata()) {
            shouldDecode = true;
        }
        return shouldDecode;
    }

    void shutdownThreads() {
        if (videoDecodeThread != null) {
            videoDecodeThread.shutdown();
            try {
                videoDecodeThread.join();
            } catch (InterruptedException ignored) {
            }
        }

        for (MetadataDecodeThread metadataDecodeThread : metadataDecodeThreads.values()) {
            if (metadataDecodeThread != null) {
                metadataDecodeThread.shutdown();
                try {
                    metadataDecodeThread.join();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}

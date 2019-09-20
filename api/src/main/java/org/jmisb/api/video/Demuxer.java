package org.jmisb.api.video;

import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.jmisb.core.video.FfmpegUtils;

/**
 * Abstract base class for Demuxers
 */
abstract class Demuxer extends ProcessingThread
{
    final AVFormatContext avFormatContext;
    VideoDecodeThread videoDecodeThread;
    MetadataDecodeThread metadataDecodeThread;
    int videoStreamIndex;
    int dataStreamIndex;
    private final VideoInputOptions options;

    Demuxer(AVFormatContext avFormatContext, VideoInputOptions options)
    {
        this.avFormatContext = avFormatContext;
        this.options = options;
    }

    void createDecodeThreads(VideoInput videoInput)
    {
        videoStreamIndex = FfmpegUtils.getVideoStreamIndex(avFormatContext);
        dataStreamIndex = FfmpegUtils.getDataStreamIndex(avFormatContext);

        if (options.isDecodeVideo())
        {
            videoDecodeThread = DemuxerUtils.createVideoDecodeThread(videoStreamIndex, avFormatContext, videoInput);
        }

        if (dataStreamIndex >= 0 && options.isDecodeMetadata())
        {
            metadataDecodeThread = new MetadataDecodeThread(videoInput, FfmpegUtils.getDataStream(avFormatContext));
        }

    }

    boolean shouldDecode(AVPacket packet)
    {
        boolean shouldDecode = false;
        if (packet.stream_index() == videoStreamIndex && options.isDecodeVideo())
            shouldDecode = true;
        else if (packet.stream_index() == dataStreamIndex && options.isDecodeMetadata())
            shouldDecode = true;
        return shouldDecode;
    }

    void shutdownThreads()
    {
        if (videoDecodeThread != null)
        {
            videoDecodeThread.shutdown();
            try
            {
                videoDecodeThread.join();
            } catch (InterruptedException ignored)
            {
            }
        }

        if (metadataDecodeThread != null)
        {
            metadataDecodeThread.shutdown();
            try
            {
                metadataDecodeThread.join();
            } catch (InterruptedException ignored)
            {
            }
        }
    }
}

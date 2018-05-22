package org.jmisb.api.video;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avformat;
import org.jmisb.core.video.FfmpegUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jmisb.core.video.TimingUtils.shortWait;

/**
 * Demux video/metadata contained in a network stream
 */
public class StreamDemuxer extends ProcessingThread
{
    private static Logger logger = LoggerFactory.getLogger(StreamDemuxer.class);
    private final VideoInput inputStream;
    private final avformat.AVFormatContext avFormatContext;
    private MetadataDecodeThread metadataDecodeThread;

    StreamDemuxer(VideoInput inputStream, avformat.AVFormatContext avFormatContext)
    {
        this.inputStream = inputStream;
        this.avFormatContext = avFormatContext;
    }

    @Override
    public void run()
    {
        Thread.currentThread().setName("Demuxer - " + inputStream.getUrl());
        logger.debug("Starting stream demuxer for " + inputStream.getUrl());

        final int videoStreamIndex = FfmpegUtils.getVideoStreamIndex(avFormatContext);
        final int dataStreamIndex = FfmpegUtils.getDataStreamIndex(avFormatContext);

        VideoDecodeThread videoDecodeThread = DemuxerUtils.createVideoDecodeThread(videoStreamIndex, avFormatContext, inputStream);

        if (dataStreamIndex >= 0)
        {
            metadataDecodeThread = new MetadataDecodeThread(inputStream, FfmpegUtils.getDataStream(avFormatContext));
        }

        avcodec.AVPacket packet = new avcodec.AVPacket();
        while (!isShutdown())
        {
            // Read a packet from the stream
            if (!DemuxerUtils.readPacket(avFormatContext, packet))
            {
                shortWait(10);
                continue;
            }

            // Pass packet to the appropriate decoder
            boolean queued = false;
            while (!queued && !isShutdown())
            {
                if (packet.stream_index() == videoStreamIndex)
                {
                    queued = videoDecodeThread.enqueue(packet);
                } else if (packet.stream_index() == dataStreamIndex)
                {
                    queued = metadataDecodeThread.enqueue(packet);
                }
            }

            // Release the packet's buffer
            avcodec.av_packet_unref(packet);
        }

        // Clean up resources
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

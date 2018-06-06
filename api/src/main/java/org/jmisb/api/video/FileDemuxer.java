package org.jmisb.api.video;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avformat;
import org.jmisb.core.video.FfmpegUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.bytedeco.javacpp.avutil.av_q2d;
import static org.jmisb.core.video.TimingUtils.shortWait;

/**
 * Demux video/metadata contained in a file
 */
class FileDemuxer extends ProcessingThread
{
    private static Logger logger = LoggerFactory.getLogger(FileDemuxer.class);
    private final VideoInput inputStream;
    private final avformat.AVFormatContext avFormatContext;

    private VideoDecodeThread videoDecodeThread;
    private MetadataDecodeThread metadataDecodeThread;

    private double videoFrameRate;

    private boolean seekRequested = false;
    private double seekPosition;

    FileDemuxer(VideoInput inputStream, avformat.AVFormatContext avFormatContext)
    {
        this.inputStream = inputStream;
        this.avFormatContext = avFormatContext;
    }

    @Override
    public void run()
    {
        Thread.currentThread().setName("Demuxer - " + inputStream.getUrl());

        videoFrameRate = FfmpegUtils.getFrameRate(avFormatContext);

        final int videoStreamIndex = FfmpegUtils.getVideoStreamIndex(avFormatContext);
        final int dataStreamIndex = FfmpegUtils.getDataStreamIndex(avFormatContext);

        videoDecodeThread = DemuxerUtils.createVideoDecodeThread(videoStreamIndex, avFormatContext, inputStream);

        if (dataStreamIndex >= 0)
        {
            metadataDecodeThread = new MetadataDecodeThread(inputStream, FfmpegUtils.getDataStream(avFormatContext));
        }

        avcodec.AVPacket packet = new avcodec.AVPacket();
        while (!isShutdown())
        {
            // If paused, sleep until play() or shutdown() is called
            // TODO: There does not seem to be a good reason to pause this demuxer thread; consider changing
            // its superclass
            if (pauseOrResume())
            {
                // Either we were interrupted, or shutdown() was called
                break;
            }

            // Check if we were asked to seek
            if (seekRequested)
            {
                // Pause the decoder threads
                videoDecodeThread.pause();
                metadataDecodeThread.pause();

                // Perform the seek
                DemuxerUtils.seek(avFormatContext, seekPosition);

                // Reset the decoders
                videoDecodeThread.clear();
                metadataDecodeThread.clear();

                // Resume decoding
                videoDecodeThread.play();
                metadataDecodeThread.play();

                seekRequested = false;
            }

            // Read a packet from the stream
            if (!DemuxerUtils.readPacket(avFormatContext, packet))
            {
                shortWait(10);
                continue;
            }

            double pts = packet.pts() * av_q2d(avFormatContext.streams(packet.stream_index()).time_base());

            // Pass packet to the appropriate decoder
            boolean queued = false;
            while (!queued && !isShutdown() && !seekRequested)
            {
                if (packet.stream_index() == videoStreamIndex)
                {
                    queued = videoDecodeThread.enqueue(packet);
                } else if (packet.stream_index() == dataStreamIndex)
                {
                    //logger.debug("Data PTS: " + pts);
                    queued = metadataDecodeThread.enqueue(packet);
                }
            }

            // Release the packet's buffer
            avcodec.av_packet_unref(packet);
        }

        if (logger.isDebugEnabled())
            logger.debug("Demuxer exiting");

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

    @Override
    protected void pause()
    {
        if (videoDecodeThread != null)
        {
            videoDecodeThread.pause();
        }
        if (metadataDecodeThread != null)
        {
            metadataDecodeThread.pause();
        }
        super.pause();
    }

    @Override
    protected void play()
    {
        super.play();
        if (videoDecodeThread != null)
        {
            videoDecodeThread.play();
        }
        if (metadataDecodeThread != null)
        {
            metadataDecodeThread.play();
        }
    }

    public void seek(double position)
    {
        // Notify our thread that a seek has been requested
        seekRequested = true;
        seekPosition = position;

        // Block until seek has been performed
        // TODO: this seems potentially dangerous
        while (seekRequested)
        {
            try
            {
                Thread.sleep(10);
            } catch (InterruptedException ignored)
            {
            }
        }
    }

    /**
     * Get the video frame rate
     *
     * @return The video frame rate, in frames/second
     */
    public double getVideoFrameRate()
    {
        return videoFrameRate;
    }
}

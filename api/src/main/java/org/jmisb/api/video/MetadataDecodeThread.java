package org.jmisb.api.video;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avformat;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.KlvParser;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.core.video.FfmpegUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.bytedeco.javacpp.avcodec.av_packet_clone;
import static org.bytedeco.javacpp.avutil.av_q2d;

/**
 * Metadata decoding thread
 * <p>
 * This thread decodes KLV metadata and sends {@link IMisbMessage}s up to the {@link VideoInput}.
 */
public class MetadataDecodeThread extends ProcessingThread
{
    private static Logger logger = LoggerFactory.getLogger(MetadataDecodeThread.class);
    private final static int INPUT_QUEUE_SIZE = 100;
    private final VideoInput inputStream;
    private final avformat.AVStream dataStream;
    private BlockingQueue<avcodec.AVPacket> packetQueue = new LinkedBlockingDeque<>(INPUT_QUEUE_SIZE);

    MetadataDecodeThread(VideoInput inputStream, avformat.AVStream dataStream)
    {
        this.inputStream = inputStream;
        this.dataStream = dataStream;
        start();
    }

    /**
     * Enqueue an incoming packet for decoding
     *
     * @param packet The packet to queue
     * @return True if the packet was queued, false if the queue is currently full
     */
    public boolean enqueue(avcodec.AVPacket packet)
    {
        try
        {
            return packetQueue.offer(av_packet_clone(packet), 10, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignored)
        {
            return false;
        }
    }

    public void clear()
    {
        // Clear out our input queue
        packetQueue.clear();
    }

    @Override
    public void run()
    {
        Thread.currentThread().setName("MetadataDecodeThread - " + inputStream.getUrl());

        avcodec.AVCodecContext codecContext = avcodec.avcodec_alloc_context3(null);
        int ret;
        if ((ret = avcodec.avcodec_parameters_to_context(codecContext, dataStream.codecpar())) < 0)
        {
            logger.error("Couldn't create AVCodecContext for stream " + dataStream.index() +
                        " codec: " + dataStream.codecpar().codec_tag() + " error " + FfmpegUtils.formatError(ret));
            avcodec.avcodec_free_context(codecContext);
            return;
        }

        // TODO: check for "KLVA"?
        String fourcc = FfmpegUtils.tagToFourCc(dataStream.codecpar().codec_tag());
        if (logger.isDebugEnabled())
        {
            logger.debug("Data stream codec tag = " + dataStream.codecpar().codec_tag() +
                    "; fourcc = " + fourcc);
        }

        // Open log file
        // TODO: fix
        FileOutputStream logFile = null;
        /*
        if (inputStream.getOptions().getDataDump() != null)
        {
            try
            {
                logFile = new FileOutputStream(inputStream.getOptions().getDataDump());
            } catch (FileNotFoundException e)
            {
                logger.error("Could not open file", e);
            }
        }
        */

        while (!isShutdown())
        {
            // If paused, sleep until play() or shutdown() is called
            if (pauseOrResume())
            {
                // Either we were interrupted, or shutdown() was called
                break;
            }

            try
            {
                avcodec.AVPacket packet = packetQueue.poll(10, TimeUnit.MILLISECONDS);
                if (packet != null)
                {
                    double pts = packet.pts() * av_q2d(dataStream.time_base());
//                    logger.debug("Data PTS = " + pts);

                    byte[] data = new byte[packet.size()];
                    packet.data().get(data);

                    // Log data, if enabled
                    if (logFile != null)
                    {
                        logFile.write(data);
                    }

                    try
                    {
                        List<IMisbMessage> messages = KlvParser.parseBytes(data);
                        for (IMisbMessage message : messages)
                        {
                            boolean queued = false;
                            while (!queued && !isShutdown() && !isPauseRequested())
                            {
                                queued = inputStream.queueMetadataFrame(new MetadataFrame(message, pts), 20);
                            }
                            if (isShutdown() || isPauseRequested())
                                break;
                        }
                    }
                    catch (KlvParseException exception)
                    {
                        logger.error("KLV parse exception", exception);
                    }
                }
            }
            catch (InterruptedException ignored)
            {

            } catch (IOException e)
            {
                logger.error("Error writing data stream file", e);
            }
        }

        if (logger.isDebugEnabled())
            logger.debug("Data stream decoder exiting");

        if (logFile != null)
        {
            try
            {
                logFile.flush();
                logFile.close();
            } catch (IOException e)
            {
                logger.error("Error writing data stream file", e);
            }
        }

        avcodec.avcodec_free_context(codecContext);
    }
}

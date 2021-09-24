package org.jmisb.api.video;

import static org.bytedeco.ffmpeg.global.avformat.avformat_close_input;
import static org.bytedeco.ffmpeg.global.avformat.avformat_free_context;
import static org.jmisb.core.video.TimingUtils.shortWait;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Abstract base class for video input. */
public abstract class VideoInput extends VideoIO implements IVideoInput {
    private static final Logger logger = LoggerFactory.getLogger(VideoInput.class);
    private final Set<IVideoListener> videoListeners = new HashSet<>();
    private final Set<IMetadataListener> metadataListeners = new HashSet<>();

    /** Queue of decoded video frames ready to be sent to listeners. */
    private final BlockingQueue<VideoFrame> decodedVideo = new LinkedBlockingQueue<>(QUEUE_SIZE);

    /** Queue of metadata frames ready to be sent to listeners. */
    private final BlockingQueue<MetadataFrame> decodedMetadata = new LinkedBlockingQueue<>(QUEUE_SIZE);

    VideoNotifier videoNotifier;
    MetadataNotifier metadataNotifier;

    private static final int QUEUE_SIZE = 100;

    String url;
    AVFormatContext formatContext;

    @Override
    public abstract void open(String url) throws IOException;

    @Override
    public abstract boolean isOpen();

    @Override
    public abstract void close();

    /**
     * Insert a sleep to control playback rate prior to notifying clients.
     *
     * @param pts PTS of the video frame to be delivered
     */
    protected abstract void delayVideo(double pts);

    /**
     * Insert a sleep to control playback rate prior to notifying clients.
     *
     * @param pts PTS of the metadata frame to be delivered
     * @throws InterruptedException If the thread is interrupted while waiting
     */
    protected abstract void delayMetadata(double pts) throws InterruptedException;

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public List<PesInfo> getPesInfo() {
        List<PesInfo> list = new ArrayList<>();
        if (isOpen() && formatContext != null) {
            final int numStreams = FfmpegUtils.getNumStreams(formatContext);
            for (int i = 0; i < numStreams; i++) {
                int type = FfmpegUtils.getStreamType(formatContext, i);
                String codec = FfmpegUtils.getCodecName(formatContext, i);
                list.add(new PesInfo(i, PesType.getType(type), codec));
            }
        }
        return list;
    }

    @Override
    public void addFrameListener(IVideoListener listener) {
        videoListeners.add(listener);
    }

    @Override
    public void removeFrameListener(IVideoListener listener) {
        videoListeners.remove(listener);
    }

    @Override
    public void addMetadataListener(IMetadataListener listener) {
        metadataListeners.add(listener);
    }

    @Override
    public void removeMetadataListener(IMetadataListener listener) {
        metadataListeners.remove(listener);
    }

    /**
     * Attempt to queue a newly decoded video frame for client notification.
     *
     * @param frame The video frame
     * @param timeout Milliseconds to wait for the queue to become available before failing
     * @return True of the frame was successfully queued
     */
    protected boolean queueVideoFrame(VideoFrame frame, long timeout) {
        VideoFrame copy = deepCopy(frame);
        boolean queued = false;
        try {
            queued = decodedVideo.offer(copy, timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignored) {
        }
        return queued;
    }

    /**
     * Attempt to queue a newly decoded metadata frame for client notification.
     *
     * @param frame The metadata frame
     * @param timeout Milliseconds to wait for the queue to become available before failing
     * @return True if the frame was successfully queued
     */
    protected boolean queueMetadataFrame(MetadataFrame frame, long timeout) {
        boolean queued = false;
        try {
            queued = decodedMetadata.offer(frame, timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignored) {
        }
        return queued;
    }

    /**
     * Start up the notifier threads.
     *
     * @param startPaused True to begin in a paused state
     */
    void startNotifiers(boolean startPaused) {
        videoNotifier = new VideoNotifier(startPaused);
        videoNotifier.start();

        metadataNotifier = new MetadataNotifier(startPaused);
        metadataNotifier.start();
    }

    void sendOneFrame() {
        videoNotifier.frame();
    }

    void stopNotifiers() {
        videoNotifier.shutdown();
        try {
            videoNotifier.join();
        } catch (InterruptedException ignored) {
        }
        videoNotifier = null;

        metadataNotifier.shutdown();
        try {
            metadataNotifier.join();
        } catch (InterruptedException ignored) {
        }
        metadataNotifier = null;

        logger.debug("Clearing decodedVideo");
        decodedVideo.clear();
        decodedMetadata.clear();
    }

    /** Thread to notify clients of new video frames. */
    protected class VideoNotifier extends Thread {
        private volatile boolean shutdown = false;
        private boolean paused;
        private boolean getOneFrame = false;

        VideoNotifier(boolean paused) {
            this.paused = paused;
        }

        @Override
        public void run() {
            Thread.currentThread().setName("VideoNotifier - " + getUrl());

            while (!shutdown) {
                while (!shutdown && paused && !getOneFrame) {
                    shortWait(50);
                }

                if (!shutdown) {
                    try {
                        VideoFrame frame = decodedVideo.poll(50, TimeUnit.MILLISECONDS);

                        if (frame != null) {
                            if (getOneFrame)
                                logger.debug("Got one frame from queue, pts = " + frame.getPts());

                            // Sleep if we are trying to control playback rate
                            delayVideo(frame.getPts());

                            videoListeners.forEach(listener -> listener.onVideoReceived(frame));
                            getOneFrame = false;
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }

        void shutdown() {
            shutdown = true;
            interrupt();
        }

        protected void pauseOutput() {
            paused = true;
        }

        protected void resumeOutput() {
            paused = false;
        }

        protected void frame() {
            getOneFrame = true;
        }
    }

    /** Thread to notify clients of new metadata. */
    protected class MetadataNotifier extends Thread {
        private volatile boolean shutdown = false;
        private boolean paused;

        MetadataNotifier(boolean paused) {
            this.paused = paused;
        }

        @Override
        public void run() {
            Thread.currentThread().setName("MetadataNotifier - " + getUrl());

            while (!shutdown) {
                while (!shutdown && paused) {
                    shortWait(50);
                }

                if (!shutdown) {
                    try {
                        MetadataFrame frame = decodedMetadata.poll(50, TimeUnit.MILLISECONDS);

                        if (frame != null) {
                            // Sleep if we are trying to control playback rate
                            delayMetadata(frame.getPts());

                            metadataListeners.forEach(
                                    listener -> listener.onMetadataReceived(frame));
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }

        void shutdown() {
            shutdown = true;
            interrupt();
        }

        protected void pauseOutput() {
            paused = true;
        }

        protected void resumeOutput() {
            paused = false;
        }
    }

    /**
     * Copy a VideoFrame.
     *
     * @param frame The frame to copy
     * @return The newly allocated frame
     */
    private static VideoFrame deepCopy(VideoFrame frame) {
        ColorModel cm = frame.getImage().getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = frame.getImage().copyData(null);
        return new VideoFrame(
                new BufferedImage(cm, raster, isAlphaPremultiplied, null), frame.getPts());
    }

    /** Free the format context. */
    void freeContext() {
        if (formatContext != null) {
            avformat_close_input(formatContext);
            avformat_free_context(formatContext);
            formatContext = null;
        }
    }

    /**
     * Check if our decoded data queues are empty.
     *
     * @return return true if all queues are empty, otherwise false.
     */
    protected boolean queuesAreEmpty() {
        return (decodedVideo.isEmpty()) && (decodedMetadata.isEmpty());
    }
}

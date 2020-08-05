package org.jmisb.api.video;

/** Statistics reported by {@link VideoStreamOutput}. */
public class OutputStatistics {
    private long numVideoFramesSent;
    private long numVideoFramesQueued;
    private long numVideoFramesEncoded;

    private long numMetadataFramesSent;
    private long numMetadataFramesQueued;

    /**
     * Get the total number of video frames sent since opening the stream.
     *
     * @return The total number of video frames
     */
    public long getNumVideoFramesSent() {
        return numVideoFramesSent;
    }

    /**
     * Get the total number of video frames queued since opening the stream.
     *
     * @return The total number of video frames
     */
    public long getNumVideoFramesQueued() {
        return numVideoFramesQueued;
    }

    /**
     * Get the total number of video frames encoded since opening the stream.
     *
     * @return The total number of video frames
     */
    public long getNumVideoFramesEncoded() {
        return numVideoFramesEncoded;
    }

    /**
     * Get the total number of metadata frames sent since opening the stream.
     *
     * @return The total number of metadata frames
     */
    public long getNumMetadataFramesSent() {
        return numMetadataFramesSent;
    }

    /**
     * Get the total number of metadata frames queued since opening the stream.
     *
     * @return The total number of metadata frames
     */
    public long getNumMetadataFramesQueued() {
        return numMetadataFramesQueued;
    }

    /** Reset the statistics to zero. */
    void reset() {
        numVideoFramesSent = 0;
        numVideoFramesQueued = 0;
        numMetadataFramesSent = 0;
        numMetadataFramesQueued = 0;
    }

    /** Increment the total number of video frames queued. */
    void videoFrameQueued() {
        numVideoFramesQueued++;
    }

    /** Increment the total number of video frames sent. */
    void videoFrameSent() {
        numVideoFramesSent++;
    }

    /** Increment the total number of frames encoded. */
    void videoFrameEncoded() {
        numVideoFramesEncoded++;
    }

    /** Increment the total number of metadata frames queued. */
    void metadataFrameQueued() {
        numMetadataFramesQueued++;
    }

    /** Increment the total number of metadata frames sent. */
    void metadataFrameSent() {
        numMetadataFramesSent++;
    }

    @Override
    public String toString() {
        return "video = ("
                + numVideoFramesSent
                + "/"
                + numVideoFramesQueued
                + "), metadata = ("
                + numMetadataFramesSent
                + "/"
                + numMetadataFramesQueued
                + ")";
    }
}

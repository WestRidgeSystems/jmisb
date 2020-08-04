package org.jmisb.api.video;

/** Options to be specified when opening an input stream. */
public class VideoStreamInputOptions extends VideoInputOptions {
    /** Timeout before failing when opening a stream, in milliseconds. */
    private long openTimeout;

    /** Maximum analyze duration, in milliseconds. */
    private long maxAnalyzeDuration;

    /** Constructor specifying default options. */
    public VideoStreamInputOptions() {
        this.openTimeout = 10_000;
        this.maxAnalyzeDuration = 15_000;
    }

    /**
     * Constructor specifying custom options.
     *
     * @param decodeAudio True to decode audio (currently unsupported)
     * @param decodeMetadata True to decode metadata
     * @param decodeVideo True to decode video
     * @param openTimeout Timeout before failing when opening a stream, in milliseconds
     * @param maxAnalyzeDuration Max analyze duration, in milliseconds
     */
    public VideoStreamInputOptions(
            boolean decodeAudio,
            boolean decodeMetadata,
            boolean decodeVideo,
            long openTimeout,
            long maxAnalyzeDuration) {
        super(decodeAudio, decodeMetadata, decodeVideo);
        this.openTimeout = openTimeout;
        this.maxAnalyzeDuration = maxAnalyzeDuration;
    }

    /**
     * Get the timeout before failing when opening a stream.
     *
     * @return Failure timeout, in milliseconds
     */
    public long getOpenTimeout() {
        return openTimeout;
    }

    /**
     * Get the maximum analysis duration.
     *
     * @return Max analyze duration, in microseconds
     */
    public long getMaxAnalyzeDuration() {
        return maxAnalyzeDuration;
    }
}

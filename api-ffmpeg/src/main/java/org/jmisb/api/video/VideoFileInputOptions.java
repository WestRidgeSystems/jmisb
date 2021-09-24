package org.jmisb.api.video;

/** Options to be specified when opening an input file. */
public class VideoFileInputOptions extends VideoInputOptions {
    /** Indicates playback will be paused when the file is first opened. */
    private final boolean initiallyPaused;

    /** Constructor specifying default options. */
    public VideoFileInputOptions() {
        this.initiallyPaused = false;
    }

    /**
     * Constructor specifying custom options.
     *
     * @param decodeAudio True to decode audio (currently unsupported)
     * @param decodeMetadata True to decode metadata
     * @param decodeVideo True to decode video
     * @param initiallyPaused If true, playback will be paused when the file is first opened
     */
    public VideoFileInputOptions(
            boolean decodeAudio,
            boolean decodeMetadata,
            boolean decodeVideo,
            boolean initiallyPaused) {
        super(decodeAudio, decodeMetadata, decodeVideo);
        this.initiallyPaused = initiallyPaused;
    }

    /**
     * Indicates whether playback will be initially paused.
     *
     * @return True if playback should be initially paused
     */
    public boolean isInitiallyPaused() {
        return initiallyPaused;
    }
}

package org.jmisb.api.video;

/** Options to be be specified when opening an input file or stream. */
public class VideoInputOptions {
    private final boolean decodeAudio;
    private final boolean decodeMetadata;
    private final boolean decodeVideo;

    /** Construct with default values. */
    public VideoInputOptions() {
        decodeAudio = false;
        decodeMetadata = true;
        decodeVideo = true;
    }

    /**
     * Constructor.
     *
     * @param decodeAudio True to decode audio (currently unsupported)
     * @param decodeMetadata True to decode metadata
     * @param decodeVideo True to decode video
     */
    public VideoInputOptions(boolean decodeAudio, boolean decodeMetadata, boolean decodeVideo) {
        this.decodeAudio = decodeAudio;
        this.decodeMetadata = decodeMetadata;
        this.decodeVideo = decodeVideo;
    }

    /**
     * Whether to decode audio elementary streams.
     *
     * @return true to decode audio, false to ignore audio
     */
    public boolean isDecodeAudio() {
        return decodeAudio;
    }

    /**
     * Whether to decode metadata elementary streams.
     *
     * @return true to decode metadata, false to ignore metadata
     */
    public boolean isDecodeMetadata() {
        return decodeMetadata;
    }

    /**
     * Whether to decode video elementary streams.
     *
     * @return true to decode video, false to ignore video
     */
    public boolean isDecodeVideo() {
        return decodeVideo;
    }
}

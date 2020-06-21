package org.jmisb.api.video;

/** Options to be be specified when opening an input file or stream */
public class VideoInputOptions {
    private final boolean decodeAudio;
    private final boolean decodeMetadata;
    private final boolean decodeVideo;

    /** Construct with default values */
    public VideoInputOptions() {
        decodeAudio = false;
        decodeMetadata = true;
        decodeVideo = true;
    }

    /**
     * Constructor
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

    public boolean isDecodeAudio() {
        return decodeAudio;
    }

    public boolean isDecodeMetadata() {
        return decodeMetadata;
    }

    public boolean isDecodeVideo() {
        return decodeVideo;
    }
}

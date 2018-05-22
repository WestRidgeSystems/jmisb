package org.jmisb.api.video;

/**
 * Options to be specified when opening an input file
 */
public class VideoFileInputOptions
{
    /** Indicates playback will be paused when the file is first opened */
    private boolean initiallyPaused;

    /**
     * Constructor specifying default options
     */
    public VideoFileInputOptions()
    {
        this(false);
    }

    /**
     * Constructor specifying custom options
     *
     * @param initiallyPaused If true, playback will be paused when the file is first opened
     */
    public VideoFileInputOptions(boolean initiallyPaused)
    {
        this.initiallyPaused = initiallyPaused;
    }

    /**
     * Indicates whether playback will be initially paused
     *
     * @return True if playback should be initially paused
     */
    public boolean isInitiallyPaused()
    {
        return initiallyPaused;
    }
}

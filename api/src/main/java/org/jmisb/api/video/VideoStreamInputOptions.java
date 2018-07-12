package org.jmisb.api.video;

/**
 * Options to be specified when opening an input stream
 */
public class VideoStreamInputOptions
{
    /** Timeout before failing when opening a stream, in milliseconds */
    private long openTimeout;

    /** Maximum analyze duration, in milliseconds */
    private long maxAnalyzeDuration;

    /**
     * Constructor specifying default options
     */
    public VideoStreamInputOptions()
    {
        this(10_000, 15_000);
    }

    /**
     * Constructor specifying custom options
     *
     * @param openTimeout Timeout before failing when opening a stream, in milliseconds
     * @param maxAnalyzeDuration Max analyze duration, in milliseconds
     */
    public VideoStreamInputOptions(long openTimeout, long maxAnalyzeDuration)
    {
        this.openTimeout = openTimeout;
        this.maxAnalyzeDuration = maxAnalyzeDuration;
    }

    /**
     * Get the timeout before failing when opening a stream
     *
     * @return Failure timeout, in milliseconds
     */
    public long getOpenTimeout()
    {
        return openTimeout;
    }

    /**
     * Get the maximum analysis duration
     *
     * @return Max analyze duration, in microseconds
     */
    public long getMaxAnalyzeDuration()
    {
        return maxAnalyzeDuration;
    }
}

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

    /** Filename of debug file for logging data stream (null to turn logging off) */
    private String dataDump;

    /**
     * Constructor specifying default options
     */
    public VideoStreamInputOptions()
    {
        this(10_000, 15_000, null);
    }

    /**
     * Constructor specifying custom options
     *
     * @param openTimeout Timeout before failing when opening a stream, in milliseconds
     * @param maxAnalyzeDuration Max analyze duration, in milliseconds
     * @param dataDump Filename for logging data stream (null to turn logging off)
     */
    public VideoStreamInputOptions(long openTimeout, long maxAnalyzeDuration, String dataDump)
    {
        this.openTimeout = openTimeout;
        this.maxAnalyzeDuration = maxAnalyzeDuration;
        this.dataDump = dataDump;
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

    /**
     * Get the data stream log file
     *
     * @return Filename of the data stream log file
     */
    public String getDataDump()
    {
        return dataDump;
    }
}

package org.jmisb.api.video;

import org.jmisb.api.klv.IMisbMessage;

/**
 * A timestamped metadata packet containing one {@link IMisbMessage}
 */
public class MetadataFrame
{
    private final IMisbMessage misbMessage;
    private final double pts;

    /**
     * Create a metadata frame
     * @param misbMessage The message packet
     * @param pts The presentation timestamp, in seconds
     */
    public MetadataFrame(IMisbMessage misbMessage, double pts)
    {
        this.misbMessage = misbMessage;
        this.pts = pts;
    }

    /**
     * Get the message packet
     * @return The message packet
     */
    public IMisbMessage getMisbMessage()
    {
        return misbMessage;
    }

    /**
     * Get the presentation timestamp
     * @return The presentation timestamp, in seconds
     */
    public double getPts()
    {
        return pts;
    }
}

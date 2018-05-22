package org.jmisb.api.video;

import java.awt.image.BufferedImage;

/**
 * An uncompressed video frame
 */
public class VideoFrame
{
    final private BufferedImage bufferedImage;
    final private double pts;

    /**
     * Create a video frame
     * @param image The image
     * @param pts The presentation timestamp, in seconds
     */
    public VideoFrame(BufferedImage image, double pts)
    {
        this.bufferedImage = image;
        this.pts = pts;
    }

    /**
     * Get the image
     * @return The image
     */
    public BufferedImage getImage()
    {
        return bufferedImage;
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

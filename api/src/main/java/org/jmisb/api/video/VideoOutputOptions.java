package org.jmisb.api.video;

/**
 * Options to be specified when opening an output file or stream
 */
public class VideoOutputOptions
{
    private final int width;
    private final int height;
    private final double frameRate;
    private final int bitRate;
    private final int gopSize;

    /**
     * Construct with default values
     *
     * @param width Video frame width, in pixels
     * @param height Video frame height, in pixels
     */
    public VideoOutputOptions(int width, int height)
    {
        this(width, height, 1500000, 30.0, 30);
    }

    /**
     * Constructor
     *
     * @param width Video frame width, in pixels
     * @param height Video frame height, in pixels
     * @param bitRate Target stream bit rate in bits/second
     * @param frameRate Stream frame rate in frames/second
     * @param gopSize Group of Picture size, i.e., the I-frame interval
     */
    public VideoOutputOptions(int width, int height, int bitRate, double frameRate, int gopSize)
    {
        this.width = width;
        this.height = height;
        this.bitRate = bitRate;
        this.frameRate = frameRate;
        this.gopSize = gopSize;
    }

    /**
     * Get the image width
     * @return Width in pixels
     */
    public int getWidth() { return width; }

    /**
     * Get the image height
     * @return Height in pixels
     */
    public int getHeight() { return height; }

    /**
     * Get the stream bit rate
     * @return Bit rate in bits/second
     */
    public int getBitRate() { return bitRate; }

    /**
     * Get the stream frame rate
     * @return Frame rate in frames/second
     */
    public double getFrameRate() { return frameRate; }

    /**
     * Get the Group of pictures (GOP) size
     * @return GOP size, i.e., the I-frame interval
     */
    public int getGopSize() { return gopSize; }
}

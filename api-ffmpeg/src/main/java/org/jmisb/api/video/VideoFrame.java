package org.jmisb.api.video;

import java.awt.image.BufferedImage;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.jmisb.api.klv.st0603.TimeStatus;

/** An uncompressed video frame. */
public class VideoFrame {
    private final BufferedImage bufferedImage;
    private final double pts;
    private ST0603TimeStamp timeStamp = null;
    private TimeStatus timeStatus = null;

    /**
     * Create a video frame.
     *
     * @param image The image
     * @param pts The presentation timestamp, in seconds
     */
    public VideoFrame(BufferedImage image, double pts) {
        this.bufferedImage = image;
        this.pts = pts;
    }

    /**
     * Get the image.
     *
     * @return The image
     */
    public BufferedImage getImage() {
        return bufferedImage;
    }

    /**
     * Get the presentation timestamp.
     *
     * @return The presentation timestamp, in seconds
     */
    public double getPts() {
        return pts;
    }

    /**
     * Get the precision time stamp for this frame.
     *
     * <p>This is the time that the frame was collected.
     *
     * <p>Not all frames will have a precision time stamp associated. While it is now required by
     * the MISP, there are a lot of older / non-compliant imagery sources.
     *
     * @return the precision time stamp for this frame, or null.
     */
    public ST0603TimeStamp getTimeStamp() {
        return timeStamp;
    }

    /**
     * Set the precision time stamp for this frame.
     *
     * <p>This is the time that the frame was collected.
     *
     * @param timeStamp the precision time stamp for this frame, or null.
     */
    public void setTimeStamp(ST0603TimeStamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Get the time status for this frame.
     *
     * <p>Not all frames will have a time status associated. While it is now required by the MISP,
     * there are a lot of older / non-compliant imagery sources.
     *
     * @return the time status for this frame, or null.
     */
    public TimeStatus getTimeStatus() {
        return timeStatus;
    }

    /**
     * Set the time status for this frame.
     *
     * @param timeStatus the time status for this frame, or null.
     */
    public void setTimeStatus(TimeStatus timeStatus) {
        this.timeStatus = timeStatus;
    }
}

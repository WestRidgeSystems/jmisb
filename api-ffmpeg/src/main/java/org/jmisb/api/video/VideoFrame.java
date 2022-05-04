package org.jmisb.api.video;

import java.awt.image.BufferedImage;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.jmisb.api.klv.st0603.TimeStatus;
import org.jmisb.api.klv.st1204.CoreIdentifier;

/** An uncompressed video frame. */
public class VideoFrame {
    private final BufferedImage bufferedImage;
    private final double pts;
    private ST0603TimeStamp timeStamp = null;
    private TimeStatus timeStatus = null;
    private CoreIdentifier miisCoreId = null;

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

    /**
     * Get the MIIS Core Identifier.
     *
     * <p>Not all frames will have a MIIS Core Identifier. ST 2101 requires it at least every 30
     * seconds, or when it changes. Also, this concept is fairly new, and there are a lot of older
     * imagery sources.
     *
     * @return the core identifier for this frame, or null if there is no core identifier in the
     *     frame.
     */
    public CoreIdentifier getMiisCoreId() {
        return miisCoreId;
    }

    /**
     * Set the core identifier for this frame.
     *
     * @param miisCoreId the core identifier to set on the frame.
     */
    public void setMiisCoreId(CoreIdentifier miisCoreId) {
        this.miisCoreId = miisCoreId;
    }
}

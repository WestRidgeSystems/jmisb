package org.jmisb.api.video;

/** Interface for video frame arrival notifications. */
public interface IVideoListener {
    /**
     * Notification that a video frame has been received.
     *
     * @param image The video frame
     */
    void onVideoReceived(VideoFrame image);
}

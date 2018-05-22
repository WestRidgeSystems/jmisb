package org.jmisb.api.video;

/**
 * Interface for reading video/metadata from a file
 */
public interface IVideoFileInput extends IVideoInput
{
    /**
     * Play the video file
     */
    void play();

    /**
     * Check if the video file is playing
     *
     * @return True if currently playing
     */
    boolean isPlaying();

    /**
     * Set the playback speed
     *
     * @param multiplier The rate multiplier (e.g., 2.0 for 2x rate)
     */
    void setPlaybackSpeed(double multiplier);

    /**
     * Get the current playback speed
     *
     * @return The rate multiplier (e.g., 2.0 for 2x rate)
     */
    double getPlaybackSpeed();

    /**
     * Pause video file playback
     */
    void pause();

    /**
     * Get the total video file duration
     *
     * @return The duration, in seconds
     */
    double getDuration();

    /**
     * Get the current playback position
     *
     * @return The current position, in seconds
     */
    double getPosition();

    /**
     * Seek to a specified position in the file
     *
     * @param position The desired position, in seconds
     */
    void seek(double position);

    /**
     * Get the number of video frames
     *
     * @return The total number of video frames in the file
     */
    int getNumFrames();
}

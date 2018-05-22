package org.jmisb.api.video;

import java.io.IOException;

/**
 * Base interface for reading video and metadata
 */
public interface IVideoInput extends AutoCloseable
{
    /**
     * Open video for reading
     *
     * @param url URL of the video to open
     * @throws IOException if the video was not successfully opened
     */
    void open(String url) throws IOException;

    /**
     * Check if the video is open
     *
     * @return True if the video is open
     */
    boolean isOpen();

    /**
     * Close the video
     *
     * @throws IOException if an error occurs while closing the file
     */
    @Override
    void close() throws IOException;

    /**
     * Get the URL
     *
     * @return URL string
     */
    String getUrl();

    /**
     * Add a video frame listener
     *
     * @param listener Listener to add
     */
    void addFrameListener(FrameListener listener);

    /**
     * Remove a video frame listener
     *
     * @param listener Listener to remove
     */
    void removeFrameListener(FrameListener listener);

    /**
     * Add a metadata listener
     *
     * @param listener Listener to add
     */
    void addMetadataListener(MetadataListener listener);

    /**
     * Remove a metadata listener
     *
     * @param listener Listener to remove
     */
    void removeMetadataListener(MetadataListener listener);
}

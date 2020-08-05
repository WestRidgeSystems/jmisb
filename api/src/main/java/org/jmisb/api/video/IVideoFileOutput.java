package org.jmisb.api.video;

import java.io.IOException;

/** Interface for writing video/metadata to a file. */
public interface IVideoFileOutput extends AutoCloseable {
    /**
     * Open a new file for writing.
     *
     * @param filename The file name
     * @throws IOException if the file could not be opened
     */
    void open(String filename) throws IOException;

    /**
     * Check if a file is open.
     *
     * @return True if a file is open
     */
    boolean isOpen();

    /**
     * Close the file.
     *
     * @throws IOException if an error occurs while closing the file
     */
    @Override
    void close() throws IOException;

    /**
     * Append a {@link VideoFrame} to the file.
     *
     * @param frame The video frame to add
     * @throws IllegalArgumentException if the input frame is invalid
     * @throws IOException if the file could not be written
     */
    void addVideoFrame(VideoFrame frame) throws IOException;

    /**
     * Append a {@link MetadataFrame} to the file.
     *
     * @param frame The metadata frame to add
     * @throws IOException if the file could not be written
     */
    void addMetadataFrame(MetadataFrame frame) throws IOException;
}

package org.jmisb.api.video;

import org.jmisb.api.common.Beta;

import java.io.IOException;

/**
 * Interface for writing video/metadata to a UDP/IP network stream
 */
@Beta
public interface IVideoStreamOutput extends AutoCloseable
{
    /**
     * Open a stream for writing
     * <p>
     * The required syntax for the URL is <code>udp://hostname:port</code>.
     *
     * @param url The URL to open
     * @throws IllegalArgumentException if the URL format is invalid or protocol is unsupported
     * @throws IOException if the stream could not be opened
     */
    void open(String url) throws IOException;

    /**
     * Check if the stream is open
     *
     * @return True if the stream is open
     */
    boolean isOpen();

    /**
     * Close the stream
     *
     * @throws IOException if an error occurs while closing the stream
     */
    @Override
    void close() throws IOException;

    /**
     * Queue a {@link VideoFrame} for output
     *
     * @param frame The video frame to send
     * @throws IllegalArgumentException if the input frame is invalid
     * @throws IOException if the stream could not be written
     */
    void queueVideoFrame(VideoFrame frame) throws IOException;

    /**
     * Queue a {@link MetadataFrame} for output
     *
     * @param frame The metadata frame to send
     * @throws IOException if the stream could not be written
     */
    void queueMetadataFrame(MetadataFrame frame) throws IOException;
}

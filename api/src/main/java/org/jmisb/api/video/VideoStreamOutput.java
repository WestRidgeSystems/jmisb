package org.jmisb.api.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Manages an outgoing video stream
 */
public class VideoStreamOutput
{
    private static Logger logger = LoggerFactory.getLogger(VideoStreamOutput.class);
    private final String url;
    private final VideoOutputOptions options;

    /**
     * Open an output stream
     * @param url The video stream URL
     * @param options Stream options
     */
    public VideoStreamOutput(String url, VideoOutputOptions options)
    {
        this.url = url;
        this.options = options;
    }

    /**
     * Start streaming
     */
    public void start()
    {

    }

    /**
     * Stop streaming
     */
    public void stop()
    {

    }

    /**
     * Buffer a video frame for output
     * @param frame
     * @throws IOException
     */
    public void addVideoFrame(VideoFrame frame) throws IOException
    {

    }

    /**
     * Buffer a metadata frame for output
     * @param metadataFrame
     * @throws IOException
     */
    public void addMetadataFrame(MetadataFrame metadataFrame) throws IOException
    {

    }
}

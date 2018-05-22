package org.jmisb.api.video;

/**
 * Interface for reading video/metadata from a network stream
 */
public interface IVideoStreamInput extends IVideoInput
{
    /**
     * Get the input stream options
     *
     * @return The options
     */
    VideoStreamInputOptions getOptions();
}
